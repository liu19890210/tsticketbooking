package guominchuxing.tsbooking.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import java.util.List;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.bean.BoardBean;

/**
 * Created by admin on 2017/4/27.
 * 游客看板适配器
 */

public class BoardAdapter extends RecyclerAdatper<BoardBean> {
    private Activity context;
    private List<BoardBean> list;
    private CallPhone callPhone;

    public BoardAdapter(Activity context) {
        super(context);
        this.context = context;
    }

    public BoardAdapter(Context context, List<BoardBean> list) {
        super(context, list);
    }

    @Override
    public int getContentView(int viewType) {
        return R.layout.includ_item_board;
    }

    @Override
    public void onInitView(RecyclerHolder holder, final BoardBean bean, int position) {
        holder.setText(R.id.tv_productName, bean.getMainName() + "  " + bean.getProductName());
        holder.setText(R.id.tv_name, bean.getRelMan());
        holder.setText(R.id.tv_mobile, bean.getRelMbl());
        holder.setText(R.id.tv_num, bean.getQuantity() + "");
        holder.setText(R.id.tv_other, bean.getRemark());
        //电话
        holder.setOnClickListener(R.id.rl_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent启动拨打电话
               callPhone.callPhone(bean.getRelMbl());
            }
        });
    }


   public void setCallPhone(CallPhone callPhone){
        this.callPhone = callPhone;
   }

    public interface CallPhone{
        void callPhone(String phone);
    }
}