package bean;

/**
 * Created by dell on 2016/11/15.
 */
public class GetActivesItemDetailOnce {
    public String errcode;
    public String errmsg;
    public data data;

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

    public GetActivesItemDetailOnce.data getData() {
        return data;
    }

    public void setData(GetActivesItemDetailOnce.data data) {
        this.data = data;
    }

    public static class data {
        public String ID;
        public String Pic;
        public String Title;
        public String Type;
        public String coupon;
        public String cost;
        public String address;
        public String lng;
        public String lat;
        public String Des;
        public String s_datetime;
        public String e_datetime;

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String des) {
            Des = des;
        }

        public String getS_datetime() {
            return s_datetime;
        }

        public void setS_datetime(String s_datetime) {
            this.s_datetime = s_datetime;
        }

        public String getE_datetime() {
            return e_datetime;
        }

        public void setE_datetime(String e_datetime) {
            this.e_datetime = e_datetime;
        }
    }
}
