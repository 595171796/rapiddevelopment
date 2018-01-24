package zdkdream.rd_components.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
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

    private static final int SUCCESS_COLOR = Color.parseColor("#7fb80e");

    private static Toast currentToast;

    /**
     * 请求成功
     */
    public static void success(@NonNull Context context, @NonNull String message) {
        custom(context,
                message,
                getDrawable(context, R.drawable.toast_success, -1),
                DEFAULT_TEXT_COLOR,
                SUCCESS_COLOR,
                Toast.LENGTH_SHORT,
                true,
                true);
    }


    /**
     * 提示
     */
    public static void prompt(@NonNull Context context, @NonNull String message) {
        custom(context, message,
                getDrawable(context, R.drawable.toast_guanyu, -1),
                DEFAULT_TEXT_COLOR,
                ERROR_COLOR,
                Toast.LENGTH_SHORT,
                true,
                true);
    }


    public static void error(@NonNull Context context, @NonNull String message) {
        error(context,
                message,
                DEFAULT_TEXT_COLOR,
                Toast.LENGTH_SHORT,
                true);

    }

    public static void error(@NonNull Context context, @NonNull String message, @ColorInt int textColor) {
        error(context,
                message,
                textColor,
                Toast.LENGTH_SHORT,
                true);

    }

    @SuppressLint("CheckResult")
    public static void error(@NonNull Context context, @NonNull String message, @ColorInt int textColor, int duration, boolean withIcon) {
        custom(context, message,
                getDrawable(context, R.drawable.ic_clear_white_48dp, -1),
                textColor, ERROR_COLOR,
                duration,
                withIcon,
                true);
    }

    public static void custom(Context context, String message) {
        custom(context, message,
                null,
                DEFAULT_TEXT_COLOR,
                Toast.LENGTH_SHORT,
                false);
    }

    @SuppressLint("CheckResult")
    public static void custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, int duration, boolean withIcon) {
        custom(context,
                message,
                icon,
                textColor,
                -1,
                duration,
                withIcon,
                false);
    }


    @SuppressLint("CheckResult")
    public static void custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        custom(context,
                message,
                getDrawable(context, iconRes, textColor),
                textColor,
                tintColor,
                duration,
                withIcon,
                shouldTint);
    }

    /**
     * @param context
     * @param message 显示的消息
     * @param icon    显示的图标
     * @return
     */
    public static void custom(@NonNull Context context,
                              @NonNull String message,
                              Drawable icon,
                              @ColorInt int textColor,
                              @ColorInt int tintColor,
                              int duration,
                              boolean withIcon,
                              boolean shouldTint) {
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
            drawableFrame = getDrawable(context, R.drawable.toast_frame, textColor);
        }

        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("避免withcion为true的时候 icon 为null");
            } else {

            }
            setBackground(toastIcon, icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastText.setText(message);
        toastText.setTextColor(textColor != 0 ? textColor : toastText.getTextColors().getDefaultColor());
        toastText.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        currentToast.show();
    }

    /**
     * 兼容drawable版本
     *
     * @param context
     * @param id
     * @return
     */
    public static final Drawable getDrawable(@NonNull Context context, @DrawableRes int id, @ColorInt int iamge) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = context.getDrawable(id);
            drawable.setColorFilter(new PorterDuffColorFilter(iamge != -1 ? iamge : DEFAULT_TEXT_COLOR, PorterDuff.Mode.SRC_IN));
            return drawable;
        } else {
            Drawable drawable = context.getResources().getDrawable(id);
            drawable.setColorFilter(new PorterDuffColorFilter(iamge != -1 ? iamge : DEFAULT_TEXT_COLOR, PorterDuff.Mode.SRC_IN));
            return drawable;
        }
    }


    /**
     * @param context
     * @param tintColor 颜色
     * @return
     */
    public static final Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        //将drawable 强制转换成NinePatchDrawable;
        final NinePatchDrawable toastNinePatch = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame, -1);
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