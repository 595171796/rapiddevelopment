package zdkdream.rapiddevelopment.newbase_activity;

import android.app.Application;
import android.content.Context;

/**
 * @author CT on 2018/1/26.
 * @email 595171796@qq.com
 */

public class BaseApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }
}
