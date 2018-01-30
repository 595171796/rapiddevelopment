package zdkdream.rapiddevelopment.inputsoft;

import android.os.Bundle;
import android.support.annotation.Nullable;

import zdkdream.rapiddevelopment.R;
import zdkdream.rapiddevelopment.databinding.ActivityInputSoftBinding;
import zdkdream.rapiddevelopment.newbase_activity.BaseActivity;
import zdkdream.rd_components.inputsoft.KeyboardLayout;
import zdkdream.rd_components.inputsoft.SoftKeyInputHidWidget;

/**
 * @author CT on 2018/1/24.
 * @email 595171796@qq.com
 * 按键不被输入法遮挡住
 */

public class InputSoftActivity extends BaseActivity<ActivityInputSoftBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示状态栏
//        useTransStatusBar = true;
        setContentView(R.layout.activity_input_soft);
        //隐藏toolbar
        setToolbarGone();
        setmTbTilteText("登录");


        //监听输入法弹出
        addInputLinstener();

    }

    private void addInputLinstener() {

        bindingView.inputSoftKeyboard.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                if (isActive) {
                    scrollToBottom();
                }
            }
        });
    }

    private void scrollToBottom() {

        bindingView.inputSoftScroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                bindingView.inputSoftScroll.smoothScrollTo(0, bindingView.inputSoftScroll.getBottom() + SoftKeyInputHidWidget.getStatusBarHeight(InputSoftActivity.this));
            }
        }, 100);
    }
}
