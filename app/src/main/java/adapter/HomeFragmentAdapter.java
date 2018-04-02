package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import common.ViewHolder;

/**
 * Created by dell on 2016/10/8.
 */
public class HomeFragmentAdapter extends CommonAdapter {



    public HomeFragmentAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
