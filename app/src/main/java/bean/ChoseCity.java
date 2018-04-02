package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/1.
 */
public class ChoseCity {
    public int errcode;
    public String errmsg;
    public List<data> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }


    public List<ChoseCity.data> getData() {
        return data;
    }

    public void setData(List<ChoseCity.data> data) {
        this.data = data;
    }



    public static class data{
        public String Code;
        public String Name;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }

}
