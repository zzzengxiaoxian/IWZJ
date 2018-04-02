package activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import com.iwzj.ltkj.iwzj.R;

/**
 * 弹出实名认证时候的pop
 * Created by dell on 2016/10/10.
 */
public class PopupWindowVerified extends PopupWindow {
    private Button btn_cancel, btn_choicepic, btn_takepic;
    private View mMenuView;

    public PopupWindowVerified(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_verified, null);

        btn_takepic = (Button) mMenuView.findViewById(R.id.btn_3);
        btn_choicepic = (Button) mMenuView.findViewById(R.id.btn_2);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_1);

        btn_cancel.setText("取消");
        btn_choicepic.setText("从相册中选取");
        btn_takepic.setText("拍照");
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        btn_choicepic.setOnClickListener(itemsOnClick);
        btn_takepic.setOnClickListener(itemsOnClick);

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
