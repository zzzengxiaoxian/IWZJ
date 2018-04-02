package common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;

/**
 * Created by dell on 2016/11/22.
 */
public class IsTopActivity extends Activity {

    public boolean isTopActivity(Activity activity) {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName().contains((CharSequence) activity);
    }
}
