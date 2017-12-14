package zdkdream.rd_components.text;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;


/**
 * @author CT on 2017/12/13.
 * @email 595171796@qq.com
 * 文本处理工具类
 * 1、拼接字符串
 * 2、拼接字符串 定制颜色 大小 删除线 下划线
 */

public class TextTool {

    private static TextTool textTool;
    private StringBuffer string;
    private SpannableStringBuilder builder;
    private int textCount = 0;

    private TextTool() {
    }

    public static TextTool Builder() {
        if (textTool == null) {
            synchronized (TextTool.class) {
                textTool = new TextTool();
            }
        }
        return textTool;
    }

    /**
     * 初始字符串
     */
    public TextTool generateText(String s) {
        string = new StringBuffer(s);
        return this;
    }

    /**
     * 初始数字字符串
     */
    public TextTool generateText(int s) {
        string = new StringBuffer(String.valueOf(s));
        return this;
    }


    /**
     * 添加字符串
     *
     * @param s
     * @return
     */
    public TextTool addText(String s) {
        string.append(s);
        return this;
    }

    /**
     * 添加数字字符串
     */
    public TextTool addText(int s) {
        string.append(s);
        return this;
    }

    /**
     * 获取字符串
     */
    public String obtainText() {
        return string.toString();
    }


    /**
     * 拼接不同风格的字符串
     * 首次拼接
     */
    public TextTool spannable() {
        builder = new SpannableStringBuilder();
        return this;
    }


    public TextTool addTextSizeColor(String s, int color, int textSize) {
        return addTextSizeColor(s, color, textSize, false, false);
    }

    /**
     * 添加带有删除线的Text
     */
    public TextTool addTextStrike(String s, int color, int textSize) {
        return addTextSizeColor(s, color, textSize, true, false);
    }


    /**
     * 添加带有下划线的Text
     */
    public TextTool addTextUnderline(String s, int color, int textSize) {
        return addTextSizeColor(s, color, textSize, false, true);
    }

    /**
     * @param s
     * @param color     文字颜色
     * @param textSize  文字大小
     * @param Strike    删除线
     * @param Underline 下划线
     * @return 添加文字，设置大小颜色
     * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
     * Spannable.SPAN_EXCLUSIVE_INCLUSIVE	：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式
     * Spannable.SPAN_INCLUSIVE_EXCLUSIVE	：前面包括，后面不包括。
     * Spannable.SPAN_INCLUSIVE_INCLUSIVE	：前后都包括。
     */
    public TextTool addTextSizeColor(String s, int color, int textSize, boolean Strike, boolean Underline) {

        //添加文字
        builder.append(s);
        //设置字体颜色
        builder.setSpan(new ForegroundColorSpan(color), textCount, builder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //字体大小
        builder.setSpan(new AbsoluteSizeSpan(textSize, true), textCount, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        if (Strike) {
            builder.setSpan(new StrikethroughSpan(), textCount, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (Underline) {
            builder.setSpan(new StrikethroughSpan(), textCount, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        textCount += s.length();

        return this;
    }


    /**
     * 添加删除线
     * 此方法为单独使用 未拼接字符串的情况
     */
    public TextTool Underline(String s) {
        builder.append(s);
        builder.setSpan(new UnderlineSpan(), 0, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 添加下划线
     * 此方法为单独使用 未拼接字符串的情况
     */
    public TextTool Strike(String s) {
        builder.append(s);
        builder.setSpan(new StrikethroughSpan(), 0, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 添加文字前景色
     *
     * @return
     */
    public TextTool addTextBackground(int backColor) {
        builder.setSpan(new BackgroundColorSpan(backColor), textCount, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }


    /**
     * 获取拼接的字符串
     *
     * @return
     */
    public SpannableStringBuilder obtainSpannable() {
        return builder;
    }


}
