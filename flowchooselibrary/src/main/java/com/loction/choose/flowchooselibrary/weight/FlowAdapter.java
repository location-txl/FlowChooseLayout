package com.loction.choose.flowchooselibrary.weight;

import android.view.View;

import java.util.List;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/6/28 0028 23:16
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class FlowAdapter<T> implements IApdater {

	protected List<T> data;


	private FlowObseroble obseroble = new FlowObseroble();


	public FlowAdapter(List<T> data) {
		this.data = data;
	}

	@Override
	public void registObserver(FlowChooseLayout.DataObserver dataObserver) {
		obseroble.registerObserver(dataObserver);
	}

	@Override
	public void unregistObserver(FlowChooseLayout.DataObserver dataSetObserver) {
		obseroble.unregisterObserver(dataSetObserver);
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public void notifyDataSetChanged() {
		obseroble.notifyChanged();
	}

	@Override
	public void notifyDataInvied(int position) {
		obseroble.notifyChangedInvied(position);
	}

	@Override
	public void notifyDataSetChanged(int position) {
		obseroble.notifyChanged(position);
	}

	@Override
	public void onChangeState(View view, int position, boolean isChecked) {

	}
}
