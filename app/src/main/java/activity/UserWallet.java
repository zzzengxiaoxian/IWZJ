package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/10.
 * <p/>
 * loading 属性动画
 * 在需要显示的地方调用showLoadUi(false, 0);  注意：这里加载文字可以自定义
 * 在需要取消的地方调用showLoadUi(true, 0);
 * 在需要重载的地方调用showReloadUi();
 * 在需要释放资源的地方调用release();
 */
public class UserWallet extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwallet);

        toolbar = (Toolbar) findViewById(R.id.toolbarwallet);

        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
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
