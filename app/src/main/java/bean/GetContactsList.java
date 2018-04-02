package bean;

import java.util.List;

/**
 * Created by dell on 2016/11/29.
 */
public class GetContactsList {

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

    public List<GetContactsList.data> getData() {
        return data;
    }

    public void setData(List<GetContactsList.data> data) {
        this.data = data;
    }

    public static class data {
        public String ID;
        public String Name;
        public String Tel;
        public String Relation;

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

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getRelation() {
            return Relation;
        }

        public void setRelation(String relation) {
            Relation = relation;
        }
    }
}
