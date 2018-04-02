package activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 远程看护
 * Created by dell on 2016/11/17.
 */
public class HomeCare extends AppCompatActivity {

    @Bind(R.id.toolbar_care)
    Toolbar toolbar;

    @Bind(R.id.linear_homecare_zlhj)
    View zlhj;
    View zlhj_;
    @Bind(R.id.linear_homecare_zljh)
    View zljh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_homecare);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        zlhj_ = findViewById(R.id.linear_homecare_zljh);
//
//        zlhj_.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //已安装，打开程序，需传入参数包名："com.skype.android.verizon"
//                if (isAppInstalled(HomeCare.this, "com.example.xyhmonitor")) {
//                    Intent i = new Intent();
//                    ComponentName cn = new ComponentName("com.example.xyhmonitor",
//                            "com.xyhmonitor.Main_Activity");
//                    i.setComponent(cn);
//                    startActivityForResult(i, RESULT_OK);
//                    //        可以使用intent进行跳转，如下简单示例，通过intent跳转
//            PackageManager packageManager = getPackageManager();
//            Intent intent = new Intent();
//            intent = packageManager.getLaunchIntentForPackage("com.packagename.xxxx");
//            startActivity(intent);
//                }
//                //未安装，跳转至market下载该程序
//                else {
//            Uri uri = Uri.parse("market://details?id=com.skype.android.verizon");//id为包名
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(it);
//
//                    new SweetAlertDialog(HomeCare.this)
//                            .setTitleText("提示")
//                            .setCancelText("取消")
//                            .setConfirmText("去下载")
//                            .setContentText("您还没有安装此APP，若下载安装，请点击 去下载 后在浏览器中打开下载链接")
//                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    //http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk
//                                    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk"));
//                                    it.setClassName("com.Android.browser", "com.android.browser.BrowserActivity");
//                                    HomeCare.this.startActivity(it);
//                                }
//                            })
//                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialogInterface) {
//
//                                    dialogInterface.dismiss();
//                                }
//                            });
//                }
//            }
//        });

    }

    /*
     * check the app is installed
     */
//    private boolean isAppInstalled(Context context, String packagename) {
//        PackageInfo packageInfo;
//        try {
//            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            packageInfo = null;
//            e.printStackTrace();
//        }
//        if (packageInfo == null) {
//            //System.out.println("没有安装");
//            return false;
//        } else {
//            //System.out.println("已经安装");
//            return true;
//        }
//    }

    @OnClick(R.id.linear_homecare_zljh)
    public void zljhclick() {

        if (isAvilible(this, "com.tencent.mm")) {// 传入指定应用包名com.example.xyhmonitor
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivityForResult(intent, 0);
            Toast.makeText(HomeCare.this, "已安装微信",Toast.LENGTH_LONG).show();
        }else{
            new SweetAlertDialog(HomeCare.this)
                    .setTitleText("提示")
                    .setCancelText("取消")
                    .setConfirmText("去下载")
                    .setContentText("您还没有安装此APP，若下载安装，请点击 去下载 后在浏览器中打开下载链接")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk
                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk"));
                            it.setClassName("com.Android.browser", "com.android.browser.BrowserActivity");
                            HomeCare.this.startActivity(it);
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {

                            dialogInterface.dismiss();
                        }
                    });
            Toast.makeText(HomeCare.this, "未安装微信",Toast.LENGTH_LONG).show();
        }

//        //已安装，打开程序，需传入参数包名："com.skype.android.verizon"
//        if (isAppInstalled(HomeCare.this, "com.example.xyhmonitor")) {
//            Intent i = new Intent();
//            ComponentName cn = new ComponentName("com.example.xyhmonitor",
//                    "com.xyhmonitor.Main_Activity");
//            i.setComponent(cn);
//            startActivityForResult(i, RESULT_OK);
//            //        可以使用intent进行跳转，如下简单示例，通过intent跳转
//            PackageManager packageManager = getPackageManager();
//            Intent intent = new Intent();
//            intent = packageManager.getLaunchIntentForPackage("com.packagename.xxxx");
//            startActivity(intent);
//        }
//        //未安装，跳转至market下载该程序
//        else {
////            Uri uri = Uri.parse("market://details?id=com.skype.android.verizon");//id为包名
////            Intent it = new Intent(Intent.ACTION_VIEW, uri);
////            startActivity(it);
//
//            new SweetAlertDialog(HomeCare.this)
//                    .setTitleText("提示")
//                    .setCancelText("取消")
//                    .setConfirmText("去下载")
//                    .setContentText("您还没有安装此APP，若下载安装，请点击 去下载 后在浏览器中打开下载链接")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            //http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk
//                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://syjh.oss-cn-hangzhou.aliyuncs.com/%E7%A5%9E%E9%B9%B0%E7%9B%91%E6%8A%A4.apk"));
//                            it.setClassName("com.Android.browser", "com.android.browser.BrowserActivity");
//                            HomeCare.this.startActivity(it);
//                        }
//                    })
//                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialogInterface) {
//
//                            dialogInterface.dismiss();
//                        }
//                    });
//        }
    }

    @OnClick(R.id.linear_homecare_zlhj)
    public void zlhjclick() {

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

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName ：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
