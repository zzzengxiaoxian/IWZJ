package bean;

/**
 * Created by dell on 2016/11/16.
 */
public class GetActivesItemDetailCycle {

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

    public GetActivesItemDetailCycle.data getData() {
        return data;
    }

    public void setData(GetActivesItemDetailCycle.data data) {
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
        public String week;
        public String s_time;
        public String e_time;
        public String s_date;
        public String e_date;
        public String week_zh;

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

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getS_time() {
            return s_time;
        }

        public void setS_time(String s_time) {
            this.s_time = s_time;
        }

        public String getE_time() {
            return e_time;
        }

        public void setE_time(String e_time) {
            this.e_time = e_time;
        }

        public String getS_date() {
            return s_date;
        }

        public void setS_date(String s_date) {
            this.s_date = s_date;
        }

        public String getE_date() {
            return e_date;
        }

        public void setE_date(String e_date) {
            this.e_date = e_date;
        }

        public String getWeek_zh() {
            return week_zh;
        }

        public void setWeek_zh(String week_zh) {
            this.week_zh = week_zh;
        }
    }

}
