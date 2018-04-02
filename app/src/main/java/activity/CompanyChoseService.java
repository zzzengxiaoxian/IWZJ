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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import adapter.CenterAdapter;
import adapter.CityListAdapter;
import bean.ChoseCity;
import bean.GetCenter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 选择服务中心
 * Created by dell on 2016/10/17.
 */
public class CompanyChoseService extends AppCompatActivity {

    public int INDEX;//INDEX=1代表从homeactive传来的值，INDEX=2代表从homecompany传来的值


    @Bind(R.id.toolbar_choseservice)
    Toolbar toolbar;
    @Bind(R.id.complete)
    TextView tvcomplete;
    @Bind(R.id.listView)
    ListView listView;

    String url_citygetCenter = "http://www.yimijianfang.com/api/app/getcenter/";//<cityid>?token=<token>
    final OkHttpClient client = new OkHttpClient();
    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;
    //获取城市列表用到的bean
    GetCenter getCenter;
    GetCenter.data center_data;
    List<GetCenter.data> centerdatalist;

    SharedPreferences preferences, sharedPreferences, sp_center;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companychoseservice);

        ButterKnife.bind(this);
        if (toolbar != null) {
            //设置返回图标
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        preferences = getSharedPreferences("city_name", MODE_PRIVATE);
        //获取TOKEN
        cityid = preferences.getString("cityId", "130600");
        cityName = preferences.getString("chosecity", "保定市");
        Log.i("cityid is", cityid);
        Log.i("cityName is", cityName);

        HaveToken.Token();
        token = HaveToken.str_center;
        MD5.md5(token);
        MD5token = MD5.b;
        Log.i("url", MD5token);

        getRequest();

        sharedPreferences = getSharedPreferences("firsthavecenter", MODE_PRIVATE);
        SharedPreferences.Editor editorcenter = sharedPreferences.edit();
        editorcenter.putBoolean("firsthavecenter", false);
        editorcenter.commit();

        //选择一次后 再次进入都会默认选择之前的哪项
        sp_center = getSharedPreferences("defaultcenter", MODE_PRIVATE);

        Intent intent=getIntent();
        INDEX=intent.getIntExtra("INDEX",1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                center_data = centerdatalist.get(position);

                if (INDEX == 1) {
                    new SweetAlertDialog(CompanyChoseService.this)
                            .setTitleText("提示")
                            .setContentText("您选择的服务中心为" + center_data.getName() + "确认为此服务中心吗？")
                            .setConfirmText("确认")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent();
//                                intent.putExtra("center", center_data.getName());
//                                intent.putExtra("centercode", center_data.getID());
                                    SharedPreferences.Editor edtorcenter = sp_center.edit();
                                    edtorcenter.putString("defaultcentername", center_data.getName());
                                    edtorcenter.putString("defaultcentercode", center_data.getID());
                                    edtorcenter.commit();

                                    intent.setClass(CompanyChoseService.this, HomeActive.class);
                                    startActivity(intent);
                                    sweetAlertDialog.cancel();
                                    CompanyChoseService.this.finish();
                                }
                            })
                            .show();
                } else {
                    new SweetAlertDialog(CompanyChoseService.this)
                            .setTitleText("提示")
                            .setContentText("您选择的服务中心为" + center_data.getName() + "确认为此服务中心吗？")
                            .setCancelText("查看此中心详情")
                            .setConfirmText("确认")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent = new Intent();
                                    intent.putExtra("center", center_data.getName());
                                    intent.putExtra("centercode", center_data.getID());
                                    intent.setClass(CompanyChoseService.this, CompanyDetail.class);
                                    startActivity(intent);
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent();
//                                intent.putExtra("center", center_data.getName());
//                                intent.putExtra("centercode", center_data.getID());
                                    SharedPreferences.Editor edtorcenter = sp_center.edit();
                                    edtorcenter.putString("defaultcentername", center_data.getName());
                                    edtorcenter.putString("defaultcentercode", center_data.getID());
                                    edtorcenter.commit();

                                    intent.setClass(CompanyChoseService.this, HomeCompany.class);
                                    startActivity(intent);
                                    sweetAlertDialog.cancel();
                                    CompanyChoseService.this.finish();
                                }
                            })
                            .show();
                }


            }
        });


    }


    @OnClick(R.id.complete)
    public void complete() {

        CompanyChoseService.this.finish();
    }

    private void getRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_citygetCenter + cityid + "?token=" + MD5token)
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
                        Log.i("TAG", "打印GET响应的数据：" + message);
                        Gson gson = new Gson();
                        getCenter = gson.fromJson(message, GetCenter.class);
                        centerdatalist = getCenter.getData();
                        for (GetCenter.data centerlist1 : centerdatalist)
                            System.out.println("getID is" + centerlist1.getID() +
                                    "getName is" + centerlist1.getName() +
                                    "getaddress is" + centerlist1.getAddress() +
                                    "getLat is" + centerlist1.getLat() +
                                    "getLng is" + centerlist1.getLng());

//                        //设置信息到界面
                        centerdatalist.add(center_data);

//                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CenterAdapter adapter = new CenterAdapter(CompanyChoseService.this, centerdatalist);
                                listView.setAdapter(adapter);
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
