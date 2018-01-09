package zdkdream.rd_components.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

/**
 * @author CT on 2017/12/7.
 * @email 595171796@qq.com
 * android 常用的一些方法
 */

public class AndroidTools {

    /**
     * 倒计时
     *
     * @param button   控件
     * @param waitTime 倒计时总时长  600
     * @param interval 倒计时的间隔时间 10
     * @param hint     倒计时完毕时显示的文字
     */
    public static void countDown(final Button button, long waitTime, long interval, final String hint) {
        button.setEnabled(false);
        android.os.CountDownTimer timer = new android.os.CountDownTimer(waitTime, interval) {

            @Override
            public void onTick(long millisUntilFinished) {
                button.setText((millisUntilFinished / 1000) + " S");
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setText(hint);
            }
        };
        timer.start();
    }


    /**
     * 拨打电话
     */
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
