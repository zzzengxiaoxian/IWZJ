package com.iwzj.ltkj.iwzj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import activity.CompanyChoseService;
import activity.HomePlace;
import cn.pedant.SweetAlert.SweetAlertDialog;
import fragment.HomeFragment;
import fragment.PhoneFragment;
import fragment.ResettableFragment;
import fragment.UserFragment;
import io.karim.MaterialTabs;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Holds references to fragments from the time they are attached to Activity until they are dettached.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<ResettableFragment> mFragments = new ArrayList<>();

    ViewPager mViewPager;
    MaterialTabs mMaterialTabs;

    SharedPreferences sharedPreferences, preferences;


//    final int REQUEST_CODE = 1000;
//    String isFisrt="is";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //在这儿将isfirstin 设置为false，代表已经不是第一次登陆了。
        preferences = getSharedPreferences("APPisFirst", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putBoolean("isFirstIn", false);
        editor1.commit();


//        new SweetAlertDialog(this)
//                .setTitleText("提示")
//                .setContentText("请点击开始定位按钮定位您的位置，以便为您提供附近更好的服务")
//                .setConfirmText("开始定位")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        Intent intent = new Intent();
//                        intent.setClass(MainActivity.this, HomePlace.class);
////                            startActivityForResult(intent, REQUEST_CODE);
//                        sweetAlertDialog.cancel();
//                        MainActivity.this.finish();
//                    }
//                })
//                .show();

        //materialTabs--start
        // Initialize the ViewPager and set an adapter
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mMaterialTabs = (MaterialTabs) findViewById(R.id.material_tabs);
        // Bind the tabs to the ViewPager
        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mMaterialTabs.setViewPager(mViewPager);

//        // Set custom font/typeface to text in tabs
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
//        mMaterialTabs.setTypeface(typeface, Typeface.BOLD);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        Intent intent1 = getIntent();
        int index = intent1.getIntExtra("frgmentindex", 0);
        if (index == 1) {
            mViewPager.setCurrentItem(2);
        } else {
            mViewPager.setCurrentItem(0);

        }
        //materialTabs--start

        //获取定位界面传来的城市值
        Intent intent = getIntent();
        String intent_city = intent.getStringExtra("city");
        String intent_citycode = intent.getStringExtra("citycode");


        //将城市值保存在sharedpreferences中，下次登录默认值为此。
        sharedPreferences = getSharedPreferences("city_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("chosecity", intent_city);
        editor.putString("cityId", intent_citycode);
        editor.commit();


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            String result = data.getStringExtra("first");
//            Log.i(TAG, result);
//            if (result.equals("isfirst")) {
//                isFisrt = true;
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    private void resetDefaults() {
        for (ResettableFragment f : mFragments) {
            f.setupAndReset();
        }
    }

    /**
     * Add fragment to {@link #mFragments} when attached to Activity.
     */
    public void addFragment(ResettableFragment f) {
        mFragments.add(f);
    }


    /**
     * Remove fragment from {@link #mFragments} when detached to Activity.
     */
    public void removeFragment(ResettableFragment f) {
        mFragments.remove(f);
    }

    public class MainActivityPagerAdapter extends FragmentPagerAdapter implements MaterialTabs.CustomTabProvider {

        private final String[] TITLES = {"首页", "热线电话", "我的"};

        private final int[] UNSELECTED_ICONS = {R.drawable.bottom_home_unactive, R.drawable.bottom_phone_unactive, R.drawable.bottom_user_unactive};
        private final int[] SELECTED_ICONS = {R.drawable.bottom_home_active, R.drawable.bottom_phone_active, R.drawable.bottom_user_active};

        public MainActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                default:
                    return new HomeFragment();
                case 1:
                    return new PhoneFragment();
                case 2:
                    return new UserFragment();

            }
        }

        @Override
        public View getCustomTabView(ViewGroup parent, int position) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(UNSELECTED_ICONS[position]);
            return imageView;
        }

        @Override
        public void onCustomTabViewSelected(View view, int position, boolean alreadySelected) {
            Log.i(TAG, "custom tab view selected with position = " + position);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(SELECTED_ICONS[position]);
            }
        }

        @Override
        public void onCustomTabViewUnselected(View view, int position, boolean alreadyUnselected) {
            Log.i(TAG, "custom tab view unselected with position = " + position);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(UNSELECTED_ICONS[position]);
            }
        }
    }


    /*双击退出*/
    private static Boolean isQuit = false;
    private Timer timer = new Timer();

    // 监听按下系统back按键后，提示Toast并推出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isQuit == false) {
                isQuit = true;
                Toast.makeText(getBaseContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
                finish();
            }
        } else {
        }
        return false;
    }
}
