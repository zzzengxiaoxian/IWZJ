package activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/12.
 */
public class AboutUs extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView web,callphone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        toolbar= (Toolbar) findViewById(R.id.toolbar_aboutus);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        web= (TextView) findViewById(R.id.web);
        callphone= (TextView) findViewById(R.id.callphone);
        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = callphone.getText().toString().trim();
                Log.d("TAG", "phone is: " + phone);
                //1）直接拨打电话
//                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
//                startActivity(intent);
                //2）跳转到拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(AboutUs.this,AboutUsWeb.class);
                startActivity(intent);

            }
        });

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
