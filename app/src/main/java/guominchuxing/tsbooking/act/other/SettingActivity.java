package guominchuxing.tsbooking.act.other;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;

/**
 * Created by admin on 2017/4/20.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_mobile)
    RelativeLayout rl_mobile;
    @BindView(R.id.rl_password)
    RelativeLayout rl_password;
    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initVIew();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initVIew() {
        tv_title.setText("设置");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);
        rl_mobile.setOnClickListener(this);
        rl_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rl_mobile:
                 intent(ResetMobileActivity.class);
                break;
            case R.id.rl_password:
                 intent(ForgetActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }
}
