package activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/20.
 */
public class AudioTechnology extends AppCompatActivity{


    private Toolbar toolbar;
    WebView webView;
    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology);

        toolbar= (Toolbar) findViewById(R.id.toolbar_yuele);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //加载动画
        progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.loading);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("努力加载中");
        webView= (WebView) findViewById(R.id.webviewtechnology);


        //设置webview属性能够执行javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        /**
         * 是否存储页面DOM结构，默认false。
         */
        webView.getSettings().setDomStorageEnabled(true);

        /***支持通过JS打开新窗口***/
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /**
         * 设置是否加载网络资源。注意如果值从true切换为false后，WebView不会自动加载，
         * 除非调用WebView#reload().如果没有android.Manifest.permission#INTERNET权限，
         * 值设为false，则会抛出java.lang.SecurityException异常。
         * 默认值：有android.Manifest.permission#INTERNET权限时为false，其他为true。
         */
        webView.getSettings().setBlockNetworkImage(false);
        /**
         * 是否加载网络图片资源。注意如果getLoadsImagesAutomatically返回false，则该方法没有效果。
         * 如果使用setBlockNetworkLoads设置为false，该方法设置为false，也不会显示网络图片。
         * 当值从true改为false时。WebView会自动加载网络图片。
         */
//        webView.getSettings().setBlockNetworkImage(true);
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        //设置webView可以缩放，只可以双击缩放
        webView.getSettings().setSupportZoom(true);
        //设置是否可缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //无限缩放
        webView.getSettings().setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.loadUrl("https://m.ximalaya.com/album-tag/it");
        //webview.reload();// reload page
        /*不打开系统的浏览器，打开在程序中的一个界面*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受信任所有网站的证书
                super.onReceivedSslError(view, handler, error);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //告诉webview先不要自动加载图片，等页面finish后再发起图片加载
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
                progressDialog.dismiss();

            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            /*自定义页面加载progress*/
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar

            }

            /*获取当前页面正在加载那个页面title*/
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleview.setText(title);//a textview

            }
        });


        webView.setDownloadListener(new MyDownloadListenter());

    }
    class MyDownloadListenter implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            //下载任务...，主要有两种方式
            //（1）自定义下载任务
            //（2）调用系统的download的模块
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    /*点击返回键是返回上一个网页*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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


    @Override
    protected void onDestroy() {
        webView.loadUrl(" ");
        super.onDestroy();
    }
}
