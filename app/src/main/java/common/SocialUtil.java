package common;

/**
 * Created by dell on 2018/1/3.
 */

import net.arvin.socialhelper.SocialHelper;

/**
 * Created by arvinljw on 17/11/27 17:33
 * Function：
 * Desc：
 */
public class SocialUtil {
    private static SocialUtil sInstance = new SocialUtil();

    private SocialHelper socialHelper;

    private SocialUtil() {
        socialHelper = new SocialHelper.Builder()
                .setQqAppId("qqAppId")
                .setWxAppId("wxAppId")
                .setWxAppSecret("wxAppSecret")
                .setWbAppId("wbAppId")
                .setWbRedirectUrl("wbRedirectUrl")
                .build();
    }

    public static SocialUtil getInstance() {
        return sInstance;
    }

    public SocialHelper socialHelper() {
        return socialHelper;
    }
}