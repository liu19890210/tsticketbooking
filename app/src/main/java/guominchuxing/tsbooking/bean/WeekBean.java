//package guominchuxing.tsbooking.bean;
//
//import java.util.Comparator;
//import java.util.List;
//
///**
// * Created by admin on 2017/6/24.
// * /report/days.do
// *{
// "code":200,
// "data":[
// {
// "aliPayAmt":"0.07",
// "aliPayCnt":"7",
// "aliPayNum":"7",
// "billAmt":"0.16",
// "billCnt":"16",
// "billNum":"16",
// "cancelAmt":"0.09",
// "cancelCnt":"9",
// "cancelNum":"9",
// "checkedAmt":"0.02",
// "checkedCnt":"2",
// "checkedNum":"2",
// "dayStockNum":"100",
// "expiredAmt":"00.00",
// "expiredCnt":"0",
// "expiredNum":"0",
// "forId":"324191196446461952",
// "forName":"贵州天豪大酒店",
// "gmCreditPayAmt":"00.00",
// "gmCreditPayCnt":"0",
// "gmCreditPayNum":"0",
// "gmPayAmt":"00.00",
// "gmPayCnt":"0",
// "gmPayNum":"0",
// "mchId":"",
// "offlinePayAmt":"00.00",
// "offlinePayCnt":"0",
// "offlinePayNum":"0",
// "onlinePayAmt":"0.07",
// "onlinePayCnt":"7",
// "onlinePayNum":"7",
// "otherId":"",
// "otherName":"",
// "otherPayAmt":"00.00",
// "otherPayCnt":"0",
// "otherPayNum":"0",
// "otherType":0,
// "payAmt":"0.07",
// "payCnt":"7",
// "payNum":"7",
// "period":"20170622",
// "periodType":40,
// "provCode":"",
// "refundCancelAmt":"00.00",
// "refundCancelCnt":"0",
// "refundCancelNum":"0",
// "refundNoAmt":"00.00",
// "refundNoCnt":"0",
// "refundNoNum":"0",
// "refundReqAmt":"0.01",
// "refundReqCnt":"1",
// "refundReqNum":"1",
// "refundYesAmt":"00.00",
// "refundYesCnt":"0",
// "refundYesNum":"0",
// "relResNum":"100",
// "reportId":"10295",
// "reportType":30,
// "timeoutAmt":"0.09",
// "timeoutCnt":"9",
// "timeoutNum":"9",
// "usedAmt":"0.02",
// "usedCnt":"2",
// "usedNum":"2",
// "userCancelAmt":"00.00",
// "userCancelCnt":"0",
// "userCancelNum":"0",
// "weiPayAmt":"00.00",
// "weiPayCnt":"0",
// "weiPayNum":"0"
// },
// Object{...}
// ],
// "msg":"",
// "oK":true,
// "url":""
// }
// */
//
//public class WeekBean {
//    private int code;
//    private List<WeekData> data;
//    private String msg;
//    private boolean ok;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public List<WeekData> getData() {
//        return data;
//    }
//
//    public void setData(List<WeekData> data) {
//        this.data = data;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public boolean isOk() {
//        return ok;
//    }
//
//    public void setOk(boolean ok) {
//        this.ok = ok;
//    }
//
//    public static class WeekData{
//        private String period;
//        private String usedNum;
//
//        public String getPeriod() {
//            return period;
//        }
//
//        public void setPeriod(String period) {
//            this.period = period;
//        }
//
//        public String getUsedNum() {
//            return usedNum;
//        }
//
//        public void setUsedNum(String usedNum) {
//            this.usedNum = usedNum;
//        }
//
//        @Override
//        public String toString() {
//            return "WeekData{" +
//                    "period='" + period + '\'' +
//                    ", usedNum='" + usedNum + '\'' +
//                    '}';
//        }
//    }
//
//
//    public static class SortClass implements Comparator{
//
//        @Override
//        public int compare(Object arg0, Object arg1) {
//            WeekData weekData0 = (WeekData) arg0;
//            WeekData weekData1 = (WeekData) arg1;
//            int flag =weekData0.getPeriod().compareTo(weekData1.getPeriod());
//            return flag;
//        }
//    }
//
//}
