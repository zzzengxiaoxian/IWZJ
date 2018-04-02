package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;
import java.util.Map;

import activity.HomeItemActive;
import common.GetImageByUrl;

/**
 * Created by dell on 2016/11/14.
 */
public class HomeItemActivesAdapter extends BaseAdapter {
    // 要显示的数据的集合
    private List<Map<String, Object>> data;


    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;

    public HomeItemActivesAdapter(Context context, List<Map<String, Object>> data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // 判断当前条目是否为null
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_itemactives, null);//之前用到的item_itemactives
            viewHolder.iv_image = (ImageView) convertView
                    .findViewById(R.id.img_activesitempic);
            viewHolder.img_activestype = (ImageView) convertView
                    .findViewById(R.id.img_activestype);
            viewHolder.tv_title = (TextView) convertView
                    .findViewById(R.id.tv_activesitemTitlename);
            viewHolder.tv_des = (TextView) convertView
                    .findViewById(R.id.tv_activesItem_coupon);
//            viewHolder.cardView = (CardView) convertView.findViewById(R.id.one_card_view);
//            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    data.get(position);
//                }
//            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String url = map.get("activepic").toString();
        String name = map.get("activetitle").toString();
        String coupon = map.get("activecoupon").toString();
        String type = map.get("acticetype").toString();


        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(viewHolder.iv_image, url);
        viewHolder.tv_des.setText("人数上限：" + coupon);
        viewHolder.tv_title.setText(name);
        if (type.equals("1")) {
            viewHolder.img_activestype.setImageResource(R.mipmap.ic_yici);
        } else if (type.equals("2")) {
            viewHolder.img_activestype.setImageResource(R.mipmap.ic_xielie);
        } else {
            viewHolder.img_activestype.setImageResource(R.mipmap.ic_xunhuan);
        }

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {
        public ImageView iv_image, img_activestype;
        public TextView tv_title, tv_des;
        public CardView cardView;
    }
}
