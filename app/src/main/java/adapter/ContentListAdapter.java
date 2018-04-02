package adapter;

import android.content.Context;
import android.widget.ImageView;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;

import bean.ShowapiNews;
import common.GetImageByUrl;
import common.ViewHolder;

/**
 * Created by dell on 2016/10/19.
 */
public class ContentListAdapter extends CommonListAdapter<ShowapiNews.Showapi_res_body.Pagebean.Contentlist> {

    public ContentListAdapter(Context context, List<ShowapiNews.Showapi_res_body.Pagebean.Contentlist> datas) {
        super(context, datas, R.layout.item_contentlist);
    }


    @Override
    public void convert(ListViewHolder holder, ShowapiNews.Showapi_res_body.Pagebean.Contentlist contentlist) {

        holder.setText(R.id.Title, contentlist.getTitle())
                .setText(R.id.Desc, "频道内容:" + contentlist.getTitle())
                .setText(R.id.pubDate, contentlist.getPubDate())
                .setText(R.id.Source, "消息来源：" + contentlist.getSource());



//        GetImageByUrl getImageByUrl = new GetImageByUrl();
//        getImageByUrl.setImage((ImageView) holder.getView(R.id.Img), contentlist.getImageurls().);
    }
}
