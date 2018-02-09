package guominchuxing.tsbooking.bean;

/**
 * Created by admin on 2018/1/25.
 * 历史数据实体类
 */

public class HistoryBeans extends BaseBean{

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
        return "Data{" +
                "voucherNo='" + voucherNo + '\'' +
                ", mainName='" + mainName + '\'' +
                ", productName='" + productName + '\'' +
                ", checkedTime='" + checkedTime + '\'' +
                ", guest='" + guest + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

}
