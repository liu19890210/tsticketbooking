package guominchuxing.tsbooking.act.other;

import android.content.Intent;
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
 * 更改绑定手机号
 * Created by admin on 2017/4/28.
 */

public class ResetMobileActivity extends BaseActivity implements TextWatcher {
    private static final String TAG = "ResetMobileActivity";
    private String token,mobile;
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
    @Override
    protected int getLayout() {
        return R.layout.activity_reset_mobile;
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
        tv_title.setText("更改绑定手机号");
        et_mobile.addTextChangedListener(this);
        et_code.addTextChangedListener(this);
        iv_back.setBackgroundResource(R.mipmap.iv_back);
        iv_back.setOnClickListener(this);
        btn.setText("下一步");
        btn.setTextColor(Color.parseColor("#ffffff"));
        btn.setBackgroundResource(R.drawable.btn_radius_grey);
        btn.setOnClickListener(this);
        btn_codeGet.setText(R.string.str_get_code);
        btn_codeGet.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_mobile.setText(mobile);
        et_mobile.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
//        String mobile = et_mobile.getText().toString().trim();
        String codeText = et_code.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_codeGet:
//                if (TextUtils.isEmpty(mobile)){
//                    ToastShow("输入手机号",0);
//                }else {
                    GetHttpSmsCode(mobile);
                    CountDownButtonHelper helper = new CountDownButtonHelper(btn_codeGet,
                            "获取验证码", 60, 1);
                    helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                        @Override
                        public void finish() {

                        }
                    });
                    helper.start();
//                }
                break;
            case R.id.btn:
                if (TextUtils.isEmpty(mobile) && TextUtils.isEmpty(codeText)){
                    ToastShow("输入验证码",1);
                }else {
                    getCheckCode(codeText,mobile);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }


    /**
     *
     * @param mobile
     */
    private void GetHttpSmsCode(String mobile) {
      String token = SharefereUtil.getValue(this, "token", "");
      subscription = ApiManager.getDefaut().getSmsDo(token,mobile,Const.TYPE_6,Const.APP)
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
                              SharefereUtil.putValue(ResetMobileActivity.this,"token",token);
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

    /**
     * 校验
     * @param oldCode
     * @param m
     */
    private void getCheckCode(final String oldCode, String m) {
        showLoadDialog("加载中...");
        String token = SharefereUtil.getValue(this, "token", "");
//        Log.e(TAG,"new ==>"+ token);
        subscription = ApiManager.getDefaut().getCheckCode(token,m,oldCode,Const.TYPE_6,Const.USER,Const.APP)
                .compose(RxUtil.<ResetCodeBean>rxSchedulerHelper())
                .subscribe(new Action1<ResetCodeBean>() {
                    @Override
                    public void call(ResetCodeBean resetCodeBean) {
                        dismissLoadDialog();
                        Log.e(TAG,"校验"+resetCodeBean.toString());
                        try {
                            int code = resetCodeBean.getCode();
                            String msg = resetCodeBean.getMsg();
                            String data = resetCodeBean.getData();
                            if (data.equals("true")){
                                Intent intent = new Intent(ResetMobileActivity.this,ResetMobileNewActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("oldCode",oldCode);
                                intent.putExtras(bundle);
                                startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null){
            subscription.unsubscribe();
        }
    }
}
