package guominchuxing.tsbooking.dialog;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by admin on 2017/12/18.
 * 日期控件
 */

public class DateDialog extends AlertDialog{

    protected DateDialog(Context context) {
        super(context);
    }

    protected DateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected DateDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
