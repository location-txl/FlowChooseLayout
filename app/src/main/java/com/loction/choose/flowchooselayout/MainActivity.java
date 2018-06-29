package com.loction.choose.flowchooselayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.loction.choose.flowchooselibrary.util.LogUtils;
import com.loction.choose.flowchooselibrary.weight.FlowChooseLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final FlowChooseLayout flowChooseLayout = findViewById(R.id.id_attr);
		final List<DataBean> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			DataBean dataBean = new DataBean();
			dataBean.setName("你好" + i);
			list.add(dataBean);
		}
		myAdapter = new MyAdapter(list);
		Map<Integer, Integer> de = new HashMap<>();
		de.put(0, FlowChooseLayout.CHECK_TYPE_CENTER);
		de.put(1, FlowChooseLayout.CHECK_TYPE_END);
		flowChooseLayout.setAdapter(myAdapter);
		flowChooseLayout.setDefaultCheckd(de);
		myAdapter.notifyDataSetChanged();
		findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Random random = new Random();
				int i = random.nextInt(list.size());
				DataBean dataBean = list.get(i);
				dataBean.setName("哈哈哈");
				myAdapter.notifyDataSetChanged(i);

			}
		});


		findViewById(R.id.id_clear).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				List<Integer> allCheckedIndex = flowChooseLayout.getAllCheckedIndex();
				LogUtils.i("choose===>" + "选中===》" + allCheckedIndex.toString());
			}
		});


	}
}
