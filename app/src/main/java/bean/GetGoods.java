package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/8.
 */
public class GetGoods {
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

    public List<GetGoods.data> getData() {
        return data;
    }

    public void setData(List<GetGoods.data> data) {
        this.data = data;
    }

    public static class data {
        public String ID;
        public String Pic;
        public String Title;
        public String OldPrice;
        public String NewPrice;
        public String Des;

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

        public String getOldPrice() {
            return OldPrice;
        }

        public void setOldPrice(String oldPrice) {
            OldPrice = oldPrice;
        }

        public String getNewPrice() {
            return NewPrice;
        }

        public void setNewPrice(String newPrice) {
            NewPrice = newPrice;
        }

        public String getDes() {
            return Des;
        }

        public void setDes(String des) {
            Des = des;
        }
    }
}
