package adapter;

import android.content.Context;

import com.iwzj.ltkj.iwzj.R;

import java.util.List;

import bean.ChoseCity;

/**
 * Created by dell on 2016/11/1.
 */
public class CityListAdapter extends CommonListAdapter<ChoseCity.data> {

    public CityListAdapter(Context context, List<ChoseCity.data> datas) {
        super(context, datas, R.layout.item_citylist);
    }

    @Override
    public void convert(ListViewHolder holder, ChoseCity.data data) {

        holder.setText(R.id.tv_citylist,data.getName());

    }
}
