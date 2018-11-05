## FlowChooseLayout

#### 导入
```groovy
implementation 'com.location:flowChooseLayout:1.0.2'
```

FlowChooseLayout  是一款 基于流体布局的多选单选控件

FlowChooseLayout自去年11月份发布以来  经历了多次重大版本的更新

1. 2017年11月  第一版[FlowChooseLayout](https://github.com/TXLLocation/FlowChooseLayout)发布只支持setList方式 样式支持TextView

    在今年我们重构了FlowChooseLayout代码  支持任何view 所有样式由开发者自己规划

2. 2018年8月  我们正式重构了FlowChooseLayout代码  

   - 舍弃之前的setList方法 
   - 全局使用适配器方法，采用观察者模式 一个适配器控制多个FlowLayout
   - 重写了之前权重的逻辑  可以像Lainlayout根据当前的布局找到更合适的大小而不是于之前采用最大权重的方式
   - 内部舍弃创建view 目前FlowChooseLayout只参与view的展示与点击
   - 对于之前的addView方式 我们并不是一竿子打掉 两种方式随意切换

   > 效果

   ![](https://github.com/TLocation/FlowChooseLayout/blob/master/7c81504a-b352-4e8d-9cb8-30411886054d.gif)


#### 使用方式

> 在xml声明

```xml
<com.loction.choose.flowchooselibrary.weight.FlowChooseLayout
	android:id="@+id/id_attr"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	app:flow="true"
	app:isMultiSelect="true"
	app:rowSpacing="10dp"
	app:childSpacing="10dp"/>
```

> 自定义属性

```xml
<declare-styleable name="FlowChooseLayout">
		<!--是否流体布局-->
		<attr name="flow" format="boolean" />
		<!--每个view之间宽度 如果设置权重则此效果无效-->
		<attr name="childSpacing" format="enum|dimension">
			<enum name="auto" value="-65536" />
		</attr>
		<!--最后一行字view的间距-->
		<attr name="childSpacingForLastRow" format="enum|dimension">
			<enum name="auto" value="-65536" />
			<enum name="align" value="-65537" />
		</attr>
		<attr name="rowSpacing" format="enum|dimension">
			<enum name="auto" value="-65536" />
		</attr>
		<!--排列方式 是否从右边开始布局  默认左边-->
		<attr name="rtl" format="boolean" />
		<attr name="maxRows" format="integer" />
		<!--设置权重  则宽度必须为math或者固定数值-->
		<attr name="weight" format="boolean" />
		<!--均分数目   weight为false时无效-->
		<attr name="weightNum" format="integer" />
		<!--设定是否多选 默认为单选-->
		<attr name="isMultiSelect" />
		<!--是否使用适配器模式-->
		<attr name="adapter"/>
	</declare-styleable>
```

> java代码  自定义adaptetr继承FlowAdapter<T> 类

```java
public class MyAdapter extends FlowAdapter<DataBean> {

    /**
    * 实现默认的构造方法
    */
	public MyAdapter(List<DataBean> data) {
		super(data);
	}

    /**
    *   重写getView 方法  构造view
    *   形参 view不等于null的时候只有在刷新数据的时候不为空  其余一律为空
    */
	@Override
	public View getView(ViewGroup parent, View view, int position) {
		View contentView = null;
		if (view != null) {
			contentView = view;
		} else {
			contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_flow, parent, false);
		}
		TextView textView = contentView.findViewById(R.id.view_content);
		textView.setText(data.get(position).getName());
		return contentView;
	}


    /**
    *  当view状态改变的时候调用此方法
    
       state  true  选中
              false  不选中
    */
	@Override
	public void onChangeState(View view, int position, boolean state) {
		TextView textView = view.findViewById(R.id.view_content);
		if (state) {
			view.setBackgroundColor(Color.RED);
			textView.setText("已开启");
		} else {
			view.setBackgroundColor(Color.BLUE);
			textView.setText("选择");
		}

	}
}
```

> 设置适配器

```java
final FlowChooseLayout flowChooseLayout = findViewById(R.id.id_attr);
		final List<DataBean> list = new ArrayList<>();
		String[] stringArray = getResources().getStringArray(R.array.flow_demo);
		for (int i = 0; i < stringArray.length; i++) {
			DataBean dataBean = new DataBean();
dataBean.setName(stringArray[i]);
			list.add(dataBean);
		}
        //初始化适配器
		myAdapter = new MyAdapter(list);
       //设置默认选中
		flowChooseLayout.setDefaultCheckd(0);
       //设置适配器
		flowChooseLayout.setAdapter(myAdapter);

      findViewById(R.id.id_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //增加数据  刷新适配器
				DataBean dataBean = new DataBean();
				dataBean.setName("增加选中项");
				list.add(dataBean);
				myAdapter.notifyDataSetChanged();
			}
		});


		findViewById(R.id.id_clear).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                //获取已经选中的索引
				List<Integer> allCheckedIndex = flowChooseLayout.getAllCheckedIndex();
				LogUtils.i("choose===>" + "选中===》" + allCheckedIndex.toString());
				textView.setText("已经选中的索引\n");
				textView.append(allCheckedIndex.toString());
			}
		});
```



