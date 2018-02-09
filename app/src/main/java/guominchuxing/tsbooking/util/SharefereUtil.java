package guominchuxing.tsbooking.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liuwenqing on 2016/8/16.
 */
public class SharefereUtil {
    public final static String SETTING = "Setting";
    public static void putVaule(Context mContext, String key, int vaule){
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        editor.putInt(key,vaule);
        editor.commit();
    }
    public static void putValue(Context context, boolean key, String value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putBoolean(value, key);
        sp.commit();
    }
    public static void putValue(Context context, String key, String value) {
        SharedPreferences.Editor sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }
    public static int getValue(Context context, String key, int defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }
    public static boolean getValue(Context context, String key, boolean defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }
    public static String getValue(Context context, String key, String defValue) {
        SharedPreferences sp =  context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }



}
