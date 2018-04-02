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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.MallListAdapterB;
import bean.GetCenter;
import bean.MallList;
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
 * 特惠商城
 * Created by dell on 2016/10/17.
 */
public class HomeCompany extends AppCompatActivity {
    @Bind(R.id.toolbar_company)
    Toolbar toolbar;
    @Bind(R.id.ic_search)
    ImageView img_search;
    @Bind(R.id.place_spinner)
    View linear_place;
    @Bind(R.id.img_homecompany)
    ImageView img_banner;
    //    @Bind(R.id.drug)
//    View linear_drug;
//    @Bind(R.id.healthfood)
//    View linear_healthfood;
//    @Bind(R.id.lifeuse)
//    View linear_lifeuse;
//    @Bind(R.id.fuzhuqixie)
//    View linear_fzqx;
    @Bind(R.id.txtplace)
    TextView txtplace;
    @Bind(R.id.listView1)
    GridView listView;
    //    @Bind(R.id.loadting_progress)
//    ProgressBar progressDialog;
    //    http://www.langtianhealth.com:20080/v2/image/53ad6cb80da30b1067586a2973d4d586
    String url_citygetCenter = "http://www.yimijianfang.com/api/app/getcenter/";//<cityid>?token=<token>
    String url_getmalllist = "http://www.yimijianfang.com/api/app/getmall_list/";//<centerid>?token=<token>
    final OkHttpClient client = new OkHttpClient();

    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;

    private String token_mall;
    private String MD5token_mall;
    private String centerid;
    private String centername;

    //默认第一行为显示在下拉菜单处
    private String center_defaultName;
    private String center_defaultId;

    //获取城市列表用到的bean
    GetCenter getCenter;
    GetCenter.data center_data;
    List<GetCenter.data> centerdatalist;


    //获取特惠商城列表的bean 根据centerid获取
    MallList mallList;
    public static MallList.data mall_data;
    public static List<MallList.data> malldatalist;

    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    String imgbannerurl = "https://raw.githubusercontent.com/zzzengxiaoxian/MyApp/master/photo/thsc.png";
    SharedPreferences preferences, sharedPreferences, sp_center;
    Boolean isFirstHaveCenter = false;

    public int INDEX = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_homecompany);

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


        //获取
        HaveToken.Token();
        token = HaveToken.str_center;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;

        //获取服务中心网络请求数据


        getCenterRequest();

        token_mall = HaveToken.str_mall;
        MD5.md5(token_mall);
        MD5token_mall = MD5.b;


        //如果没有选区固定的服务中心 那么就获取默认的服务中心。
//        getMallDefaultRequest();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mall_data = malldatalist.get(position);
                Intent intent = new Intent();
                intent.putExtra("mallName", mall_data.getName());
                intent.putExtra("mallId", mall_data.getID());//itemid
                intent.putExtra("centercodeid", centerid);
                intent.setClass(HomeCompany.this,
                        MallItemActivity.class);
                startActivity(intent);
            }
        });


    }


    private void getCenterRequest() {
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
                        Log.i("TAG", "打印GET服务中心接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(HomeCompany.this, "获取数据失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            getCenter = gson.fromJson(message, GetCenter.class);
                            centerdatalist = getCenter.getData();
                            for (GetCenter.data centerlist1 : centerdatalist)
                                System.out.println("getID is" + centerlist1.getID() +
                                        "getName is" + centerlist1.getName() +
                                        "getaddress is" + centerlist1.getAddress() +
                                        "getLat is" + centerlist1.getLat() +
                                        "getLng is" + centerlist1.getLng());

//                        //设置信息到实体类中
                            centerdatalist.add(center_data);

                            center_defaultName = centerdatalist.get(0).getName().toString();
                            center_defaultId = centerdatalist.get(0).getID().toString();
                            Log.i("center_defaultName is ", center_defaultName + "center_defaultId is" + center_defaultId);//获取List中的第一行值的getname属性。
                            System.out.println("centerdatalist.size() is" + centerdatalist.size());
//                            Intent intent = getIntent();
//                            centername = intent.getStringExtra("center");
//                            centerid = intent.getStringExtra("centercode");
                            sp_center = getSharedPreferences("defaultcenter", MODE_PRIVATE);
                            centerid = sp_center.getString("defaultcentercode", center_defaultId);
                            centername = sp_center.getString("defaultcentername", center_defaultName);

                            if (centername == null) {
                                centername = center_defaultName;
                                centerid = center_defaultId;
                            }

                            //获取特惠商城网络数据请求
                            getMallRequest();

//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    sharedPreferences = getSharedPreferences("firsthavecenter", MODE_PRIVATE);
                                    isFirstHaveCenter = sharedPreferences.getBoolean("firsthavecenter", true);

                                    if (isFirstHaveCenter == true) {
                                        new SweetAlertDialog(HomeCompany.this)
                                                .setTitleText("提示")
                                                .setContentText("当前为您定位为" + centername + "点击切换进行选择服务中心")
                                                .setCancelText("切换")
                                                .setConfirmText("确认")
                                                .showCancelButton(true)
                                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        Intent intent = new Intent();
                                                        intent.putExtra("INDEX", INDEX);
                                                        intent.setClass(HomeCompany.this, CompanyChoseService.class);
                                                        startActivity(intent);
                                                        HomeCompany.this.finish();
                                                    }
                                                })
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        sweetAlertDialog.cancel();
                                                    }
                                                })
                                                .show();
                                    }
                                    txtplace.setText(centername);

                                    //imagerlader加载图片没有缓存的
