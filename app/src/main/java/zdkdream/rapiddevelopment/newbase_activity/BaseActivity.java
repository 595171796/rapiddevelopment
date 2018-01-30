package zdkdream.rapiddevelopment.newbase_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import zdkdream.rapiddevelopment.R;
import zdkdream.rapiddevelopment.databinding.ActivityBaseBinding;
import zdkdream.rd_components.statusbar.SystemBarTintManager;

import static zdkdream.rd_components.statusbar.StatusBarTool.setTransparentBar;

/**
 * @author CT on 2018/1/24.
 * @email 595171796@qq.com
 */

@SuppressLint("Registered")
public class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener {
    protected ActivityBaseBinding mBaseBinding;
    protected T bindingView;


    protected boolean useTransStatusBar = true;
    /**
     * 状态栏颜色
     */
    private int mStatusBarColor = 0;

    /**
     * 用于判断是否设置文字大小即颜色
     */
    private final static int CARRIEDOUT = 1;


    @Override
    public void setContentView(int layoutResID) {
        //设置透明状态
        if (useTransStatusBar) {
            setStatusBar(this);
        }
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        // content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.main_container);
        mContainer.addView(bindingView.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        //设置标题栏
        setToolBar();


    }

    protected void setToolBar() {
        //处理toolbar
        mBaseBinding.toolbar.mainToolbarLimage.setOnClickListener(this);
        Toolbar toolbar = mBaseBinding.toolbar.mainToolbar;
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_toolbar_Limage:
                onClickLimage();
                break;
            case R.id.main_toolbar_Ltext:
                onClickLtext();
                break;
            case R.id.main_toolbar_Rimage:
                onClickRimage();
                break;
            case R.id.main_toolbar_Rtext:
                onClickRtext();

            case R.id.main_toolbar_title:
                onClickTitle();
                break;
        }
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
     * 默认隐藏的
     *
     * @param mTbLiftText 左边文字
     */
    public void setmTbLiftText(String mTbLiftText) {
        setmTbLiftText(mTbLiftText, CARRIEDOUT);
    }

    /**
     * @param mTbLiftText 文本
     * @param size        文字大小
     */
    public void setmTbLiftText(String mTbLiftText, int size) {
        setmTbLiftText(mTbLiftText, size, CARRIEDOUT);
    }

    /**
     * @param mTbLiftText 文本
     * @param size        文字大小
     * @param color       颜色
     */
    public void setmTbLiftText(String mTbLiftText, int size, int color) {
        setTextInfo(mBaseBinding.toolbar.mainToolbarLtext, mTbLiftText, size, color);
    }


    /**
     * 设置title显示的文字
     *
     * @param content 文字内容
     */
    public void setmTbTilteText(String content) {
        setmTbTilteText(content, CARRIEDOUT);
    }

    /**
     * @param content 文字内容
     * @param size    文本大小
     */
    public void setmTbTilteText(String content, int size) {
        setmTbTilteText(content, size, CARRIEDOUT);

    }


    /**
     * @param content 文字内容
     * @param size    文本大小
     * @param color   文字颜色
     */
    public void setmTbTilteText(String content, int size, int color) {
        setTextInfo(mBaseBinding.toolbar.mainToolbarTitle, content, size, color);
    }


    /**
     * 默认隐藏的
     *
     * @param mTbRightImage toolbar 右边显示icon
     */
    public void setmTbRightImage(@DrawableRes int mTbRightImage) {
        ImageView mRightImage = mBaseBinding.toolbar.mainToolbarRimage;
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setImageResource(mTbRightImage);
        mRightImage.setOnClickListener(this);
    }


    /**
     * 右边文字
     *
     * @param content 文字内容
     */
    public void setmTbRightText(String content) {
        setmTbRightText(content, CARRIEDOUT);
    }

    /**
     * 右边文字
     *
     * @param content 文字内容
     * @param size    文字大小
     */
    public void setmTbRightText(String content, int size) {
        setmTbRightText(content, size, CARRIEDOUT);
    }

    /**
     * 右边文字
     *
     * @param content 文字内容
     * @param size    文字大小
     * @param color   文字颜色
     */
    public void setmTbRightText(String content, int size, int color) {
        setTextInfo(mBaseBinding.toolbar.mainToolbarRtext, content, size, color);
    }

    /**
     * 处理文本工具
     *
     * @param content 显示内容
     * @param size    文字大小
     * @param color   文字颜色
     */
    private void setTextInfo(TextView text, String content, int size, int color) {
        if (text.getVisibility() != View.VISIBLE) {
            text.setVisibility(View.VISIBLE);
        }
        text.setText(content);
        if (size != CARRIEDOUT) {
            text.setTextSize(size);
        }
        if (color != CARRIEDOUT) {
            text.setTextColor(ActivityCompat.getColor(this, color));
        }
        text.setOnClickListener(this);
    }


    /**
     * 隐藏左边图片
     */
    public void setTbLiftImageGone() {
        mBaseBinding.toolbar.mainToolbarLimage.setVisibility(View.GONE);
    }

    /**
     * 隐藏title
     */
    public void setTbTilteGone() {
        mBaseBinding.toolbar.mainToolbarTitle.setVisibility(View.GONE);
    }

    /**
     * 隐藏toolbar
     */
    public void setToolbarGone() {
        mBaseBinding.toolbar.mainToolbar.setVisibility(View.GONE);
    }


    /**
     * 右边文字点击
     */
    protected void onClickRtext() {

    }


    /**
     * 右边图片点击
     */
    protected void onClickRimage() {

    }

    /**
     * 左边文字点击
     */
    protected void onClickLtext() {

    }

    /**
     * title的点击
     */
    protected void onClickTitle() {

    }


    /**
     * 左边图片点击
     */
    protected void onClickLimage() {
        onBackPressed();
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
        mBaseBinding = null;
        bindingView = null;
        super.onDestroy();
    }
}
