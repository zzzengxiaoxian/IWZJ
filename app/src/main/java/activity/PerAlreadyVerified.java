package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.Base64util;

/**
 * * 实名认证 (已认证的时候，需要修改的时候)
 * Created by dell on 2016/11/28.
 */
public class PerAlreadyVerified extends AppCompatActivity {

    @Bind(R.id.toolbar_alreadyverified)
    Toolbar toolbar;

    @Bind(R.id.btn_alreadyXG)
    Button btn_change;
    @Bind(R.id.tv_alreadyVerName)
    TextView tv_alreadyVerName;
    @Bind(R.id.tv_alreadyVerID)
    TextView tv_alreadyVerID;
    @Bind(R.id.img_alreadyVer)
    ImageView img_alreadyVer;


    SharedPreferences sp_loginuser;
    private String Name, PID, PIC, ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peralready);
        ButterKnife.bind(this);


        if (toolbar != null) {
            //设置返回图标
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        sp_loginuser = getSharedPreferences("Userinfo", MODE_PRIVATE);
        ID = sp_loginuser.getString("UserID", "");
        PID = sp_loginuser.getString("UserPid", "");
        PIC = sp_loginuser.getString("UserPidpic", "");
        Name = sp_loginuser.getString("UserName", "");

        if (!Name.isEmpty()) {
            StringBuilder sb = new StringBuilder(Name);
            sb.replace(0, 1, "*");
            tv_alreadyVerName.setText(sb);
        }
        if (!PID.isEmpty()) {

            StringBuilder sb = new StringBuilder(PID);
            sb.replace(4, 16, "************");
            tv_alreadyVerID.setText(sb);
        }
        if (!PIC.isEmpty()) {
            Bitmap photo = Base64util.base64ToBitmap(PIC);
            img_alreadyVer.setImageBitmap(photo);
        }

    }

    @OnClick(R.id.btn_alreadyXG)
    public void OnXG() {
        Intent intent = new Intent();
        intent.setClass(PerAlreadyVerified.this, PerVerifiedName.class);
        startActivity(intent);
        this.finish();
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
