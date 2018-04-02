package activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import adapter.GetGoodsListAdapter;
import bean.GetGoods;
import bean.GetLogin;
import bean.GetRegist;
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
 * 界面
 * 注册
 * Created by dell on 2016/11/22.
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.edt_registerPhone)
    EditText edt_registerPhone;
    @Bind(R.id.tv_nexstep)
    TextView tv_nexstep;

    @Bind(R.id.tv_backlogin)
    TextView tv_backlogin;
    @Bind(R.id.toolbar_regist)
    Toolbar toolbar;
    @Bind(R.id.tv_xieyi)
    TextView tv_xieyi;

    @Bind(R.id.btn_getverification)
    Button btn_getverification;

    String token, MD5token;
    String url_getregister = "http://www.yimijianfang.com/api/app/regeditlnr?token=";//<token>
    final OkHttpClient client = new OkHttpClient();


    private String tel, yanchengma;
    private String msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        HaveToken.Token();
        token = HaveToken.str_getregister;
        MD5token = MD5.md5(token);


    }

    private void getRegistRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("tel", tel);
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_getregister + MD5token)
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
                        Log.i("TAG", "打印获取注册接口响应的数据：" + message);

                        Gson gson = new Gson();
                        GetRegist getRegist = gson.fromJson(message, GetRegist.class);

                        msg = getRegist.getErrmsg();


//
////                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (msg.equals("已经注册")) {
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.tv_backlogin)
    public void onLogin() {

        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();

    }

    @OnClick(R.id.btn_getverification)
    public void OnGetverification() {
        if (edt_registerPhone.getText().toString().length() < 11) {
            btn_getverification.isEnabled();
            Toast.makeText(RegisterActivity.this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
        } else {
            btn_getverification.isClickable();
            try {
                tel = edt_registerPhone.getText().toString();
                if (tel != null) {
                    boolean isphone = PhoneFormatCheckUtils.isPhoneLegal(tel);//判断手机号是否正确
                    if (isphone == false) {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        getRegistRequest();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick(R.id.tv_nexstep)
    public void onNexStep() {

        if (edt_registerPhone.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent();
            intent.setClass(RegisterActivity.this, ChangePwdActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    @OnClick(R.id.tv_xieyi)
    public void onXIEYI() {
        //跳转到隐私协议
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
