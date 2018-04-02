package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/16.
 */
public class ActiveServiceFragment extends Fragment {
    TextView guanzhu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frgment_fwzx, container, false);
        guanzhu = (TextView) rootView.findViewById(R.id.guanzhu);

        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        return rootView;
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
    public static ActiveServiceFragment newInstance(int sectionNumber) {
        ActiveServiceFragment fragment = new ActiveServiceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ActiveServiceFragment() {
    }


}
