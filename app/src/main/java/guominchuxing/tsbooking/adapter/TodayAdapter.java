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

public class TodayAdapter extends RecyclerView.Adapter{

    private onRecyclerViewItemClickListener itemClickListener = null;
    private List<HistoryBeans> dataList;
    private Context mContext;
    public TodayAdapter(Context mContext,List<HistoryBeans> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.include_item_adapter,parent,false);
            return new TodayViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TodayViewHolder){

         final TodayViewHolder viewHolder = (TodayViewHolder) holder;
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(holder.itemView, (String) view.getTag(), position);
                }
            });
        }

        HistoryBeans historyBeans = dataList.get(position);
        viewHolder.tv_voucherNo.setText(historyBeans.getVoucherNo());
        viewHolder.tv_name.setText(historyBeans.getMainName()+"  ( "+historyBeans.getProductName()+" )");
        viewHolder.tv_date.setText(historyBeans.getCheckedTime());
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }




    class TodayViewHolder extends RecyclerView.ViewHolder{
        TextView tv_voucherNo,tv_name,tv_date;
        public TodayViewHolder(View itemView) {
            super(itemView);
             tv_voucherNo = (TextView) itemView.findViewById(R.id.tv_voucherNo);
             tv_name = (TextView) itemView.findViewById(R.id.tv_name);
             tv_date = (TextView) itemView.findViewById(R.id.tv_date);


        }
    }
    public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;

    }

    public interface onRecyclerViewItemClickListener {

        void onItemClick(View v, String tag, int position);
    }
}
