package guominchuxing.tsbooking.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/6/1.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T>{
    protected T mView;
    protected CompositeSubscription compositeSubscription;

    protected void unSubscribe(){
        if (compositeSubscription != null){
            compositeSubscription.unsubscribe();
        }

    }
    protected void addSubscribe(Subscription subscription){
        if (compositeSubscription == null){
            compositeSubscription = new CompositeSubscription();
        }
            compositeSubscription.add(subscription);
        }


    @Override
    public void attachView(T view) {
     this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
