package zdkdream.rd_components.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @author CT on 2018/1/10.
 * @email 595171796@qq.com
 * Activity 进行统一管理
 */

//public abstract class BaseActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //将Activity实例添加到AppManager的堆栈
//        AppManager.getAppManager().addActivity(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //将Activity实例从AppManager的堆栈中移除
//        AppManager.getAppManager().finishActivity(this);
//    }
//}

public class AppMannager {
    private static Stack<Activity> activityStack;
    private static AppMannager instance;


    private AppMannager() {
    }


    /**
     * 单列模式
     */
    public static AppMannager getActivityMannager() {
        if (instance == null) {
            synchronized (AppMannager.class) {
                instance = new AppMannager();
            }
        }
        return instance;
    }


    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();

        }
        activityStack.add(activity);
    }


    /**
     * 结束指定的Activity
     */

    public void finishActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Class<?> c) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(c)) {
                finishActivity(activity);
            }
        }
    }


    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMannager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMannager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}



