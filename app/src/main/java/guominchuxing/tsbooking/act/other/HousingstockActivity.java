package guominchuxing.tsbooking.act.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.bean.HistoryBeans;

/**
 * Created by admin on 2017/4/28.
 *单个票详细信息
 */

public class HousingstockActivity extends BaseActivity {
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
    private HistoryBeans data;
    @Override
    protected int getLayout() {
        return R.layout.activity_housingstock;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        data = (HistoryBeans) getIntent().getSerializableExtra("data");
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
        tv_proof.setText(data.getCheckedTime());
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
