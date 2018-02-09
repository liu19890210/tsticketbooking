package guominchuxing.tsbooking.bean;

/**
 * Created by admin on 2017/3/9.
 * /**
 * {"code":200,"data":true,"msg":"","oK":true,"url":""}
 */


public class CheckBean {
    private int code;
    private boolean data;
    private String msg;
    private boolean ok;
    private String url;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CheckBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", ok=" + ok +
                ", url='" + url + '\'' +
                '}';
    }
}
