package guominchuxing.tsbooking.api;



import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.reflect.Proxy;

import guominchuxing.tsbooking.App;
import guominchuxing.tsbooking.act.other.LoginActivity;
import guominchuxing.tsbooking.constant.Const;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2018/1/24.
 */

public class RetrofitUtil implements GlobalManager{

    private static Retrofit sRetrofit;

    private static OkHttpClient isOkHttpClient;

    private static Object mRetrofitLock = new Object();

    private static RetrofitUtil instance;

    private static Retrofit getRetrofit(){
        if (sRetrofit == null){
            synchronized (mRetrofitLock){
                if (sRetrofit == null){
                    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    clientBuilder.addInterceptor(httpLoggingInterceptor);
                    isOkHttpClient = clientBuilder.build();
                    sRetrofit = new Retrofit.Builder().client(isOkHttpClient)
                            .baseUrl(Const.BASEIP)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

   public static RetrofitUtil getInstance() {
       if (instance == null) {
            synchronized (RetrofitUtil.class){
                if (instance == null){
                    instance = new RetrofitUtil();
                }
            }
       }
       return instance;
   }

   public <T>T get(Class<T> tClass){

       return getRetrofit().create(tClass);
   }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> tClass) {
        T t = getRetrofit().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] { tClass }, new ProxyHandler(t, this));
    }

    @Override
    public void exitLogin() {
          //取消全部请求
          isOkHttpClient.dispatcher().cancelAll();
          //返回登录页
          new Handler(Looper.getMainLooper()).post(new Runnable() {
              @Override
              public void run() {
                  Intent intent = new Intent(App.getInstance(), LoginActivity.class);
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  App.getInstance().startActivity(intent);
                  Toast.makeText(App.getInstance(), "Token is not existed!!", Toast.LENGTH_SHORT).show();
              }
          });
    }
}
