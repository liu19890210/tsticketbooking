package guominchuxing.tsbooking.base;

/**
 * Created by admin on 2017/6/1.
 * Presenter基类
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
