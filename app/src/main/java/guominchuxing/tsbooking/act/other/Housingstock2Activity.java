package guominchuxing.tsbooking.act.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;

import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.bean.CheckNumBean;
import guominchuxing.tsbooking.bean.ResultBean;
import guominchuxing.tsbooking.util.DateUtils;

/**
 * Created by admin on 2017/4/28.
 */

public class Housingstock2Activity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.tv_voucher)
    TextView tv_voucher;
    @BindView(R.id.tv_product)
    TextView tv_product;
    @BindView(R.id.tv_proof)
    TextView tv_proof;
    @BindView(R.id.tv_linkman)
    TextView tv_linkman;
    private CheckNumBean data;
    private Date date = new Date();
    @Override
    protected int getLayout() {
        return R.layout.activity_housingstock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
         data = (CheckNumBean) getIntent().getSerializableExtra("data");
        initview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initview() {
        tv_title.setText("票信息");
        iv_back.setImageResource(R.mipmap.iv_back);
        iv_back.setOnClickListener(this);
        tv_order.setText(data.getBillNo());
        tv_voucher.setText(data.getVoucherNo());
        tv_product.setText(data.getMainName()+"( "+data.getProductName()+" )");
        tv_linkman.setText(data.getGuest()+"( " +data.getMobile() +" )");
        tv_proof.setText(DateUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
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
