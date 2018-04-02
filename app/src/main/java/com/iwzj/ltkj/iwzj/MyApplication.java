package com.iwzj.ltkj.iwzj;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	/**
	 * 使用的时候记得关注一下在/sdcard/Android/data/[package_name]/cache目录下的缓存的文件。
	 * 记得定期清理缓存，否则时间一长，SD卡就会被占满了，同时也可以在ImageLoaderConfiguration中配置SD的缓存策略，
	 * 限制缓存文件数量（memoryCacheSizePercentage），限制缓存文件最大尺寸（memoryCacheSize）等选项。
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext()); 
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
}
