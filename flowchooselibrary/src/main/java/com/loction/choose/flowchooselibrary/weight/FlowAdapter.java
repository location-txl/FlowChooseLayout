/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.loction.choose.flowchooselibrary.weight;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * 如果采用适配器模式（建议采用）
 * ----------------------------------------------
 * 声明一个类  继承此类
 * 重写 @see {@link #getView(ViewGroup, View, int)} 方法  来 构造view
 * 可以选择重写 @see {@link #onChangeState(View, int, boolean)}方法 来选择在view选中或者不选中的时候显示的样式
 *
 * @param <T>
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
