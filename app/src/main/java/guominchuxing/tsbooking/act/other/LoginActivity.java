package guominchuxing.tsbooking.act.other;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import guominchuxing.tsbooking.EHomeActivity;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.api.ApiManager;
//import guominchuxing.tbooking.api.LoginApi;
import guominchuxing.tsbooking.bean.LoginBean;
import guominchuxing.tsbooking.constant.Const;
import guominchuxing.tsbooking.util.RxUtil;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.ClearEditText;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by admin on 2017/4/18.
 * 登录
 */

public class LoginActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.btn_login)
    TextView btn_login;
    @BindView(R.id.btn_forget)
    TextView btn_forget;
//    @Bind(R.id.rl_hotel_join)
//    RelativeLayout rl_hotel_join;
    @BindView(R.id.et_passWord)
    ClearEditText et_password;
    @BindView(R.id.et_account)
    ClearEditText et_account;
    private ApiManager apiManager;
    private String account,password;
//    private LoginApi loginEntity;
    private Subscription subscription;
    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTransparentForWindow(Color.parseColor("#F2F2F2"));
        et_account.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        btn_login.setOnClickListener(this);
        btn_forget.setOnClickListener(this);
//        rl_hotel_join.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_login:
                if (!isvalidate())break;
                GetHttpLogin();
                break;
            case R.id.btn_forget:
                intent(ForgetActivity.class);
                break;
//            case R.id.rl_hotel_join:
//                intent(HotelJoinActivity.class);
//                break;
            default:
        }
    }

    private boolean isvalidate(){
        account = et_account.getText().toString().trim();
        password = et_password.getText().toString().trim();
        SharefereUtil.putValue(LoginActivity.this,"account",account);
        SharefereUtil.putValue(LoginActivity.this,"password",password);
        if (TextUtils.isEmpty(account)){
            ToastShow("请输入手机号或账号",1);
            return false;
        }else if (TextUtils.isEmpty(password)){
            ToastShow("请输入密码",1);
            return false;
        }
        return true;
    }

    /**
     * get网络请求
     * @param
     * @param
     */
    private void GetHttpLogin() {
     showLoadDialog("加载中...");
     subscription = ApiManager.getDefaut().getLogin(account,password, Const.USER,Const.APP, Const.METHOD,Const.ID_TYPE)
                .compose(RxUtil.<LoginBean>rxSchedulerHelper())
                .subscribe(new Action1<LoginBean>() {
                    @Override
                    public void call(LoginBean loginBean) {
                        dismissLoadDialog();
//                        Log.e(TAG,loginBean.toString());
                        int code = loginBean.getCode();
                        String msg = loginBean.getMsg();
                        if (code == 200){
                            LoginBean.LoginData loginData = loginBean.getData();
//                            Log.e(TAG,loginData.toString());
                            LoginBean.User user = loginData.getUser();

                            String token = loginData.getToken();
//                            Log.e(TAG,"user-->"+user);
//                            Log.e(TAG,user.getName()+user.getId()+user.getMobile());
                            String name = user.getName();
                            String id = user.getId();
                            String loginId = user.getLoginId();
                            String mobile = user.getMobile();
                            String ownerId = user.getOwnerId();
                            SharefereUtil.putValue(LoginActivity.this,"loginId",loginId);
                            SharefereUtil.putValue(LoginActivity.this,"ownerId",ownerId);
                            SharefereUtil.putValue(LoginActivity.this,"name",name);
                            SharefereUtil.putValue(LoginActivity.this,"mobile",mobile);
                            SharefereUtil.putValue(LoginActivity.this,"id",id);
                            SharefereUtil.putValue(LoginActivity.this,"token",token);
                            SharefereUtil.putValue(LoginActivity.this,"account",account);
                            SharefereUtil.putValue(LoginActivity.this,"password",password);
                            SharefereUtil.putValue(LoginActivity.this,false,"isFirst");
                            intent(EHomeActivity.class);
                            finish();
                        }else{
                            ToastShow(msg,1);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dismissLoadDialog();
                        if (throwable.getMessage().equals("Unable to resolve host \"api.guomintrip.com\": No address associated with hostname")) {
                            ToastShow("网络异常", 0);
                        }else if (throwable.getMessage().equals("HTTP 502 Bad Gateway")){
                            ToastShow(throwable.getMessage(),0);

                        }else {
                            ToastShow("账号或者密码有误",0);
                        }
                        Log.e(TAG, "错误："+throwable.getMessage());
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
       String account = SharefereUtil.getValue(this,"account","");
       String password = SharefereUtil.getValue(this,"password","");
       et_account.setText(account);
       et_password.setText(password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅
        if (subscription != null){
            subscription.unsubscribe();
//            Log.e(TAG,"onDestroy"+"取消订阅");
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (et_account.length() !=0 & et_password.length()!= 0){
            btn_login.setBackgroundResource(R.drawable.btn_radius_bule);
            btn_login.setEnabled(true);
        }else {
            btn_login.setBackgroundResource(R.drawable.btn_radius_grey);
            btn_login.setEnabled(false);
        }
    }


    @Override
    public void afterTextChanged(Editable editable) {

    }
}
