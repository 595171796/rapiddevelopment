package zdkdream.rd_components.imageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import zdkdream.rd_components.text.TextTool;

/**
 * @author CT on 2017/12/28.
 * @email 595171796@qq.com
 *
 *
 */
//DrawableTool.Builder(BaseApplication.getContext()).setDrawbleInfo(R.drawable.dianhu_white,
//                DensityUtils.dp2px(BaseApplication.getContext(), 15),
//                DensityUtils.dp2px(BaseApplication.getContext(), 15))
//                .setDrawableLeft((helper.getView(R.id.needdetails_itmes_contact)))
//                .clearData();

public class DrawableTool{

    private Drawable drawable;
    private static DrawableTool drawableTool;
    private static Context context;


    public static DrawableTool Builder(Context contexts) {
        if (drawableTool == null) {
            synchronized (DrawableTool.class) {
                drawableTool = new DrawableTool();
                context = contexts;
            }
        }
        return drawableTool;
    }

    /**
     * 设置drawable
     *
     * @param sc
     * @return
     */
    public DrawableTool setDrawable(@DrawableRes int sc) {
        return setDrawbleInfo(sc, 0, 0);
    }

    /**
     * 设置大小
     *
     * @param sc     资源
     * @param width  宽度
     * @param height 高度
     * @return
     */
    public DrawableTool setDrawbleInfo(@DrawableRes int sc, int width, int height) {
        drawable = ActivityCompat.getDrawable(context, sc);

        if (width != 0 && height != 0) {
            drawable.setBounds(0, 0, width, height);
        }
        return drawableTool;
    }


    /**
     * 设置显示在左边
     */
    public <T> DrawableTool setDrawableLeft(T t) {
        setCompoundDrawable(t, drawable, null, null, null);
        return drawableTool;
    }

    /**
     * 设置显示在顶部
     */
    public <T> DrawableTool setDrawableTop(T t) {
        setCompoundDrawable(t, null, drawable, null, null);
        return drawableTool;
    }

    /**
     * 设置显示在顶部
     */
    public <T> DrawableTool setDrawableRight(T t) {
        setCompoundDrawable(t, null, null, drawable, null);
        return drawableTool;
    }

    /**
     * 设置显示在顶部
     */
    public <T> DrawableTool setDrawableBottom(T t) {
        setCompoundDrawable(t, null, null, null, drawable);
        return drawableTool;
    }


    /**
     * 设置图片位置
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private <T> DrawableTool setCompoundDrawable(T t, @Nullable Drawable left, @Nullable Drawable top,
                                                 @Nullable Drawable right, @Nullable Drawable bottom) {
        if (drawable == null) {
            throw new NullPointerException("请先设置setDrawable()");
        }

        if (t instanceof TextView) {
            ((TextView) t).setCompoundDrawables(left, top, right, bottom);

        } else if (t instanceof Button) {
            ((Button) t).setCompoundDrawables(left, top, right, bottom);
        }

        return drawableTool;
    }


    public void clearData() {
        if (drawableTool != null) {
            drawable = null;
            context = null;
            drawableTool = null;
        }
    }
}