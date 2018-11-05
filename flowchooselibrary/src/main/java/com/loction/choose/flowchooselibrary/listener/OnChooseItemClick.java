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
package com.loction.choose.flowchooselibrary.listener;

import android.view.View;

/**
 * 流体布局回调点击事件
 */
public interface OnChooseItemClick {

    /**
     *
     * @param position  点击的索引
     * @param view      点击的view
     * @param isChecked  是否选中  true 选中  false未选中
     */
    void onItemDataListener(int position, View view, boolean isChecked);


}
