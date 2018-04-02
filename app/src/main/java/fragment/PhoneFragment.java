package fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfishjy.library.RippleBackground;

import drawable.CircleDrawable;
import drawable.MultiCircleDrawable;


/**
 * 电话Fragment
 * Created by dell on 2016/10/8.
 */
public class PhoneFragment extends Fragment implements ResettableFragment {

    private MainActivity mainActivity;
    private ImageView mIvCircle, mimageView;
    private TextView phoneNumber;
    //加载一张网络图片
    String imageUrl = "https://raw.githubusercontent.com/zzzengxiaoxian/MyApp/master/photo/hotphone.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phone, container, false);

        mimageView = (ImageView) v.findViewById(R.id.mimageView);
        final RippleBackground rippleBackground = (RippleBackground) v.findViewById(R.id.content);
        mIvCircle = (ImageView) v.findViewById(R.id.iv_circle_2);
        phoneNumber = (TextView) v.findViewById(R.id.phonenumber);

        //开始水波动画
        rippleBackground.startRippleAnimation();
        //结束水波动画
//        rippleBackground.stopRippleAnimation();


        //imageloader显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_default_adimage)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, mimageView, options);

        //打电话按钮监听
        mIvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = phoneNumber.getText().toString();
                Log.d("TAG", "phone is: " + phone);
                //1）直接拨打电话
//                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
//                startActivity(intent);
                //2）跳转到拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //3）跳转到联系人页面，使用一下代码：
//                Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//                startActivity(intentPhone);
            }
        });

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) getActivity();
        mainActivity.addFragment(this);
    }

    @Override
    public void onDetach() {
        mainActivity.removeFragment(this);
        super.onDetach();
    }

    @Override
    public void setupAndReset() {

    }
}
