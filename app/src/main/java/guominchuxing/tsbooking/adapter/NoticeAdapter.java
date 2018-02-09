package guominchuxing.tsbooking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.bean.NoticeBean;

/**
 * Created by admin on 2017/5/24.
 * 公告适配
 */

public class NoticeAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<NoticeBean.NoticeData> dataList;
    public NoticeAdapter(Context mContext,List<NoticeBean.NoticeData> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.notice_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof ViewHolder){
           ViewHolder viewHolder = (ViewHolder) holder;
           NoticeBean.NoticeData data = dataList.get(position);
           viewHolder.tv_date.setText(data.getUpdateTime());
           viewHolder.tv_title.setText(data.getTitle());
           viewHolder.tv_content.setText(Html.fromHtml(data.getTypeName())+":  "+Html.fromHtml(data.getContent()));
       }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_date,tv_content;
        public ViewHolder(View itemView) {
            super(itemView);
          tv_title = (TextView) itemView.findViewById(R.id.tv_title);
          tv_date = (TextView) itemView.findViewById(R.id.tv_date);
          tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
