package activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iwzj.ltkj.iwzj.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dell on 2016/10/19.
 */
public class WebNews extends AppCompatActivity{
    private  Toolbar toolbar;
    @Bind(R.id.webview_news)WebView webViewnews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webnews);

        toolbar = (Toolbar) findViewById(R.id.toolbar_newszx);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String link=intent.getStringExtra("weblink");

        //设置webview属性能够执行javascript脚本
        webViewnews.getSettings().setJavaScriptEnabled(true);
        //设置webView可以缩放，只可以双击缩放
        webViewnews.getSettings().setSupportZoom(true);
        //设置是否可缩放
        webViewnews.getSettings().setBuiltInZoomControls(true);
        //无限缩放
        webViewnews.getSettings().setUseWideViewPort(true);
        // 应用可以有缓存

        webViewnews.loadUrl(link);
        //webview.reload();// reload page
        /*不打开系统的浏览器，打开在程序中的一个界面*/
         /*不打开系统的浏览器，打开在程序中的一个界面*/
        webViewnews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        /*自定义页面加载progress*/
        webViewnews.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar

            }
        });
        /*获取当前页面正在加载那个页面title*/
        webViewnews.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
//                titleview.setText(title);//a textview
            }
        });




    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                webViewnews.goBack();// 返回前一个页面
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*点击返回键是返回上一个网页*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webViewnews.canGoBack()) {
            webViewnews.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
