package fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.arvin.socialhelper.SocialHelper;

import activity.LoginActivity;
import activity.ShareMore;
import activity.UserCalender;
import activity.UserOrder;
import activity.UserPersonalINFO;
import activity.UserReading;
import activity.UserSetting;
import activity.UserSmartCard;
import activity.UserWallet;
import activity.popuwindow_share;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.AndroidShared;
import common.AvatarImageView;
import common.Base64util;
import common.DrawableToBitmap;

import static android.R.attr.bitmap;
import static android.R.attr.drawable;
import static android.R.attr.start;
import static android.R.attr.type;


/**
 * 我的界面
 * Created by dell on 2016/8/8.
 */
public class UserFragment extends Fragment implements ResettableFragment, View.OnClickListener {

    private MainActivity mainActivity;
    private TextView tvname, mywallet, smartcard, mycalendar, myorder, setting, reading, share;
    popuwindow_share menuWindow;

    SharedPreferences sharedPreferences, pre_logininfo;

    private String Nickname, pic;
    private AvatarImageView avatarImageView;

    private SocialHelper socialHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mywallet = (TextView) v.findViewById(R.id.txt_mywallet);
        smartcard = (TextView) v.findViewById(R.id.txt_smartcard);
        mycalendar = (TextView) v.findViewById(R.id.txt_calendar);
        myorder = (TextView) v.findViewById(R.id.txt_myorder);
        setting = (TextView) v.findViewById(R.id.txt_setting);
        reading = (TextView) v.findViewById(R.id.txt_userreading);
        share = (TextView) v.findViewById(R.id.txt_shareapp);
        tvname = (TextView) v.findViewById(R.id.tvname);//登陆后用来从服务端接受姓名并显示出来
        avatarImageView = (AvatarImageView) v.findViewById(R.id.profile_image);

        mywallet.setOnClickListener(this);
        smartcard.setOnClickListener(this);
        mycalendar.setOnClickListener(this);
        myorder.setOnClickListener(this);
        setting.setOnClickListener(this);
        reading.setOnClickListener(this);
        share.setOnClickListener(this);
        tvname.setOnClickListener(this);

        pre_logininfo = getActivity().getSharedPreferences("Userinfo", getActivity().MODE_PRIVATE);
        pic = pre_logininfo.getString("UserPhoto", "");
        Nickname = pre_logininfo.getString("UserNickName", "");
        System.out.println("UserPhoto is " + pic);
        avatarImageView.setClickable(false);

