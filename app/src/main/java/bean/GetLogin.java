package bean;

/**
 * Created by dell on 2016/11/22.
 */
public class GetLogin {
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

    public data getData() {
        return data;
    }

    public void setData(data data) {
        this.data = data;
    }


    public static class data {

        public String ID;
        public String NickName;
        public String Loginpwd;
        public String Photo;
        public String AppVersion;
        public String Origin;
        public String Password;
        public String randomstr;
        public String Mobile;
        public String Sex;
        public String CardID;
        public String CenterID;
        public String ProvinceID;
        public String CityID;
        public String DistrictID;
        public String Address;
        public String Name;
        public String IsTrueMobile;
        public String IsTrueName;
        public String Pid;
        public String Pidpic;
        public String add_date;
        public String status;
        public String dele_status;
        public String contact;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String nickName) {
            NickName = nickName;
        }

        public String getLoginpwd() {
            return Loginpwd;
        }

        public void setLoginpwd(String loginpwd) {
            Loginpwd = loginpwd;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }

        public String getAppVersion() {
            return AppVersion;
        }

        public void setAppVersion(String appVersion) {
            AppVersion = appVersion;
        }

        public String getOrigin() {
            return Origin;
        }

        public void setOrigin(String origin) {
            Origin = origin;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getRandomstr() {
            return randomstr;
        }

        public void setRandomstr(String randomstr) {
            this.randomstr = randomstr;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String mobile) {
            Mobile = mobile;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public String getCardID() {
            return CardID;
        }

        public void setCardID(String cardID) {
            CardID = cardID;
        }

        public String getCenterID() {
            return CenterID;
        }

        public void setCenterID(String centerID) {
            CenterID = centerID;
        }

        public String getProvinceID() {
            return ProvinceID;
        }

        public void setProvinceID(String provinceID) {
            ProvinceID = provinceID;
        }

        public String getCityID() {
            return CityID;
        }

        public void setCityID(String cityID) {
            CityID = cityID;
        }

        public String getDistrictID() {
            return DistrictID;
        }

        public void setDistrictID(String districtID) {
            DistrictID = districtID;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getIsTrueMobile() {
            return IsTrueMobile;
        }

        public void setIsTrueMobile(String isTrueMobile) {
            IsTrueMobile = isTrueMobile;
        }

        public String getIsTrueName() {
            return IsTrueName;
        }

        public void setIsTrueName(String isTrueName) {
            IsTrueName = isTrueName;
        }

        public String getPid() {
            return Pid;
        }

        public void setPid(String pid) {
            Pid = pid;
        }

        public String getPidpic() {
            return Pidpic;
        }

        public void setPidpic(String pidpic) {
            Pidpic = pidpic;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDele_status() {
            return dele_status;
        }

        public void setDele_status(String dele_status) {
            this.dele_status = dele_status;
        }
    }
}
