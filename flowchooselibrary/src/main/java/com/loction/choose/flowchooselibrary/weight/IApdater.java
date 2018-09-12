package com.loction.choose.flowchooselibrary.weight;

import android.view.View;
import android.view.ViewGroup;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/28 17:47
 * description：
 */

public interface IApdater {
	/**
	 * 返回view的总数量
	 *
	 * @return
	 */
	int getItemCount();


	/**
	 * 返回一个View 用于View的构建
	 *
	 * @param parent   父控件
	 * @param view     如果做局部刷新时 此View不为null
	 * @param position 当前的索引
	 * @return 返回view
	 */
	View getView(ViewGroup parent, View view, int position);

//
//	/**
//	 * 绑定父控件
//	 *
//	 * @param flowChooseLayout
//	 */
//	void bindParent(FlowChooseLayout flowChooseLayout);
//
//
//	/**
//	 * 解绑父控件
//	 */
//	void unBindParent();

	/**
	 * 注册观察者
	 *
	 * @param dataObserver
	 */
	void registObserver(FlowChooseLayout.DataObserver dataObserver);


	/**
	 * 解绑被观察者
	 *
	 * @param dataSetObserver
	 */
	void unregistObserver(FlowChooseLayout.DataObserver dataSetObserver);

	/**
	 * 数据更新时调用
	 */
	void notifyDataSetChanged();


	/**
	 * 局部刷新
	 *
	 * @param position
	 */
	void notifyDataSetChanged(int position);

	/**
	 * 刷新从当前索引到后面的全部数据
	 *
	 * @param position
	 */
	void notifyDataInvied(int position);


	/**
	 * 状态改变时调用
	 *
	 * @param view
	 * @param position
	 * @param isChecked 是否选中  true选中  false选中
	 */
	void onChangeState(View view, int position,boolean isChecked);

}
