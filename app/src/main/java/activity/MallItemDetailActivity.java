package activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.GetGoodsListAdapter;
import bean.GetGoods;
import bean.GetGoodsDetail;
import butterknife.Bind;
import butterknife.ButterKnife;
import common.GradationScrollView;
import common.HaveToken;
import common.MD5;
import common.NetworkImageHolderView;
import common.StatusBarUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商品内容详情
 * Created by dell on 2016/11/9.
 */
public class MallItemDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener, GradationScrollView.ScrollViewListener {


    @Bind(R.id.toolbar_malldeatil)
    Toolbar toolbar;
    @Bind(R.id.convenientBanner_malldetail)
    ConvenientBanner convenientBanner;
    @Bind(R.id.tv_malldetail_title)
    TextView tv_title;
    @Bind(R.id.tv_malldetail_des)
    TextView tv_des;
    @Bind(R.id.tv_malldetail_content)
    TextView tv_content;
    @Bind(R.id.tv_malldetail_oldprice)
    TextView tv_oldprice;
    @Bind(R.id.tv_malldetail_newprice)
    TextView tv_newprice;
    @Bind(R.id.scrollView)
    GradationScrollView scrollView;

    private int imageHeight;//顶部高度
    private float headerHeight;
    private float minHeaderHeight;//顶部最低高度，即Bar的高度

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    //    private List<String> networkImages;
    private List<String> imgs = new ArrayList<>();//convenientBanner加载网络图片
    //    private String[] images = {"http://www.langtianhealth.com:20080/v2/image/53ad6cb80da30b1067586a2973d4d586"};///加载网络数组

    String goodsId, centerId;

    String url_getgoodsdeatil = "http://www.yimijianfang.com/api/app/goods_detail/";//<id>/?token=<token>
    final OkHttpClient client = new OkHttpClient();
    String token, MD5token;

    GetGoodsDetail getGoodsDetail;
    GetGoodsDetail.data getGoodsDetail_data;
    //    List<GetGoodsDetail.data> getGoodsDetailList;
    List<GetGoodsDetail.data.Src> getSrcList;
    String Title;
    String Des;
    String NewPrice;
    String OldPrice;
    String Content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImgTransparent(this);//设置最顶上的状态栏透明度 沉浸式
        setContentView(R.layout.activity_mallitemdetail);
        ButterKnife.bind(this);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        goodsId = intent.getStringExtra("goodsid");
        centerId = intent.getStringExtra("centercodeid");

        //获取
        HaveToken.Token();
        token = HaveToken.str_goodsdetail;
        //加密token
        MD5.md5(token);
        MD5token = MD5.b;
        //获取服务中心网络请求数据
        getGoodsDeatilRequest();

//        convenientBanner.setFocusable(true);
//        convenientBanner.setFocusableInTouchMode(true);
//        convenientBanner.requestFocus();
        initListeners();
    }

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = convenientBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                convenientBanner.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = convenientBanner.getHeight();

                scrollView.setScrollViewListener(MallItemDetailActivity.this);
            }
        });
    }

    private void getGoodsDeatilRequest() {
        //Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应。

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url_getgoodsdeatil + goodsId + "/" + "?token=" + MD5token)
                .build();
        //ss
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String message = response.body().string();
                        Log.i("TAG", "打印GET服务中心接口响应的数据：" + message);
                        if (message.isEmpty() || message == null) {
                            Toast.makeText(MallItemDetailActivity.this, "获取数据失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            getGoodsDetail = gson.fromJson(message, GetGoodsDetail.class);
                            getGoodsDetail.printOut();
//                        System.out.println("getContent"+getGoodsDetail_data.getContent()+
//                                "getDes"+getGoodsDetail_data.getDes()+
//                                "getTitle"+getGoodsDetail_data.getTitle()+
//                                "getID"+getGoodsDetail_data.getID());
                            Title = getGoodsDetail.getData().getTitle();
                            Des = getGoodsDetail.getData().getDes();
                            NewPrice = getGoodsDetail.getData().getNewPrice();
                            OldPrice = getGoodsDetail.getData().getOldPrice();
                            OldPrice = "¥" + OldPrice;
                            Content = getGoodsDetail.getData().getContent();
                            getSrcList = getGoodsDetail.getData().getSrc();

                            for (GetGoodsDetail.data.Src src1 : getSrcList) {
                                System.out.println("Src is+" + src1.getSrc() +
                                        "getSrcList is +" + getSrcList.size());

                                imgs.add(src1.getSrc());
                                //list->数组
//                                int size = getSrcList.size();
//                                images = src1.getSrc();

                            }
                            System.out.println("imgs is+" + imgs.toString());
//


//                        //UI来更新UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    tv_title.setText(Title);
                                    tv_des.setText(Des);
                                    tv_newprice.setText("¥" + NewPrice);

                                    //让旧价钱带有横杠带上横线
                                    SpannableString sp_OldPrice = new SpannableString(OldPrice);
                                    sp_OldPrice.setSpan(new StrikethroughSpan(), 0, OldPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    tv_oldprice.setText(sp_OldPrice);

                                    tv_content.setText(Content);
                                    initIndicator();//初始化加载的图片
                                }
                            });

                        }
                        try {

                        } catch (IllegalStateException | JsonSyntaxException exception) {
                            Log.i("TAG", exception.toString());
                        }


                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void initIndicator() {
        initImageLoader();
        //网络加载例子
//        networkImages = Arrays.asList(images);
//        networkImages=imgs;
        if (imgs.size() == 1) {


            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, imgs)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
//                        //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnItemClickListener(this);
        } else {

            convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, imgs)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
//                        //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnItemClickListener(this);
        }
    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                MallItemDetailActivity.this).defaultDisplayImageOptions(defaultOptions)
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
        Toast.makeText(MallItemDetailActivity.this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(MallItemDetailActivity.this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
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

    /**
     * 滑动监听
     *
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            toolbar.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (y > 0 && y <= imageHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
//            toolbar.setAlpha(Color.argb((int) alpha, 255,255,255));
            toolbar.setBackgroundColor(Color.argb((int) alpha, 144, 151, 166));
        } else {    //滑动到banner下面设置普通颜色
            toolbar.setBackgroundColor(Color.argb((int) 255, 144, 151, 166));
        }
    }
}
