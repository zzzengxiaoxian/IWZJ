package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;
import java.util.Map;

import activity.HomeItemDtDAcitivty;
import common.GetImageByUrl;

/**
 * Created by dell on 2016/11/10.
 */
public class DtdServiceItemListAdapter extends BaseAdapter {

    // 要显示的数据的集合
    private List<Map<String, Object>> data;


    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;

    String phone;

    /**
     * 构造函数
     *
     * @param context
     * @param data
     */
    public DtdServiceItemListAdapter(Context context, List<Map<String, Object>> data) {
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
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断当前条目是否为null
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_dtdserviceitemlist, null);
            viewHolder.iv_pic = (ImageView) convertView
                    .findViewById(R.id.img_dtdserviceitempic);
            viewHolder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_dtdserviceItemTitlename);
            viewHolder.tv_sarea = (TextView) convertView
                    .findViewById(R.id.tv_dtdserviceItemS_Area);
            viewHolder.tv_kilimoter = (TextView) convertView
                    .findViewById(R.id.tv_kiliometer);
            viewHolder.iv_phone = (ImageView) convertView
                    .findViewById(R.id.img_callphone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String url = map.get("dtdlisturl").toString();
        String name = map.get("dtdlistname").toString();
        phone = map.get("dtdlistphone").toString();
        String area = map.get("dtdarea").toString();
        String length = map.get("dtdlength").toString();
        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(viewHolder.iv_pic, url);
        viewHolder.tv_title.setText(name);
        viewHolder.tv_sarea.setText("范围：" + area);
        viewHolder.tv_kilimoter.setText(length);
        viewHolder.iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
    });

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {
        public ImageView iv_pic, iv_phone;
        public TextView tv_title, tv_sarea, tv_kilimoter;
    }
}
