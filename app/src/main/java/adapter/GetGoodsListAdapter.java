package adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;
import java.util.Map;

import common.GetImageByUrl;

/**
 * Created by dell on 2016/11/8.
 */
public class GetGoodsListAdapter extends BaseAdapter {

    // 要显示的数据的集合
    private List<Map<String, Object>> data;


    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;

    /**
     * 构造函数
     *
     * @param context
     * @param data
     */
    public GetGoodsListAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        // 判断当前条目是否为null
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_getgoodslist, null);
            viewHolder.iv_image = (ImageView) convertView
                    .findViewById(R.id.img_goods_imageview);
            viewHolder.tv_Title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            viewHolder.tv_dec = (TextView) convertView
                    .findViewById(R.id.tv_dec);
            viewHolder.tv_newprice = (TextView) convertView
                    .findViewById(R.id.tv_newprice);
            viewHolder.tv_oldprice = (TextView) convertView
                    .findViewById(R.id.tv_oldprice);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String url = map.get("url").toString();
        String Title = map.get("Title").toString();
        String Des = map.get("Des").toString();
        String NewPrice = map.get("NewPrice").toString();
        String OldPrice = map.get("OldPrice").toString();
        String OldPrice_1 = "¥" + OldPrice;
        //让旧价钱带有横杠带上横线
        SpannableString sp_OldPrice = new SpannableString(OldPrice_1);
        sp_OldPrice.setSpan(new StrikethroughSpan(), 0, OldPrice_1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上
        GetImageByUrl getImageByUrl = new GetImageByUrl();
        getImageByUrl.setImage(viewHolder.iv_image, url);
        viewHolder.tv_Title.setText(Title);
        viewHolder.tv_dec.setText(Des);
        viewHolder.tv_newprice.setText("¥" + NewPrice);
        viewHolder.tv_oldprice.setText(sp_OldPrice);

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_Title, tv_dec, tv_newprice, tv_oldprice;

    }
}