//                                ImageLoader.getInstance().displayImage(imgbannerurl, img_banner);
                                    //imageloader有缓存加载图片
                                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                                            .showStubImage(R.drawable.ic_empty)         //加载开始默认的图片
                                            .showImageForEmptyUri(R.mipmap.ic_default_adimage)     //url爲空會显示该图片，自己放在drawable里面的
                                            .showImageOnFail(R.drawable.ic_error)                //加载图片出现问题，会显示该图片
                                            .cacheInMemory()                                               //缓存用
                                            .cacheOnDisc()                                                    //缓存用
                                            .displayer(new RoundedBitmapDisplayer(5))       //图片圆角显示，值为整数
                                            .build();
                                    ImageLoader.getInstance().displayImage(imgbannerurl, img_banner, options);
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

    private void getMallDefaultRequest() {

    }

    private void getMallRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getmalllist + centerid + "?token=" + MD5token_mall)
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
                        Log.i("TAG", "打印GET特惠商城接口的响应的数据：" + message);
                        Gson gson = new Gson();
                        mallList = gson.fromJson(message, MallList.class);
                        malldatalist = mallList.getData();


                        for (MallList.data malllist1 : malldatalist) {
                            System.out.println("getID is" + malllist1.getID() +
                                    "getName is" + malllist1.getName() +
                                    "getPic is" + malllist1.getPic());
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("url", malllist1.getPic());//将键值对放入map
                            map.put("name", malllist1.getName());
                            data.add(map);//将map存入list
                        }


//                        //设置信息到实体类中
                        malldatalist.add(mall_data);
//                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //获取数据

//                                MallListAdapterA adapter = new MallListAdapterA(HomeCompany.this,malldatalist);
                                BaseAdapter adapter = new MallListAdapterB(HomeCompany.this, data);
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

    @OnClick(R.id.ic_search)
    public void search() {
        Intent intent = new Intent();
        intent.setClass(this, CompanySearchTwo.class);
        startActivity(intent);
    }

    @OnClick(R.id.place_spinner)
    public void place() {
        Intent intent = new Intent();
        intent.putExtra("INDEX", INDEX);
        intent.setClass(this, CompanyChoseService.class);
        startActivity(intent);
        HomeCompany.this.finish();
    }

//    @OnClick(R.id.drug)
//    public void drug() {
//        Intent intent = new Intent();
//        intent.setClass(this, CompanyDrug.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.lifeuse)
//    public void life() {
//        Intent intent = new Intent();
//        intent.setClass(this, CompanyLifeUse.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.healthfood)
//    public void food() {
//        Intent intent = new Intent();
//        intent.setClass(this, CompanyHealthFood.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.fuzhuqixie)
//    public void machine() {
//        Intent intent = new Intent();
//        intent.setClass(this, CompanyMachine.class);
//        startActivity(intent);
//    }


}
