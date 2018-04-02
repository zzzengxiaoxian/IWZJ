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

import org.w3c.dom.Text;

import java.io.IOException;

import bean.GetChangePWD;
import bean.GetLogin;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 修改登录密码
 * Created by dell on 2016/11/23.
 */
public class ChangePwdActivity extends AppCompatActivity {

    @Bind(R.id.tv_donechange)
    TextView tv_donechange;

    @Bind(R.id.edt_newpwd)
    EditText edt_newpwd;

    @Bind(R.id.edt_oldpwd)
    EditText edt_oldpwd;
    @Bind(R.id.edt_confirmpwd)
    EditText edt_confirmpwd;

    @Bind(R.id.toolbar_changepwd)
    Toolbar toolbar;


    private String token, MD5token;
    private String url_getchangpwd = "http://www.yimijianfang.com/api/app/changepwd?token=";//<token>
    final OkHttpClient client = new OkHttpClient();

    private String pwd, newpwd, confirpwd, ID;
    SharedPreferences pre_logininfomation;
    private String msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepwd);
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        HaveToken.Token();
        token = HaveToken.str_changepwd;
        MD5token = MD5.md5(token);


        pre_logininfomation = getSharedPreferences("Userinfo", MODE_PRIVATE);
        ID = pre_logininfomation.getString("UserID", "");//获取到的是加密后的密码

        //1.如果进入程序后修改密码需要解密操作 2.如果在注册的时候，立刻修改密码。不用解密，直接用输入的验证码来修改。


    }


    private void getChangepwd() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。
        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", ID);
        builder.add("oldpwd", pwd);
        builder.add("newpwd", newpwd);
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_getchangpwd + MD5token)
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
                        Log.i("TAG", "打印获取修改密码接口响应的数据：" + message);

                        Gson gson = new Gson();
                        GetChangePWD getChangePWD = gson.fromJson(message, GetChangePWD.class);
                        msg = getChangePWD.getErrmsg();


////                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (msg.equals("原密码不正确")) {
                                    Toast.makeText(ChangePwdActivity.this, msg, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (msg.equals("请求成功")) {
                                        Toast.makeText(ChangePwdActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        ChangePwdActivity.this.finish();
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


    @OnClick(R.id.tv_donechange)
    public void OnDoneChange() {
        newpwd = edt_newpwd.getText().toString().trim();
        pwd = edt_oldpwd.getText().toString().trim();
        confirpwd = edt_confirmpwd.getText().toString().trim();

        if (pwd.isEmpty()) {
            Toast.makeText(ChangePwdActivity.this, "请输入原密码", Toast.LENGTH_SHORT).show();
        } else {
            if (newpwd.isEmpty()) {
                Toast.makeText(ChangePwdActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
            } else {
                if (confirpwd.isEmpty()) {
                    Toast.makeText(ChangePwdActivity.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
//                    if (pwd != loginpwd) {
//                        Toast.makeText(ChangePwdActivity.this, "原密码输入不正确", Toast.LENGTH_SHORT).show();
//                    } else {
//
//                    }
                    if (newpwd.length() < 6) {
                        Toast.makeText(ChangePwdActivity.this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!confirpwd.equals(newpwd)) {
                            Toast.makeText(ChangePwdActivity.this, "两次输入的新密码不同", Toast.LENGTH_SHORT).show();
                        } else {
                            //进行新密码上传操作。
                            getChangepwd();
                        }
                    }
                }
            }
        }

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
