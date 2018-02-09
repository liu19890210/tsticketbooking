package guominchuxing.tsbooking.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/6/26.
 * {
 "code":200,
 "countable":false,
 "data":[
 {
 "content":"sdfasdf",
 "createTime":"2017-06-22 21:44:52",
 "del":false,
 "endTime":"2017-06-22 00:00:00",
 "forType":1,
 "noticeId":"327564622997491712",
 "ownerId":"10000000",
 "startTime":"2017-06-22 00:00:00",
 "title":"test",
 "type":1,
 "typeName":"最新活动",
 "updateTime":"2017-06-22 21:44:52"
 },
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

public class NoticeBean {
    private int code;
    private List<NoticeData> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<NoticeData> getData() {
        return data;
    }

    public void setData(List<NoticeData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class NoticeData implements Serializable{
         private String title;
         private String startTime;
         private String typeName;
         private String content;
         private String updateTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "NoticeData{" +
                    "title='" + title + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", typeName='" + typeName + '\'' +
                    ", content='" + content + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    '}';
        }
    }
}
