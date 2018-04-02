package common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dell on 2016/12/6.
 */

public class Intents {

    private static Intents intents;

    public static Intents getIntents() {
        if (intents == null)
            intents = new Intents();
        return intents;
    }

    // context this, cs跳转对象 bundle 传递参数
    public static void Intenthavebundle(Context context, Class<?> cs, Bundle bundle) {
        Intent i = new Intent(context, cs);
        if (bundle != null)
            i.putExtras(bundle);
        context.startActivity(i);
    }
    //ActivityUtil
    public static void startToActivity(Context context, Class cls){
        Intent intent=new Intent(context,cls);
        context.startActivity(intent);
    }
}
