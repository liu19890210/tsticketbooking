package guominchuxing.tsbooking.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/5/27.
 */

public class RxUtil {
    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T,T> rxSchedulerHelper(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
