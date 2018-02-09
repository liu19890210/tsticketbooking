package guominchuxing.tsbooking.adapter;

/**
 * Created by admin on 2017/12/15.
 *
 */

public interface OnItemClickListener {
    /**
     * @param holder 操作的ViewHolder
     * @param position 点击item的位置
     */
    void onItemClick(RecyclerHolder holder, int position);

}
