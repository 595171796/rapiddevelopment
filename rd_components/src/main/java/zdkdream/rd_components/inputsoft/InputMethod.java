package zdkdream.rd_components.inputsoft;

import android.app.Activity;
import android.app.Service;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * @author CT on 2018/1/24.
 * @email 595171796@qq.com
 * 输入法操作
 * 避免输入法面板遮挡
 * <p>在 manifest.xml 中 activity 中设置</p>
 * <p>android:windowSoftInputMode="adjustPan"</p>
 */
//监听输入法状态
//           private View inputLayout;
//                inputLayout = getWindow().getDecorView();
//                //监听输入法消失
//                inputLayout.getViewTreeObserver().addOnGlobalLayoutListener(
//                        inputLayoutChangeListener = new OnInputLayoutChangeListener(getWindow().getDecorView()) {
//
//                            @Override
//                            public void onLayoutChange() {
//                                if (addTextDialog != null && addTextDialog.getDialog() != null && addTextDialog.getDialog().isShowing()) {
//                                    addTextDialog.dismiss();
//                                }
//                            }
//                        }
//                );
//                @Override
//                protected void onDestroy() {
//                    //回收绘制监听
//                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
//                        inputLayout.getViewTreeObserver().removeGlobalOnLayoutListener(inputLayoutChangeListener);
//                    } else {
//                        inputLayout.getViewTreeObserver().removeOnGlobalLayoutListener(inputLayoutChangeListener);
//                    }
//                    super.onDestroy();
//                }

public class InputMethod {


    private View mChildOfContent;
    private int usableHeightPrevious;
    private int statusBarHeight;
    private FrameLayout.LayoutParams frameLayoutParams;
    private Activity mActivity;


    /**
     * 打开输入法
     */
    public static void ShowSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        //迫使输入法打开
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void ShowSoftInput(final View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    /**
     * 关闭输入法
     */

    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Service.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }


    public static void hideSoftInput(final View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }


    /**
     * 监听输入法的改变
     */
    abstract class OnInputLayoutChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private View rootView;

        public OnInputLayoutChangeListener(View rootView) {
            this.rootView = rootView;
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            //r.top 是状态栏高度
            int screenHeight = rootView.getRootView().getHeight();
            int softHeight = screenHeight - rect.bottom;
            //判断输入法状态
            if (softHeight > 100) {
                onLayoutOpen();
            } else {
                onLayoutHide();

            }
        }

        /**
         * 输入法已打开
         */
        public abstract void onLayoutHide();

        /**
         * 输入法已关闭
         */
        public abstract void onLayoutOpen();

    }


    private InputMethod(Activity activity) {
        mActivity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        statusBarHeight = getStatusBarHeight();
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    /**
     * 若使用沉浸式布局 出现不能弹出情况 请在onCreate中
     * InputMethod.assistActivity(this)
     *
     * @param activity
     */
    public static void assistActivity(Activity activity) {
        new InputMethod(activity);
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                // 如果有高度变化，mChildOfContent.requestLayout()之后界面才会重新测量
                // 这里就随便让原来的高度减去了1
                frameLayoutParams.height = usableHeightSansKeyboard - 1;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top + statusBarHeight;
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            int dimensionPixelSize = mActivity.getResources().getDimensionPixelSize(x);
            return dimensionPixelSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}





