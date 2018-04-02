package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.GetChangeNickandSex;
import bean.GetLogin;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.HaveToken;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 昵称
 * Created by dell on 2016/10/10.
 */
public class PerCallName extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText name;
    private TextView save;

    String token, MD5token;
    String url_getchangenickname = "http://www.yimijianfang.com/api/app/changeinfo?token=";//<token>
    final OkHttpClient client = new OkHttpClient();

    SharedPreferences pre_logininfomation;
    private String Nickname, Sex, ID, msg;
    private int sexid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callname);

        toolbar = (Toolbar) findViewById(R.id.toolbar_callname);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        name = (EditText) findViewById(R.id.edt_name);
        save = (TextView) findViewById(R.id.save);

        if (toolbar != null) {
            //设置返回图标
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //第一种获得单选按钮值的方法
        //为radioGroup设置一个监听器:setOnCheckedChanged()
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                Sex = radioButton.getText().toString();
                if (Sex.equals("男")) {
                    sexid = 0;
                } else {
                    sexid = 1;
                }
                Toast.makeText(getApplicationContext(), "按钮组值发生改变,你选了" + Sex, Toast.LENGTH_LONG).show();
            }
        });


        pre_logininfomation = getSharedPreferences("Userinfo", MODE_PRIVATE);

        ID = pre_logininfomation.getString("UserID", "");
        Nickname = pre_logininfomation.getString("UserNickName", "");
        Sex = pre_logininfomation.getString("UserSex", "");
        if (Sex.isEmpty()) {

        } else {
            if (Sex.equals("男")) {
                sexid = 0;
            } else {
                sexid = 1;
            }
        }


        if (Nickname.isEmpty()) {
            Nickname = name.getText().toString().trim();
        } else {
            name.setText(Nickname);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().isEmpty()) {
                    new SweetAlertDialog(PerCallName.this)
                            .setTitleText("您好，称呼不能为空")
                            .show();
                } else {
                    if (Sex.isEmpty()) {
                        new SweetAlertDialog(PerCallName.this)
                                .setTitleText("请选择性别")
                                .show();
                    } else {
                        getRegistRequest();
                    }
                }

            }
        });

        HaveToken.Token();
        token = HaveToken.str_changeinfo;
        MD5token = MD5.md5(token);

    }


    private void getRegistRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。
        //post表单数据
        Sex = Integer.toString(sexid);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", ID);
        builder.add("nickname", Nickname);
        builder.add("sex", Sex);
        RequestBody formBody = builder.build();
        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_getchangenickname + MD5token)
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
                        GetChangeNickandSex getChangeNickandSex = gson.fromJson(message, GetChangeNickandSex.class);
                        msg = getChangeNickandSex.getErrmsg();


////                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (msg.equals("请求成功")) {
                                    Toast.makeText(PerCallName.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    pre_logininfomation = getSharedPreferences("Userinfo", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pre_logininfomation.edit();
                                    editor.putString("UserNickName", name.getText().toString());
                                    editor.putString("UserSex", Sex);
                                    editor.commit();
                                    Intent intent = new Intent();
                                    intent.setClass(PerCallName.this, UserPersonalINFO.class);
                                    startActivity(intent);
                                    PerCallName.this.finish();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
