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

import android.database.Observable;
import android.widget.BaseAdapter;


/**
 *
 * FlowChooseLayout 被观察者  用于绑定数据更新
 */

public class FlowObseroble extends Observable<FlowChooseLayout.DataObserver> {

	public void notifyChanged() {
		for (int i = mObservers.size() - 1; i >= 0; i--) {
			mObservers.get(i).onChanged();
		}
	}
	public void notifyChanged(int position){
		for (int i = mObservers.size() - 1; i >= 0; i--) {
			mObservers.get(i).onChangeed(position);
		}
	}

	public void notifyChangedInvied(int position){
		for (int i = mObservers.size() - 1; i >= 0; i--) {
			mObservers.get(i).onChangeedInvid(position);
		}
	}

}
