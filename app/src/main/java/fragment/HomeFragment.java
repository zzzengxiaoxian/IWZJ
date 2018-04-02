package fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.iwzj.ltkj.iwzj.MainActivity;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activity.HomeActive;
import activity.HomeCare;
import activity.HomeCompany;
import activity.HomeFamily;
import activity.HomeHealthy;
import activity.HomeMusicPlay;
import activity.HomeMyMessage;
import activity.HomeNews;
import activity.HomePlace;
import activity.HomeService;
import activity.LoginActivity;
import activity.UserPersonalINFO;
import adapter.CommonAdapter;
import cn.pedant.SweetAlert.SweetAlertDialog;
import common.EmptyWrapper;
import common.HeaderAndFooterWrapper;
import common.LoadMoreWrapper;
import common.NetworkImageHolderView;
import common.UpMarqueeTextView;


/**
 * 主页fragment
 * Created by dell on 2016/8/8.
 */
public class HomeFragment extends Fragment implements ResettableFragment, AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener, View.OnClickListener {

    private MainActivity mainActivity;

    //跑马灯 竖直的文字 use
    private UpMarqueeTextView mMarqueeTextView;
    private String[] names = {"i吾之家app全新上线！", "我的健康我做主！", "希望您的每一天都充满欢乐！"};//设置跑马灯文字
    private int index = 0;//标志

    //广告栏 use
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<String> networkImages;
    private String[] images = {
            "https://raw.githubusercontent.com/zzzengxiaoxian/MyApp/master/photo/hotphone.jpg"
            ,"https://raw.githubusercontent.com/zzzengxiaoxian/MyApp/master/photo/thsc.png"

    };

    //recyclevewi use
    RecyclerView recyclerView;
    private CommonAdapter<String> mAdapter;
    private List<String> mDatas = new ArrayList<>();
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private TextView tv_place;
    private ImageView message;

    View linear_healthy, linear_family, linear_active, linear_company, linear_service, linear_news, linear_music, linear_seek, linear_tarvel, linear_love;


    SharedPreferences sharedPreferences, sp_login;

    String city, cityid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        mMarqueeTextView = (UpMarqueeTextView) v.findViewById(R.id.marquee);
        convenientBanner = (ConvenientBanner) v.findViewById(R.id.convenientBanner);
        tv_place = (TextView) v.findViewById(R.id.tv_place);
        message = (ImageView) v.findViewById(R.id.img_message);
        linear_healthy = v.findViewById(R.id.linear_healthy);
        linear_healthy = v.findViewById(R.id.linear_healthy);
        linear_family = v.findViewById(R.id.linear_family);
        linear_active = v.findViewById(R.id.linear_activity);
        linear_company = v.findViewById(R.id.linear_company);
        linear_service = v.findViewById(R.id.linear_service);
        linear_news = v.findViewById(R.id.linear_news);
        linear_music = v.findViewById(R.id.linear_music);
        linear_seek = v.findViewById(R.id.linear_seek);
        linear_tarvel = v.findViewById(R.id.linear_travle);
        linear_love = v.findViewById(R.id.linear_love);

        linear_healthy.setOnClickListener(this);
        linear_healthy.setOnClickListener(this);
        linear_family.setOnClickListener(this);
        linear_active.setOnClickListener(this);
        linear_company.setOnClickListener(this);
        linear_service.setOnClickListener(this);
        linear_news.setOnClickListener(this);
        linear_music.setOnClickListener(this);
        linear_seek.setOnClickListener(this);
        linear_tarvel.setOnClickListener(this);
        linear_love.setOnClickListener(this);
        message.setOnClickListener(this);
        tv_place.setOnClickListener(this);
        // start thread 每3秒index 加一次
        new Thread(new MyThread()).start();


        init();
//        initDatas();


//        //start recycleview
//
//        //RecycleView 设置以什么模式显示,这里用线性宫格显示 类似于grid view
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        //设置adapter
//        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.item_list, mDatas) {
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
//            }
//        };
//        //设置header和Footer
//        initHeaderAndFooter();
//        //当数据为空时执行
//        //initEmptyView();
//        //加载更多选项时,底部设置加载更多的progress正在加载...字样
//        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
//        //加载自定义dialog布局
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
//        //加载后显示的数据设置.
//        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0; i < 10; i++) {
//                            mDatas.add("Add:" + i);
//                        }
//                        mLoadMoreWrapper.notifyDataSetChanged();
//
//                    }
//                }, 3000);
//            }
//        });
//        //然后重新setadapter 将加载后的数据显示
//        recyclerView.setAdapter(mLoadMoreWrapper);
//        //设置每一项的点击事件
//        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
//            //单机事件
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Toast.makeText(getActivity(), "pos = " + position, Toast.LENGTH_SHORT).show();
//                mAdapter.notifyItemRemoved(position);
//            }
//
//            //长按事件
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });


        //将城市值保存在sharedpreferences中，下次登录去得此值
        sharedPreferences = getActivity().getSharedPreferences("city_name", getActivity().MODE_PRIVATE);
        city = sharedPreferences.getString("chosecity", "保定市");
        cityid = sharedPreferences.getString("cityId", "130600");
        Log.i("cityname", city);
        Log.i("cityid", cityid);

        tv_place.setText(city);

        return v;
    }

