package com.loction.choose.flowchooselibrary.weight;

import android.database.Observable;
import android.widget.BaseAdapter;


/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/28 17:55
 * description：
 * Flow被观察者  用于绑定数据更新
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
