package activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.iwzj.ltkj.iwzj.R;

/**
 * 紧急联系人
 * Created by dell on 2016/10/10.
 */
public class PopupWindowContactList extends PopupWindow {
    private Button btn_cancel, btn_change, btn_delet;
    private View mMenuView;

    public PopupWindowContactList(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_contactlist, null);

        btn_change = (Button) mMenuView.findViewById(R.id.btn_3);
        btn_delet = (Button) mMenuView.findViewById(R.id.btn_2);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_1);

        btn_cancel.setText("取消");
        btn_change.setText("修改");
        btn_delet.setText("删除");
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        btn_delet.setOnClickListener(itemsOnClick);
        btn_change.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
