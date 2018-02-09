package guominchuxing.tsbooking.act.voucher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.act.other.Housingstock2Activity;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.CheckBean;
import guominchuxing.tsbooking.bean.CheckNumBean;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.scanner.CaptureActivity;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.ContentWithSpaceEditText;
import guominchuxing.tsbooking.dialog.LogoutDialog;
import guominchuxing.tsbooking.view.XNumberKeyboardView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/18.
 */

public class VoucherActivity extends BaseActivity implements XNumberKeyboardView.IOnKeyboardListener{
    private static final String TAG = "VoucherActivity";

    private String voucher,token;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_right)
    RelativeLayout rl_right;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.et_voucher)
    ContentWithSpaceEditText et_voucher;
    @BindView(R.id.view_keyboard)
    XNumberKeyboardView view_keyboard;
    @BindView(R.id.rl_btn)
    RelativeLayout rl_btn;
    private Subscription subscription;
    // 语音合成对象
    private SpeechSynthesizer mTts;
    @Override
    protected int getLayout() {
        return R.layout.activity_voucher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      try {
        token = SharefereUtil.getValue(this, "token", "");
//          Log.e(TAG,token);
          // 初始化合成对象
          mTts = SpeechSynthesizer.createSynthesizer(this, null);
        initView();

      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private void initView() {
        iv_back.setImageResource(R.mipmap.back);
        tv_title.setText("输入凭证号");
        rl_back.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        view_keyboard.setIOnKeyboardListener(this);

        rl_btn.setOnClickListener(this);
        rl_right.setVisibility(View.VISIBLE);
        iv_right.setBackgroundResource(R.mipmap.vo_scan);
        et_voucher.setInputType(InputType.TYPE_NULL);
        et_voucher.setCursorVisible(true);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.rl_btn:
                voucher = et_voucher.getText().toString().trim();
                if (!TextUtils.isEmpty(voucher)){
                    getHttpCheckData(voucher);
                }else {
                    ToastShow("请输入凭证号",0);
                }
                break;
            case R.id.rl_right:
                intent(CaptureActivity.class);
                finish();
                break;
            case R.id.rl_back:
                 finish();
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
                    Log.i(TAG,resultBean.toString());
                     int code = resultBean.getCode();
                     String msg = resultBean.getMsg();
                     data = resultBean.getData();
                    if (200 == code) {
                        String mainName = data.getMainName();
                        String guest = data.getGuest();
                        String mobile =  data.getMobile();

                        message = mainName +
                                " "+ guest +"  " + mobile;
                        getHttpcheck(num);
                    }else if (msg.equals("未登录")){
                        LogoutDialog dialog = new LogoutDialog(VoucherActivity.this);
                        dialog.getDialog(msg);
                    }else {
                        ToastShow(msg,0);
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    ToastShow("凭证号有误",0);
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
//                        Log.e(TAG,"msg==>"+msg);
                        if (code == 200){
                            String title = "验证成功";
                            startSpeech(title);
                            int iconId = R.mipmap.ic_alert;
                            getAlert(title,iconId,message);
                        }
                        else {
                            String title = "验证失败";
                            getAlertNo(title,R.mipmap.ic_alert_no,msg);
                            startSpeech(title);
                            dismissLoadDialog();
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
                        Intent intent = new Intent(VoucherActivity.this,Housingstock2Activity.class);
                        intent.putExtra("data", data);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("继续验证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
    @Override
    public void onInsertKeyEvent(String text) {
        et_voucher.append(text);
    }

    @Override
    public void onDeleteKeyEvent() {
        int start = et_voucher.length() - 1;
        if (start >= 0) {
            et_voucher.getText().delete(start, start + 1);
        }
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
    @Override
    protected void onDestroy() {
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
}
