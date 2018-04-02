package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.iwzj.ltkj.iwzj.R;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import net.arvin.socialhelper.SocialHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import common.SocialUtil;
import common.ThreadManager;
import common.Util2;
import okhttp3.internal.Util;

/**
 * Created by dell on 2018/1/3.
 */
public class ShareMore extends AppCompatActivity {


    @Bind(R.id.sharedQQ)
    TextView tvshareqq;

    @Bind(R.id.sharedWX)
    TextView tvsharewx;

    @Bind(R.id.sharedwb)
    TextView tvsharewb;


    private SocialHelper socialHelper;


    public static Tencent mTencent;
    private static final String mAppid = "1105987677";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharemore);
        ButterKnife.bind(this);

        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }

        tvshareqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void shareOnlyImageOnQZone(View view) {
        final Bundle params = new Bundle();
        //本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Environment.getExternalStorageDirectory().getAbsolutePath().concat("http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"));
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN); //打开这句话，可以实现分享纯图到QQ空间
        doShareToQQ(params);
    }

    public void shareOnlyImageOnQQ(View view) {
        final Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Environment.getExternalStorageDirectory().getAbsolutePath().concat("/a.png"));
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//        //这条分享消息被好友点击后的跳转URL。
//        params.putString(Constants.PARAM_TARGET_URL, "http://connect.qq.com/");
//        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
//        params.putString(Constants.PARAM_TITLE, "我在测试");
//        //分享的图片URL
//        params.putString(Constants.PARAM_IMAGE_URL, "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
//        //分享的消息摘要，最长50个字
//        params.putString(Constants.PARAM_SUMMARY, "测试");
//        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//        params.putString(Constants.PARAM_APPNAME, "??我在测试");
//        //标识该消息的来源应用，值为应用名称+AppId。
//        params.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);
        doShareToQQ(params);
    }


    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getManager().post(ThreadManager.THREAD_BACKGROUND, new Runnable() {
            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(ShareMore.this, params, qqShareListener);
                }
            }
        });


    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Util2.toastMessage(ShareMore.this, "onCancel: ");
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Util2.toastMessage(ShareMore.this, "onComplete: " + response.toString());
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util2.toastMessage(ShareMore.this, "onError: " + e.errorMessage, "e");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }


}
