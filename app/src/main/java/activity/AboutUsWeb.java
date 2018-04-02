package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/12.
 */
public class AboutUsWeb extends AppCompatActivity {
    private Toolbar toolbar;
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutusweb);
        toolbar = (Toolbar) findViewById(R.id.toolbar_aboutusweb);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView = (WebView) findViewById(R.id.webview);
        //设置webview属性能够执行javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置webView可以缩放，只可以双击缩放
        webView.getSettings().setSupportZoom(true);
        //设置是否可缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //无限缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("http://www.iwzj.org/");
        //webview.reload();// reload page
        /*不打开系统的浏览器，打开在程序中的一个界面*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /*自定义页面加载progress*/
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //get the newProgress and refresh progress bar

            }
        });
        /*获取当前页面正在加载那个页面title*/
        webView.setWebChromeClient(new WebChromeClient() {
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
}
