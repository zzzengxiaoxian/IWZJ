package com.iwzj.ltkj.iwzj;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.multidex.MultiDex;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import service.LocationService;

/**
 * Created by dell on 2016/10/14.
 */
// 请在AndroidManifest.xml中application标签下android:name中指定该类
public class ThisApplication extends Application {
    /**
     * 使用的时候记得关注一下在/sdcard/Android/data/[package_name]/cache目录下的缓存的文件。
     * 记得定期清理缓存，否则时间一长，SD卡就会被占满了，同时也可以在ImageLoaderConfiguration中配置SD的缓存策略，
     * 限制缓存文件数量（memoryCacheSizePercentage），限制缓存文件最大尺寸（memoryCacheSize）等选项。
     */

    public LocationService locationService;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {

        /*百度APISTORE的API接口的key*/
        ApiStoreSDK.init(getApplicationContext(), "66a21a35684cb4923f197407e98d9389");

        super.onCreate();
        initImageLoader(getApplicationContext());

        /***
         * 百度地图的SDK
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());

        //初始化MobSDK
        MobSDK.init(this);

    }


    /**初始化图片加载类配置信息**/
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)//加载图片的线程数
                .denyCacheImageMultipleSizesInMemory() //解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸。
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//设置磁盘缓存文件名称
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置加载显示图片队列进程
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }
}
