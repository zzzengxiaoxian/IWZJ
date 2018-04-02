package activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.iwzj.ltkj.iwzj.R;

import net.yanzm.mth.MaterialTabHost;

import java.util.Locale;

import fragment.ActiveALLFragment;
import fragment.ActiveServiceFragment;
import fragment.ChinaNewsFragment;
import fragment.ComputerFragment;
import fragment.EducationFocusFragment;
import fragment.FinancialFocusFragment;
import fragment.HouseFocusFragment;
import fragment.InternationalFocusFragment;
import fragment.InternetFocusFragment;
import fragment.MilitaryFocusFragment;
import fragment.SocietyFoucsFragment;
import fragment.TechnologyFocusFragment;

/**
 * 新闻资讯
 * Created by dell on 2016/10/13.
 */
public class HomeNews extends AppCompatActivity {
    private Toolbar toolbar;
    ViewPager mViewPager;
    MaterialTabHost tabHost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenews);

        toolbar = (Toolbar) findViewById(R.id.toolbar_newszx);
        if (toolbar != null) {
            toolbar.setTitle(" ");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        // Initialize the ViewPager and set an adapter
        mViewPager = (ViewPager) findViewById(R.id.pager);
        tabHost = (MaterialTabHost) findViewById(R.id.tabhost);
/*SETUP*/
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
//        tabHost.setType(MaterialTabHost.Type.Centered);
//        tabHost.setType(MaterialTabHost.Type.LeftOffset);
        NewsPagerAdapter pagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(pagerAdapter.getPageTitle(i));
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);

        tabHost.setOnTabChangeListener(new MaterialTabHost.OnTabChangeListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }
        });


    }





    /*设置adapter*/
    public class NewsPagerAdapter extends FragmentPagerAdapter {

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return ChinaNewsFragment.newInstance(position);
                case 1:
                    return InternationalFocusFragment.newInstance(position);
                case 2:
                    return MilitaryFocusFragment.newInstance(position);
                case 3:
                    return FinancialFocusFragment.newInstance(position);
                case 4:
                    return InternetFocusFragment.newInstance(position);
                case 5:
                    return HouseFocusFragment.newInstance(position);
                case 6:
                    return EducationFocusFragment.newInstance(position);
                case 7:
                    return TechnologyFocusFragment.newInstance(position);
                case 8:
                    return SocietyFoucsFragment.newInstance(position);
                case 9:
                    return ComputerFragment.newInstance(position);
            }
//            if (position == 0) {
//                return ActiveALLFragment.newInstance(position);
//            } else {
//                return ActiveServiceFragment.newInstance(position);
//            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "国内".toUpperCase(l);
                case 1:
                    return "国际".toUpperCase(l);
                case 2:
                    return "军事".toUpperCase(l);
                case 3:
                    return "财经".toUpperCase(l);
                case 4:
                    return "网络".toUpperCase(l);
                case 5:
                    return "房产".toUpperCase(l);
                case 6:
                    return "理财".toUpperCase(l);
                case 7:
                    return "科技".toUpperCase(l);
                case 8:
                    return "体育".toUpperCase(l);
                case 9:
                    return "电脑".toUpperCase(l);
            }
            return null;
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
