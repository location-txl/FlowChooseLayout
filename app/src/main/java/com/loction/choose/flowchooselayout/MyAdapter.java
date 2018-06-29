package com.loction.choose.flowchooselayout;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loction.choose.flowchooselibrary.util.LogUtils;
import com.loction.choose.flowchooselibrary.weight.FlowAdapter;
import com.loction.choose.flowchooselibrary.weight.FlowChooseLayout;

import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/6/28 0028 23:39
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class MyAdapter extends FlowAdapter<DataBean> {


	public MyAdapter(List<DataBean> data) {
		super(data);
	}

	@Override
	public View getView(ViewGroup parent, View view, int position) {
		TextView textView = new TextView(parent.getContext());
		textView.setBackgroundColor(Color.RED);
		textView.setText(data.get(position).getName());
		return textView;
	}


	@Override
	public void onChangeState(View view, int position, int state) {
		TextView textView = (TextView) view;
		switch (state) {
			case FlowChooseLayout.CHECK_TYPE_START:
				textView.setText("开始");
				break;
			case FlowChooseLayout.CHECK_TYPE_CENTER:
				textView.setText("中间");
				break;
			case FlowChooseLayout.CHECK_TYPE_END:
				textView.setText("结束");
				break;
			default:
				LogUtils.d("未知类型");
		}
	}
}
