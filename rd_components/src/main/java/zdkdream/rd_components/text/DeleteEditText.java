package zdkdream.rd_components.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import zdkdream.rd_components.R;


/**
 * @author CT on 2017/12/4.
 * @email 595171796@qq.com
 * 带删除的EditText
 */

public class DeleteEditText extends AppCompatEditText {

    /**
     * 定义属性变量
     */
    //删除资源图标 ID
    private int ic_deleteResID;
    //删除图标
    private Drawable ic_delete;
    //删除图片起点(X,Y)、删除图标宽、高(PX)
    private int delete_x, delete_y, delete_width, delete_height;

    public DeleteEditText(Context context) {
        super(context);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化属性
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.deleteEditText);
        /**
         * 初始化删除图标
         */
        ic_deleteResID = typedArray.getResourceId(R.styleable.deleteEditText_ic_delete, R.drawable.delete);
        //根据资源ID 获取drawable
        ic_delete = getResources().getDrawable(ic_deleteResID);
        //设置图片大小
        delete_x = typedArray.getInteger(R.styleable.deleteEditText_delete_x, 0);
        delete_y = typedArray.getInteger(R.styleable.deleteEditText_delete_y, 0);
        delete_width = typedArray.getInteger(R.styleable.deleteEditText_delete_width, 60);
        delete_height = typedArray.getInteger(R.styleable.deleteEditText_delete_width, 60);
        ic_delete.setBounds(delete_x, delete_y, delete_width, delete_height);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        /**
         * hasFocus()返回是否获得EditTEXT的焦点，即是否选中
         * setDeleteIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标
         */
        setDeleteIconVisible(hasFocus() && text.length() > 0);
    }


    /**
     * 复写EditText本身的方法：onFocusChanged（）
     * 调用时刻：焦点发生变化时
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(hasFocus() && length() > 0);
    }

    private void setDeleteIconVisible(boolean deleteVisible) {
        setCompoundDrawables(null, null, deleteVisible ? ic_delete : null, null);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
        switch (event.getAction()) {
            // 判断动作 = 手指抬起时
            case MotionEvent.ACTION_UP:
                Drawable drawable = ic_delete;

                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {

                    // 判断条件说明
                    // event.getX() ：抬起时的位置坐标
                    // getWidth()：控件的宽度
                    // getPaddingRight():删除图标图标右边缘至EditText控件右边缘的距离
                    // 即：getWidth() - getPaddingRight() = 删除图标的右边缘坐标 = X1
                    // getWidth() - getPaddingRight() - drawable.getBounds().width() = 删除图标左边缘的坐标 = X2
                    // 所以X1与X2之间的区域 = 删除图标的区域
                    // 当手指抬起的位置在删除图标的区域（X2=<event.getX() <=X1），即视为点击了删除图标 = 清空搜索框内容
                    setText("");

                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
