package activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.PostTrueName;
import butterknife.Bind;
import butterknife.ButterKnife;
import common.HaveToken;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dell on 2016/11/29.
 */
public class FamilyFoucsMe extends AppCompatActivity {


    @Bind(R.id.toolbar_focusme)
    Toolbar toolbar;


    private String url_getfamily = "http://www.yimijianfang.com/api/app/getcontactlist/";//<lnrid>?token=
    private String token, MD5token, lnrid, msg;
    final OkHttpClient client = new OkHttpClient();

    SharedPreferences sp_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familyfoucsme);

        ButterKnife.bind(this);
        if (toolbar != null) {
            //设置返回图标
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        sp_login = getSharedPreferences("userinfo", MODE_PRIVATE);
        lnrid = sp_login.getString("UserID", "");


        HaveToken.Token();
        token = HaveToken.str_contlist;
        MD5token = MD5.md5(token);

        Getfamilylist();

    }


    private void Getfamilylist() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。


        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getfamily + lnrid + "?token=" + MD5token)
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
                            Toast.makeText(FamilyFoucsMe.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

//                            Gson gson = new Gson();
//                            PostTrueName posttruename = gson.fromJson(message, PostTrueName.class);
//                            msg = posttruename.getErrmsg();
//
//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {


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
