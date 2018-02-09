package guominchuxing.tsbooking.bean;

/**
 * Created by admin on 2017/12/14.
 * 看板内容
 */

public class BoardBean extends BaseBean{

    private String mainName;
    private String productName;
    private String relMan;
    private String relMbl;
    private String remark;
    private int quantity;

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

    public String getRelMan() {
        return relMan;
    }

    public void setRelMan(String relMan) {
        this.relMan = relMan;
    }

    public String getRelMbl() {
        return relMbl;
    }

    public void setRelMbl(String relMbl) {
        this.relMbl = relMbl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BoardBean{" +
                "mainName='" + mainName + '\'' +
                ", productName='" + productName + '\'' +
                ", relMan='" + relMan + '\'' +
                ", relMbl='" + relMbl + '\'' +
                ", remark='" + remark + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
