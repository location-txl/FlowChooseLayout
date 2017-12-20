package com.loction.choose.flowchooselayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.loction.choose.flowchooselibrary.weight.FlowChooseLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlowChooseLayout flowChooseLayout = findViewById(R.id.id_attr);
        List<String> list = new ArrayList<>();
        list.add("张飞");
        list.add("关羽");
        list.add("刘备");
        flowChooseLayout.setList(list);
    }
}
