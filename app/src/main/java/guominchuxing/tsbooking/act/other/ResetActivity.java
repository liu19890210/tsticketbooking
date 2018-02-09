package guominchuxing.tsbooking.act.other;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import guominchuxing.tsbooking.util.MyTextUtils;
import guominchuxing.tsbooking.util.SharefereUtil;
import guominchuxing.tsbooking.view.ClearEditText;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 重置密码
 * Created by admin on 2017/4/28.
 */

public class ResetActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = "ResetActivity";
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.et_newPassword)
    ClearEditText et_newPassword;
    @BindView(R.id.et_one)
    ClearEditText et_one;
    String token, mobile,verificationCode;
    @Override
    protected int getLayout() {
        return R.layout.activity_reset;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            token = SharefereUtil.getValue(this,"token","");
            mobile = SharefereUtil.getValue(this,"mobile","");
            verificationCode = SharefereUtil.getValue(this,"verificationCode","");
            initview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initview() {
        tv_title.setText("重置密码");
        iv_back.setBackgroundResource(R.mipmap.iv_back);
        iv_back.setOnClickListener(this);
        btn.setText("下一步");
        btn.setTextColor(Color.parseColor("#ffffff"));
        et_newPassword.addTextChangedListener(this);
        et_one.addTextChangedListener(this);
        btn.setOnClickListener(this);

    }
    private String newPassword;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        newPassword = et_one.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn:
                if (!isvalidate())
                    break;
               getPassword(newPassword);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }

    private void getPassword(String newPassword) {

        ApiManager.getDefaut().getPassword(token,mobile, newPassword, Const.METHOD_2,verificationCode,Const.USER_TYPE,Const.APP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
//                        Log.e(TAG,resetCodeBean.toString()+"++++");
                        try {
                            int code = resetCodeBean.getCode();
                            String msg = resetCodeBean.getMsg();
                            if (200 == code){
                                intent(LoginActivity.class);
                                ToastShow("重置密码成功",0);
                                App.finishAllActivity();

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
//                        Log.e(TAG,throwable.getMessage());

                    }
                });


    }
    private boolean isvalidate(){
//        String newPassword = et_one.getText().toString().trim();
        String password = et_newPassword.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)){
            ToastShow("请输入密码",1);
            return false;
        }
        if (!password.equals(newPassword)){
            ToastShow("密码输入不一致",1);
            return false;
        }
        if (!MyTextUtils.isPassword(password) && !MyTextUtils.isPassword(newPassword)){
            ToastShow("密码不符合规则",0);
            return false;
        }
     return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (et_newPassword.length() !=0 & et_one.length() !=0){
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
}
