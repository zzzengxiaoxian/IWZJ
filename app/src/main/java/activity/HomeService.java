package activity;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.DtdServiceAdapter;
import bean.GetDtdService;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 上门服务
 * Created by dell on 2016/10/17.
 */
public class HomeService extends AppCompatActivity {

    @Bind(R.id.toolbar_serivce)
    Toolbar toolbar;
    @Bind(R.id.service_search)
    EditText tvsearch;
    //    @Bind(R.id.listview_service)
//    ListView listView;
    @Bind(R.id.gridview_dtdservice)
    GridView gridView;

    private Dialog progressDialog;

    String url_getdtd_list = "http://www.yimijianfang.com/api/app/getdtd_list/";//<centerid>?token=<token>
    private String token;
    private String cityid;
    private String MD5token;
    private String cityName;
    final OkHttpClient client = new OkHttpClient();
    SharedPreferences preferences;

    GetDtdService getDtdService;
    GetDtdService.data getDtdService_data;
    List<GetDtdService.data> getDtdServicelist;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    private double applng;
    private double applat;
    private boolean isstart;
    //    private LocationService locationService;//定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeservice);
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        //加载动画
        progressDialog = new Dialog(HomeService.this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.loading);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("努力加载中");
        progressDialog.show();


        //开始定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();


        //取出城市名称 id
        preferences = getSharedPreferences("city_name", MODE_PRIVATE);
        cityid = preferences.getString("cityId", "130600");
        cityName = preferences.getString("chosecity", "保定市");
        Log.i("cityid is", cityid);
        Log.i("cityName is", cityName);

        //获取
        HaveToken.Token();
        token = HaveToken.str_dtdservice;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;
        //获取服务中心网络请求数据
        getServiceRequest();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getDtdService_data = getDtdServicelist.get(position);

                Intent intent = new Intent();
                intent.putExtra("dtdserviceId", getDtdService_data.getID());//itemid
                intent.putExtra("dtdserviceName", getDtdService_data.getName());
                intent.putExtra("latitude", applat);
                intent.putExtra("lontitude", applng);
                intent.setClass(HomeService.this, HomeItemDtDAcitivty.class);
                startActivity(intent);
            }
        });

    }


    private void getServiceRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getdtd_list + cityid + "?token=" + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {

                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();

                        String message = response.body().string();
                        Log.i("TAG", "打印GET服务中心接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(HomeService.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            getDtdService = gson.fromJson(message, GetDtdService.class);
                            getDtdServicelist = getDtdService.getData();
                            for (GetDtdService.data getdtdservice1 : getDtdServicelist) {
                                System.out.println("getID is" + getdtdservice1.getID() +
                                        "getName is" + getdtdservice1.getName() +
                                        "get pic is" + getdtdservice1.getPic());

                                Map<String, Object> map1 = new HashMap<String, Object>();
                                map1.put("dtdname", getdtdservice1.getName());
                                map1.put("dtdurl", getdtdservice1.getPic());//将键值对放入map
                                data.add(map1);//将map存入list

                            }
//                        //设置信息到实体类中
                            getDtdServicelist.add(getDtdService_data);


//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseAdapter adapter = new DtdServiceAdapter(HomeService.this, data);
                                    gridView.setAdapter(adapter);


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


    @OnClick(R.id.service_search)
    public void search() {
        Intent intent = new Intent();
        intent.setClass(this, CompanySearchTwo.class);
        startActivity(intent);
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


    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        mLocationClient.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop(); //停止定位服务
        super.onDestroy();
    }
    //---------------------------获取经纬度

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());

            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            applat = location.getLatitude();
            System.out.println("applat is" + applat);
            applng = location.getLongitude();
            System.out.println("applng is" + applng);
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }

    /*高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
       低功耗定位模式：这种定位模式下，不会使用GPS进行定位，只会使用网络定位（WiFi定位和基站定位）；
       仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
       低功耗定位模式：这种定位模式下，不会使用GPS进行定位，只会使用网络定位（WiFi定位和基站定位）；
       仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。*/
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);

    }
}
