package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/14.
 */
public class GetHomeActiveItem {

    public String errcode;
    public String errmsg;
    public List<data> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<GetHomeActiveItem.data> getData() {
        return data;
    }

    public void setData(List<GetHomeActiveItem.data> data) {
        this.data = data;
    }

    public static class data {
        public String ID;
        public String Pic;
        public String Title;
        public String Type;
        public String coupon;
        public String cost;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String pic) {
            Pic = pic;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }
    }


}
