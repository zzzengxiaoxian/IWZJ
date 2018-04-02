package activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iwzj.ltkj.iwzj.R;

/**
 * Created by dell on 2016/10/13.
 */
public class popuwindow_share extends PopupWindow {

    private ImageView img_wechate, img_circle, img_qq, img_message;
    private View mMenuView;
    private TextView Cancle;

    public popuwindow_share(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popupwindow_share, null);

        img_wechate = (ImageView) mMenuView.findViewById(R.id.wechat);
        img_circle = (ImageView) mMenuView.findViewById(R.id.circle);
        img_qq = (ImageView) mMenuView.findViewById(R.id.qq);
        img_message = (ImageView) mMenuView.findViewById(R.id.message);
        Cancle = (TextView) mMenuView.findViewById(R.id.tv_cancle);
        Cancle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        img_wechate.setOnClickListener(itemsOnClick);
        img_circle.setOnClickListener(itemsOnClick);
        img_qq.setOnClickListener(itemsOnClick);
        img_message.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_share).getTop();
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
