package guominchuxing.tsbooking.bean;

import com.iflytek.cloud.thirdparty.T;

/**
 * Created by admin on 2018/1/25.
 * * "code":200,
 "countable":false,
 "data":[
 {
 }
 ],
 "msg":"",
 "oK":true,
 "pageNum":"",
 "pageSize":"",
 "pages":"",
 "total":"",
 "url":""
   }
 */

public class ResultBean<T> {
    private int code;
    private boolean countable;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
