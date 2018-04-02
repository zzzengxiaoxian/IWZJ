package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.HaveToken;
import common.MD5;

/**
 * 亲请关注
 * Created by dell on 2016/10/16.
 */
public class HomeFamily extends AppCompatActivity {
    @Bind(R.id.toolbar_family)
    Toolbar toolbar;

    @Bind(R.id.tv_gzw)
    TextView tv_gzw;

//    @Bind(R.id.float_add)
//    FloatingActionButton floatingActionButton_add;

    private String url_getfamily = "http://www.yimijianfang.com/api/app/getcontactlist/<lnrid>?token=";
    private String token, MD5token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefamily);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        HaveToken.Token();
        token = HaveToken.str_contlist;
        MD5token = MD5.md5(token);


    }

    @OnClick(R.id.tv_gzw)
    public void OnGZW() {
        Intent intent = new Intent();
        intent.setClass(HomeFamily.this, FamilyFoucsMe.class);
        startActivity(intent);

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
