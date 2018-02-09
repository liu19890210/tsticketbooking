package guominchuxing.tsbooking.footerView;

/**
 * Created by admin on 2017/8/31.
 */

public interface FooterViewListener {
    /**
     * 网络不好de时候想要展示UI
     * @param isAvailable
     */
     void onNetChange(boolean isAvailable);

    /**
     * 正常loading的view
     */
     void onLoadingMore();

    /**
     * 没有更多数据
     */
     void onNoMore(CharSequence message);

    /**
     * 错误时展示的view
     */
     void onError(CharSequence message);
}
