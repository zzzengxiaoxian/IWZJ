package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.GetLogin;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import common.PhoneFormatCheckUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 登录界面
 * Created by dell on 2016/11/22.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.edt_loginPhone)
    EditText edt_loginPhone;
    @Bind(R.id.tv_regist)
    TextView tv_regist;
    @Bind(R.id.edt_Loginpwd)
    EditText edt_Loginpwd;
    @Bind(R.id.btn_login)
    Button btn_btn_login;
    @Bind(R.id.toolbar_login)
    Toolbar toolbar;


    private String phone;
    private String pwd;
    private String msg;
    private String ID;
    private String NickName;
    private String Loginpwd;
    private String Photo;
    private String AppVersion;
    private String Origin;
    private String Password;
    private String randomstr;
    private String Mobile;
    private String Sex;
    private String CardID;
    private String CenterID;
    private String ProvinceID;
    private String CityID;
    private String DistrictID;
    private String Address;
    private String Name;
    private String IsTrueMobile;
    private String IsTrueName;
    private String Pid;
    private String Pidpic;
    private String add_date;
    private String status;
    private String dele_status;
    private String contact;


    String token, MD5token;
    String url_getlogin = "http://www.yimijianfang.com/api/app/checkuser?token=";//<token>
    final OkHttpClient client = new OkHttpClient();

    SharedPreferences sharedPreferences, pre_logininfomation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //登录成功后 将个人资料获取 并将其保存在sharedpreference 将isnotLogin设为false 代表已经登录
        //                SharedPreferences.Editor editor1 = sharedPreferences.edit();
//                editor1.putBoolean("isnotLogin", false);
//                editor1.commit();


        HaveToken.Token();
        token = HaveToken.str_checkuser;
        MD5token = MD5.md5(token);


    }


    @OnClick(R.id.tv_regist)
    public void onRegist() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        this.finish();

    }

    @OnClick(R.id.btn_login)
    public void onLogin() {

        phone = edt_loginPhone.getText().toString();
        pwd = edt_Loginpwd.getText().toString();

        if (phone.isEmpty()) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Boolean isphone = PhoneFormatCheckUtils.isPhoneLegal(phone);

            if (isphone == false) {
                Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            } else {
                if (pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        getRegistRequest();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void getRegistRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。
        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("tel", phone);
        builder.add("pwd", pwd);
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_getlogin + MD5token)
                .build();
        //ss
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印获取登陆接口响应的数据：" + message);


                        Gson gson = new Gson();
                        GetLogin getGoods = gson.fromJson(message, GetLogin.class);
                        msg = getGoods.getErrmsg();

                        ID = getGoods.getData().getID();
                        NickName = getGoods.getData().getNickName();
                        Loginpwd = getGoods.getData().getLoginpwd();
                        Photo = getGoods.getData().getPhoto();
                        AppVersion = getGoods.getData().getAppVersion();
                        Password = getGoods.getData().getPassword();
                        Origin = getGoods.getData().getOrigin();
                        randomstr = getGoods.getData().getRandomstr();
                        Mobile = getGoods.getData().getMobile();
                        Sex = getGoods.getData().getSex();
                        CardID = getGoods.getData().getCardID();
                        CenterID = getGoods.getData().getCenterID();
                        ProvinceID = getGoods.getData().getProvinceID();
                        CityID = getGoods.getData().getCityID();
                        DistrictID = getGoods.getData().getDistrictID();
                        Address = getGoods.getData().getAddress();
                        Name = getGoods.getData().getName();
                        IsTrueMobile = getGoods.getData().getIsTrueMobile();
                        IsTrueName = getGoods.getData().getIsTrueName();
                        Pid = getGoods.getData().getPid();
                        Pidpic = getGoods.getData().getPidpic();
                        add_date = getGoods.getData().getAdd_date();
                        status = getGoods.getData().getStatus();
                        dele_status = getGoods.getData().getDele_status();
                        contact=getGoods.getData().getContact();


                        pre_logininfomation = getSharedPreferences("Userinfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor_user = pre_logininfomation.edit();
                        editor_user.putString("UserID", ID);
                        editor_user.putString("UserPhoto", Photo);
                        editor_user.putString("UserNickName", NickName);
                        editor_user.putString("UserSex", Sex);
                        editor_user.putString("UserMobile", Mobile);
                        editor_user.putString("UserAddress", Address);
                        editor_user.putString("UserLoginpwd", Loginpwd);
                        editor_user.putString("UserAppVersion", AppVersion);
                        editor_user.putString("UserPassword", Password);
                        editor_user.putString("UserOrigin", Origin);
                        editor_user.putString("Userrandomstr", randomstr);
                        editor_user.putString("UserCardID", CardID);
                        editor_user.putString("UserCenterID", CenterID);
                        editor_user.putString("UserProvinceID", ProvinceID);
                        editor_user.putString("UserCityID", CityID);
                        editor_user.putString("UserDistrictID", DistrictID);
                        editor_user.putString("UserName", Name);
                        editor_user.putString("UserIsTrueMobile", IsTrueMobile);
                        editor_user.putString("UserIsTrueName", IsTrueName);
                        editor_user.putString("UserPid", Pid);
                        editor_user.putString("UserPidpic", Pidpic);
                        editor_user.putString("Useradd_date", add_date);
                        editor_user.putString("Userstatus", status);
                        editor_user.putString("Userdele_status", dele_status);
                        editor_user.putString("Usercontact", contact);
                        editor_user.commit();


////                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (msg.equals("密码错误")) {
                                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                                } else {
                                    if (msg.equals("请求成功")) {
                                        //如果你登陆过了 将登录状态设置为已经登录。
                                        sharedPreferences = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isnotLogin", false);
                                        editor.commit();

                                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent();
                                        intent.putExtra("frgmentindex", 1);
                                        intent.setClass(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        LoginActivity.this.finish();
                                    }
                                }

                            }
                        });


                        try {

                        } catch (IllegalStateException | JsonSyntaxException exception) {
                            Log.i("TAG", exception.toString());
                        }


                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @OnClick(R.id.tv_regist)
    public void onRegister() {

    }


    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
