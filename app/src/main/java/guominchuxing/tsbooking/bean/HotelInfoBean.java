package guominchuxing.tsbooking.bean;

/**
 * Created by admin on 2017/5/23.
 */

public class HotelInfoBean extends BaseBean{

        private String name;
        private String levelName;
        private String brandName;
        private String address;
        private String linkman;
        private String mobile;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "HotelInfoData{" +
                    "name='" + name + '\'' +
                    ", levelName='" + levelName + '\'' +
                    ", brandName='" + brandName + '\'' +
                    ", address='" + address + '\'' +
                    ", linkman='" + linkman + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }

}

