package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.HomeItemActivesAdapter;
import bean.GetHomeActiveItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 活动天地某一类互动的所有活动事件列表界面
 * Created by dell on 2016/11/14.
 */
public class HomeItemActive extends AppCompatActivity {
    @Bind(R.id.toolbar_homeactiveitem)
    Toolbar toolbar;
    @Bind(R.id.tv_homeactiveitem_titleName)
    TextView tv_title;
    @Bind(R.id.tv_item_homeactiveshaixuan)
    TextView tv_dtdshaxuan;
    @Bind(R.id.homeitemactive_listview)
    ListView listView;
    String url_getactiveitem_list = "http://www.yimijianfang.com/api/app/getactives_list/";//<centerid>/<itemid>/<page>/<applng>/</applat>?token=<token>
    private String token;
    private String MD5token;
    private String ActiveItemId;
    private String ActiveItemName;
    private String centerid;
    final OkHttpClient client = new OkHttpClient();

    GetHomeActiveItem getHomeActiveItem;
    GetHomeActiveItem.data getHomeActiveItem_data;
    List<GetHomeActiveItem.data> getHomeActivedatalist;


    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    private int page = 1;
    int Index;
    XRefreshView refreshView;
    public static long lastRefreshTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeitemactive);

        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        refreshView = (XRefreshView) findViewById(R.id.custom_view);
        Intent intent = getIntent();
        ActiveItemId = intent.getStringExtra("activelistId");
        ActiveItemName = intent.getStringExtra("activelistName");
        centerid = intent.getStringExtra("activecenterid");

        tv_title.setText(ActiveItemName);
        //获取
        HaveToken.Token();
        token = HaveToken.str_getactiveslist;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;

        if (page == 1) {
            //获取服务中心网络请求数据
            getActiveItemRequest();
        }


//        refreshView.setOnAbsListViewScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                LogUtils.i("onScrollStateChanged");
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                LogUtils.i("onScroll");
//            }
//        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getHomeActiveItem_data = getHomeActivedatalist.get(position);

                Intent intent = new Intent();
                intent.putExtra("ActivesItemID", getHomeActiveItem_data.getID());
                intent.putExtra("ActivesItemType", getHomeActiveItem_data.getType());
                intent.putExtra("ActiveItemName", getHomeActiveItem_data.getTitle());
                intent.setClass(HomeItemActive.this,
                        ActivesDetail.class);
                startActivity(intent);
            }
        });
        // 设置是否可以下拉刷新
        refreshView.setPullRefreshEnable(false);
        // 设置是否可以上拉加载
        refreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        refreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        //        refreshView.setAutoRefresh(false);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.stopRefresh();
                        lastRefreshTime = refreshView.getLastRefreshTime();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getActiveItemRequest();
                        refreshView.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
                if (direction > 0) {
                    Toast.makeText(getApplicationContext(), "下拉", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "上拉", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @OnClick(R.id.tv_item_homeactiveshaixuan)
    public void onshaixuan() {

    }


    private void getActiveItemRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        page = page + 1;
        System.out.print("page is" + page);
        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getactiveitem_list + centerid + "/" + ActiveItemId + "/" + "\"" + page + "\"" + "/" + "?token=" + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET活动天地详情接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(HomeItemActive.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            getHomeActiveItem = gson.fromJson(message, GetHomeActiveItem.class);
                            getHomeActivedatalist = getHomeActiveItem.getData();

                            for (GetHomeActiveItem.data gethomeactiveitem1 : getHomeActivedatalist) {
                                System.out.println("getID is" + gethomeactiveitem1.getID() +
                                        "getTitle is" + gethomeactiveitem1.getTitle() +
                                        "get pic is" + gethomeactiveitem1.getPic() +
                                        "getType" + gethomeactiveitem1.getType() +
                                        "get getCoupon" + gethomeactiveitem1.getCoupon() +
                                        "get getCost" + gethomeactiveitem1.getCost());

                                Map<String, Object> map1 = new HashMap<String, Object>();
                                map1.put("activetitle", gethomeactiveitem1.getTitle());
                                map1.put("activepic", gethomeactiveitem1.getPic());//将键值对放入map
                                map1.put("acticetype", gethomeactiveitem1.getType());//将键值对放入map
                                map1.put("activecoupon", gethomeactiveitem1.getCoupon());//将键值对放入map
                                map1.put("activecost", gethomeactiveitem1.getCost());//将键值对放入map
                                data.add(map1);//将map存入list

                            }

                            if (data.isEmpty()) {

                                Index = 1;
                            }

//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (Index == 0) {
                                        BaseAdapter adapter = new HomeItemActivesAdapter(HomeItemActive.this, data);
                                        listView.setAdapter(adapter);
                                    } else {//模拟没有更多数据的情况
                                        refreshView.setLoadComplete(true);
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
