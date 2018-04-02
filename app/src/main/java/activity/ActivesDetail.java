package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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

import adapter.ActivesDetailTimesSeriesAdapter;
import bean.GetActivesItemDetailCycle;
import bean.GetActivesItemDetailOnce;
import bean.GetActivesItemDetailSeries;
import butterknife.Bind;
import butterknife.ButterKnife;
import common.GetImageByUrl;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 活动详情界面
 * Created by dell on 2016/11/15.
 */
public class ActivesDetail extends AppCompatActivity {
    //单次
    @Bind(R.id.toolbar_activedetail_once)
    Toolbar toolbar;
    @Bind(R.id.tv_activesdetail_once)
    TextView tv_tollbartitle;
    @Bind(R.id.img_activedetail_once)
    ImageView img_banner;
    @Bind(R.id.tv_activedetail_img_type_once)
    ImageView img_type;
    @Bind(R.id.tv_activedetail_title_once)
    TextView tv_activedetail_title;
    @Bind(R.id.tv_activedetail_Des_once)
    TextView tv_activedetail_des;
    @Bind(R.id.tv_activedetail_cost_once)
    TextView tv_activedetail_cost;
    @Bind(R.id.tv_activedetail_coupon_once)
    TextView tv_activedetail_coupon;
    @Bind(R.id.tv_activedetail_address_once)
    TextView tv_activedetail_address;
    @Bind(R.id.tv_activedetail_stimes_once)
    TextView tv_activedetail_stimes;
    @Bind(R.id.tv_activedetail_e_times_once)
    TextView tv_activedetail_etimes;

    //系类
    Toolbar toolbar_series;
    TextView tv_tollbartitle_series;
    ImageView img_banner_series;
    ImageView img_type_series;
    TextView tv_activedetail_title_series;
    TextView tv_activedetail_des_series;
    TextView tv_activedetail_cost_series;
    TextView tv_activedetail_coupon_series;
    TextView tv_activedetail_address_series;
    ListView listView_series;


    //循环
    Toolbar toolbar_cycle;
    TextView tv_tollbartitle_cycle;
    ImageView img_banner_cycle;
    ImageView img_type_cycle;
    TextView tv_activedetail_title_cycle;
    TextView tv_activedetail_des_cycle, tv_activedetail_e_times_cycle;
    TextView tv_activedetail_cost_cycle, tv_activedetail_edate_cycle;
    TextView tv_activedetail_coupon_cycle, tv_activedetail_sdate_cycle;
    TextView tv_activedetail_address_cycle, tv_activedetail_stimes_cycle, tv_activedetail_week_cycle;


    String url_getactivesdetail = "http://www.yimijianfang.com/api/app/actices_detail/";//<id>/<type>/?token=<token>
    private String token;
    private String MD5token;
    private String ActivesItemDetailId;
    private String ActivesItemType;

    //系列实体类
    GetActivesItemDetailSeries getActivesItemDetail;
    GetActivesItemDetailSeries.data getActivesItemDeatil_data;
    GetActivesItemDetailSeries.data.times getActivesItemDetail_times;
    List<GetActivesItemDetailSeries.data.times> getactivesitemdetaitimeslist;

    //一次实体类
    GetActivesItemDetailOnce getActivesItemDetailOnce;
    GetActivesItemDetailOnce.data getActivesItemDetailOnce_data;

    //循环实体类
    GetActivesItemDetailCycle getActivesItemDetailCycle;
    GetActivesItemDetailCycle.data getActivesItemDetailCycle_data;


    private String ActiveType;
    private String Activecoupon;
    private String Activecost;
    private String ActiveTitle;
    private String Activeaddress;
    private String Activelng;
    private String Activelat;
    private String ActiveDes;
    private String Actives_sdate;
    private String Activee_edate;
    private String ActivePic;
    private String ActiveItemName;
    //循环额外添加：
    private String Activeweek_zh;
    private String Actives_time;
    private String Activee_time;


    final OkHttpClient client = new OkHttpClient();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ActivesItemDetailId = intent.getStringExtra("ActivesItemID");
        ActivesItemType = intent.getStringExtra("ActivesItemType");
        ActiveItemName = intent.getStringExtra("ActiveItemName");

