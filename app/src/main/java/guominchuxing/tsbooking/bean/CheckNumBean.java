package guominchuxing.tsbooking.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/3/9.
 * /**
 * "code":200,
 "data":{
 "billId":"321598705272627200",
 "billNo":"10000026381020170606",
 "checked":true,
 "checkedNum":1,
 "checkedTime":"2017-06-06 11:05:33",
 "checkerName":"清远大酒店",
 "expireFrom":"2017-06-06 00:00:00",
 "expireTo":"2017-12-31 00:00:00",
 "expired":false,
 "guest":"老司机",
 "mainId":"321584554450096128",
 "mainName":"清远大酒店",
 "mobile":"13670105319",
 "productId":"321584986664734720",
 "productName":"大床房",
 "productTag":11,
 "productType":131002,
 "quantity":1,
 "tagName":"特价房",
 "typeName":"房券产品",
 "voucherId":"321598705339736064",
 "voucherNo":"110728857601"
 },
 "msg":"",
 "oK":true,
 "url":""
 }
 */


public class CheckNumBean extends BaseBean{

      private String voucherNo;
      private String mainName;
      private String productName;
      private String checkedTime;
      private String guest;
      private String mobile;
      private String billNo;

      public String getVoucherNo() {
          return voucherNo;
      }

      public void setVoucherNo(String voucherNo) {
          this.voucherNo = voucherNo;
      }

      public String getMainName() {
          return mainName;
      }

      public void setMainName(String mainName) {
          this.mainName = mainName;
      }

      public String getProductName() {
          return productName;
      }

      public void setProductName(String productName) {
          this.productName = productName;
      }

      public String getCheckedTime() {
          return checkedTime;
      }

      public void setCheckedTime(String checkedTime) {
          this.checkedTime = checkedTime;
      }

      public String getGuest() {
          return guest;
      }

      public void setGuest(String guest) {
          this.guest = guest;
      }

      public String getMobile() {
          return mobile;
      }

      public void setMobile(String mobile) {
          this.mobile = mobile;
      }

      public String getBillNo() {
          return billNo;
      }

      public void setBillNo(String billNo) {
          this.billNo = billNo;
      }

      @Override
      public String toString() {
          return "DataCheck{" +
                  "voucherNo='" + voucherNo + '\'' +
                  ", mainName='" + mainName + '\'' +
                  ", productName='" + productName + '\'' +
                  ", checkedTime='" + checkedTime + '\'' +
                  ", guest='" + guest + '\'' +
                  ", mobile='" + mobile + '\'' +
                  ", billNo='" + billNo + '\'' +
                  '}';
      }

}
