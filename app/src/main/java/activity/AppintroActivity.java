package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.iwzj.ltkj.iwzj.R;

import common.SampleSlide;

/**
 * Created by dell on 2016/11/2.
 */
public class AppintroActivity extends AppIntro{
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));

        setFlowAnimation();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, HomePlace.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), "welcome!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {


    }

    public void getStarted(View v) {
        loadMainActivity();
    }
}
