package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import adapter.DtdServiceAdapter;
import bean.GetChangeAvatar;
import bean.GetDtdService;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.AvatarImageView;
import common.Base64util;
import common.HaveToken;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 个人资料
 * Created by dell on 2016/8/18.
 */
public class UserPersonalINFO extends AppCompatActivity implements View.OnClickListener {


    private Toolbar toolbar;

    private AvatarImageView avatarImageView;
    private TextView txt_phonenumber, txt_name, txt_truename, txt_adress, txt_lxr, txtxgmm;
    View phoneview, view_name, view_truename, view_address, view_lxr;

    SharedPreferences sp_login;

    String _mobile, _nickname, _photo, _address, _istrue, _isjinji, _ID;

    String url_getavatarImage = "http://www.yimijianfang.com/api/app/changephoto?token=";
    private String token;
    private String MD5token;
    final OkHttpClient client = new OkHttpClient();

    private String msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        innitview();
        txt_phonenumber.setOnClickListener(this);
        txtxgmm.setOnClickListener(this);
        view_truename.setOnClickListener(this);
        view_address.setOnClickListener(this);
        view_name.setOnClickListener(this);
        view_lxr.setOnClickListener(this);
        phoneview.setOnClickListener(this);

//        avatarImageView.setDialogBackgroundColor("#00AAAA"); //设置对话框的背景色
//        avatarImageView.setBtnClickedColor("#00AAAA"); //设置按钮点击后的颜色
//        avatarImageView.setAnimResId(R.style.avatar_dialog_animation); //设置dialog显示的动画
//        avatarImageView.setTitleColor("#FFEEAA");  //设置标题的颜色
//        avatarImageView.setBtnBackgroundColor("#FFEEAA"); //设置按钮的背景色
//        avatarImageView.setTitleLineColor("#FFEEAA"); //设置标题下的分割线的颜色
//        avatarImageView.setLineColor("#FFEEAA"); //设置按钮之间的分割线的颜色
//        avatarImageView.setTitlePaddingTopBottom(30); //设置标题的padding
//        avatarImageView.setBtnPaddingTopBottom(30); //设置按钮的padding
//        avatarImageView.setTitleText("testTitle"); //设置标题的文字
//        avatarImageView.setPhotoButtonText("testPhotoText"); //设置拍照按钮的文字
//        avatarImageView.setChoosePicButtonText("testChooseText"); //设置选择照片的文字
//        avatarImageView.setDialogCorner(20); //设置dialog的角度
//        avatarImageView.setBtnTextColor("#FFEEAA"); //设置按钮文本的颜色
//        avatarImageView.setTitleTextSize(30); //设置标题的文字大小
//        avatarImageView.setBtnTextSize(30); //设置按钮的文字大小