        if (!pic.isEmpty()) {
            String searchChars = "www.yimijianfang.com";
            if (pic.contains(searchChars)) {
                try {
                    ImageLoader.getInstance().displayImage(pic, avatarImageView);
//                    System.out.println("photo is " + pic);
//                    Bitmap photo = Base64util.netPicToBmp(pic);
//                    System.out.println("photo is" + photo);
//                    avatarImageView.setImageBitmap(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Bitmap photo = Base64util.base64ToBitmap(pic);
                avatarImageView.setImageBitmap(photo);
            }
        }


        if (!Nickname.isEmpty()) {
            tvname.setText(Nickname);
        }


        View view_user;

        view_user = v.findViewById(R.id.view_user);

        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sharedPreferences = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin = sharedPreferences.getBoolean("isnotLogin", true);

                if (isFirstLogin == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {


                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserPersonalINFO.class);
                    startActivity(intent);
                }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_mywallet:
                sharedPreferences = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin = sharedPreferences.getBoolean("isnotLogin", true);

                if (isFirstLogin == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentWallet = new Intent();
                    intentWallet.setClass(getActivity(), UserWallet.class);
                    startActivity(intentWallet);
                }

                break;
            case R.id.txt_smartcard:
                sharedPreferences = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_2 = sharedPreferences.getBoolean("isnotLogin", true);

                if (isFirstLogin_2 == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentsmartcard = new Intent();
                    intentsmartcard.setClass(getActivity(), UserSmartCard.class);
                    startActivity(intentsmartcard);
                }


                break;
            case R.id.txt_calendar:
                sharedPreferences = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_3 = sharedPreferences.getBoolean("isnotLogin", true);

                if (isFirstLogin_3 == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentcalendar = new Intent();
                    intentcalendar.setClass(getActivity(), UserCalender.class);
                    startActivity(intentcalendar);
                }

                break;
            case R.id.txt_myorder:
                sharedPreferences = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_4 = sharedPreferences.getBoolean("isnotLogin", true);

                if (isFirstLogin_4 == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentorder = new Intent();
                    intentorder.setClass(getActivity(), UserOrder.class);
                    startActivity(intentorder);
                }

                break;
            case R.id.txt_setting:
                Intent intentsetting = new Intent();
                intentsetting.setClass(getActivity(), UserSetting.class);
                startActivity(intentsetting);
                break;
            case R.id.txt_userreading:
                Intent intentreading = new Intent();
                intentreading.setClass(getActivity(), UserReading.class);
                startActivity(intentreading);

                break;
            case R.id.txt_shareapp:

                menuWindow = new popuwindow_share(getActivity(),
                        itemsOnClick);
                menuWindow.showAtLocation(
                        getActivity().findViewById(R.id.user_main), Gravity.BOTTOM
                                | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
    }

    View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.wechat:
//                    new SweetAlertDialog(getActivity()).setTitleText("你点击了微信分享").show();
                    AndroidShared androidSharedWX = new AndroidShared(getActivity());
                    androidSharedWX.shareWeChatFriend("爱吾之家", "共享这个软件给你，亲请关注，为老服务，欢迎使用爱吾之家，为家里老人保驾护航。", 0, null);
                    break;
                case R.id.circle:
//    Resource -> Drawable
//    Drawable draw1 = this.getResources().getDrawable(R.drawable.icon);
//                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.wl_bg0);
////                    Bitmap bitmap = DrawableToBitmap.drawableToBitmap(getResources().getDrawable(R.drawable.wl_bg0));
//                    AndroidShared androidSharedCir = new AndroidShared(getActivity());
//                    androidSharedCir.shareWeChatFriendCircle("爱吾之家", "共享这个软件给你，亲请关注，为老服务，欢迎使用爱吾之家，为家里老人保驾护航。",0, null);

                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ShareMore.class);
                    startActivity(intent);


                    break;
                case R.id.qq:
//                    new SweetAlertDialog(getActivity()).setTitleText("你点击了qq分享").show();

                    AndroidShared androidSharedQQ = new AndroidShared(getActivity());
                    androidSharedQQ.shareQQFriend("爱吾之家", "共享这个软件给你，亲请关注，为老服务，欢迎使用爱吾之家，为家里老人保驾护航。",
                            0, null);


                    break;
                case R.id.message:
                    Uri smsToUri = Uri.parse("smsto:");
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                    //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
                    sendIntent.putExtra("sms_body", "共享这个软件给你，亲请关注，为老服务，欢迎使用爱吾之家，为家里老人保驾护航。");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivityForResult(sendIntent, 1002);
                    break;
//                case R.id.weibo:
//                    AndroidShared androidSharedweibo = new AndroidShared(getActivity());
//                    androidSharedweibo.sharedWeiBo("爱吾之家", "共享这个软件给你，亲请关注，为老服务，欢迎使用爱吾之家，为家里老人保驾护航。",
//                            0, null);
//                    break;
            }

        }

    };

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null && socialHelper != null) {//qq分享如果选择留在qq，通过home键退出，再进入app则不会有回调
//            socialHelper.onActivityResult(requestCode, resultCode, data);
//        }
//    }

//    @Override
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (socialHelper != null) {
//            socialHelper.onNewIntent(intent);
//        }
//    }
}
