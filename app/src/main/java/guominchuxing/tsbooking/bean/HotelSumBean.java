package guominchuxing.tsbooking.bean;

/**
 * Created by admin on 2017/6/22.
 * {
 "code":200,
 "data":{
 "totalSales":
 {
 "aliPayAmt":"00.00",
 "aliPayCnt":"0",
 "aliPayNum":"0",
 "billAmt":"540.00",
 "billCnt":"3",
 "billNum":"3",
 "cancelAmt":"00.00",
 "cancelCnt":"3",
 "cancelNum":"0",
 "checkedAmt":"00.00",
 "checkedCnt":"0",
 "checkedNum":"0",
 "dayStockNum":"0",
 "expiredAmt":"00.00",
 "expiredCnt":"0",
 "expiredNum":"0",
 "forId":"297064917469499392",
 "forName":"云水谣大酒店",
 "gmCreditPayAmt":"00.00",
 "gmCreditPayCnt":"0",
 "gmCreditPayNum":"0",
 "gmPayAmt":"00.00",
 "gmPayCnt":"0",
 "gmPayNum":"0",
 "mchId":"",
 "offlinePayAmt":"00.00",
 "offlinePayCnt":"0",
 "offlinePayNum":"0",
 "onlinePayAmt":"00.00",
 "onlinePayCnt":"0",
 "onlinePayNum":"0",
 "otherId":"",
 "otherName":"",
 "otherPayAmt":"00.00",
 "otherPayCnt":"0",
 "otherPayNum":"0",
 "otherType":"",
 "payAmt":"00.00",
 "payCnt":"0",
 "payNum":"0",
 "period":"",
 "periodType":40,
 "provCode":"",
 "refundCancelAmt":"00.00",
 "refundCancelCnt":"0",
 "refundCancelNum":"0",
 "refundNoAmt":"00.00",
 "refundNoCnt":"0",
 "refundNoNum":"0",
 "refundReqAmt":"00.00",
 "refundReqCnt":"0",
 "refundReqNum":"0",
 "refundYesAmt":"00.00",
 "refundYesCnt":"0",
 "refundYesNum":"0",
 "relResNum":"",
 "reportId":"",
 "reportType":30,
 "timeoutAmt":"00.00",
 "timeoutCnt":"3",
 "timeoutNum":"0",
 "usedAmt":"00.00",
 "usedCnt":"0",
 "usedNum":"0",
 "userCancelAmt":"00.00",
 "userCancelCnt":"0",
 "userCancelNum":"0",
 "weiPayAmt":"00.00",
 "weiPayCnt":"0",
 "weiPayNum":"0"
 },
 "totalRooms":"0"
 },
 "msg":"",
 "oK":true,
 "url":""
 }
 */

public class HotelSumBean {
    private int code;
    private HotelSumData data;
    private boolean ok;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HotelSumData getData() {
        return data;
    }

    public void setData(HotelSumData data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class HotelSumData{
    private SalesBean totalSales;
    private String totalRooms;

    public SalesBean getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(SalesBean totalSales) {
        this.totalSales = totalSales;
    }

        public String getTotalRooms() {
            return totalRooms;
        }

        public void setTotalRooms(String totalRooms) {
            this.totalRooms = totalRooms;
        }

}
    public class SalesBean{
        private String usedNum;
        private String billAmt;
        private String checkedAmt;

        public String getUsedNum() {
            return usedNum;
        }

        public void setUsedNum(String usedNum) {
            this.usedNum = usedNum;
        }

        public String getBillAmt() {
            return billAmt;
        }

        public void setBillAmt(String billAmt) {
            this.billAmt = billAmt;
        }

        public String getCheckedAmt() {
            return checkedAmt;
        }

        public void setCheckedAmt(String checkedAmt) {
            this.checkedAmt = checkedAmt;
        }

        @Override
        public String toString() {
            return "SalesBean{" +
                    "billAmt='" + billAmt + '\'' +
                    ", checkedAmt='" + checkedAmt + '\'' +
                    '}';
        }
    }
}
