package zdkdream.rd_components.text;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.logging.LogRecord;

/**
 * @author CT on 2017/12/7.
 * @email 595171796@qq.com
 */

public class SrcollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private final static int FLAG_START_AUTO_SCROLL = 0;
    private final static int FLAG_STOP_AUTO_SCROLL = 1;

    private int mTextSize = 16;
    private int mPadding = 5;
    private int mTextColor = Color.BLACK;
    private Context mContext;
    private int currentID = -1;
    private ArrayList<String> textList;
    private Handler mHandler;
    private OnItemChickListener onItemChickListener;

    public SrcollTextView(Context context) {
        this(context, null);
    }

    public SrcollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        textList = new ArrayList<>();
    }

    /**
     * @param mTextSize  文字大小
     * @param mPadding   填充距离
     * @param mTextColor 文字颜色
     */
    public void setTextAtrribute(int mTextSize, int mPadding, int mTextColor) {
        this.mTextSize = mTextSize;
        this.mPadding = mPadding;
        this.mTextColor = mTextColor;
    }

    public void setAnimTime(long animDuration) {
        //创建视图奇幻器
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, animDuration, 0);
        in.setDuration(animDuration);
        //AccelerateInterpolator 开始慢 后加速
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -animDuration);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(in);

    }

    @SuppressLint("HandlerLeak")
    public void setTextStillTime(final long time) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL: {
                        if (textList.size() > 0) {
                            currentID++;
                            setText(textList.get(currentID % textList.size()));
                        }
                        mHandler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, time);
                    }
                    break;
                    case FLAG_STOP_AUTO_SCROLL: {
                        mHandler.removeMessages(FLAG_START_AUTO_SCROLL);
                    }
                    break;
                    default:
                        break;
                }
            }
        };

    }


    /**
     * 设置数据
     * @param list
     */
    public void setTextList(ArrayList<String> list){
        textList.clear();
        textList.addAll(list);
        currentID=-1;
    }

    /**
     * 开始滚动
     */
    public void startAutoSrcoll(){
        mHandler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        mHandler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }


    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(mTextColor);
        t.setTextSize(mTextSize);

        t.setClickable(true);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemChickListener != null && textList.size() > 0 && currentID != -1) {
                    onItemChickListener.onItemClick(currentID % textList.size());
                }
            }
        });
        return t;
    }

    public void setOnItemChickListener(OnItemChickListener onItemChickListener) {
        this.onItemChickListener = onItemChickListener;
    }

    interface OnItemChickListener {
        void onItemClick(int position);
    }
}
