package guominchuxing.tsbooking.act.other;

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
import rx.functions.Action1;

/**
 * 重置密码
 * Created by admin on 2017/4/28.
 */

public class ForgetActivity extends BaseActivity implements TextWatcher{
    private static final String TAG = "ForgetActivity";
    
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn)
    TextView btn;
    @BindView(R.id.et_bindMobile)
    ClearEditText et_bindMobile;
    @BindView(R.id.et_code)
    ClearEditText et_code;
    @BindView(R.id.btn_code)
    TextView btn_code;
    private Subscription subscription;
    @Override
    protected int getLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mobile = SharefereUtil.getValue(this, "mobile", "");
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
        btn.setBackgroundResource(R.drawable.btn_radius_grey);
        et_bindMobile.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
        btn.setOnClickListener(this);
        btn_code.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_bindMobile.setText(mobile);
//        et_bindMobile.setFocusable(false);
    }

    private String mobile,verificationCode;
    @Override
    public void onClick(View v) {
        super.onClick(v);
       mobile = et_bindMobile.getText().toString().trim();
       verificationCode = et_code.getText().toString().trim();


        switch (v.getId()){
            case R.id.btn_code:
                    getSmsCode(mobile);
                    CountDownButtonHelper helper = new CountDownButtonHelper(btn_code,
                            "获取验证码", 60, 1);
                    helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                        @Override
                        public void finish() {
//                            ToastShow("倒计时结束",1);
                        }
                    });
                    helper.start();
                break;
            case R.id.btn:
                if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(verificationCode)){
                    getCheckCode(verificationCode,mobile);
                }else {
                    ToastShow("输入验证码",0);
                }

                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }
    /**
     * 获取验证码
     * @return
     */
    private void getSmsCode(String mobile) {
       Log.e(TAG,mobile);
       String token = SharefereUtil.getValue(this,"token","");
//       Log.e(TAG,"old==>"+token);
       subscription = ApiManager.getDefaut().getSmsDo(token,mobile,Const.TYPE_3,Const.APP)
                      .compose(RxUtil.<ResetCodeBean>rxSchedulerHelper())
                      .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
                        Log.e(TAG,resetCodeBean.toString());
                        try {
                            int code = resetCodeBean.getCode();
                            String msg = resetCodeBean.getMsg();
                            if (200 == code){
                             String token = resetCodeBean.getData();
                             SharefereUtil.putValue(ForgetActivity.this,"token",token);
                            }else {
                                ToastShow(msg,1);
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
     * 校验
     * @param code
     * @param m
     */
    private void getCheckCode(String code, String m) {
       String token = SharefereUtil.getValue(this,"token","");
//        Log.e(TAG,"new==>"+token);
       subscription = ApiManager.getDefaut().getCheckCode(token,m,code,Const.TYPE_3,Const.USER_TYPE,Const.APP)
                      .compose(RxUtil.<ResetCodeBean>rxSchedulerHelper())
                      .subscribe(new Action1<ResetCodeBean>() {
                       @Override
                        public void call(ResetCodeBean resetCodeBean) {
                        Log.e(TAG,resetCodeBean.toString());
                        try {
                            int code = resetCodeBean.getCode();
                            String data = resetCodeBean.getData();
                            String msg = resetCodeBean.getMsg();
                            if (data.equals("true")){
                                SharefereUtil.putValue(ForgetActivity.this,"mobile",mobile);
                                SharefereUtil.putValue(ForgetActivity.this,"verificationCode",verificationCode);
                                intent(ResetActivity.class);
                            }else if (data.equals("false")){
                                ToastShow("验证码有误,重新获取",0);
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

                    }
                });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.unsubscribe();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (et_bindMobile.length() != 0 & et_code.length() !=0){
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