//    //为空时加载的adapter和布局
//    private void initEmptyView() {
//        mEmptyWrapper = new EmptyWrapper(mAdapter);
//        mEmptyWrapper.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, recyclerView, false));
//    }
//
//    //初始化头部尾部
//    private void initHeaderAndFooter() {
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
//
//        TextView t1 = new TextView(getActivity());
//        t1.setText("Header 1");
//        TextView t2 = new TextView(getActivity());
//        t2.setText("Header 2");
//        mHeaderAndFooterWrapper.addHeaderView(t1);
//        mHeaderAndFooterWrapper.addHeaderView(t2);
//    }
//
//    //初始化显示的数据
//    private void initDatas() {
//        for (int i = 'A'; i <= 'z'; i++) {
//            mDatas.add((char) i + "");
//        }
//    }

    //------------------------end recycleview
    /*跑马灯文字判断 当大于最大条数时候，这里是3，就回滚第一条*/
    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    String text = names[index];
                    index++;
                    if (index > 2) {
                        index = 0;
                    }
                    mMarqueeTextView.setText(text);
            }
            super.handleMessage(msg);
        }
    };

    /*各个布局的点击事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_healthy:
                sp_login = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_health = sp_login.getBoolean("isnotLogin", true);

                if (isFirstLogin_health == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intenthealthy = new Intent();
                    intenthealthy.setClass(getActivity(), HomeHealthy.class);
                    startActivity(intenthealthy);
                }

                break;
            case R.id.linear_family:
                sp_login = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_family = sp_login.getBoolean("isnotLogin", true);

                if (isFirstLogin_family == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentfamily = new Intent();
                    intentfamily.setClass(getActivity(), HomeFamily.class);
                    startActivity(intentfamily);
                }

                break;
            case R.id.linear_activity:
                sp_login = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_active = sp_login.getBoolean("isnotLogin", true);

                if (isFirstLogin_active == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentactive = new Intent();
                    intentactive.setClass(getActivity(), HomeActive.class);
                    startActivity(intentactive);
                }

                break;
            case R.id.linear_company:
                sp_login = getActivity().getSharedPreferences("APPisnotLogin", getActivity().MODE_PRIVATE);
                Boolean isFirstLogin_company = sp_login.getBoolean("isnotLogin", true);

                if (isFirstLogin_company == true) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intentcompany = new Intent();
                    intentcompany.setClass(getActivity(), HomeCompany.class);
                    intentcompany.putExtra("city", city);
                    intentcompany.putExtra("cityid", cityid);
                    startActivity(intentcompany);
                }

                break;
            case R.id.linear_service:
                Intent intentservice = new Intent();
                intentservice.setClass(getActivity(), HomeService.class);
                startActivity(intentservice);
                break;
            case R.id.linear_news:
                Intent intentnews = new Intent();
                intentnews.setClass(getActivity(), HomeNews.class);
                startActivity(intentnews);
                break;
            case R.id.linear_music:
                Intent intentmusic = new Intent();
                intentmusic.setClass(getActivity(), HomeMusicPlay.class);
                startActivity(intentmusic);
                break;
            case R.id.linear_seek:
                Intent intentkanhu = new Intent();
                intentkanhu.setClass(getActivity(), HomeCare.class);
                startActivity(intentkanhu);
                break;
            case R.id.linear_travle:
                new SweetAlertDialog(getActivity())
                        .setTitleText("敬请期待").show();
                break;
            case R.id.linear_love:
                new SweetAlertDialog(getActivity())
                        .setTitleText("敬请期待").show();
                break;
            case R.id.tv_place:
                Intent intentplace = new Intent();
                intentplace.setClass(getActivity(), HomePlace.class);
                startActivity(intentplace);
                getActivity().finish();
                break;
            case R.id.img_message:
                Intent intent_msg = new Intent();
                intent_msg.setClass(getActivity(), HomeMyMessage.class);
                startActivity(intent_msg);

                break;

        }

    }

    //3秒
    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);     // sleep 3000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }
        }
    }

    private void init() {
        initImageLoader();
        //本地图片例子
//        convenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
//                .setOnItemClickListener(this);

//        convenientBanner.setManualPageable(false);//设置不能手动影响

        //网络加载例子
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
//                        //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this);


//手动New并且添加到ListView Header的例子
//        ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
//        mConvenientBanner.setMinimumHeight(500);
//        mConvenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnItemClickListener(this);
//        listView.addHeaderView(mConvenientBanner);
    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    //点击切换效果
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

//        点击后加入两个内容
        localImages.clear();
//        localImages.add(R.mipmap.ic_launcher);
//        localImages.add(R.mipmap.ic_launcher);
        convenientBanner.notifyDataSetChanged();

        //控制是否循环
        convenientBanner.setCanLoop(!convenientBanner.isCanLoop());

        convenientBanner.getViewPager().setPageTransformer(true, new RotateUpTransformer());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(getActivity(), "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) getActivity();
        mainActivity.addFragment(this);
    }

    @Override
    public void onDetach() {
        mainActivity.removeFragment(this);
        super.onDetach();
    }

    @Override
    public void setupAndReset() {

    }


}
