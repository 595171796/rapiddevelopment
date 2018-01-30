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


    private List<MainGridData> dataList = GetGridData.getData();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //处理toolbar
        bindingView.mainToolbar.mainToolbarTitle.setText("技术盒子");
        bindingView.mainToolbar.mainToolbarLimage.setVisibility(View.GONE);
        bindingView.mainToolbar.mainToolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(bindingView.mainToolbar.mainToolbar);


        MainGridAdapter adapter = new MainGridAdapter(dataList);
        bindingView.mainGridview.setAdapter(adapter);
        bindingView.mainGridview.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StartActivityTool.startToActivity(this, (Class) dataList.get(position).getOpenActivity());
    }

    @Override
    public void onClick(View v) {

    }
}
