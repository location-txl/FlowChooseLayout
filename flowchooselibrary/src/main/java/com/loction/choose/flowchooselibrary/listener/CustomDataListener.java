package com.loction.choose.flowchooselibrary.listener;

/**
 * Created by localadmin on 2017/12/20.
 */

public abstract class CustomDataListener<T> implements DataListener {

    public abstract String setListItemData(T t);

    @Override
    public String setData(String string) {
        return string;
    }
}
