package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebChromeClient;

import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;

/**
 * splash界面
 * Created by dell on 2016/11/2.
 */
public class WelcomeActivity extends Activity {


    SharedPreferences sharedPreferences, sp_firstlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        sharedPreferences=getSharedPreferences("APPisFirst",MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putBoolean("isFirstIn",false);
//        editor.commit();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Boolean isFirstIn = false;
                sharedPreferences = getSharedPreferences("APPisFirst", MODE_PRIVATE);
                //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);

                Boolean isnotLogin = false;
                sp_firstlogin = getSharedPreferences("APPisnotLogin", MODE_PRIVATE);
                //取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                isnotLogin = sp_firstlogin.getBoolean("isnotLogin", true);

                if (isFirstIn == true) {
                    Intent intent = new Intent(WelcomeActivity.this, AppintroActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                }
            }
        }, 3000);

    }


}
