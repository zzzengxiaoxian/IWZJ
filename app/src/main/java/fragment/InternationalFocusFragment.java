package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.google.gson.Gson;
import com.iwzj.ltkj.iwzj.R;

import java.util.List;

import activity.WebNews;
import adapter.ContentListAdapter;
import bean.ShowapiNews;

/**
 * Created by dell on 2016/10/19.
 */
public class InternationalFocusFragment extends Fragment {


    ShowapiNews showapiNews;
    ShowapiNews.Showapi_res_body.Pagebean.Contentlist conten_tlist;
    List<ShowapiNews.Showapi_res_body.Pagebean.Contentlist> contentlist;
    List<ShowapiNews.Showapi_res_body.Pagebean.Contentlist.Imageurls> imageurlslist;
    ShowapiNews.Showapi_res_body.Pagebean.Contentlist.Imageurls image_urls;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_internetfocus, container, false);

        intUI();

        listView = (ListView) rootView.findViewById(R.id.listInternationnews);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conten_tlist = contentlist.get(position);
                Intent intent=new Intent();
                intent.putExtra("weblink",conten_tlist.getLink());
                intent.setClass(getActivity(),
                        WebNews.class);
                startActivity(intent);


                Toast.makeText(getActivity(), "你点击了" + conten_tlist.getTitle().toString(), Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

    /*解析国内最新新闻*/
    private void intUI() {
        Parameters para = new Parameters();
        para.put("channelId", "5572a109b3cdc86cf39001de");
        para.put("channelName", "国际最新");
        para.put("title", "上市");
        para.put("page", "1");
        para.put("needContent", "0");
        para.put("needHtml", "0");
        ApiStoreSDK.execute("http://apis.baidu.com/showapi_open_bus/channel_news/search_news",// 接口地址
                ApiStoreSDK.GET,// 接口方法
                para,// 接口参数
                new ApiCallBack() {// 接口回调方法 回调方法在UI线程中执行，可以直接修改UI。
                    //响应数据保存在responseString中
                    @Override
                    public void onSuccess(int status, String responseString) {
                        Log.i("sdkdemo", "onSuccess");
                        Gson gson = new Gson();
//                        Type type = new TypeToken<ShowapiNews>() {}.getType();
//                        showapiNews = gson.fromJson(responseString, type);
                        showapiNews = gson.fromJson(responseString, ShowapiNews.class);


                        ShowapiNews.Showapi_res_body showapi_res_body = showapiNews.getShowapi_res_body();
                        ShowapiNews.Showapi_res_body.Pagebean pagebean = showapi_res_body.getPagebean();
                        contentlist = pagebean.getContentlist();
                        /*循环读取出数据设置到contentlist1中*/
                        for (ShowapiNews.Showapi_res_body.Pagebean.Contentlist contentlist1 : contentlist)
                            System.out.println("id is" + contentlist1.getChannelId() +
                                    "name is" + contentlist1.getChannelName() + "desc is" + contentlist1.getDesc());


                        //设置信息到界面
                        contentlist.add(conten_tlist);

                        ContentListAdapter adapter = new ContentListAdapter(getActivity(), contentlist);
                        listView.setAdapter(adapter);


                        //通过循环读取list中的数据
                        for (int i = 0; i < contentlist.size(); i++) {

                            System.out.print(contentlist.get(i) + "\n"); // 通过list.get(索引)的方法取出集合中的值

                        }
//                        String name= contentlisresult.getChannelName();
                        Log.i("taaaaaaaaaa", "name IS+++++++" + "a is+" + contentlist);
                        super.onSuccess(status, responseString);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("sdkdemo", "onComplete");
                    }

                    /*onError 调用时，statusCode参数值对应如下：
                    -1 没有检测到当前网络；
                        -3 没有进行初始化;
                        -4 传参错误
                        其他数值是http状态码，或服务器响应的errNum，请查阅响应字符串responseString
                    */
                    @Override
                    public void onError(int status, String responseString, Exception e) {
                        Log.i("sdkdemo", "onError, status: " + status);
                        Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));

                    }
                }
        );
    }


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InternationalFocusFragment newInstance(int sectionNumber) {
        InternationalFocusFragment fragment = new InternationalFocusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public InternationalFocusFragment() {
    }

}
