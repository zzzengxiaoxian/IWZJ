package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import org.w3c.dom.Text;

/**
 * 用户必读
 * Created by dell on 2016/10/12.
 */
public class UserReading extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private TextView question,yhxy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userreading);

        toolbar= (Toolbar) findViewById(R.id.toolbar_userreading);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        question= (TextView) findViewById(R.id.question);
        yhxy= (TextView) findViewById(R.id.yhxy);

        question.setOnClickListener(this);
        yhxy.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.question:
                Intent intenta=new Intent();
                intenta.setClass(this,CommonProblem.class);
                startActivity(intenta);

                break;
            case R.id.yhxy:
                Intent intentb=new Intent();
                intentb.setClass(this,UserAgreement.class);
                startActivity(intentb);
                break;
        }
    }
}
