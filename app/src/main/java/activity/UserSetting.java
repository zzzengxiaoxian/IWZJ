package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import common.DataCleanManager;

/**
 * 设置界面
 * Created by dell on 2016/10/12.
 */
public class UserSetting extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView yjfk, aboutus, cleancach, syll;
    private View view_syll;
    private Button btn_quite;

    SharedPreferences sp_login, sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersetting);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        yjfk = (TextView) findViewById(R.id.yjfk);
        aboutus = (TextView) findViewById(R.id.aboutus);
        syll = (TextView) findViewById(R.id.syll);
        view_syll = findViewById(R.id.view_liuliang);
        cleancach = (TextView) findViewById(R.id.cleancash);
        btn_quite = (Button) findViewById(R.id.quiteaccount);

        yjfk.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        syll.setOnClickListener(this);
        view_syll.setOnClickListener(this);
        cleancach.setOnClickListener(this);
        btn_quite.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yjfk:
                Intent intentfeedback = new Intent();
                intentfeedback.setClass(this, Feedback.class);
                startActivity(intentfeedback);
                break;
            case R.id.aboutus:
                Intent intentaboutus = new Intent();
                intentaboutus.setClass(this, AboutUs.class);
                startActivity(intentaboutus);
                break;
            case R.id.view_liuliang:
                new SweetAlertDialog(this)
                        .setTitleText("使用流量为:" + syll.getText().toString())
                        .show();
                break;
            case R.id.cleancash:
                /** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
                DataCleanManager.cleanInternalCache(UserSetting.this);
                new SweetAlertDialog(this)
                        .setTitleText("已清除")
                        .show();
            case R.id.quiteaccount:
                /*退出账号*/
                //我的当前信息清楚
                sp_login = getSharedPreferences("Userinfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp_login.edit();
                editor.clear();
                editor.commit();
                //如果你登陆过了 将登录状态设置为已经登录。
                sharedPreferences = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor_login = sharedPreferences.edit();
                editor_login.clear();
                editor_login.commit();
                //
                Intent intentLogin = new Intent();
                intentLogin.setClass(this, LoginActivity.class);
                startActivity(intentLogin);
                UserSetting.this.finish();
                break;
        }

    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
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
