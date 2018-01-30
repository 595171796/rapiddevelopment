package zdkdream.rapiddevelopment;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import zdkdream.rapiddevelopment.databinding.ActivityMainGridviewBinding;
import zdkdream.rapiddevelopment.mydata.MainGridData;

/**
 * @author CT on 2018/1/26.
 * @email 595171796@qq.com
 */

public class MainGridAdapter extends BaseAdapter {

    private List<MainGridData> data;


    public MainGridAdapter(List<MainGridData> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ActivityMainGridviewBinding gridviewBinding;
        if (convertView == null) {
            gridviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_main_gridview, null, false);
            convertView = gridviewBinding.getRoot();
            convertView.setTag(gridviewBinding);
        } else {
            gridviewBinding = (ActivityMainGridviewBinding) convertView.getTag();
        }
        gridviewBinding.mainGrdivewImage.setImageResource(data.get(position).getImageId());
        gridviewBinding.mainGridviewName.setText(data.get(position).getName());
        return convertView;
    }
}
