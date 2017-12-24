package com.loction.choose.flowchooselayout;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loction.choose.flowchooselibrary.listener.CustomDataListener;
import com.loction.choose.flowchooselibrary.listener.OnChooseItemClick;
import com.loction.choose.flowchooselibrary.weight.FlowChooseLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FlowChooseLayout flowChooseLayout = findViewById(R.id.id_attr);
        List<String> list = new ArrayList<>();
        list.add("张飞");
        list.add("关羽");
        list.add("刘备");
        List<DataBean> list1 = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            DataBean dataBean = new DataBean();
//            dataBean.setName("测试"+i);
//            list1.add(dataBean);
//        }
        DataBean dataBean = new DataBean();
        dataBean.setName("张飞");
        DataBean dataBean1 = new DataBean();
        dataBean1.setName("关羽");
        DataBean dataBean2 = new DataBean();
        dataBean2.setName("刘备");
        DataBean dataBean3 = new DataBean();
        dataBean3.setName("得玛西亚之力");
        DataBean dataBean4 = new DataBean();
        dataBean4.setName("德玛西亚皇子");
        DataBean dataBean5 = new DataBean();
        dataBean5.setName("德邦总管");
        DataBean dataBean6 = new DataBean();
        dataBean6.setName("疾风剑豪");
        DataBean dataBean7 = new DataBean();
        dataBean7.setName("奥利安娜");
       list1.add(dataBean);
       list1.add(dataBean1);
       list1.add(dataBean2);
       list1.add(dataBean3);
       list1.add(dataBean4);
       list1.add(dataBean5);
       list1.add(dataBean6);
       list1.add(dataBean7);

        flowChooseLayout.setList(list1, new CustomDataListener<DataBean>() {
            @Override
            public String setListItemData(DataBean o) {
                return o.getName();
            }
        });
        flowChooseLayout.setOnChooseItemClick(new OnChooseItemClick() {
            @Override
            public void onItemDataListener(int position, View view, boolean isChecked) {
                Toast.makeText(MainActivity.this, "position===" + position + "\n选中==" + (isChecked ? "选中" : "未选中"), Toast.LENGTH_SHORT).show();

            }
        });

        flowChooseLayout.setDefaultItemCheck(10);
        findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<DataBean> allCheckData = flowChooseLayout.getAllCheckData(DataBean.class);
                Log.e("TAG", allCheckData.toString() + "\n" + flowChooseLayout.getAllCheckedIndex().toString());
            }
        });

        findViewById(R.id.id_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowChooseLayout.clearAllItemChecked();
            }
        });


    }
}
