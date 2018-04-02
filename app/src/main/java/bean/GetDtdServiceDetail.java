package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/10.
 */
public class GetDtdServiceDetail {
    public String  errcode;
    public String  errmsg;
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

    public GetDtdServiceDetail.data getData() {
        return data;
    }

    public void setData(GetDtdServiceDetail.data data) {
        this.data = data;
    }

    public static class data{
        public String  ID;
        public String  Pic;
        public String  Name;
        public String  lng;
        public String  lat;
        public String  s_area;
        public String  Content;
        public String  address;
        public String  tel;
        public String  introduce;
        public String  stime;
        public String  etime;
        public String  ZZpic;
        public String  area;

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

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
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

        public String getS_area() {
            return s_area;
        }

        public void setS_area(String s_area) {
            this.s_area = s_area;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getZZpic() {
            return ZZpic;
        }

        public void setZZpic(String ZZpic) {
            this.ZZpic = ZZpic;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }
}
