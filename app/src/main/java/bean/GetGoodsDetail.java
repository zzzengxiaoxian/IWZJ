package bean;

import android.util.Log;

import java.util.List;

/**
 * Created by dell on 2016/11/9.
 */
public class GetGoodsDetail {

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

    public GetGoodsDetail.data getData() {
        return data;
    }

    public void setData(GetGoodsDetail.data data) {
        this.data = data;
    }


    public void printOut() {
        Log.d("ydzJson", "" + errcode);
        Log.d("ydzJson", "" + errmsg);
        data.printOut();
    }
    public static class data {
        public String ID;
        public String Pic;
        public String Title;
        public String OldPrice;
        public String NewPrice;
        public String Des;
        public String Content;
        public List<Src> Src;

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

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public List<GetGoodsDetail.data.Src> getSrc() {
            return Src;
        }

        public void setSrc(List<GetGoodsDetail.data.Src> src) {
            Src = src;
        }

        public void printOut() {
            Log.d("ydzJson", "" + ID);
            Log.d("ydzJson", "" + Pic);
            Log.d("ydzJson", "" + Title);
            Log.d("ydzJson", "" + OldPrice);
            Log.d("ydzJson", "" + NewPrice);
            Log.d("ydzJson", "" + Des);
            Log.d("ydzJson", "" + Content);
            Log.d("ydzJson", "" + Pic);
            for (int index = 0; index < Src.size(); index++) {
                Src.get(index).printOut();
            }
        }

        public static class Src {
            public String Src;

            public String getSrc() {
                return Src;
            }

            public void setSrc(String src) {
                Src = src;
            }

            public void printOut() {
                Log.d("ydzJson2", "" + Src);
            }
        }
    }
}
