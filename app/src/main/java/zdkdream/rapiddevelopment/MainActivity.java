package zdkdream.rapiddevelopment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import zdkdream.rapiddevelopment.databinding.ActivityMainBinding;
import zdkdream.rapiddevelopment.mydata.GetGridData;
import zdkdream.rapiddevelopment.mydata.MainGridData;
import zdkdream.rapiddevelopment.newbase_activity.BaseActivity;
import zdkdream.rd_components.statusbar.StatusBarTool;
import zdkdream.rd_components.tools.StartActivityTool;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements AdapterView.OnItemClickListener {


    private List<MainGridData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不设置为透明状态栏
        useTransStatusBar = false;
        setContentView(R.layout.activity_main);
        //设置不显示左边图片
        setTbLiftImageGone();
        //设置title
        setmTbTilteText("技术盒子", 18, R.color.white);

        dataList = GetGridData.getData();

        //初始类容
        initView();
    }


    /**
     * 处理View
     */
    private void initView() {
        MainGridAdapter adapter = new MainGridAdapter(dataList);
        bindingView.mainGridview.setAdapter(adapter);
        bindingView.mainGridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StartActivityTool.startToActivity(this, (Class) dataList.get(position).getOpenActivity());
    }
}
