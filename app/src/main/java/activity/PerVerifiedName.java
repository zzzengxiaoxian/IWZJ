package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;

import bean.GetChangeAvatar;
import bean.PostTrueName;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.AvatarImageView;
import common.Base64util;
import common.CameraGallaryUtil;
import common.HaveToken;
import common.IdcardValidator;
import common.Intents;
import common.MD5;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 实名认证
 * Created by dell on 2016/10/10.
 */
public class PerVerifiedName extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView submit;
    private EditText name, id;
    private ImageView imageView;
    PopupWindowVerified menuWindow;


    SharedPreferences sp_loginuser, getSp_loginuser;


    private String Name, PID, PIC, ID;
    private Intent intent;

    private String geturl_TrueName = "http://www.yimijianfang.com/api/app/confirm?token=";//<token>
    private String token;
    private String MD5token;
    final OkHttpClient client = new OkHttpClient();

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfiedname);

        toolbar = (Toolbar) findViewById(R.id.toolbar_verified);
        submit = (TextView) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.identity);
        imageView = (ImageView) findViewById(R.id.IDpic);
        if (toolbar != null) {
            //设置返回图标
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        //获取
        HaveToken.Token();
        token = HaveToken.str_truename;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;


        getSp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
        ID = getSp_loginuser.getString("UserID", "");
        PID = getSp_loginuser.getString("UserPid", "");
        PIC = getSp_loginuser.getString("UserPidpic", "");
        Name = getSp_loginuser.getString("UserName", "");

        if (!Name.isEmpty()) {
            name.setText(Name);

        }
        if (!PID.isEmpty()) {
            id.setText(PID);
        }
        if (!PIC.isEmpty()) {
            Bitmap photo = Base64util.base64ToBitmap(PIC);
            imageView.setImageBitmap(photo);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuWindow = new PopupWindowVerified(PerVerifiedName.this,
                        itemsOnClick);
                menuWindow.showAtLocation(
                        PerVerifiedName.this.findViewById(R.id.main), Gravity.BOTTOM
                                | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                PID = id.getText().toString();

                if (name.getText().toString().isEmpty() || Name.isEmpty()) {

                    new SweetAlertDialog(PerVerifiedName.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("请输入您的合法姓名!")
                            .show();
                } else {
                    if (id.getText().toString().isEmpty() || PID.isEmpty()) {
                        new SweetAlertDialog(PerVerifiedName.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("身份证号码不能为空")
                                .show();
                    } else {
                        String pid = id.getText().toString();
                        boolean IDCARD = IdcardValidator.isValidatedAllIdcard(pid);
                        Log.d("TAG", "IDCARD: " + IDCARD);
                        if (PID.isEmpty() || IDCARD == false) {
                            new SweetAlertDialog(PerVerifiedName.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("身份证号码错误，请重新输入!")
                                    .show();
                        } else {
                            if (PIC.isEmpty()) {
                                Toast.makeText(PerVerifiedName.this, "请上传身份证件照", Toast.LENGTH_SHORT).show();
                            } else {
                                //获取服务中心网络请求数据
                                postTrueName();
                            }

                        }
                    }


                }

            }
        });


    }


    View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_2:
                    //选取照片

                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, CameraGallaryUtil.PHOTO_REQUEST_GALLERY);
                    break;
                case R.id.btn_3:
                    //拍照
                    // 利用系统自带的相机应用:拍照
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null
                    // 如果此处指定，则后来的data为null
                    // 只有指定路径才能获取原图
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, CameraGallaryUtil.fileUri);
                    startActivityForResult(intent, CameraGallaryUtil.PHOTO_REQUEST_TAKEPHOTO);
                    break;
            }

        }

    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = CameraGallaryUtil.getBitmapFromCG(this, requestCode, resultCode, data);
        PIC = Base64util.bitmapToBase64(bitmap);
        imageView.setImageBitmap(bitmap);
    }


    private void postTrueName() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        //post表单数据
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", ID);
        builder.add("name", Name);
        builder.add("pid", PID);
        builder.add("baseurl", PIC);
        RequestBody formBody = builder.build();

        final Request request = new Request.Builder()
                .post(formBody)
                .tag(this)
                .url(geturl_TrueName + MD5token)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印post提交身份认证接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(PerVerifiedName.this, "请确认网络链接状况", Toast.LENGTH_SHORT).show();
                        } else {

                            Gson gson = new Gson();
                            PostTrueName posttruename = gson.fromJson(message, PostTrueName.class);
                            msg = posttruename.getErrmsg();
//
//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {

                                        if (msg.equals("请求成功")) {
                                            System.out.print("username is " + Name);
                                            System.out.print("UserPid is " + PID);
                                            System.out.print("UserPidpic is " + PIC);
                                            sp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor_user = sp_loginuser.edit();
                                            editor_user.putString("UserName", Name);
                                            editor_user.putString("UserPid", PID);
                                            editor_user.putString("UserPidpic", PIC);
                                            editor_user.commit();
                                            Intents.startToActivity(PerVerifiedName.this, UserPersonalINFO.class);
                                            PerVerifiedName.this.finish();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

//

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
}
