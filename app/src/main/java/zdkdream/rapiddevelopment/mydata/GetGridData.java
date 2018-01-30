package zdkdream.rapiddevelopment.mydata;


import java.util.ArrayList;
import java.util.List;

import zdkdream.rapiddevelopment.R;
import zdkdream.rapiddevelopment.inputsoft.InputSoftActivity;

/**
 * @author CT on 2018/1/26.
 * @email 595171796@qq.com
 */

public class GetGridData {

    private static List<MainGridData> data = new ArrayList<>();

    public static List<MainGridData> getData() {
        MainGridData data1 = new MainGridData();
        data1.setImageId(R.drawable.input_soft);
        data1.setName("输入法相关操作");
        data1.setOpenActivity(InputSoftActivity.class);
        data.add(data1);


        return data;
    }

}