        if (toolbar != null) {


            toolbar.setTitle(" ");
            //设置返回图标
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);


        }

        //获取
        HaveToken.Token();
        token = HaveToken.str_changephoto;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;


        sp_login = getSharedPreferences("Userinfo", MODE_PRIVATE);
        _ID = sp_login.getString("UserID", "");
        _photo = sp_login.getString("UserPhoto", "");
        _nickname = sp_login.getString("UserNickName", "");
        _mobile = sp_login.getString("UserMobile", "");
        _address = sp_login.getString("UserAddress", "");
        _istrue = sp_login.getString("UserIsTrueName", "");
        _isjinji = sp_login.getString("Usercontact", "");


        txt_phonenumber.setText(_mobile);
        txt_adress.setText(_address);
        txt_name.setText(_nickname);
        txt_truename.setText(_istrue);
        txt_adress.setText(_address);
        txt_lxr.setText(_isjinji);

        View view_user;

        view_user = findViewById(R.id.view_user);

        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UserPersonalINFO.this, "请点击图片", Toast.LENGTH_SHORT).show();
            }
        });

        if (!_photo.isEmpty()) {
            String searchChars = "www.yimijianfang.com";
            if (_photo.contains(searchChars)) {
                try {
                    ImageLoader.getInstance().displayImage(_photo, avatarImageView);
//                    Bitmap photo = Base64util.netPicToBmp(_photo);
//                    avatarImageView.setImageBitmap(photo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Bitmap photo = Base64util.base64ToBitmap(_photo);
                avatarImageView.setImageBitmap(photo);
            }
        }

        avatarImageView.setAfterCropListener(new AvatarImageView.AfterCropListener() {
            @Override
            public void afterCrop(Bitmap photo) {

                _photo = Base64util.bitmapToBase64(photo);
                //获取服务中心网络请求数据
                postavatarRequest();
                Toast.makeText(UserPersonalINFO.this, "设置新的头像成功", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void postavatarRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", _ID);
        builder.add("baseurl", _photo);
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(url_getavatarImage + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET提交头像接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(UserPersonalINFO.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            GetChangeAvatar getChangeAvatar = gson.fromJson(message, GetChangeAvatar.class);
                            msg = getChangeAvatar.getErrmsg();
//
//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        sp_login = getSharedPreferences("Userinfo", MODE_PRIVATE);
                                        SharedPreferences.Editor editor_user = sp_login.edit();
                                        editor_user.putString("UserPhoto", _photo);
                                        editor_user.commit();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

//                                    if (msg.equals("请求成功")) {
//                                        Toast.makeText(UserPersonalINFO.this, "上传成功", Toast.LENGTH_SHORT).show();
//                                    }

                                }
                            });
                        }

                        try {

                        } catch (IllegalStateException | JsonSyntaxException exception) {
                            Log.i("TAG", exception.toString());
                        }


                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void innitview() {
        view_name = findViewById(R.id.view_name);
        view_truename = findViewById(R.id.view_truename);
        view_address = findViewById(R.id.view_addresses);
        view_lxr = findViewById(R.id.view_lxr);
        toolbar = (Toolbar) findViewById(R.id.toolbar_per);
        avatarImageView = (AvatarImageView) findViewById(R.id.avatarIv);
        txt_adress = (TextView) findViewById(R.id.txt_address);
        txt_phonenumber = (TextView) findViewById(R.id.txt_phonenumber);
        txt_lxr = (TextView) findViewById(R.id.txt_lxr);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txtxgmm = (TextView) findViewById(R.id.xgmm);
        txt_truename = (TextView) findViewById(R.id.txt_nametrue);
        phoneview = findViewById(R.id.view_phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //在拍照、选取照片、裁剪Activity结束后，调用的方法
        if (avatarImageView != null) {
            avatarImageView.onActivityResult(requestCode, resultCode, data);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //监听返回按钮操作事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(UserPersonalINFO.this, MainActivity.class);
                intent.putExtra("frgmentindex", 1);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //电话
            case R.id.view_phone:

                String phone = txt_phonenumber.getText().toString();
                new SweetAlertDialog(this)
                        .setTitleText("您的手机号为" + phone)
                        .show();
                break;
            //昵称
            case R.id.view_name:
                Intent intent = new Intent();
                intent.setClass(this, PerCallName.class);
                startActivity(intent);
                break;
            //是否认证
            case R.id.view_truename:

                if (_istrue.equals("已认证") || _istrue.equals("审核中")) {
                    Intent intentb = new Intent();
                    intentb.setClass(this, PerAlreadyVerified.class);
                    startActivity(intentb);
                } else {
                    //开始认证界面
                    Intent intenta = new Intent();
                    intenta.setClass(this, PerVerifiedName.class);
                    startActivity(intenta);
                }


                break;
            //地址
            case R.id.view_addresses:
                Intent intentc = new Intent();
                intentc.setClass(this, PerAddress.class);
                startActivity(intentc);

                break;
            //联系人
            case R.id.view_lxr:
                Intent intentlxr = new Intent();
                intentlxr.setClass(UserPersonalINFO.this, EmergencyContactActivity.class);
                startActivity(intentlxr);
                break;
            //修改密码
            case R.id.xgmm:
                Intent intentxg = new Intent();
                intentxg.setClass(UserPersonalINFO.this, ChangePwdActivity.class);
                startActivity(intentxg);
                break;
        }
    }
}
