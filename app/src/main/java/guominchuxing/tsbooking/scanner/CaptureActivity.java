package guominchuxing.tsbooking.scanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.result.ResultParser;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.act.other.Housingstock2Activity;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.CheckBean;
import guominchuxing.tsbooking.bean.CheckNumBean;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.scanner.camera.CameraManager;
import guominchuxing.tsbooking.scanner.common.BitmapUtils;
import guominchuxing.tsbooking.scanner.decode.BitmapDecoder;
import guominchuxing.tsbooking.scanner.decode.CaptureActivityHandler;
import guominchuxing.tsbooking.scanner.view.ViewfinderView;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.dialog.LogoutDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscription;
import rx.functions.Action1;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 * <p>
 * 此Activity所做的事： 1.开启camera，在后台独立线程中完成扫描任务；
 * 2.绘制了一个扫描区（viewfinder）来帮助用户将条码置于其中以准确扫描； 3.扫描成功后会将扫描结果展示在界面上。
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback, EasyPermissions.PermissionCallbacks {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final int REQUEST_CODE = 100;

    private static final int PARSE_BARCODE_FAIL = 300;
    private static final int PARSE_BARCODE_SUC = 200;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 是否有预览
     */
    private boolean hasSurface;

    /**
     * 活动监控器。如果手机没有连接电源线，那么当相机开启后如果一直处于不被使用状态则该服务会将当前activity关闭。
     * 活动监控器全程监控扫描活跃状态，与CaptureActivity生命周期相同.每一次扫描过后都会重置该监控，即重新倒计时。
     */
    private InactivityTimer inactivityTimer;

    /**
     * 声音震动管理器。如果扫描成功后可以播放一段音频，也可以震动提醒，可以通过配置来决定扫描成功后的行为。
     */
    private BeepManager beepManager;

    /**
     * 闪光灯调节器。自动检测环境光线强弱并决定是否开启闪光灯
     */
    private AmbientLightManager ambientLightManager;

    private CameraManager cameraManager;
    /**
     * 扫描区域
     */
    private ViewfinderView viewfinderView;

    private CaptureActivityHandler handler;

    private Result lastResult;

    private boolean isFlashlightOpen;

    private ImageView capture_flashlight;
    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 编码类型，该参数告诉扫描器采用何种编码方式解码，即EAN-13，QR
     * Code等等 对应于DecodeHintType.POSSIBLE_FORMATS类型
     * 参考DecodeThread构造函数中如下代码：hints.put(DecodeHintType.POSSIBLE_FORMATS,
     * decodeFormats);
     */
    private Collection<BarcodeFormat> decodeFormats;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 该参数最终会传入MultiFormatReader，
     * 上面的decodeFormats和characterSet最终会先加入到decodeHints中 最终被设置到MultiFormatReader中
     * 参考DecodeHandler构造器中如下代码：multiFormatReader.setHints(hints);
     */
    private Map<DecodeHintType, ?> decodeHints;

    /**
     * 【辅助解码的参数(用作MultiFormatReader的参数)】 字符集，告诉扫描器该以何种字符集进行解码
     * 对应于DecodeHintType.CHARACTER_SET类型
     * 参考DecodeThread构造器如下代码：hints.put(DecodeHintType.CHARACTER_SET,
     * characterSet);
     */
    private String characterSet;

    private Result savedResultToShow;

    private IntentSource source;
    private TextView tv_title;
    private String token;
    // 语音合成对象
    private SpeechSynthesizer mTts;
//    // 默认发音人
    private String voicer = "xiaoyan";
    private Subscription subscription;
    /**
     * 图片的路径
     */
    private String photoPath;

    private Handler mHandler = new MyHandler(this);

    @Override
    protected int getLayout() {
        return R.layout.capture;
    }

    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 * 2000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
                }

                break;

        }
        // Forward results to EasyPermissions

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @AfterPermissionGranted(Const.REQUEST_CAMERA_PERM)
    public void cameraTask() {

//        ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要打开照相权限",
                    Const.REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);


        }

    }
    @AfterPermissionGranted(Const.WRITE_EXTERNAL_STORAGE)
    private void openWriteStorage(){

        if (EasyPermissions.hasPermissions(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {}else {
            EasyPermissions.requestPermissions(this, "需要打开文件权限",
                    Const.REQUEST_CAMERA_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }
    private void OpenVibrate(){
        if (EasyPermissions.hasPermissions(this,Manifest.permission.VIBRATE))
        {}else {
            EasyPermissions.requestPermissions(this, "需要打开震动权限",
                    Const.VIBRATE, Manifest.permission.VIBRATE);
        }

    }
    static class MyHandler extends Handler {

        private WeakReference<Activity> activityReference;

        public MyHandler(Activity activity) {
            activityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PARSE_BARCODE_SUC: // 解析图片成功
//                    Toast.makeText(activityReference.get(),
//                            "解析成功，结果为：" + msg.obj, Toast.LENGTH_SHORT).show();

                    break;

                case PARSE_BARCODE_FAIL:// 解析图片失败

                    Toast.makeText(activityReference.get(), "解析图片失败",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            super.handleMessage(msg);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        token = SharefereUtil.getValue(this,"token","");

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, null);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//		setContentView(R.layout.capture);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);

        // 监听图片识别按钮
//
        findViewById(R.id.iv_back).setOnClickListener(this);

        findViewById(R.id.capture_scan_photo).setOnClickListener(this);

        capture_flashlight = (ImageView) findViewById(R.id.capture_flashlight);
        capture_flashlight.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.

        // 相机初始化的动作需要开启相机并测量屏幕大小，这些操作
        // 不建议放到onCreate中，因为如果在onCreate中加上首次启动展示帮助信息的代码的 话，
        // 会导致扫描窗口的尺寸计算有误的bug
        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.capture_viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        lastResult = null;

        // 摄像头预览功能必须借助SurfaceView，因此也需要在一开始对其进行初始化
        // 如果需要了解SurfaceView的原理
        // 参考:http://blog.csdn.net/luoshengyang/article/details/8661317
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view); // 预览
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);

        } else {
            // 防止sdk8的设备初始化预览异常
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
        }

        // 加载声音配置，其实在BeemManager的构造器中也会调用该方法，即在onCreate的时候会调用一次
        beepManager.updatePrefs();

        // 启动闪光灯调节器
        ambientLightManager.start(cameraManager);

        // 恢复活动监控器
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }


    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();

        // 关闭摄像头
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (subscription != null){
            subscription.unsubscribe();
        }
        if( null != mTts ){
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if ((source == IntentSource.NONE) && lastResult != null) { // 重新进行扫描
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.setTorch(true);
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.setTorch(false);
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            final ProgressDialog progressDialog;
            switch (requestCode) {
                case REQUEST_CODE:

                    // 获取选中图片的路径
                    Cursor cursor = getContentResolver().query(
                            intent.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photoPath = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();

                    showLoadDialog("正在扫描...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Bitmap img = BitmapUtils
                                        .getCompressedBitmap(photoPath);
//                                Log.e(TAG,img+"===>img"+photoPath+"===>photoPath");
                                BitmapDecoder decoder = new BitmapDecoder(
                                        CaptureActivity.this);
                                Result result = decoder.getRawResult(img);
//                                Log.e(TAG,result.toString()+"==》result");
//                                Log.i(TAG, "获取图片: path=" + photoPath);

                                if (result != null) {
                                    //
                                     getHttpCheckData(result.toString());
                                    //
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_SUC;
                                    m.obj = ResultParser.parseResult(result)
                                            .toString();
                                    mHandler.sendMessage(m);
                                } else {
                                    Log.i(TAG, "解析失败: path=" + photoPath);
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_FAIL;
                                    mHandler.sendMessage(m);
                                }

//							progressDialog.dismiss();
                                dismissLoadDialog();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    break;

            }
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        /*hasSurface = false;*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {

        // 重新计时
        inactivityTimer.onActivity();

        lastResult = rawResult;

        // 把图片画到扫描框
		viewfinderView.drawResultBitmap(barcode);

        beepManager.playBeepSoundAndVibrate();
        String result = ResultParser.parseResult(rawResult).toString();
        //
        getHttpCheckData(result);
//        Toast.makeText(this,
//                "识别结果:" + ResultParser.parseResult(rawResult).toString(),
//                Toast.LENGTH_SHORT).show();

    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }

        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 向CaptureActivityHandler中发送消息，并展示扫描到的图像
     *
     * @param bitmap
     * @param result
     */
    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog ad = new AlertDialog.Builder(this,R.style.DialogAlert)
                .setTitle("提示")
                .setCancelable(true)
                .setMessage("照相权限被禁止，无法使用该功能，必须打开")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cameraTask();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         finish();
                    }
                })
                .create();
        ad.show();
        CustomLayoutParams(ad);


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.app_name));
//        builder.setMessage(getString(R.string.msg_camera_framework_bug));
//        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
//        builder.setNegativeButton("取消", new FinishListener(this));
////        builder.setOnCancelListener(new FinishListener(this));
//        builder.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_scan_photo: // 图片识别
                // 打开手机中的相册
                openWriteStorage();

                Intent innerIntent = new Intent(Intent.ACTION_PICK);
                innerIntent.setType("image/*");
                Intent wrapperIntent = Intent.createChooser(innerIntent,
                        "选择二维码图片");
                this.startActivityForResult(wrapperIntent, REQUEST_CODE);
                break;

            case R.id.capture_flashlight:
                if (isFlashlightOpen) {
                    cameraManager.setTorch(false); // 关闭闪光灯
                    isFlashlightOpen = false;
                    capture_flashlight.setImageResource(R.mipmap.light_no);

                } else {
                    cameraManager.setTorch(true); // 打开闪光灯
                    isFlashlightOpen = true;
                    capture_flashlight.setImageResource(R.mipmap.light);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }

    }
    /**
     *输入凭证号检验
     * @param voucher
     */
    private String message;
    private CheckNumBean data;
    private void getHttpCheckData(String voucher) {
        final String num = voucher.replace(" ","");
//        Log.e(TAG,"num++++"+num);
        subscription = ApiManager.getDefaut().getCheckNum(num,token,Const.APP,Const.METHOD)
                .compose(RxUtil.<ResultBean<CheckNumBean>>rxSchedulerHelper())
                .subscribe(new Action1<ResultBean<CheckNumBean>>() {
                    @Override
                    public void call(ResultBean<CheckNumBean> resultBean) {
//                   Log.e(TAG,"num==>"+checkNumBean.toString());
                        int code = resultBean.getCode();
                        String msg = resultBean.getMsg();
                        if (200 == code) {
                            String mainName = resultBean.getData().getMainName();
                            String guest = resultBean.getData().getGuest();
                            String mobile = resultBean.getData().getMobile();
                            message = mainName +
                                    " "+ guest +"  " + mobile;
                            getHttpcheck(num);
                        }else if (msg.equals("未登录")){
                            LogoutDialog dialog = new LogoutDialog(CaptureActivity.this);
                            dialog.getDialog(msg);
                        }else {
                            ToastShow(msg,0);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        ToastShow("凭证号有误",0);
                        startSpeech("凭证号有误");

                        restartPreviewAfterDelay(1500L);
                        Log.e(TAG,throwable.getMessage());
                    }
                });

    }

    /**
     * 执行检票
     * @param num
     */
    private void getHttpcheck(String num) {
        subscription = ApiManager.getDefaut().getCheck(token,num, Const.APP)
                .compose(RxUtil.<CheckBean>rxSchedulerHelper())
                .subscribe(new Action1<CheckBean>() {
                    @Override
                    public void call(CheckBean checkBean) {
                        Log.e(TAG,checkBean.toString());
                        int code = checkBean.getCode();
                        String msg = checkBean.getMsg();
                        if (code == 200){
                            String title = "验证成功";
                            startSpeech(title);
                            int iconId = R.mipmap.ic_alert;
                            getAlert(title,iconId,message);

                        }
                        else {
                            String title = "验证失败";
                            startSpeech("验证失败");
                            getAlertNo(title,R.mipmap.ic_alert_no,msg);
                            dismissLoadDialog();
//

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,throwable.getMessage());
                    }
                });
    }

    /**
     * 成功提示
     * @param title
     * @param iconId
     * @param message
     */
    private void getAlert(String title,int iconId,String message) {
        AlertDialog ad = new AlertDialog.Builder(this,R.style.DialogAlert)
                .setIcon(iconId)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("查看详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CaptureActivity.this,Housingstock2Activity.class);
                        intent.putExtra("data", data);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("继续验证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartPreviewAfterDelay(500L);
                    }
                })
                .create();
        ad.show();
        CustomLayoutParams(ad);
    }

    /**
     * 失败提示
     * @param title
     * @param iconId
     * @param message
     */
    private void getAlertNo(String title,int iconId,String message) {
        AlertDialog ad = new AlertDialog.Builder(this,R.style.DialogAlert)
                .setIcon(iconId)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .create();
        ad.show();
        CustomLayoutParams(ad);
    }
    /**
     //     * 语音监听
     //     */
    SynthesizerListener mTtsInitListener = new SynthesizerListener(){
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    /**
     *
     * @param Speech
     */
    private void startSpeech(String Speech){
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "55");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "30");//设置音量，范围0~100
        mTts.startSpeaking(Speech,mTtsInitListener);

    }
}
