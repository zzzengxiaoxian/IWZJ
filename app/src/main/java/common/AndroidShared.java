package common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.iwzj.ltkj.iwzj.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;

/**
 * Created by dell on 2016/10/3.
 * 判断分享 程序是否存在
 */

public class AndroidShared {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 文本类型
     */
    public static int TEXT = 0;

    /**
     * 图片类型
     */
    public static int DRAWABLE = 1;

    public AndroidShared(Context context) {
        this.context = context;
    }

    /**
     * 分享到QQ好友
     *
     * @param msgTitle (分享标题)
     * @param msgText  (分享内容)
     * @param type     (分享类型)
     * @param drawable (分享图片，若分享类型为AndroidShare.TEXT，则可以为null)
     */
    public void shareQQFriend(String msgTitle, String msgText, int type,
                              Bitmap drawable) {

        shareMsg("com.tencent.mobileqq",
                "com.tencent.mobileqq.activity.JumpActivity", "QQ", msgTitle,
                msgText, type, drawable);
    }


    /**
     * 分享到微信好友
     *
     * @param msgTitle (分享标题)
     * @param msgText  (分享内容)
     * @param type     (分享类型)
     * @param drawable (分享图片，若分享类型为AndroidShare.TEXT，则可以为null)
     */
    public void shareWeChatFriend(String msgTitle, String msgText, int type,
                                  Bitmap drawable) {

        shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI", "微信",
                msgTitle, msgText, type, drawable);
    }

    /**
     * 分享到微信朋友圈(分享朋友圈一定需要图片)
     *
     * @param msgTitle (分享标题)
     * @param msgText  (分享内容)
     * @param drawable (分享图片)
     */
//    public void shareWeChatFriendCircle(String msgTitle, String msgText,
//                                        Bitmap drawable) {
//
//        shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI",
//                "微信", msgTitle, msgText, AndroidShared.DRAWABLE, drawable);
//    }
    public void shareWeChatFriendCircle(String msgTitle, String msgText,int type
                                        ,Bitmap drawable) {

        shareMsg("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI",
                "微信", msgTitle, msgText, type, drawable);
    }

    /**
     * QQ空间分享
     * @param msgTitle
     * @param msgText
     * @param type
     * @param drawable
     */
    public void sharedQQZone(String msgTitle, String msgText, int type,
                             Bitmap drawable) {
        shareMsg("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity", "QQ图片",
                msgTitle, msgText, type, drawable);

    }

    /**
     * 新浪微博分享分享
     * @param msgTitle
     * @param msgText
     * @param type
     * @param drawable
     */
    public void sharedWeiBo(String msgTitle, String msgText, int type,
                             Bitmap drawable) {
        shareMsg("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity", "新浪微博",
                msgTitle, msgText, type, drawable);

    }

    /**
     * 点击分享的代码
     *
     * @param packageName  (包名,跳转的应用的包名)
     * @param activityName (类名,跳转的页面名称)
     * @param appname      (应用名,跳转到的应用名称)
     * @param msgTitle     (标题)
     * @param msgText      (内容)
     * @param type         (发送类型：text or pic 微信朋友圈只支持pic)
     */
    @SuppressLint("NewApi")
    private void shareMsg(String packageName, String activityName,
                          String appname, String msgTitle, String msgText, int type,
                          Bitmap drawable) {
        if (!packageName.isEmpty() && !isAvilible(context, packageName)) {// 判断APP是否存在
            Toast.makeText(context, "请先安装" + appname, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent intent = new Intent("android.intent.action.SEND");
        if (type == AndroidShared.TEXT) {
            intent.setType("text/plain");
        } else if (type == AndroidShared.DRAWABLE) {
            intent.setType("image/*");
//          BitmapDrawable bd = (BitmapDrawable) drawable;
//          Bitmap bt = bd.getBitmap();
            //MediaStore.Images 获取手机的音频、图片、视频等相关信息
            final Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                    context.getContentResolver(), drawable, null, null));
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }

        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!packageName.isEmpty()) {
            intent.setComponent(new ComponentName(packageName, activityName));
            context.startActivity(intent);
        } else {
            context.startActivity(Intent.createChooser(intent, msgTitle));
        }
    }

    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName
     * @return
     */
    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 指定分享到qq
     *
     * @param context
     * @param bitmap
     */
    public void sharedQQ(Activity context, Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
                context.getContentResolver(), BitmapFactory.decodeResource(context.getResources(), R.mipmap.share_icon_qq), null, null));
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setPackage("com.tencent.mobileqq");
        imageIntent.setType("image/*");
        imageIntent.putExtra(Intent.EXTRA_STREAM, uri);
        imageIntent.putExtra(Intent.EXTRA_TEXT, "您的好友邀请您进入天好圈");
        imageIntent.putExtra(Intent.EXTRA_TITLE, "天好圈");
        context.startActivity(imageIntent);
    }


    /**
     * 获取Android系统分享列表
     *
     * @param context
     * @return
     */
    public List<AppInfoVo> getShareApps(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<AppInfoVo> appInfoVos = new ArrayList<AppInfoVo>();
        List<ResolveInfo> resolveInfos = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("*/*");
        PackageManager pManager = context.getPackageManager();
        resolveInfos = pManager.queryIntentActivities(intent, PackageManager
                .COMPONENT_ENABLED_STATE_DEFAULT);
        for (int i = 0; i < resolveInfos.size(); i++) {
            AppInfoVo appInfoVo = new AppInfoVo();
            ResolveInfo resolveInfo = resolveInfos.get(i);
            appInfoVo.setAppName(resolveInfo.loadLabel(packageManager).toString());
            appInfoVo.setIcon(resolveInfo.loadIcon(packageManager));
            appInfoVo.setPackageName(resolveInfo.activityInfo.packageName);
            appInfoVo.setLauncherName(resolveInfo.activityInfo.name);
            appInfoVos.add(appInfoVo);
        }
        return appInfoVos;
    }

    /**
     * 获取安卓系统列表类
     */
    public class AppInfoVo {
        private Drawable icon;
        private String appName;
        private String packageName;
        private boolean isSystemApp;
        private long codesize;
        private String launcherName;


        public String getLauncherName() {
            return launcherName;
        }

        public void setLauncherName(String launcherName) {
            this.launcherName = launcherName;
        }

        public long getCodesize() {
            return codesize;
        }

        public void setCodesize(long codesize) {
            this.codesize = codesize;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public boolean isSystemApp() {
            return isSystemApp;
        }

        public void setSystemApp(boolean isSystemApp) {
            this.isSystemApp = isSystemApp;
        }

    }

    /**
     * 常见应用包名*********
     微信朋友圈
     “com.tencent.mm”
     “com.tencent.mm.ui.tools.ShareToTimeLineUI”
     微信朋友
     “com.tencent.mm”
     “com.tencent.mm.ui.tools.ShareImgUI”
     QQ好友
     “com.tencent.mobileqq”
     “com.tencent.mobileqq.activity.JumpActivity”
     QQ空间分享视频
     “com.qzone”
     “com.qzonex.module.maxvideo.activity.QzonePublishVideoActivity”
     QQ空间分享图片、文字
     “com.qzone”
     “com.qzonex.module.operation.ui.QZonePublishMoodActivity”
     新浪微博
     “com.sina.weibo”
     “com.sina.weibo.composerinde.ComposerDispatchActivity”
     */


}