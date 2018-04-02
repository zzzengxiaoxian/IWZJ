package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/10.
 */
public class GetDtdService {

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

    public List<GetDtdService.data> getData() {
        return data;
    }

    public void setData(List<GetDtdService.data> data) {
        this.data = data;
    }

    public static class data {
        public String ID;
        public String Name;
        public String Pic;

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

        public String getPic() {
            return Pic;
        }

        public void setPic(String pic) {
            Pic = pic;
        }
    }
}
