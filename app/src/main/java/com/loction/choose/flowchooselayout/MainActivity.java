package com.loction.choose.flowchooselayout;

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
        for (int i = 0; i < 3; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setName("测试");
            list1.add(dataBean);
        }


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
