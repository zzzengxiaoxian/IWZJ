package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.iwzj.ltkj.iwzj.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by dell on 2016/10/13.
 */
public class HomeMusicPlay extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    View news, crosstalk, music, novel, talkshow, radiobroast, science, lectureroom, finance, hzmt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        toolbar = (Toolbar) findViewById(R.id.toolbar_music);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        news = findViewById(R.id.news);
        crosstalk = findViewById(R.id.crosstalk);
        music = findViewById(R.id.music);
        novel = findViewById(R.id.novel);
        talkshow = findViewById(R.id.talkshow);
        radiobroast = findViewById(R.id.radiobroadcast);
        science = findViewById(R.id.science);
        lectureroom = findViewById(R.id.lectureroom);
        finance = findViewById(R.id.finance);
        hzmt = findViewById(R.id.hzmt);
        news.setOnClickListener(this);
        crosstalk.setOnClickListener(this);
        music.setOnClickListener(this);
        novel.setOnClickListener(this);
        talkshow.setOnClickListener(this);
        radiobroast.setOnClickListener(this);
        science.setOnClickListener(this);
        lectureroom.setOnClickListener(this);
        finance.setOnClickListener(this);
        hzmt.setOnClickListener(this);


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
        switch (view.getId()) {
            case R.id.news:
                Intent intentnews = new Intent();
                intentnews.setClass(HomeMusicPlay.this, AudioNews.class);
                startActivity(intentnews);
                break;
            case R.id.crosstalk:
                Intent intentncrosstalk = new Intent();
                intentncrosstalk.setClass(HomeMusicPlay.this, AudioCrosstalk.class);
                startActivity(intentncrosstalk);
                break;
            case R.id.music:
                Intent intentmusic = new Intent();
                intentmusic.setClass(HomeMusicPlay.this, AudioMusic.class);
                startActivity(intentmusic);
                break;
            case R.id.novel:
                Intent intentnovel = new Intent();
                intentnovel.setClass(HomeMusicPlay.this, AudioFiction.class);
                startActivity(intentnovel);
                break;
            case R.id.talkshow:
                Intent intenttalkshow = new Intent();
                intenttalkshow.setClass(HomeMusicPlay.this, AudioTalkShow.class);
                startActivity(intenttalkshow);
                break;
            case R.id.radiobroadcast:
                Intent intentradiobroadcast = new Intent();
                intentradiobroadcast.setClass(HomeMusicPlay.this, AudioRadioDrama.class);
                startActivity(intentradiobroadcast);
                break;
            case R.id.lectureroom:
                Intent intentlectureroom = new Intent();
                intentlectureroom.setClass(HomeMusicPlay.this, AudioLectureRoom.class);
                startActivity(intentlectureroom);
                break;
            case R.id.finance:
                Intent intentfinance = new Intent();
                intentfinance.setClass(HomeMusicPlay.this, AudioEconomics.class);
                startActivity(intentfinance);
                break;
            case R.id.science:
                Intent intentscience = new Intent();
                intentscience.setClass(HomeMusicPlay.this, AudioTechnology.class);
                startActivity(intentscience);
                break;
            case R.id.hzmt:
                new SweetAlertDialog(this)
                        .setTitleText("合作媒体：")
                        .setConfirmText("喜马拉雅听")
                        .show();
                break;

        }

    }
}
