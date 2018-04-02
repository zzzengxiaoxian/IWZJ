package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;
import java.util.Map;

import activity.HomeActive;
import common.GetImageByUrl;

/**
 * Created by dell on 2016/11/14.
 */
public class HomeActiveListAdapter extends BaseAdapter {
    // 要显示的数据的集合
    private List<Map<String, Object>> data;


    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;

    public HomeActiveListAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断当前条目是否为null
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_activelist, null);
            viewHolder.iv_image = (ImageView) convertView
                    .findViewById(R.id.img_activelist);
            viewHolder.tv_text = (TextView) convertView
                    .findViewById(R.id.tv_activelist);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String url = map.get("url_activelist").toString();
        String name = map.get("name_activelist").toString();
        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(viewHolder.iv_image, url);
        viewHolder.tv_text.setText(name);

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_text;
    }
}
