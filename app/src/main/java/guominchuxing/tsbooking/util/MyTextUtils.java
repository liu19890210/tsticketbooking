package guominchuxing.tsbooking.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2017/5/31.
 */

public class MyTextUtils {
    /**
     * 密码由数字、字母和常用字符组合的6-20位字符
     * @param password
     * @return
     */
    public static boolean isPassword(String password){
        boolean isValid = false;
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?!([^(0-9a-zA-Z)]|[\\(\\)])+$)([^(0-9a-zA-Z)]|[\\(\\)]|[a-zA-Z]|[0-9]){6,20}$");
        Matcher m =p.matcher(password);
        if (m.matches()){
            isValid = true;

        }
        return isValid;
    }
}
