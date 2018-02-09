package guominchuxing.tsbooking;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */


public class App extends Application {
    private static App instance;
    private static List<Activity> activityList = new ArrayList<>();


    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;

    public static synchronized App getInstance() {
        return instance;
    }
//    static {
//        AppCompatDelegate.setDefaultNightMode(
//                AppCompatDelegate.MODE_NIGHT_NO);
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        getSpeechUtility();
        //初始化屏宽高
        getScreenSize();



    }

    /**
     * 讯飞sdk
     */
    private void getSpeechUtility() {
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

          SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
//         Setting.setShowLog(false);
    }

    /**
     * 添加activity到activityList中，在onCreate（）调用
     * @param activity
     */
    public static void addActivity(Activity activity){
      if (activityList != null && activityList.size()>0 ){
          if (!activityList.contains(activity)){
              activityList.add(activity);
          }
        }else {
          activityList.add(activity);
      }

      }

    /**
     * 结束activity到activityList，在onDestory()调用
     * @param activity
     */
      public static void finishActivity(Activity activity){
        if (activity != null && activityList !=null && activityList.size()>0){
              activityList.remove(activity);
        }
      }

    /**
     * 结束所有的activity
     */
    public static void finishAllActivity(){
      if (activityList !=null && activityList.size()>0){
          synchronized (activityList){
          for (Activity activity : activityList){
              if (null != activity){
                  activity.finish();
              }
          }
          }
      }
          activityList.clear();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
      }
    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }


}