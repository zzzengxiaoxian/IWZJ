package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.iwzj.ltkj.iwzj.ThisApplication;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import adapter.CityListAdapter;
import adapter.ContentListAdapter;
import bean.ChoseCity;
import bean.ShowapiNews;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import service.LocationService;

/**
 * 定位城市获取城市
 * Created by dell on 2016/10/28.
 */
public class HomePlace extends AppCompatActivity {

    private LocationService locationService;

    @Bind(R.id.toolbar_homeplace)
    Toolbar toolbar;
    @Bind(R.id.tv_placename)
    TextView placename;

    @Bind(R.id.listPlace)
    ListView listView;

    private Button startLocation;
    private String city;
    private String token;
    private String MD5token;
    //获取城市列表用到的bean
    ChoseCity choseCity;
    ChoseCity.data city_data;
    List<ChoseCity.data> citydatalist;


    String url_citylist = "http://www.yimijianfang.com/api/app/citys?token=";
    final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeplace);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        startLocation = (Button) findViewById(R.id.addfence);

        //获取TOKEN
        HaveToken.Token();
        token = HaveToken.str_city;
        MD5.md5(token);
        MD5token = MD5.b;
        Log.i("url", MD5token);


        getRequest();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city_data = citydatalist.get(position);
                Intent intent = new Intent();
                intent.putExtra("city", city_data.getName());
                intent.putExtra("citycode", city_data.getCode());
//                intent.putExtra("first", "isfirst");
//                setResult(RESULT_OK, intent);//获取城市 是否是第一次
                intent.setClass(HomePlace.this,
                        MainActivity.class);
                startActivity(intent);
                HomePlace.this.finish();
            }
        });

    }


    private void getRequest() {
//Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_citylist + MD5token)
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
                        if (message.isEmpty() || message == null) {

                            Toast.makeText(HomePlace.this, "请检查网络是否连接，获取数据失败", Toast.LENGTH_SHORT).show();
                        } else {


                            Gson gson = new Gson();
                            choseCity = gson.fromJson(message, ChoseCity.class);
                            citydatalist = choseCity.getData();
                            for (ChoseCity.data contentlist1 : citydatalist)
                                System.out.println("getCode is" + contentlist1.getCode() +
                                        "getName is" + contentlist1.getName());

                            //设置信息到界面
                            citydatalist.add(city_data);
                            //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CityListAdapter adapter = new CityListAdapter(HomePlace.this, citydatalist);
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

    @OnClick(R.id.tv_placename)
    public void place() {

        Intent intent = new Intent();
        intent.putExtra("city", city);
        intent.setClass(HomePlace.this, MainActivity.class);
        startActivity(intent);
        HomePlace.this.finish();
    }


    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        try {
            Log.i("LOGmES", str);
//            if (placename != null)
//                placename.setText(str);
            if (placename != null)
                placename.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((ThisApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        startLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (startLocation.getText().toString().equals(getString(R.string.startlocation))) {
                    locationService.start();// 定位SDK
                    // start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
                    startLocation.setText(getString(R.string.stoplocation));
                } else {
                    locationService.stop();
                    startLocation.setText(getString(R.string.startlocation));
                }
            }
        });
    }

    /*****
     * copy to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
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
//                int start=sb.toString().indexOf("city :");
//                int end=sb.toString().indexOf("District");
//                city=sb.toString().substring(start+1,end);
//                logMsg(city);
//                Log.i("LOGMSG",city);

            }


            city = location.getCity();

            logMsg(city);
            Log.i("LOGMSG", city);


        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
