package guominchuxing.tsbooking.api;

import android.app.ProgressDialog;
import android.content.Context;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * Created by admin on 2017/5/11.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private SoftReference<Context> context;
    private ProgressDialog pro;
    /**
     * 构造
     * @param context
     */
    public ProgressSubscriber(SoftReference<Context> context) {
        this.context = context;
        initView();
    }

    private void initView() {
       Context mContext = context.get();
       pro = new ProgressDialog(mContext);

    }
    public void dismissProgressDialog(){
        if (pro != null && pro.isShowing()){
            pro.dismiss();
        }
    }

    public void showProgressDialog(){
      Context mContext = context.get();
      if (pro == null || mContext == null)return;
        if (!pro.isShowing()){
            pro.isShowing();
        }
    }
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        dismissProgressDialog();
    }

    @Override
    public void onNext(Object o) {

    }

}
