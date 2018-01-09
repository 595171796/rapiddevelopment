package zdkdream.rd_components.text;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final static int TEXTSIZE = 14;

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
     * <p>
     * 可个更改为枚举类型 判断
     */
    private TextTool addTextSizeColor(String s, int color, int textSize, boolean Strike, boolean Underline) {

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
            builder.setSpan(new UnderlineSpan(), textCount, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        textCount += s.length();

        return this;
    }


    /**
     * 设置为上标
     */
    public TextTool setTopText(String s, int color, int textSize) {
        int count = s.length();
        addTextSizeColor(s, color, textSize);
        builder.setSpan(new SuperscriptSpan(), textCount - count, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    public TextTool setTopText(int s, int color, int textSize) {
        return setTopText(String.valueOf(s), color, textSize);
    }


    /**
     * 设置为下标
     */
    public TextTool setBottomText(String s, int color, int textSize) {
        int count = s.length();
        addTextSizeColor(s, color, textSize);
        builder.setSpan(new SubscriptSpan(), textCount - count, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    public TextTool setBottomText(int s, int color, int textSize) {
        return setBottomText(String.valueOf(s), color, textSize);
    }

/*
    public TextTool setBottomText(int s) {
        return setBottomText(String.valueOf(s), color, TEXTSIZE);
    }*/


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
        //避免下次加载时 textcount已有值
        textCount = 0;
        return builder;
    }


    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空格<br> {@code false}: 不为 null 且不全空格
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空白字符<br> {@code false}: 不为 null 且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null 转为长度为 0 的字符串
     *
     * @param s 待转字符串
     * @return s 为 null 转为长度为 0 字符串，否则不改变
     */
    public static String null2Length0(final String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }


    /*---------------------------处理非法字符--------------------------------*/


    static class LimitString implements TextWatcher {
        /**
         * et
         */
        private EditText et = null;
        /**
         * 筛选条件
         */
        private String regex;
        /**
         * 默认的筛选条件(正则:只能输入中文)
         */
        private String DEFAULT_REGEX = "[^\u4E00-\u9FA5]";
        private final static String C_REGEX = "[^\u4E00-\u9FA5]";
        private final static String CEN_REGEX = "[^a-zA-Z0-9\u4E00-\u9FA5_]";
        private final static String EN_REGEX = "[^a-zA-Z0-9]";

        /**
         * 构造方法
         *
         * @param et
         */
        public LimitString(EditText et) {
            this.et = et;
            this.regex = DEFAULT_REGEX;
        }

        /**
         * 构造方法
         *
         * @param et    et
         * @param regex 筛选条件
         */
        public LimitString(EditText et, String regex) {
            this.et = et;
            this.regex = regex;
        }

        public LimitString setFormat(Format format) {
            switch (format) {
                case C:
                    DEFAULT_REGEX = C_REGEX;

                    break;
                case CEN:
                    DEFAULT_REGEX = CEN_REGEX;

                    break;
                case EN:
                    DEFAULT_REGEX = EN_REGEX;
                    break;
                default:
                    break;
            }
            return this;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            String inputStr = clearLimitStr(regex, str);
            et.removeTextChangedListener(this);
            // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
            s.replace(0, s.length(), inputStr.trim());
            et.addTextChangedListener(this);
        }

        /**
         * 清除不符合条件的内容
         *
         * @param regex
         * @return
         */
        private String clearLimitStr(String regex, String str) {
            return str.replaceAll(regex, "");
        }

        public enum Format {
            /**
             * C 中文
             * CEN 中文英文数字
             * EN 英文和数字
             */
            C, EN, CEN
        }
    }

}



