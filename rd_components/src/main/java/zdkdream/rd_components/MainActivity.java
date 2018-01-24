package zdkdream.rd_components;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.main_testEditText);

        //是否让EditText获取焦点
        editText.setFocusable(false);
        //EditText焦点触摸模式
        editText.setFocusableInTouchMode(false);
        //设置过滤模式
//        editText.setFilters();
        //清除焦点
        editText.clearFocus();
        //监听键盘点击事件
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
    }
}
