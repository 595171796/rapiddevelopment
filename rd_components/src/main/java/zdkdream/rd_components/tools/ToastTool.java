package zdkdream.rd_components.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import zdkdream.rd_components.R;

/**
 * @author CT on 2017/12/7.
 * @email 595171796@qq.com
 * Toast 辅助类
 */

public class ToastTool {

    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private static Toast currentToast;


    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }


    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, getDrawable(context, iconRes), textColor, tintColor, duration, withIcon, shouldTint);
    }

    /**
     * @param context
     * @param message 显示的消息
     * @param icon    显示的图标
     * @return
     */
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon,
                               @ColorInt int textColor,
                               @ColorInt int tintColor,
                               int duration,
                               boolean withIcon,
                               boolean shouldTint
    ) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        final View toastLayout = LayoutInflater.from(context).inflate(R.layout.popup_toast_view, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastText = toastLayout.findViewById(R.id.toast_text);
        /**
         * 显示背景颜色
         */
        Drawable drawableFrame;
        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = getDrawable(context, R.drawable.toast_frame);
        }

        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("避免withcion为true的时候 icon 为null");
            } else {

            }
            setBackground(toastLayout, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastText.setText(message);
        toastText.setTextColor(textColor);
        toastText.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);

        return currentToast;
    }

    /**
     * 兼容drawable版本
     *
     * @param context
     * @param id
     * @return
     */
    public static final Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }


    /**
     * @param context
     * @param tintColor 颜色
     * @return
     */
    public static final Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        //将drawable 强制转换成NinePatchDrawable;
        final NinePatchDrawable toastNinePatch = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        //颜色混合
        toastNinePatch.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastNinePatch;
    }

    /**
     * 设置背景
     */
    public static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

}
