package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GetContactsListAdapter;
import bean.DeletContact;
import bean.GetContactsList;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 紧急联系人
 * Created by dell on 2016/11/23.
 */
public class EmergencyContactActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_lxr)
    Toolbar toolbar;
    @Bind(R.id.listview_lxr)
    ListView listView;
    @Bind(R.id.tv_addlxr)
    TextView tv_addlxr;


    SharedPreferences sp_loginuser, sharedPreferences_contactlist;

    PopupWindowContactList menuWindow;

    private String lnrid, contactId;

    private String geturl_contactlist = "http://www.yimijianfang.com/api/app/getcontactlist/";//<token>
    private String geturl_deletcontact = "http://www.yimijianfang.com/api/app/delcontact/";//<id>?token=<token>
    private String token, token_delet;
    private String MD5token, MD5token_delet;
    final OkHttpClient client = new OkHttpClient();

    private String msg, name, phone, relation, name_put, phone_put, relation_put;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    List<GetContactsList.data> getContactslistdata;
    GetContactsList.data ContactsList_data;
    GetContactsList getContactsList;
    int isfromAddCont = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (isfromAddCont == 1) {
            sharedPreferences_contactlist = getSharedPreferences("ContactAddInfo", MODE_PRIVATE);
            name = sharedPreferences_contactlist.getString("name", "");
            phone = sharedPreferences_contactlist.getString("phone", "");
            relation = sharedPreferences_contactlist.getString("relation", "");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击按钮   弹出popupwindow  选择编辑、删除联系人和取消
                ContactsList_data = getContactslistdata.get(i);
                contactId = ContactsList_data.getID();
                name_put = ContactsList_data.getName();
                phone_put = ContactsList_data.getTel();
                relation_put = ContactsList_data.getRelation();
                System.out.println("*****************Contactid" + ContactsList_data.getID());

                menuWindow = new PopupWindowContactList(EmergencyContactActivity.this,
                        itemsOnClick);
                menuWindow.showAtLocation(
                        EmergencyContactActivity.this.findViewById(R.id.contact_main), Gravity.BOTTOM
                                | Gravity.CENTER_HORIZONTAL, 0, 0);


            }
        });

        //判断是否是从AddEmergencyContact界面过来
        Intent intent = getIntent();
        isfromAddCont = intent.getIntExtra("isAddcont", 0);

        sp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
        lnrid = sp_loginuser.getString("UserID", "");

        //获取
        HaveToken.Token();
        token = HaveToken.str_contlist;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;

        token_delet = HaveToken.str_deletcontact;
        MD5.md5(token_delet);
        MD5token_delet = MD5.b;

        Getfamilylist();


    }

    private void GetDeletContact() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(geturl_deletcontact + contactId + "?token=" + MD5token_delet)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET删除联系人的接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(EmergencyContactActivity.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            DeletContact deletContact = gson.fromJson(message, DeletContact.class);
                            msg = deletContact.getErrmsg();
//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        if (msg.equals("请求成功")) {
                                            EmergencyContactActivity.this.finish();
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

    View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_2:
                    //选取照片
                    GetDeletContact();
                    break;
                case R.id.btn_3:
                    //修改
                    Intent intent = new Intent();
                    intent.putExtra("contactID", contactId);
                    intent.putExtra("contactName", name_put);
                    intent.putExtra("contactPhone", phone_put);
                    intent.putExtra("contactRelation", relation_put);
                    intent.putExtra("contactindex", 1);
                    intent.setClass(EmergencyContactActivity.this, AddAndUpdateContact.class);
                    startActivity(intent);
                    EmergencyContactActivity.this.finish();
                    break;
            }

        }

    };

    @OnClick(R.id.tv_addlxr)
    public void OnAddLxr() {
        Intent intent = new Intent();
        intent.setClass(EmergencyContactActivity.this, AddAndUpdateContact.class);
        startActivity(intent);
        this.finish();
    }

    private void Getfamilylist() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。


        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(geturl_contactlist + lnrid + "?token=" + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET关注我的人的接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(EmergencyContactActivity.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            getContactsList = gson.fromJson(message, GetContactsList.class);
                            msg = getContactsList.getErrmsg();
                            getContactslistdata = getContactsList.getData();
                            if (getContactsList.getData().isEmpty()) {
                                Toast.makeText(EmergencyContactActivity.this, "您可以点击加号进行添加", Toast.LENGTH_SHORT).show();
                                if (isfromAddCont == 1) {
                                    Map<String, Object> map1 = new HashMap<String, Object>();
                                    map1.put("name", name);
                                    map1.put("phone", phone);
                                    map1.put("relation", relation);
                                    data.add(map1);
                                }
                            } else {

                                for (GetContactsList.data getcontactlist1 : getContactslistdata) {
                                    System.out.println("getID is" + getcontactlist1.getID() +
                                            "getname is" + getcontactlist1.getName() +
                                            "gettle is" + getcontactlist1.getTel() +
                                            "getrelation is" + getcontactlist1.getRelation()
                                    );

                                    Map<String, Object> map = new HashMap<String, Object>();
                                    map.put("name", getcontactlist1.getName());//将键值对放入map
                                    map.put("phone", getcontactlist1.getTel());
                                    map.put("relation", getcontactlist1.getRelation());
                                    data.add(map);//将map存入list
                                }

                            }


//


//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        BaseAdapter adapter = new GetContactsListAdapter(EmergencyContactActivity.this, data);
                                        System.out.print("adapter.getCount()" + adapter.getCount());

                                        sp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
                                        SharedPreferences.Editor editor_user = sp_loginuser.edit();
                                        editor_user.putString("Usercontact", "已提交" + adapter.getCount() + "位");
                                        editor_user.commit();
                                        listView.setAdapter(adapter);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

//

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

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(EmergencyContactActivity.this, UserPersonalINFO.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
