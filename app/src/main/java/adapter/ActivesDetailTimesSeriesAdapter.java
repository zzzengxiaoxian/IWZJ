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

import activity.ActivesDetail;
import common.GetImageByUrl;

/**
 * 系列活动适配器
 * Created by dell on 2016/11/15.
 */
public class ActivesDetailTimesSeriesAdapter extends BaseAdapter {

    // 要显示的数据的集合
    private List<Map<String, Object>> data;


    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;


    public ActivesDetailTimesSeriesAdapter(Context context, List<Map<String, Object>> data) {
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
            convertView = View.inflate(context, R.layout.item_activedetailtimesseries, null);
            viewHolder.teims_Sdate_series = (TextView) convertView
                    .findViewById(R.id.teims_Sdate_series);
            viewHolder.teims_Edate_series = (TextView) convertView
                    .findViewById(R.id.teims_Edate_series);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        // 获取图片的url路径
        String sdate = map.get("activeTimeSdate").toString();
        String edate = map.get("activeTimeEdate").toString();
        viewHolder.teims_Sdate_series.setText("开始时间:"+sdate);
        viewHolder.teims_Edate_series.setText("结束时间:"+edate);
        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     */
    class ViewHolder {
        public TextView teims_Sdate_series, teims_Edate_series;
    }
}
