package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/10.
 */
public class GetDtdServiceItemList {

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

    public List<GetDtdServiceItemList.data> getData() {
        return data;
    }

    public void setData(List<GetDtdServiceItemList.data> data) {
        this.data = data;
    }

    public static  class  data{
        public String ID;
        public String Pic;
        public String Name;
        public String lng;
        public String lat;
        public String s_area;
        public String tel;
        public String area;
        public String length;

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }
}
