package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import net.yanzm.mth.MaterialTabHost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.HomeActiveListAdapter;
import adapter.MallListAdapterB;
import bean.GetActiveList;
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
 * 活动天地
 * Created by dell on 2016/10/16.
 */
public class HomeActive extends AppCompatActivity {

    @Bind(R.id.gridview_homeactive)
    GridView gridView;
    @Bind(R.id.toolbar_active)
    Toolbar toolbar;
    @Bind(R.id.txtcenter)
    TextView txtcenter;
    ViewPager mViewPager;
    MaterialTabHost tabHost;


    String url_getactivelist = "http://www.yimijianfang.com/api/app/getactive_list/";//<centerid>?token=<token>
    String url_getcenter = "http://www.yimijianfang.com/api/app/getcenter/";//获取哪个服务中心
    final OkHttpClient client = new OkHttpClient();

    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;

    private String token_center;
    private String MD5token_center;
    private String centerid;
    private String centername;

    //默认第一行为显示在下拉菜单处
    private String center_defaultName;
    private String center_defaultId;


    //获取城市列表用到的bean
    GetCenter getCenter;
    GetCenter.data center_data;
    List<GetCenter.data> centerdatalist;

    SharedPreferences preferences, sharedPreferences, sp_center;

    Boolean isFirstHaveCenter = false;

    GetActiveList getActiveList;
    List<GetActiveList.data> getActivelistdata;
    GetActiveList.data getaActive_data;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    public int INDEX = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactive);

        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Initialize the ViewPager and set an adapter
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        tabHost = (MaterialTabHost) findViewById(R.id.tabhost);
//        /*SETUP*/
//        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
////        tabHost.setType(MaterialTabHost.Type.Centered);
////        tabHost.setType(MaterialTabHost.Type.LeftOffset);
//        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        for (int i = 0; i < pagerAdapter.getCount(); i++) {
//            tabHost.addTab(pagerAdapter.getPageTitle(i));
//        }
//
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOnPageChangeListener(tabHost);
//
//        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
//            @Override
//            public void onTabSelected(int position) {
//                viewPager.setCurrentItem(position);
//            }
//        });
        //取出城市名称 id
        preferences = getSharedPreferences("city_name", MODE_PRIVATE);
        cityid = preferences.getString("cityId", "130600");
        cityName = preferences.getString("chosecity", "保定市");
        Log.i("cityid is", cityid);
        Log.i("cityName is", cityName);
        //获取
        HaveToken.Token();
        token = HaveToken.str_getactivelist;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;


        token_center = HaveToken.str_center;
        MD5.md5(token_center);
        MD5token_center = MD5.b;
        //如果没有选区固定的服务中心 那么就获取默认的服务中心。
        getCenterRequest();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getaActive_data = getActivelistdata.get(position);

                Intent intent = new Intent();
                intent.putExtra("activelistId", getaActive_data.getID());//itemid
                intent.putExtra("activelistName", getaActive_data.getName());
                intent.putExtra("activecenterid", centerid);
                intent.setClass(HomeActive.this, HomeItemActive.class);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.txtcenter)
    public void place() {
        Intent intent = new Intent();
        intent.putExtra("INDEX", INDEX);
        intent.setClass(this, CompanyChoseService.class);
        startActivity(intent);
        HomeActive.this.finish();
    }

    private void getCenterRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getcenter + cityid + "?token=" + MD5token_center)
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
                            Toast.makeText(HomeActive.this, "获取数据失败，请检查网络状况", Toast.LENGTH_SHORT).show();
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

                            sp_center = getSharedPreferences("defaultcenter", MODE_PRIVATE);
                            centerid = sp_center.getString("defaultcentercode", center_defaultId);
                            centername = sp_center.getString("defaultcentername", center_defaultName);
                            System.out.println("centername1 is" + centername);
                            if (centername == null) {
                                centername = center_defaultName;
                                centerid = center_defaultId;
                                System.out.println("centername2 is" + centername);
                            }

                            getActiveRequest();

//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sharedPreferences = getSharedPreferences("firsthavecenter", MODE_PRIVATE);
                                    isFirstHaveCenter = sharedPreferences.getBoolean("firsthavecenter", true);

                                    if (isFirstHaveCenter == true) {
                                        new SweetAlertDialog(HomeActive.this)
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
                                                        intent.setClass(HomeActive.this, CompanyChoseService.class);
                                                        startActivity(intent);
                                                        HomeActive.this.finish();
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

                                    txtcenter.setText(centername);


//                                    //imagerlader加载图片没有缓存的
////                                ImageLoader.getInstance().displayImage(imgbannerurl, img_banner);
//                                    //imageloader有缓存加载图片
//                                    DisplayImageOptions options = new DisplayImageOptions.Builder()
//                                            .showStubImage(R.drawable.ic_empty)         //加载开始默认的图片
//                                            .showImageForEmptyUri(R.mipmap.ic_default_adimage)     //url爲空會显示该图片，自己放在drawable里面的
//                                            .showImageOnFail(R.drawable.ic_error)                //加载图片出现问题，会显示该图片
//                                            .cacheInMemory()                                               //缓存用
//                                            .cacheOnDisc()                                                    //缓存用
//                                            .displayer(new RoundedBitmapDisplayer(5))       //图片圆角显示，值为整数
//                                            .build();
//                                    ImageLoader.getInstance().displayImage(imgbannerurl, img_banner, options);
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


    private void getActiveRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getactivelist + centerid + "?token=" + MD5token)
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
                        Log.i("TAG", "打印GET活动天地接口的响应的数据：" + message);
                        Gson gson = new Gson();
                        getActiveList = gson.fromJson(message, GetActiveList.class);
                        getActivelistdata = getActiveList.getData();


                        for (GetActiveList.data getactive1 : getActivelistdata) {
                            System.out.println("getID is" + getactive1.getID() +
                                    "getName is" + getactive1.getName() +
                                    "getPic is" + getactive1.getPic());
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("url_activelist", getactive1.getPic());//将键值对放入map
                            map.put("name_activelist", getactive1.getName());
                            data.add(map);//将map存入list
                        }


//                        //设置信息到实体类中
                        getActivelistdata.add(getaActive_data);
//                        //UI来更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //获取数据

//                                MallListAdapterA adapter = new MallListAdapterA(HomeCompany.this,malldatalist);
                                BaseAdapter adapter = new HomeActiveListAdapter(HomeActive.this, data);
                                gridView.setAdapter(adapter);
                            }
                        });
//

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

//    /*设置  adapter*/
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            if (position == 0) {
//                return ActiveALLFragment.newInstance(position);
//            } else {
//                return ActiveServiceFragment.newInstance(position);
//            }
//
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 2;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return getString(R.string.allof).toUpperCase(l);
//                case 1:
//                    return getString(R.string.gzfwzx).toUpperCase(l);
////                case 2:
////                    return getString(R.string.title_section3).toUpperCase(l);
//            }
//            return null;
//        }
//    }


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



