package guominchuxing.tsbooking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.bean.HistoryBeans;

/**
 * Created by admin on 2017/4/27.
 */

public class CurrMonthAdapter extends RecyclerView.Adapter{
    private CurrMonthAdapter.onRecyclerViewItemClickListener itemClickListener = null;
    private List<HistoryBeans> dataList;
    private Context mContext;
    public CurrMonthAdapter(Context mContext,List<HistoryBeans> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public CurrMonthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_item_adapter,null);
        return new CurrMonthAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CurrMonthAdapter.ViewHolder viewHolder = (CurrMonthAdapter.ViewHolder) holder;
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(holder.itemView, (String) view.getTag(), position);
                }
            });
        }
        HistoryBeans todayBean = dataList.get(position);
        viewHolder.tv_voucherNo.setText(todayBean.getVoucherNo());
        viewHolder.tv_name.setText(todayBean.getMainName()+"  ( "+todayBean.getProductName()+" )");
        viewHolder.tv_date.setText(todayBean.getCheckedTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_voucherNo,tv_name,tv_date;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_voucherNo = (TextView) itemView.findViewById(R.id.tv_voucherNo);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);


        }
    }
    public void setOnItemClickListener(CurrMonthAdapter.onRecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;


    }

    public interface onRecyclerViewItemClickListener {

        void onItemClick(View v, String tag, int position);
    }
}
