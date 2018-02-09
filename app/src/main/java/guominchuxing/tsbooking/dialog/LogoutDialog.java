package guominchuxing.tsbooking.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import guominchuxing.tsbooking.R;
import guominchuxing.tsbooking.act.other.LoginActivity;

/**
 * Created by admin on 2017/6/14.
 */

public class LogoutDialog {
    private Activity mActivity;
    public LogoutDialog(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 提示框
     * @param msg
     */
    public void getDialog(String msg){

        final AlertDialog adb = new AlertDialog.Builder(mActivity, R.style.DialogAlert)
                .setTitle("提示")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(mActivity,LoginActivity.class);
                        mActivity.startActivity(intent);
                        mActivity.finish();
                    }
                }).create();
        adb.show();
        WindowManager.LayoutParams params  = adb.getWindow().getAttributes();
        int width = adb.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        params.width = width*3/4;
        adb.getWindow().setAttributes(params);
    }
}
