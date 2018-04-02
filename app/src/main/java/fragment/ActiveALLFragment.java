package fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iwzj.ltkj.iwzj.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import activity.HomeActive;
import activity.HomeCompany;
import adapter.MallListAdapterB;
import bean.MallList;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2016/10/16.
 */
public class ActiveALLFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_activeall, container, false);
//        TextView tv = (TextView) v.findViewById(R.id.neirong);
//        tv.setText("Here is page " + getArguments().getInt(ARG_SECTION_NUMBER));
        return v;
    }


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ActiveALLFragment newInstance(int sectionNumber) {
        ActiveALLFragment fragment = new ActiveALLFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ActiveALLFragment() {
    }

}
