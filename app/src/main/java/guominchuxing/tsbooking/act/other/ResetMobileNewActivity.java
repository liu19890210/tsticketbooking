package guominchuxing.tsbooking.act.other;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import guominchuxing.tsbooking.App;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.api.ApiManager;
import guominchuxing.tsbooking.bean.ResetCodeBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.util.CountDownButtonHelper;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.ClearEditText;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/4/28.
 */

public class ResetMobileNewActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = "ResetMobileNewActivity";
    private String token;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.et_mobile)
    ClearEditText et_mobile;
    @BindView(R.id.et_code)
    ClearEditText et_code;
    @BindView(R.id.btn_codeGet)
    TextView btn_codeGet;
    private Subscription subscription;
    private String oldCode;
    @Override
    protected int getLayout() {
        return R.layout.activity_reset_mobile_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initview() {
        oldCode = this.getIntent().getExtras().getString("oldCode");
        tv_title.setText("更改绑定手机号");
        et_mobile.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
        iv_back.setBackgroundResource(R.mipmap.iv_back);
        iv_back.setOnClickListener(this);
        btn.setText("完成");
        btn.setTextColor(Color.parseColor("#ffffff"));
        btn.setBackgroundResource(R.drawable.btn_radius_grey);
        btn.setOnClickListener(this);
        btn_codeGet.setText(R.string.str_get_code);
        btn_codeGet.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        String mobile = et_mobile.getText().toString().trim();
        String codeText = et_code.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_codeGet:
                if (TextUtils.isEmpty(mobile)){
                    ToastShow("输入手机号",0);
                }else{

                getExistsDo(mobile);

                }
                break;
            case R.id.btn:
                if (TextUtils.isEmpty(mobile) && TextUtils.isEmpty(codeText)){
                   ToastShow("输入验证码",1);
                }else{
                getChangeDo(codeText,mobile);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    /**
     * 判断手机号存在
     * @param mobile
     */
    private void getExistsDo(final String mobile) {
        final String token = SharefereUtil.getValue(this, "token", "");
        subscription = ApiManager.getDefaut().getExistsDo(mobile,Const.TYPE_3,token,Const.APP)
                .compose(RxUtil.<ResetCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
//                        Log.e(TAG,resetCodeBean.toString()+"存在");
                        try {
                        int code = resetCodeBean.getCode();
                        String msg = resetCodeBean.getMsg();
                        String data = resetCodeBean.getData();
                            if (data.equals("false")){
                                getSmsDo(mobile ,token);
                            }else {
                                ToastShow("该手机号已注册，请更换其他手机号码",1);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
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
     * 倒计时
     * @param mobile
     */
    private void getSmsDo(String mobile, String token) {
        GetHttpSmsCode(mobile,token);
        CountDownButtonHelper helper = new CountDownButtonHelper(btn_codeGet,
                "获取验证码", 60, 1);
        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

            @Override
            public void finish() {
//                        ToastShow("倒计时结束",1);
////                        Toast.makeText(MainActivity.this, "倒计时结束",
////                                Toast.LENGTH_SHORT).show();
            }
        });
        helper.start();

    }

    /**
     * f发送验证码
     * @param mobile
     * @param token
     */
    private void GetHttpSmsCode(String mobile,String token) {
//        String token = SharefereUtil.getValue(this, "token", "");
//        Log.e(TAG,mobile +"=="+ token +"旧");
        subscription = ApiManager.getDefaut().getSmsDo(token,mobile,Const.TYPE_7,Const.APP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
                        Log.e(TAG,resetCodeBean.toString());
                        try {
                            int code = resetCodeBean.getCode();
                            String msg = resetCodeBean.getMsg();
                            if (200 == code){
                            String token = resetCodeBean.getData();
                            SharefereUtil.putValue(ResetMobileNewActivity.this,"token",token);
                            }else {
                                ToastShow(msg,0);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,throwable.getMessage());
//                        ToastShow(throwable.getMessage(),0);

                    }
                });


    }

    /**
     * 校验
     * @param code
     * @param m
     */
    private void getChangeDo(String code, String m) {
        showLoadDialog("加载中...");
        String token = SharefereUtil.getValue(this, "token", "");
//        Log.e(TAG,code +"=="+ token +"new==> " +"oldCode ===>"+oldCode);
        subscription = ApiManager.getDefaut().getChangeDo(oldCode,m,code,token,Const.APP)
                .compose(RxUtil.<ResetCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
                        dismissLoadDialog();
//                        Log.e(TAG,resetCodeBean.toString());
                        try {
                            int code = resetCodeBean.getCode();
                            String msg = resetCodeBean.getMsg();
                            if (200 == code){
                              outLogin();
                            }else {
                                ToastShow(msg,0);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dismissLoadDialog();
                        Log.e(TAG,throwable.getMessage());

                    }
                });


    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (et_mobile.length() != 0 & et_code.length() !=0){
            btn.setBackgroundResource(R.drawable.btn_radius_bule);
            btn.setEnabled(true);
        }else {
            btn.setBackgroundResource(R.drawable.btn_radius_grey);
            btn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 退出登录
     */
    private void outLogin() {
        final AlertDialog adb = new AlertDialog.Builder(this,R.style.DialogAlert)
                .setMessage("手机号更换成功，登录密码不做改变")
                .setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent(LoginActivity.class);
                        App.finishAllActivity();
                    }
                })
                .create();
        adb.show();
        CustomLayoutParams(adb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.unsubscribe();
        }
    }
}
