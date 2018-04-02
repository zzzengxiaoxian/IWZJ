package common;

import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 各个接口的TOKEN
 * Created by dell on 2016/11/1.
 */
public class HaveToken {

    //接口名定义
    private static String CITYLIST = "citys";//城市列表接口名
    private static String GETCENTER = "getcenter";//获取服务中心接口名
    private static String GETMALL = "getmall_list";//获取特惠商城接口名
    private static String GETGOODS_LIST = "getgoods_list";//获取特惠商城商品item列表内容
    private static String GETDTDSERVICE = "getdtd_list";//获取上门服务（Door to door service）
    private static String GOODSDETAIL = "goods_detail";//获取特惠商城下每一个商品的详细内容接口
    private static String GETDTDSERVICELIST = "getservice_list";//获取上门服务item服务公司列表
    private static String GETDTDSERVICEDETAIL = "dtd_detail";//获取上门服务企业的详细信息
    private static String GETACTIVELIST = "getactive_list";//获取活动天地信息
    private static String GETACTIVEITEM = "getactives_list";//活动天地中一个大类包含的所有小类接口列表
    private static String GETACTIVESDETAIL = "actices_detail";//活动天地中一个活动具体信息
    private static String GETREGISTER = "regeditlnr";//注册信息
    private static String GETLOGIN = "checkuser";//验证登录信息
    private static String CHANGEPHOTO = "changephoto";//头像信息
    private static String CHANGEPWD = "changepwd";//修改密码
    private static String CHANGENICKSEX = "changeinfo";//修改密码
    private static String TURENAMECONFIRM = "confirm";//修改密码
    private static String GETCONTACTLIST = "getcontactlist";//紧急联系人列表
    private static String GETADDCONT = "insertcontact";//添加紧急联系人
    private static String GETUPDATECONT = "updatecontact";//修改紧急联系人
    private static String DELETCONTACT = "delcontact";//删除紧急联系人


    //TOKEN定义
    public static String str_city;
    public static String str_center;
    public static String str_mall;
    public static String str_getgoods_list;
    public static String str_dtdservice;
    public static String str_goodsdetail;
    public static String str_getdtdservicelist;
    public static String str_getdtdservicedetail;
    public static String str_getactivelist;
    public static String str_getactiveslist;
    public static String str_getactivesdetail;
    public static String str_getregister;
    public static String str_checkuser;
    public static String str_changephoto;
    public static String str_changepwd;
    public static String str_changeinfo;
    public static String str_truename;
    public static String str_contlist;
    public static String str_addcontlist;
    public static String str_updatecontlist;
    public static String str_deletcontact;

    public static void Token() {


        //TOKEN 组成形式为方法名字+密码+时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");//以何种形式显示
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = sdf.format(curDate);
        Log.i("现在时间：", date.toString());
        String pwd = "flsk!#ks123";//秘钥

        //获取城市列表的TOKEN接口组成
        str_city = CITYLIST + pwd + date;
        //获取服务中心token接口
        str_center = GETCENTER + pwd + date;
        str_mall = GETMALL + pwd + date;
        str_dtdservice = GETDTDSERVICE + pwd + date;
        str_getgoods_list = GETGOODS_LIST + pwd + date;
        str_goodsdetail = GOODSDETAIL + pwd + date;
        str_getdtdservicelist = GETDTDSERVICELIST + pwd + date;
        str_getdtdservicedetail = GETDTDSERVICEDETAIL + pwd + date;
        str_getactivelist = GETACTIVELIST + pwd + date;
        str_getactiveslist = GETACTIVEITEM + pwd + date;
        str_getactivesdetail = GETACTIVESDETAIL + pwd + date;
        str_getregister = GETREGISTER + pwd + date;
        str_checkuser = GETLOGIN + pwd + date;
        str_changephoto = CHANGEPHOTO + pwd + date;
        str_changepwd = CHANGEPWD + pwd + date;
        str_changeinfo = CHANGENICKSEX + pwd + date;
        str_truename = TURENAMECONFIRM + pwd + date;
        str_contlist = GETCONTACTLIST + pwd + date;
        str_addcontlist = GETADDCONT + pwd + date;
        str_updatecontlist = GETUPDATECONT + pwd + date;
        str_deletcontact = DELETCONTACT + pwd + date;
    }
}
