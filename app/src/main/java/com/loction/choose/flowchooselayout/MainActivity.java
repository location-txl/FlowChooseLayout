package com.loction.choose.flowchooselayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loction.choose.flowchooselibrary.util.LogUtils;
import com.loction.choose.flowchooselibrary.weight.FlowChooseLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FlowChooseLayout flowChooseLayout = findViewById(R.id.id_attr);
        final List<String> list = new ArrayList<>();
        list.add("张飞");
        list.add("关羽");
        list.add("刘备");
        list.add("曹操");
        list.add("曹操");
        myAdapter = new MyAdapter(list);
        flowChooseLayout.setAdapter(myAdapter);

        findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list.add("测试");
                list.add("张硕");
                list.add("张超");
              myAdapter.notifyDataSetChanged();
            }
        });


        findViewById(R.id.id_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Integer> allCheckedIndex = flowChooseLayout.getAllCheckedIndex();
                LogUtils.i("choose===>" + "选中===》"+allCheckedIndex.toString());
            }
        });


    }
}
