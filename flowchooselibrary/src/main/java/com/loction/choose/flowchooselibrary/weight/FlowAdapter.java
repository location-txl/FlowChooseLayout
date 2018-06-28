package com.loction.choose.flowchooselibrary.weight;

import android.view.View;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/6/28 0028 23:16
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class FlowAdapter<T extends View> implements IApdater<T> {
    private FlowObseroble obseroble = new FlowObseroble();
    @Override
    public void registObserver(FlowChooseLayout.DataObserver dataObserver) {
        obseroble.registerObserver(dataObserver);
    }

    @Override
    public void unregistObserver(FlowChooseLayout.DataObserver dataSetObserver) {
        obseroble.unregisterObserver(dataSetObserver);
    }

    @Override
    public void notifyDataSetChanged() {
         obseroble.notifyChanged();
    }
}
