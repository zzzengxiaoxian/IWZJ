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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.PostAddContact;
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
 * 添加修改紧急联系人
 * Created by dell on 2016/11/23.
 */
public class AddAndUpdateContact extends AppCompatActivity {

    @Bind(R.id.toolbar_addlxr)
    Toolbar toolbar;
    @Bind(R.id.tv_addlxr_save)
    TextView tv_addlxr_save;
    @Bind(R.id.edt_addname)
    EditText edt_name;
    @Bind(R.id.edt_addphone)
    EditText edt_phone;
    @Bind(R.id.edt_addreleation)
    EditText edt_releation;


    private String name, phone, relation, ID, name_update, phone_update, relation_update, contactid;
    private String url_addcontact = "http://www.yimijianfang.com/api/app/insertcontact?token=";
    private String url_updatecontact = "http://www.yimijianfang.com/api/app/updatecontact?token=";
    private String token;
    private String MD5token_add, MD5token_update;
    final OkHttpClient client = new OkHttpClient();

    private String msg;

    SharedPreferences sp_loginuser, sharedPreferences_addcont;

    private int contactindex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemergency);
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
        ID = sp_loginuser.getString("UserID", "");

        Intent intent = getIntent();
        contactindex = intent.getIntExtra("contactindex", 0);
        contactid = intent.getStringExtra("contactID");
        name_update = intent.getStringExtra("contactName");
        phone_update = intent.getStringExtra("contactPhone");
        relation_update = intent.getStringExtra("contactRelation");

        System.out.println("*****************contactindex" + contactindex);

        if (contactindex == 1) {
            edt_name.setText(name_update);
            edt_phone.setText(phone_update);
            edt_releation.setText(relation_update);

            HaveToken.Token();
            token = HaveToken.str_updatecontlist;
            MD5.md5(token);
            MD5token_update = MD5.b;
        } else {
            //获取
            HaveToken.Token();
            token = HaveToken.str_addcontlist;
            //加密token
            MD5.md5(token);
            MD5token_add = MD5.b;
        }


    }

    private void postAddContact() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", ID);
        builder.add("name", name);
        builder.add("tel", phone);
        builder.add("relation", relation);
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_addcontact + MD5token_add)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印POST添加紧急联系人接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(AddAndUpdateContact.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            PostAddContact postAddContact = gson.fromJson(message, PostAddContact.class);
                            msg = postAddContact.getErrmsg();

//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {


                                        if (msg.equals("请求成功")) {

                                            //添加的联系人信息  在已进入这个界面里边值还是空的
                                            sharedPreferences_addcont = getSharedPreferences("ContactAddInfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences_addcont.edit();
                                            editor.putString("name", name);
                                            editor.putString("phone", phone);
                                            editor.putString("relation", relation);
                                            editor.commit();

                                            Intent intent = new Intent();
                                            intent.putExtra("isAddcont", 1);
                                            intent.setClass(AddAndUpdateContact.this, EmergencyContactActivity.class);
                                            startActivity(intent);
                                            AddAndUpdateContact.this.finish();
                                        } else {
                                            Toast.makeText(AddAndUpdateContact.this, "请检查网络", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        }

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


    private void postUpdateContact() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", contactid);
        builder.add("name", name);
        builder.add("tel", phone);
        builder.add("relation", relation);
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_updatecontact + MD5token_update)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印POST修改紧急联系人接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(AddAndUpdateContact.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            PostAddContact postAddContact = gson.fromJson(message, PostAddContact.class);
                            msg = postAddContact.getErrmsg();
//
//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {


                                        if (msg.equals("请求成功")) {

                                            //添加的联系人信息  在已进入这个界面里边值还是空的
                                            sharedPreferences_addcont = getSharedPreferences("ContactAddInfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences_addcont.edit();
                                            editor.putString("name", name);
                                            editor.putString("phone", phone);
                                            editor.putString("relation", relation);
                                            editor.commit();

                                            Intent intent = new Intent();
                                            intent.putExtra("isAddcont", 1);
                                            intent.setClass(AddAndUpdateContact.this, EmergencyContactActivity.class);
                                            startActivity(intent);
                                            AddAndUpdateContact.this.finish();
                                        } else {
                                            Toast.makeText(AddAndUpdateContact.this, "请检查网络", Toast.LENGTH_SHORT).show();
                                        }
                                        //然后finish界面 ，回到原来的界面 刷新listview显示出来最新添加的联系人

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            });
                        }

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


    @OnClick(R.id.tv_addlxr_save)
    public void OnSave() {
        name = edt_name.getText().toString().trim();
        phone = edt_phone.getText().toString().trim();
        relation = edt_releation.getText().toString().trim();


        if (name.isEmpty()) {
            Toast.makeText(AddAndUpdateContact.this, "请输入完整姓名", Toast.LENGTH_SHORT).show();
        } else {
            if (phone.isEmpty()) {
                Toast.makeText(AddAndUpdateContact.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            } else {
                Boolean isphone = PhoneFormatCheckUtils.isPhoneLegal(phone);
                if (isphone == false) {
                    Toast.makeText(AddAndUpdateContact.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    if (relation.isEmpty()) {
                        Toast.makeText(AddAndUpdateContact.this, "请输入与该联系人的关系", Toast.LENGTH_SHORT).show();
                    } else {
                        if (contactindex == 1) {
                            postUpdateContact();
                        } else {
                            //请求网络 添加进入listview
                            postAddContact();
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
