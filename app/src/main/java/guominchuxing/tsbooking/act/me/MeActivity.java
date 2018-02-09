package guominchuxing.tsbooking.act.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import guominchuxing.tsbooking.App;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.act.other.FeedbackActivity;
import guominchuxing.tsbooking.act.other.HelpActivity;
import guominchuxing.tsbooking.act.other.LoginActivity;
import guominchuxing.tsbooking.act.other.SettingActivity;
import guominchuxing.tsbooking.util.SharefereUtil;

/**
 * Created by admin on 2017/4/20.
 */

public class MeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_hotelName)
    TextView tv_hotelName;
    @BindView(R.id.tv_ID)
    TextView tv_ID;
    @BindView(R.id.tv_centre)
    TextView tv_centre;
    @BindView(R.id.rl_btn)
    RelativeLayout rl_btn;
//    @Bind(R.id.rl_notice)
//    RelativeLayout rl_notice;
    @BindView(R.id.rl_help)
    RelativeLayout rl_help;
    @BindView(R.id.rl_feedback)
    RelativeLayout rl_feedback;
    @BindView(R.id.rl_setting)
    RelativeLayout rl_setting;
    private String loginId,name,id;
    @Override
    protected int getLayout() {
        return R.layout.activity_me;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        loginId = SharefereUtil.getValue(this,"loginId","");
        name = SharefereUtil.getValue(this,"name","");
         initView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initView() {
        tv_title.setText("我的");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);
        rl_btn.setOnClickListener(this);
//        rl_notice.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        tv_centre.setText(R.string.str_outLogin);
        tv_centre.setTextColor(Color.parseColor("#2F98E9"));
        tv_hotelName.setText(name);
        tv_ID.setText("ID："+loginId);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
//            case R.id.rl_notice:
//                intent(NoticeActivity.class);
//                break;
            case R.id.rl_help:
                intent(HelpActivity.class);
                break;
            case R.id.rl_feedback:
                intent(FeedbackActivity.class);
                break;
            case R.id.rl_setting:
                intent(SettingActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_btn:
                OutLogin();
                break;
            default:
        }
    }

    /**
     * 退出登录
     */
    private void OutLogin() {
        final AlertDialog adb = new AlertDialog.Builder(this,R.style.DialogAlert)
        .setMessage("退出当前账号")
        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent(LoginActivity.class);
                SharefereUtil.putValue(MeActivity.this,"password",null);
                App.finishAllActivity();
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .create();
        adb.show();
        CustomLayoutParams(adb);
    }
}
