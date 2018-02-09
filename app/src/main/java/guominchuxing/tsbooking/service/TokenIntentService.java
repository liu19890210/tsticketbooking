//package guominchuxing.ebooking.service;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.util.Log;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import guominchuxing.ebooking.EHomeActivity;
//import guominchuxing.ebooking.act.LoginActivity;
//import guominchuxing.ebooking.api.ApiManager;
//import guominchuxing.ebooking.bean.LoginBean;
//import guominchuxing.ebooking.constant.Const;
//import guominchuxing.ebooking.util.RxUtil;
//import guominchuxing.ebooking.util.SharefereUtil;
//import rx.Subscription;
//import rx.functions.Action1;
//
///**
// * Created by admin on 2017/7/2.
// */
//
//public class TokenIntentService extends IntentService{
//    private static final String TAG = "TokenIntentService";
//
//    private Subscription subscription;
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
////     * @param name Used to name the worker thread, important only for debugging.
//     */
//
//    public TokenIntentService() {
//        super("TokenIntentService");
//    }
//    String token;
//    @Override
//    protected void onHandleIntent(Intent intent) {
//
//
//        String account = intent.getExtras().getString("account");
//        String password = intent.getExtras().getString("password");
//        subscription = ApiManager.getDefaut().getLogin(account,password, Const.APP, Const.METHOD)
//                .compose(RxUtil.<LoginBean>rxSchedulerHelper())
//                .subscribe(new Action1<LoginBean>() {
//                    @Override
//                    public void call(LoginBean loginBean) {
//                        try {
//                            int code = loginBean.getCode();
//                            if (code == 200){
//                                LoginBean.LoginData loginData = loginBean.getData();
//                                token = loginData.getToken();
//                            }
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//                            Intent i = new Intent();
//                            i.putExtra("token",token);
//                            i.setAction("tokenIntentService");
//                            sendBroadcast(i);
//                        }
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                               Log.e(TAG,throwable.getMessage());
//                    }
//                });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (subscription != null){
//            subscription.unsubscribe();
//        }
//    }
//}
