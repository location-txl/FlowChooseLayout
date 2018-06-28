package com.loction.choose.flowchooselibrary.weight;

import android.view.View;
import android.view.ViewGroup;

/**
 * 项目:趣租部落
 *
 * @author：田晓龙 time：2018/6/28 17:47
 * description：
 */

public interface IApdater<T extends View> {
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
     * @param position 当前的索引
     * @return 返回view
     */
    T getView(ViewGroup parent, int position);

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

    void onChangeState(T view, int position, @FlowChooseLayout.FlowState int state);

}
