package guominchuxing.tsbooking.bean;

/**
 {
 "code":200,
 "data":{
 "user":{
 "avatar":"20170613171000000",
 "email":"",
 "id":"321584213704839168",
 "lastLoginTime":"",
 "level":"",
 "loginId":"100122",
 "loginTime":"2017-06-15 10:03:22",
 "mobile":"13713618616",
 "name":"贵州民族大酒店",
 "ownerId":"321584213704839168"
 },
 "token":"7c0127de-800b-48a6-ab4a-84acd4c52dcb"
 },
 "msg":"",
 "oK":true,
 "url":""
 }
 */

public class LoginBean {
    private int code;
    private LoginData data;
    private String msg;
    private boolean ok;
    private String url;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
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

    public class LoginData{
        private String token;
        private User user;
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @Override
        public String toString() {
            return "LoginData{" +
                    "token='" + token + '\'' +
                    ", user=" + user +
                    '}';
        }
    }

    /**
     * user":{
     "avatar":"20170613171000000",
     "email":"",
     "id":"321584213704839168",
     "lastLoginTime":"",
     "level":"",
     "loginId":"100122",
     "loginTime":"2017-06-15 10:03:22",
     "mobile":"13713618616",
     "name":"贵州民族大酒店",
     "ownerId":"321584213704839168"
     },
     */
    public class User{
      private String loginId;
      private String avatar;
      private String name;
      private String mobile;
      private String id;
      private String email;
      private String ownerId;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                ", ok=" + ok +
                ", url='" + url + '\'' +
                '}';
    }
}
