package guominchuxing.tsbooking.re;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 *实时监测网络
 * Created by admin on 2017/1/4.
 */

public class NetBroadcastReceiver extends BroadcastReceiver{

//    private NetEvevt evevt = HomeMainFragment.evevt;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//            int netWorkState = NetUtil.getNetWorkState(context);
//            // 接口回调传过去状态的类型
//            evevt.onNetChange(netWorkState);
        }
    }


    // 自定义接口
    public interface NetEvevt {

        public void onNetChange(int netMobile);

    }
}
