package guominchuxing.tsbooking.act.other;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import guominchuxing.tsbooking.EHomeActivity;
import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.BaseActivity;
import guominchuxing.tsbooking.util.SharefereUtil;
/**
 * Created by admin on 2017/5/25.
 */

public class WelcomeActivity extends BaseActivity {
    private static final String TAG = "WelcomeActivity";
    private boolean isFirst = false;
    private String password;
    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentForWindow(Color.parseColor("#00000000"));
        isFirst =  SharefereUtil.getValue(this,"isFirst",true);
        password = SharefereUtil.getValue(this,"password","");
        Log.e(TAG,password);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (TextUtils.isEmpty(password) || isFirst){
                    intent(LoginActivity.class);
                    finish();
                }
                else {
                    intent(EHomeActivity.class);
                    finish();
                }
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
