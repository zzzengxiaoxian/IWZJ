package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * HOME右上角信息界面
 * Created by dell on 2016/12/1.
 */
public class HomeMyMessage extends AppCompatActivity {

    @Bind(R.id.toolbar_mymessage)
    Toolbar toolbar;
    @Bind(R.id.tv_msg_activity)
    TextView msg_activity;
    @Bind(R.id.tv_msg_helper)
    TextView msg_helper;
    @Bind(R.id.tv_msg_time)
    TextView msg_time;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemessage);
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @OnClick(R.id.tv_msg_helper)
    public void onhelper() {
        sharedPreferences = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
        Boolean isFirstLogin = sharedPreferences.getBoolean("isnotLogin", true);

        if (isFirstLogin == true) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intentorder = new Intent();
            intentorder.setClass(this, MsgHelperActivity.class);
            startActivity(intentorder);
        }
    }

    @OnClick(R.id.tv_msg_time)
    public void onTIclick() {
        sharedPreferences = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
        Boolean isFirstLogin = sharedPreferences.getBoolean("isnotLogin", true);

        if (isFirstLogin == true) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intentorder = new Intent();
            intentorder.setClass(this, MsgTimeActivity.class);
            startActivity(intentorder);
        }
    }

    @OnClick(R.id.tv_msg_activity)
    public void onACclick() {
        sharedPreferences = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
        Boolean isFirstLogin = sharedPreferences.getBoolean("isnotLogin", true);

        if (isFirstLogin == true) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intentorder = new Intent();
            intentorder.setClass(this, MsgActiveActivity.class);
            startActivity(intentorder);
        }
    }


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
