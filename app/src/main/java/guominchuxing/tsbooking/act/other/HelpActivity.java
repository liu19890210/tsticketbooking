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

public class HelpActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_life)
    TextView tv_left;
    @BindView(R.id.iv_go)
    ImageView iv_go;
    @BindView(R.id.rl_btn)
    RelativeLayout rl_btn;
    @Override
    protected int getLayout() {
        return R.layout.activity_help;
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
        tv_title.setText("帮助");
        iv_back.setImageResource(R.mipmap.back);
        iv_back.setOnClickListener(this);
        rl_btn.setOnClickListener(this);
        iv_go.setImageResource(R.mipmap.go);
        tv_left.setText("如何验证凭证号");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            default:
        }
    }
}
