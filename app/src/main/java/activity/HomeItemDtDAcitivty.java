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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.iwzj.ltkj.iwzj.ThisApplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.DtdServiceAdapter;
import adapter.DtdServiceItemListAdapter;
import bean.GetDtdService;
import bean.GetDtdServiceItemList;
import butterknife.Bind;
import butterknife.ButterKnife;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import service.LocationService;

/**
 * 服务列表
 * Created by dell on 2016/11/8.
 */
public class HomeItemDtDAcitivty extends AppCompatActivity {

    @Bind(R.id.toolbar_dtditem)
    Toolbar toolbar;
    @Bind(R.id.tv_dtditem_titleName)
    TextView tv_title;
    @Bind(R.id.tv_item_dtdshaixuan)
    TextView tv_dtdshaxuan;
    @Bind(R.id.homeitemdtd_listview)
    ListView listView;

    String url_getdtdservice_list = "http://www.yimijianfang.com/api/app/getservice_list/";//<cityid>/<itemid>/<page>/<applng>/</applat>?token=<token>
    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;
    private String DtdServiceItemId;
    private String DtdServiceItemName;
    private double applng;
    private double applat;
    final OkHttpClient client = new OkHttpClient();
    SharedPreferences preferences;


    GetDtdServiceItemList getDtdServiceItemList;
    List<GetDtdServiceItemList.data> getDtdServicelistList;
    GetDtdServiceItemList.data getDtdServiceList_data;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeitemdtd);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

        }


        //取出城市名称 id
        preferences = getSharedPreferences("city_name", MODE_PRIVATE);
        cityid = preferences.getString("cityId", "130600");
        cityName = preferences.getString("chosecity", "保定市");
        Log.i("cityid is", cityid);
        Log.i("cityName is", cityName);

        Intent intent = getIntent();
        DtdServiceItemId = intent.getStringExtra("dtdserviceId");
        DtdServiceItemName = intent.getStringExtra("dtdserviceName");
        applat = intent.getDoubleExtra("latitude", 38);
        applng = intent.getDoubleExtra("lontitude", 115);

        System.out.print("applat is+" + applat + "applng is" + applng);

        //获取
        HaveToken.Token();
        token = HaveToken.str_getdtdservicelist;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;

        //获取服务中心网络请求数据
        getDtdservicelistRequest();

//        locationService.stop();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getDtdServiceList_data = getDtdServicelistList.get(position);

                Intent intent = new Intent();
                intent.putExtra("DetailId", getDtdServiceList_data.getID());
                intent.putExtra("mylatitude", applat);
                intent.putExtra("mylongtitude", applng);
                intent.setClass(HomeItemDtDAcitivty.this,
                        DtdDetailActivity.class);
                startActivity(intent);
            }
        });

    }


    private void getDtdservicelistRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        if (applng == 0 || applat == 0) {
            System.out.print("经纬度为0");
            Log.i("JINGWEIDUWEI经纬度为0", "JINGWEIDUWEI经纬度为0");
        }
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getdtdservice_list + cityid + "/" + DtdServiceItemId + "/" + "1" + "/" + applat + "/" + applng + "/" + "?token=" + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET服务中心接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(HomeItemDtDAcitivty.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {


                            Gson gson = new Gson();
                            getDtdServiceItemList = gson.fromJson(message, GetDtdServiceItemList.class);
                            getDtdServicelistList = getDtdServiceItemList.getData();
                            for (GetDtdServiceItemList.data getdtdserviceitemlist1 : getDtdServicelistList) {
                                System.out.println("getID is" + getdtdserviceitemlist1.getID() +
                                        "getName is" + getdtdserviceitemlist1.getName() +
                                        "get pic is" + getdtdserviceitemlist1.getPic() +
                                        "tel" + getdtdserviceitemlist1.getTel() +
                                        "get area" + getdtdserviceitemlist1.getArea() +
                                        "get length" + getdtdserviceitemlist1.getLength());

                                Map<String, Object> map1 = new HashMap<String, Object>();
                                map1.put("dtdlistname", getdtdserviceitemlist1.getName());
                                map1.put("dtdlisturl", getdtdserviceitemlist1.getPic());//将键值对放入map
                                map1.put("dtdlistphone", getdtdserviceitemlist1.getTel());//将键值对放入map
                                map1.put("dtdarea", getdtdserviceitemlist1.getArea());//将键值对放入map
                                map1.put("dtdlength", getdtdserviceitemlist1.getLength());//将键值对放入map

                                data.add(map1);//将map存入list

                            }
//                        //设置信息到实体类中
                            getDtdServicelistList.add(getDtdServiceList_data);


//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_title.setText(DtdServiceItemName);

                                    BaseAdapter adapter = new DtdServiceItemListAdapter(HomeItemDtDAcitivty.this, data);
                                    listView.setAdapter(adapter);

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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
