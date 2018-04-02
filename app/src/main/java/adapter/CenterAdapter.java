package adapter;

import android.content.Context;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;

import bean.ChoseCity;
import bean.GetCenter;

/**
 * Created by dell on 2016/11/2.
 */
public class CenterAdapter extends CommonListAdapter<GetCenter.data>{

    public CenterAdapter(Context context, List<GetCenter.data> datas) {
        super(context, datas, R.layout.item_listcenter);
    }

    @Override
    public void convert(ListViewHolder holder, GetCenter.data data) {

        holder.setText(R.id.centername,data.getName());
        holder.setText(R.id.centeraddress,data.getAddress());
        String lng=data.getLng();
        String lat=data.getLat();

//        holder.setText(R.id.kilomiters,data.get);

    }
}
