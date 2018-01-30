package zdkdream.rd_components.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author CT on 2017/12/1.
 * @email 595171796@qq.com
 * 启动Activity
 */

public class StartActivityTool {
    private static final String TAG = "StartActivityTool";

    /**
     * 启动Activity
     */
    public static void startToActivity(Context context, Class c) {
        startToActivity(context, c, null);
    }

    /**
     * 带bundle
     */
    public static void startToActivity(Context context, Class c, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    /**
     * 启动带回调的Activity ,没有携带参数
     */
    public static void startActivityForResult(Activity activity, Class c, int requestCode) {
        startActivityForResult(activity, c, null, requestCode);
    }

    /**
     * 启动带回调的Activity
     */
    public static void startActivityForResult(Activity activity, Class c, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, c);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }


}
