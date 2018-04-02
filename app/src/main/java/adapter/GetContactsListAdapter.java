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

import activity.EmergencyContactActivity;
import common.GetImageByUrl;

/**
 * Created by dell on 2016/11/29.
 */
public class GetContactsListAdapter extends BaseAdapter {
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
    public GetContactsListAdapter(Context context, List<Map<String, Object>> data) {
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
            convertView = View.inflate(context, R.layout.item_contactlist, null);
            viewHolder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_contactlistItemname);
            viewHolder.tv_phone = (TextView) convertView
                    .findViewById(R.id.tv_contactlistItemphone);
            viewHolder.tv_relation = (TextView) convertView
                    .findViewById(R.id.tv_contactlistItemrelation);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String name = map.get("name").toString();
        String phone = map.get("phone").toString();
        String relation = map.get("relation").toString();


        StringBuilder sbname = new StringBuilder(name);
        sbname.replace(0, 1, "*");

        StringBuilder sbphone = new StringBuilder(phone);
        sbphone.replace(3, 8, "*****");

        relation = "(" + relation + ")";

        viewHolder.tv_name.setText(sbname);
        viewHolder.tv_phone.setText(sbphone);
        viewHolder.tv_relation.setText(relation);

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {

        public TextView tv_name, tv_phone, tv_relation;

    }
}
