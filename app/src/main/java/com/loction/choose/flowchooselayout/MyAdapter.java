package com.loction.choose.flowchooselayout;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class MyAdapter extends FlowAdapter<TextView> {
    private List<String> list;

    public MyAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public TextView getView(ViewGroup parent, int position) {
        TextView textView = new TextView(parent.getContext());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.RED);
        textView.setText(list.get(position));
        return textView;
    }

    @Override
    public void onChangeState(TextView view, int position, int state) {
        switch (state) {
            case FlowChooseLayout.CHECK_TYPE_START:
                view.setText("开始");
                break;
            case FlowChooseLayout.CHECK_TYPE_CENTER:
                view.setText("中间");
                break;
            case FlowChooseLayout.CHECK_TYPE_END:
                view.setText("结束");
                break;
        }
    }
}
