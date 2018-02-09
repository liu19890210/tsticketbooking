package guominchuxing.tsbooking.act;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import guominchuxing.tsbooking.App;
import guominchuxing.tsbooking.R;

/**
 * Created by admin on 2017/4/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements  View.OnClickListener{
    /**
     * set layout of this activity
     * @return the id of layout
     */
    private View errorView ,contentView;
    private TextView error_tv ;
    private ImageView error_iv ;
    private RotateAnimation animation ;

    private View loginView ;
    private TextView loginTv ;
    protected abstract int getLayout();
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != 0){
            setContentView(getLayout());
        }
        App.getInstance().addActivity(this);
        ButterKnife.bind(this);
    }
    private void init(){
//        errorView = findViewById(R.id.errorView);
//        if (errorView!=null){
//            errorView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onReloadDataListener!=null){
//                        onReloadDataListener.request(true);
//                    }
//                }
//            });
//        }
//        error_iv = (ImageView) findViewById(R.id.error_iv);
//        error_tv = (TextView) findViewById(R.id.error_tv);
//        contentView = findViewById(R.id.contentView);
//        loginView = findViewById(R.id.loginView);
//        loginTv = (TextView) findViewById(R.id.login_tv);
    }

    /**
     * toast
     * @param str
     * @param i
     */
    public void ToastShow(String str,int i){
        Toast.makeText(this,str,i).show();
    }

    /**
     * 跳转activity
     * @param cla
     */
    public void intent(Class cla){
        Intent i = new Intent(this,cla);
        startActivity(i);
    }


    ProgressDialog loadDialog;

    protected void showLoadDialog(String msg) {
        if (null == loadDialog) {
            loadDialog = new ProgressDialog(this, R.style.new_circle_progress);
        }
        loadDialog.setMessage(msg);
        if (!loadDialog.isShowing())
            loadDialog.show();
    }

    protected void dismissLoadDialog() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (null != loadDialog && loadDialog.isShowing()){
                    loadDialog.dismiss();

                }
            }
        }).start();

    }
    public void CustomLayoutParams(AlertDialog adb){
        WindowManager.LayoutParams params  = adb.getWindow().getAttributes();
        int width = adb.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        params.width = width*3/4;
        adb.getWindow().setAttributes(params);
    }

    /**
     * 设置透明
     *
     * @param i
     */
    public void setTransparentForWindow(int i) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(i);
            this.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 显示错误页面
     * @param message
     * @param resId
     */
    public void showErrorView(String message,int resId){
        init();
        if (errorView==null){
            return;
        }
        if (error_iv==null){
            return;
        }
        if (error_tv==null){
            return;
        }
        if (contentView==null){
            return;
        }
        if (contentView==null){
            return;
        }
        if (loginView!=null){
            loginView.setVisibility(View.GONE);
        }
        error_iv.setImageResource(resId);
        if (!TextUtils.isEmpty(message)){
            error_tv.setText(message);
        }else {
            error_tv.setText("数据加载失败！");
        }
        error_iv.setAnimation(null);
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }
    /**
     * 显示加载页面
     * @param tip
     * @param resId
     */
    public void showLoadingPage(String tip,int resId){
        init();
        if (errorView==null){
            return;
        }
        if (error_iv==null){
            return;
        }
        if (error_tv==null){
            return;
        }
        if (contentView==null){
            return;
        }
        if (contentView==null){
            return;
        }
        errorView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
        if (loginView!=null){
            loginView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(tip)){
            error_tv.setText(tip);
        }else {
            error_tv.setText("数据正在加载...");
        }
        error_iv.setImageResource(resId);
        /** 设置旋转动画 */
        if (animation==null){
            animation =new RotateAnimation(0f,359f, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1000);//设置动画持续时间
            /** 常用方法 */
            animation.setRepeatCount(Integer.MAX_VALUE);//设置重复次数
            animation.startNow();
        }
        error_iv.setAnimation(animation);

    }
    private OnReloadDataListener onReloadDataListener;
    public void setOnReloadDataListener(OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    public interface OnReloadDataListener{
        void request(boolean isRefresh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != loadDialog && loadDialog.isShowing()){
            loadDialog.dismiss();
        }
        App.finishActivity(this);
    }


}