        if (ActivesItemType.equals("1")) {//单词
            setContentView(R.layout.activity_activedetail_once);
            ButterKnife.bind(this);
            tv_tollbartitle.setText(ActiveItemName);
            if (toolbar != null) {
                toolbar.setTitle(" ");
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);

            }
        } else {
            if (ActivesItemType.equals("2")) {//系列
                setContentView(R.layout.activity_activedetail_series);
                toolbar_series = (Toolbar) findViewById(R.id.toolbar_activedetail_series);
                tv_tollbartitle_series = (TextView) findViewById(R.id.tv_activesdetail_series);
                img_banner_series = (ImageView) findViewById(R.id.img_activedetail_series);
                img_type_series = (ImageView) findViewById(R.id.tv_activedetail_img_type_series);
                tv_activedetail_title_series = (TextView) findViewById(R.id.tv_activedetail_title_series);
                tv_activedetail_des_series = (TextView) findViewById(R.id.tv_activedetail_Des_series);
                tv_activedetail_cost_series = (TextView) findViewById(R.id.tv_activedetail_cost_series);
                tv_activedetail_coupon_series = (TextView) findViewById(R.id.tv_activedetail_coupon_series);
                tv_activedetail_address_series = (TextView) findViewById(R.id.tv_activedetail_address_series);
                listView_series = (ListView) findViewById(R.id.listview_detail_serices);
                tv_tollbartitle_series.setText(ActiveItemName);
                if (toolbar_series != null) {
                    toolbar_series.setTitle(" ");
                    setSupportActionBar(toolbar_series);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayHomeAsUpEnabled(true);

                }
            } else {//循环
                setContentView(R.layout.activity_activedetail_cycle);
                toolbar_cycle = (Toolbar) findViewById(R.id.toolbar_activedetail_cycle);
                tv_tollbartitle_cycle = (TextView) findViewById(R.id.tv_activesdetail_cycle);
                img_banner_cycle = (ImageView) findViewById(R.id.img_activedetail_cycle);
                img_type_cycle = (ImageView) findViewById(R.id.tv_activedetail_img_type_cycle);
                tv_activedetail_title_cycle = (TextView) findViewById(R.id.tv_activedetail_title_cycle);
                tv_activedetail_des_cycle = (TextView) findViewById(R.id.tv_activedetail_Des_cycle);
                tv_activedetail_cost_cycle = (TextView) findViewById(R.id.tv_activedetail_cost_cycle);
                tv_activedetail_coupon_cycle = (TextView) findViewById(R.id.tv_activedetail_coupon_cycle);
                tv_activedetail_address_cycle = (TextView) findViewById(R.id.tv_activedetail_address_cycle);
                tv_activedetail_stimes_cycle = (TextView) findViewById(R.id.tv_activedetail_stimes_cycle);
                tv_activedetail_edate_cycle = (TextView) findViewById(R.id.tv_activedetail_e_date_cycle);
                tv_activedetail_sdate_cycle = (TextView) findViewById(R.id.tv_activedetail_sdate_cycle);
                tv_activedetail_e_times_cycle = (TextView) findViewById(R.id.tv_activedetail_e_times_cycle);
                tv_activedetail_week_cycle = (TextView) findViewById(R.id.tv_activedetail_week_cycle);
                tv_tollbartitle_cycle.setText(ActiveItemName);
                if (toolbar_cycle != null) {
                    toolbar_cycle.setTitle(" ");
                    setSupportActionBar(toolbar_cycle);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayHomeAsUpEnabled(true);

                }
            }
        }


        //获取
        HaveToken.Token();
        token = HaveToken.str_getactivesdetail;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;

        //获取服务中心网络请求数据
        getActivesDetailRequest();
    }


    private void getActivesDetailRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。


        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getactivesdetail + ActivesItemDetailId + "/" + ActivesItemType + "/" + "?token=" + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET一项活动具体内容接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(ActivesDetail.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {


                            Gson gson = new Gson();
                            if (ActivesItemType.equals("1")) {//单次
                                getActivesItemDetailOnce = gson.fromJson(message, GetActivesItemDetailOnce.class);

                                ActivePic = getActivesItemDetailOnce.getData().getPic();
                                ActiveType = getActivesItemDetailOnce.getData().getType();
                                Activecoupon = getActivesItemDetailOnce.getData().getCoupon();
                                Activecost = getActivesItemDetailOnce.getData().getCost();
                                ActiveTitle = getActivesItemDetailOnce.getData().getTitle();
                                Activeaddress = getActivesItemDetailOnce.getData().getAddress();
                                Activelng = getActivesItemDetailOnce.getData().getLng();
                                Activelat = getActivesItemDetailOnce.getData().getLat();
                                ActiveDes = getActivesItemDetailOnce.getData().getDes();
                                Actives_sdate = getActivesItemDetailOnce.getData().getS_datetime();
                                Activee_edate = getActivesItemDetailOnce.getData().getE_datetime();

//                        //UI来更新UI
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
                                        GetImageByUrl getImageByUrl = new GetImageByUrl();
                                        getImageByUrl.setImage(img_banner, ActivePic);
                                        img_type.setImageResource(R.mipmap.ic_yici);
                                        tv_activedetail_title.setText(ActiveTitle);
                                        tv_activedetail_coupon.setText("限定人数:" + Activecoupon);
                                        tv_activedetail_cost.setText("花费:" + Activecost);
                                        tv_activedetail_address.setText(Activeaddress);
                                        tv_activedetail_address.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        });

                                        tv_activedetail_des.setText(ActiveDes);
                                        tv_activedetail_stimes.setText(Actives_sdate);
                                        tv_activedetail_etimes.setText(Activee_edate);

                                    }
                                });
                            } else {
                                if (ActivesItemType.equals("2")) {//系列
                                    getActivesItemDetail = gson.fromJson(message, GetActivesItemDetailSeries.class);
                                    getactivesitemdetaitimeslist = getActivesItemDetail.getData().getTimes();
                                    for (GetActivesItemDetailSeries.data.times gethomeactiveitem1 : getactivesitemdetaitimeslist) {
                                        System.out.println("getID is" + gethomeactiveitem1.getID() +
                                                "getTitle is" + gethomeactiveitem1.getE_date() +
                                                "getTitle is" + gethomeactiveitem1.getS_date());
                                        Map<String, Object> map1 = new HashMap<String, Object>();
                                        map1.put("activeTimeEdate", gethomeactiveitem1.getE_date());//将键值对放入map
                                        map1.put("activeTimeSdate", gethomeactiveitem1.getS_date());//将键值对放入map
                                        data.add(map1);//将map存入list
                                    }


                                    ActivePic = getActivesItemDetail.getData().getPic();
                                    ActiveType = getActivesItemDetail.getData().getType();
                                    Activecoupon = getActivesItemDetail.getData().getCoupon();
                                    Activecost = getActivesItemDetail.getData().getCost();
                                    ActiveTitle = getActivesItemDetail.getData().getTitle();
                                    Activeaddress = getActivesItemDetail.getData().getAddress();
                                    Activelng = getActivesItemDetail.getData().getLng();
                                    Activelat = getActivesItemDetail.getData().getLat();
                                    ActiveDes = getActivesItemDetail.getData().getDes();

//                        //UI来更新UI
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            BaseAdapter adapter = new ActivesDetailTimesSeriesAdapter(ActivesDetail.this, data);
                                            listView_series.setAdapter(adapter);
                                            setListViewHeightBasedOnChildren(listView_series);
                                            // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
                                            GetImageByUrl getImageByUrl = new GetImageByUrl();
                                            getImageByUrl.setImage(img_banner_series, ActivePic);
                                            img_type_series.setImageResource(R.mipmap.ic_xielie);
                                            tv_activedetail_title_series.setText(ActiveTitle);
                                            tv_activedetail_coupon_series.setText("名额:" + Activecoupon);
                                            tv_activedetail_cost_series.setText("花费金额:" + Activecost);
                                            tv_activedetail_address_series.setText(Activeaddress);
                                            tv_activedetail_address_series.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {


                                                }
                                            });
                                            tv_activedetail_des_series.setText(ActiveDes);

                                        }
                                    });
                                } else {//循环
                                    getActivesItemDetailCycle = gson.fromJson(message, GetActivesItemDetailCycle.class);
                                    ActivePic = getActivesItemDetailCycle.getData().getPic();
                                    ActiveType = getActivesItemDetailCycle.getData().getType();
                                    Activecoupon = getActivesItemDetailCycle.getData().getCoupon();
                                    Activecost = getActivesItemDetailCycle.getData().getCost();
                                    ActiveTitle = getActivesItemDetailCycle.getData().getTitle();
                                    Activeaddress = getActivesItemDetailCycle.getData().getAddress();
                                    Activelng = getActivesItemDetailCycle.getData().getLng();
                                    Activelat = getActivesItemDetailCycle.getData().getLat();
                                    ActiveDes = getActivesItemDetailCycle.getData().getDes();
                                    Actives_sdate = getActivesItemDetailCycle.getData().getS_date();
                                    Activee_edate = getActivesItemDetailCycle.getData().getE_date();
                                    Actives_time = getActivesItemDetailCycle.getData().getS_time();
                                    Activee_time = getActivesItemDetailCycle.getData().getE_time();
                                    Activeweek_zh = getActivesItemDetailCycle.getData().getWeek_zh();

//                        //UI来更新UI
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
                                            GetImageByUrl getImageByUrl = new GetImageByUrl();
                                            getImageByUrl.setImage(img_banner_cycle, ActivePic);
                                            img_type_cycle.setImageResource(R.mipmap.ic_xunhuan);
                                            tv_activedetail_title_cycle.setText(ActiveTitle);
                                            tv_activedetail_coupon_cycle.setText("名额:" + Activecoupon);
                                            tv_activedetail_cost_cycle.setText("花费:" + Activecost);
                                            tv_activedetail_address_cycle.setText(Activeaddress);
                                            tv_activedetail_address_cycle.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {


                                                }
                                            });
                                            tv_activedetail_des_cycle.setText(ActiveDes);
                                            tv_activedetail_sdate_cycle.setText(Actives_sdate);
                                            tv_activedetail_edate_cycle.setText(Activee_edate);
                                            tv_activedetail_stimes_cycle.setText(Actives_time);
                                            tv_activedetail_e_times_cycle.setText(Activee_time);
                                            tv_activedetail_week_cycle.setText(Activeweek_zh);


                                        }
                                    });
                                }
                            }
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


    /**
     * spinner listview chongtu
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
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
