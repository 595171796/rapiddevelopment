package zdkdream.rapiddevelopment.newbase_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import zdkdream.rapiddevelopment.R;

/**
 * @author CT on 2018/1/24.
 * @email 595171796@qq.com
 */

@SuppressLint("Registered")
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener {

    protected T bindingView;


    protected boolean useTransStatusBar = false;
    /**
     * 状态栏颜色
     */
    private int mStatusBarColor = 0;


    @Override
    public void setContentView(int layoutResID) {
        //设置透明状态
        if (useTransStatusBar) {
            setStatusBar(this);
        }
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        // content
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        getWindow().setContentView(bindingView.getRoot());

    }


    /**
     * 获取布局id
     *
     * @return id
     */
    protected abstract int getLayoutId();


    /**
     * 准备工作 例如:操作window ,request 等初始窗体工作
     */
    protected void beforeInit() {

    }

    /**
     * 初始化View控件
     *
     * @param savedInstanceState 窗体保存的状态
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            initView(savedInstanceState);
        }
        initData();
    }


    /**
     * 设置状态栏颜色为透明色，这样能保证状态栏为沉浸式状态
     * 根据SDK >= 21时，标题栏高度设为statusBarHeight(25dp)+titlBarHeight(48dp)
     * 若SDK < 21,标题栏高度直接为titlBarHeight
     */
    protected void setStatusBar(Activity activity) {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(mStatusBarColor != 0 ? mStatusBarColor : ContextCompat.getColor(this, R.color.colorPrimary));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(@ColorRes int color) {
        this.mStatusBarColor = color;
    }

    /**
     * 点击实体或虚拟按钮触发
     *
     * @param keyCode 按键识别码
     * @param event   按钮对象
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
        }
        return false;
    }


    /**
     * 按下返回键
     */
    protected void onBack() {
        finish();
    }


    @Override
    protected void onDestroy() {
        bindingView = null;
        super.onDestroy();
    }
}
