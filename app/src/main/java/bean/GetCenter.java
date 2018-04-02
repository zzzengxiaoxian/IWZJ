package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/2.
 */
public class GetCenter {

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

    public List<GetCenter.data> getData() {
        return data;
    }

    public void setData(List<GetCenter.data> data) {
        this.data = data;
    }

    public static class data{
        public String ID;
        public String Name;
        public String address;
        public String lng;
        public String lat;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
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
    }


}
