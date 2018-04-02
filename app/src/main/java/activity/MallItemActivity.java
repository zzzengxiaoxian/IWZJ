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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GetGoodsListAdapter;
import adapter.MallListAdapterB;
import bean.GetCenter;
import bean.GetGoods;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2016/11/8.
 */
public class MallItemActivity extends AppCompatActivity {


    @Bind(R.id.toolbar_companyitem)
    Toolbar toolbar;
    @Bind(R.id.item_search)
    ImageView search;
    @Bind(R.id.tv_item_title)
    TextView tv_title;
    @Bind(R.id.listView_mallitem)
    ListView listView;


    GetGoods getGoods;
    GetGoods.data getGoods_data;
    List<GetGoods.data> getGoodslist;


    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;
    private String ItemId;//获取的特惠商城ITEMID
    private String ItemName;//获取的特惠商城ITEMName
    private String centerid;//获取的选择的商城的ID

    SharedPreferences preferences, sharedPreferences;
    String url_getgoods_list = "http://www.yimijianfang.com/api/app/getgoods_list/";//<centerid>/<itemid>/<page>?token=<token>
    final OkHttpClient client = new OkHttpClient();

    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mallitem);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        //取出城市名称 id
//        preferences = getSharedPreferences("city_name", MODE_PRIVATE);
//        cityid = preferences.getString("cityId", "130600");
//        cityName = preferences.getString("chosecity", "保定市");
//        Log.i("cityid is", cityid);
//        Log.i("cityName is", cityName);

        Intent intent = getIntent();
        ItemName = intent.getStringExtra("mallName");
        ItemId = intent.getStringExtra("mallId");
        centerid = intent.getStringExtra("centercodeid");
        Log.i("ItemName is", ItemName);
        Log.i("ItemId is", ItemId);
        Log.i("centercodeid is", centerid);

        tv_title.setText(ItemName);

        //获取
        HaveToken.Token();
        token = HaveToken.str_getgoods_list;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;
        //获取服务中心网络请求数据
        getMallListRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getGoods_data = getGoodslist.get(position);
                Intent intent = new Intent();
                intent.putExtra("goodsid", getGoods_data.getID());//itemid
                intent.putExtra("centercodeid", centerid);
                intent.setClass(MallItemActivity.this,
                        MallItemDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.item_search)
    public void setSearch() {
        Intent intent = new Intent();
        intent.setClass(this, CompanySearchTwo.class);
        startActivity(intent);
    }


    private void getMallListRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getgoods_list + centerid + "/" + ItemId + "/" + "1" + "?token=" + MD5token)
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
                        Log.i("TAG", "打印GETMallItem接口响应的数据：" + message);
                        Gson gson = new Gson();
                        getGoods = gson.fromJson(message, GetGoods.class);
                        getGoodslist = getGoods.getData();
                        for (GetGoods.data getGoods1 : getGoodslist) {
                            System.out.println("getID is" + getGoods1.getID() +
                                    "getPic is" + getGoods1.getPic() +
                                    "getTitle is" + getGoods1.getTitle() +
                                    "getOldPrice is" + getGoods1.getOldPrice() +
                                    "getNewPrice is" + getGoods1.getNewPrice() +
                                    "getDes" + getGoods1.getDes());

                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("url", getGoods1.getPic());//将键值对放入map
                            map.put("Des", getGoods1.getDes());
                            map.put("Title", getGoods1.getTitle());
                            map.put("NewPrice", getGoods1.getNewPrice());
                            map.put("OldPrice", getGoods1.getOldPrice());
                            data.add(map);//将map存入list
                        }

//                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                GetGoodsListAdapter adapter = new GetGoodsListAdapter(MallItemActivity.this,getGoodslist);
                                BaseAdapter adapter = new GetGoodsListAdapter(MallItemActivity.this, data);
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
