package activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.GetDtdServiceDetail;
import bean.GetGoodsDetail;
import butterknife.Bind;
import butterknife.ButterKnife;
import common.GetImageByUrl;
import common.HaveToken;
import common.MD5;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商户详情
 * Created by dell on 2016/11/10.
 */
public class DtdDetailActivity extends AppCompatActivity {


    @Bind(R.id.toolbar_dtddetail)
    Toolbar toolbar;
    @Bind(R.id.img_dtddetailpic)
    ImageView imageView;
    @Bind(R.id.tv_dtddetailname)
    TextView tv_detailname;
    @Bind(R.id.tv_dtddetailServiceArea)
    TextView tv_dtddetailServiceArea;
    @Bind(R.id.tv_dtddetailServiceContent)
    TextView tv_dtddetailServiceContent;
    @Bind(R.id.tv_dtdphone)
    TextView tv_dtdphone;
    @Bind(R.id.tv_localhost)
    TextView tv_localhost;
    @Bind(R.id.tv_dtddetailServiceIntroduce)
    TextView tv_dtddetailServiceIntroduce;
    @Bind(R.id.tv_dtddetailServiceTime)
    TextView tv_dtddetailServiceTime;

    String url_dtdDetail = "http://www.yimijianfang.com/api/app/dtd_detail/";//<id>/?token=<token>
    final OkHttpClient client = new OkHttpClient();

    private String dtdid;
    private String token;
    private String MD5token;

    GetDtdServiceDetail getDtdServiceDetail;
    GetDtdServiceDetail.data getDtdDetaildata;
    private String ID;
    private String Pic;
    private String Name;
    private String lng;
    private String lat;
    private String s_area;
    private String Content;
    private String address;
    private String tel;
    private String introduce;
    private String stime;
    private String etime;
    private String ZZpic;
    private String area;

    private double mylatitude, mylontitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtddetail);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        dtdid = intent.getStringExtra("DetailId");
        mylatitude = intent.getDoubleExtra("mylatitude", 38);
        mylontitude = intent.getDoubleExtra("mylongtitude", 115);


        //获取TOKEN
        HaveToken.Token();
        token = HaveToken.str_getdtdservicedetail;
        MD5.md5(token);
        MD5token = MD5.b;
        getDtdDetailRequest();

    }

    private void getDtdDetailRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_dtdDetail + dtdid + "/" + "?token=" + MD5token)
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
                            Toast.makeText(DtdDetailActivity.this, "获取数据失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            getDtdServiceDetail = gson.fromJson(message, GetDtdServiceDetail.class);

                            ID = getDtdServiceDetail.getData().getID();
                            Pic = getDtdServiceDetail.getData().getPic();
                            Name = getDtdServiceDetail.getData().getName();
                            Content = getDtdServiceDetail.getData().getContent();
                            area = getDtdServiceDetail.getData().getArea();
                            address = getDtdServiceDetail.getData().getAddress();
                            tel = getDtdServiceDetail.getData().getTel();
                            stime = getDtdServiceDetail.getData().getStime();
                            etime = getDtdServiceDetail.getData().getEtime();
                            introduce = getDtdServiceDetail.getData().getIntroduce();
                            lng = getDtdServiceDetail.getData().getLng();
                            lat = getDtdServiceDetail.getData().getLat();
                            //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    Spannable fwnr = new SpannableString("服务内容：" + Content);
                                    fwnr.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Spannable fwfw = new SpannableString("服务范围：" + area);
                                    fwfw.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Spannable yysj = new SpannableString("营业时间：" + stime + "-" + etime);
                                    yysj.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    Spannable shjs = new SpannableString("商户介绍：" + introduce);
                                    shjs.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                    tv_detailname.setText(Name);
                                    tv_dtddetailServiceContent.setText(fwnr);
                                    tv_dtddetailServiceArea.setText(fwfw);
                                    tv_dtdphone.setText(tel);
                                    tv_localhost.setText(address);
                                    tv_dtddetailServiceTime.setText(yysj);
                                    tv_dtddetailServiceIntroduce.setText(shjs);

                                    tv_localhost.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent();
                                            intent.putExtra("dtdlng", lng);
                                            intent.putExtra("dtdlat", lat);
                                            intent.putExtra("myselflat", mylatitude);
                                            intent.putExtra("myselflng", mylontitude);
                                            intent.setClass(DtdDetailActivity.this, DtdServiceLocalhostMap.class);
                                            startActivity(intent);

                                        }
                                    });

                                    tv_dtdphone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });

                                    // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
                                    GetImageByUrl getImageByUrl = new GetImageByUrl();
                                    getImageByUrl.setImage(imageView, Pic);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}
