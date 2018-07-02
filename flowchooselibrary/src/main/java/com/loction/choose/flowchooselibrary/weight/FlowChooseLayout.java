package com.loction.choose.flowchooselibrary.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.loction.choose.flowchooselibrary.R;
import com.loction.choose.flowchooselibrary.listener.OnChooseItemClick;
import com.loction.choose.flowchooselibrary.util.LogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 项目名称: 新医疗(HD)
 * 类描述:  自定义流体布局
 * 创建人: localadmin（龙）
 * 创建时间: 2017/8/16 10:52
 * 修改人:
 * 修改内容:
 * 修改时间:
 */
public class FlowChooseLayout extends ViewGroup {

	private final int KEY_LOCK = 99999999;
	private DataObserver dataObserver;

	private FlowAdapter adapter;
	/**
	 * 三种状态
	 */
	public static final int CHECK_TYPE_START = 0x002;
	public static final int CHECK_TYPE_CENTER = 0x003;
	public static final int CHECK_TYPE_END = 0x004;

	private View lastView;
	private int lastPosition;


	public static final int SPACING_AUTO = -65536;

	public static final int SPACING_ALIGN = -65537;

	private static final int SPACING_UNDEFINED = -65538;

	private static final boolean DEFAULT_FLOW = true;
	private static final int DEFAULT_CHILD_SPACING = 0;
	private static final int DEFAULT_CHILD_SPACING_FOR_LAST_ROW = SPACING_UNDEFINED;
	private static final float DEFAULT_ROW_SPACING = 0;
	private static final boolean DEFAULT_RTL = false;
	private static final int DEFAULT_MAX_ROWS = Integer.MAX_VALUE;

	private boolean mFlow = DEFAULT_FLOW;
	private int mChildSpacing = DEFAULT_CHILD_SPACING;
	private int mChildSpacingForLastRow = DEFAULT_CHILD_SPACING_FOR_LAST_ROW;
	private float mRowSpacing = DEFAULT_ROW_SPACING;
	private float mAdjustedRowSpacing = DEFAULT_ROW_SPACING;
	private boolean mRtl = DEFAULT_RTL;
	private int mMaxRows = DEFAULT_MAX_ROWS;
	/**
	 * 是否权重
	 */
	private boolean isWeight;

	private boolean isAdapter;
	private String key;

	private Map<Integer, Integer> defaultState;


	public void setAdapter(FlowAdapter flowAdapter) {
		if (dataObserver == null) {
			dataObserver = new DataObserver();
		}
		if (adapter != null) {
			listAllCheckedIndex.clear();
			removeAllViews();
			adapter.unregistObserver(dataObserver);
		}
		adapter = flowAdapter;
		adapter.registObserver(dataObserver);
		initView();
	}

	/**
	 * 初始化view
	 */
	private void initView() {
		final int itemCount = adapter.getItemCount();
		for (int i = 0; i < itemCount; i++) {
			//通过适配器获取view
			final View itemview = adapter.getView(this, null, i);
			//设置验证锁
			itemview.setTag(KEY_LOCK, key);
			//获取默认状态
			if (defaultState != null && defaultState.containsKey(i)) {
				Integer state = defaultState.get(i);
				itemview.setTag(state);
				if (state != CHECK_TYPE_START) {
					if (!isAllMultiSelect) {
						lastView = itemview;
						lastPosition = i;
					}
					listAllCheckedIndex.add(i);
				}
				adapter.onChangeState(itemview, i, state);
			} else {
				itemview.setTag(CHECK_TYPE_START);
				adapter.onChangeState(itemview,i,CHECK_TYPE_START);
			}
			final int finalI = i;
			itemview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					if (itemview.getTag() == null || !(itemview.getTag() instanceof Integer)) {
						itemview.setTag(CHECK_TYPE_START);
					}

					int state = (int) itemview.getTag();
					int nextState = -1;
					switch (state) {
						case CHECK_TYPE_END:
							nextState = CHECK_TYPE_START;
							listAllCheckedIndex.remove(new Integer(finalI));
							break;
						case CHECK_TYPE_START:
							nextState = CHECK_TYPE_CENTER;
							listAllCheckedIndex.add(finalI);
							if (!isAllMultiSelect && lastView != null && lastView != itemview) {
								lastView.setTag(CHECK_TYPE_START);
								adapter.onChangeState(lastView, lastPosition, CHECK_TYPE_START);
								listAllCheckedIndex.remove(new Integer(lastPosition));
							}
							if (!isAllMultiSelect && itemview != lastView) {
								lastView = itemview;
								lastPosition = finalI;
							}

							break;
						case CHECK_TYPE_CENTER:
							nextState = CHECK_TYPE_END;
							break;
						default:
							throw new RuntimeException("flowLayout error of matching type  \n" +
									" you may send " +
									"email to tttx0307@163.com");
					}
					itemview.setTag(nextState);
					if (adapter != null) {
						adapter.onChangeState(itemview, finalI, nextState);
					}
					if (onChooseItemClick != null) {
						onChooseItemClick.onItemDataListener(finalI, itemview, state);
					}

				}
			});
			addView(itemview);
		}

	}

	@Override
	public void addView(View child, int index, LayoutParams params) {
		if (isAdapter && child.getTag(KEY_LOCK) == null) {
			throw new RuntimeException("not add view you  may setAdapter bindView perhaps adapter false");
		}
		super.addView(child, index, params);
	}

	/**
	 * @see #isWeight  为ture时才生效
	 * 一行显示几个
	 */
	private int weightNum;
	private List<Float> mHorizontalSpacingForRow = new ArrayList<>();
	private List<Integer> mHeightForRow = new ArrayList<>();
	private List<Integer> mChildNumForRow = new ArrayList<>();

	/**
	 * 存储权重时每行的行间距
	 */
	private List<Integer> mChildWeightSpacing = new ArrayList<>();


	private Context mContext;


	/**
	 * 子view是否允许多选
	 * 默认单选
	 */
	private boolean isAllMultiSelect;


	/**
	 * 子view的点击事件
	 */
	private OnChooseItemClick onChooseItemClick;


	private List<Integer> listAllCheckedIndex;

	/**
	 * 是否三级选择
	 */
	private boolean isSecond;

	public void setSecond(boolean second) {
		isSecond = second;
	}

	public void setWeight(boolean weight) {
		isWeight = weight;
	}

	public void setWeightNum(int weightNum) {
		this.weightNum = weightNum;
	}


	public void setAllMultiSelect(boolean allMultiSelect) {
		isAllMultiSelect = allMultiSelect;
	}

	public void setOnChooseItemClick(OnChooseItemClick onChooseItemClick) {
		this.onChooseItemClick = onChooseItemClick;
	}

	public FlowChooseLayout(Context context) {
		this(context, null);
	}

	public FlowChooseLayout(Context context, AttributeSet attrs) {

		super(context, attrs);
		listAllCheckedIndex = new ArrayList<>();

		this.mContext = context;
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.FlowChooseLayout, 0, 0);
		try {
			mFlow = a.getBoolean(R.styleable.FlowChooseLayout_flow, DEFAULT_FLOW);
			//子View的间距
			try {
				mChildSpacing = a.getInt(R.styleable.FlowChooseLayout_childSpacing, DEFAULT_CHILD_SPACING);
			} catch (NumberFormatException e) {
				mChildSpacing = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_childSpacing, (int) dpToPx(DEFAULT_CHILD_SPACING));
			}
			isWeight = a.getBoolean(R.styleable.FlowChooseLayout_weight, false);
			if (isWeight) {
				weightNum = a.getInt(R.styleable.FlowChooseLayout_weightNum, 0);
				if (weightNum > 0) {
					Log.e("Test", "width==" + getWidth() + "\nmessure" + getMeasuredWidth());
				}
			}
			//最后一行子view的间距
			try {
				mChildSpacingForLastRow = a.getInt(R.styleable.FlowChooseLayout_childSpacingForLastRow, SPACING_UNDEFINED);
			} catch (NumberFormatException e) {
				mChildSpacingForLastRow = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_childSpacingForLastRow, (int) dpToPx(DEFAULT_CHILD_SPACING));
			}
			//行高
			try {
				mRowSpacing = a.getInt(R.styleable.FlowChooseLayout_rowSpacing, 0);
			} catch (NumberFormatException e) {
				mRowSpacing = a.getDimension(R.styleable.FlowChooseLayout_rowSpacing, dpToPx(DEFAULT_ROW_SPACING));
			}
			//最大行数
			mMaxRows = a.getInt(R.styleable.FlowChooseLayout_maxRows, DEFAULT_MAX_ROWS);
			//居左还是居右

			mRtl = a.getBoolean(R.styleable.FlowChooseLayout_rtl, DEFAULT_RTL);
			//设定是否多选
			isAllMultiSelect = a.getBoolean(R.styleable.FlowChooseLayout_isMultiSelect, false);
			isAdapter = a.getBoolean(R.styleable.FlowChooseLayout_adapter, true);
			if (isAdapter) {
				key = UUID.randomUUID().toString();
			}
		} finally {
			a.recycle();
		}
	}


	/**
	 * 获取所有选中的下标集合
	 *
	 * @return
	 */
	public List<Integer> getAllCheckedIndex() {
		return listAllCheckedIndex;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		LogUtils.e("onMeasure");
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		mHorizontalSpacingForRow.clear();
		mChildNumForRow.clear();
		mHeightForRow.clear();

		int measuredHeight = 0, measuredWidth = 0, childCount = getChildCount();
		LogUtils.d("childcount===>" + childCount);
		LogUtils.d("weightCount===>" + weightNum);
		//行宽  最大行高度 每行个数
		int rowWidth = 0, maxChildHeightInRow = 0, childNumInRow = 0;
		//总行宽
		int rowSize = widthSize - getPaddingLeft() - getPaddingRight();
		//是否流体
		boolean allowFlow = widthMode != MeasureSpec.UNSPECIFIED && mFlow;
		//ziview间距  后面重新计算
		int childSpacing = mChildSpacing == SPACING_AUTO && widthMode == MeasureSpec.UNSPECIFIED
				? 0 : mChildSpacing;
		//获取到测量的宽度
		final int windowWidth = MeasureSpec.getSize(widthMeasureSpec);
		final int mode = MeasureSpec.getMode(widthMeasureSpec);
		Log.e("Test", "size==" + windowWidth);
		getChildSpacing(widthMeasureSpec, heightMeasureSpec, measuredHeight, childSpacing, windowWidth, mode, 0);
		float tmpSpacing = childSpacing == SPACING_AUTO ? 0 : childSpacing;
		Log.e("Test", "tmpSpac==" + tmpSpacing);
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			LayoutParams childParams = child.getLayoutParams();
			int horizontalMargin = 0, verticalMargin = 0;
			if (childParams instanceof MarginLayoutParams) {
				measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
				MarginLayoutParams marginParams = (MarginLayoutParams) childParams;
				horizontalMargin = marginParams.leftMargin + marginParams.rightMargin;
				verticalMargin = marginParams.topMargin + marginParams.bottomMargin;
			} else {
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}

			int childWidth = child.getMeasuredWidth() + horizontalMargin;
			Log.e("Test", "dui==" + child.getMeasuredWidth());
			int childHeight = child.getMeasuredHeight() + verticalMargin;
			if (allowFlow && rowWidth + childWidth > rowSize) { // Need flow to next row
				Log.e("TAG", "换行==" + i);
				if (isWeight) {
					mHorizontalSpacingForRow.add(getSpacingForRow(mChildWeightSpacing.get(mHorizontalSpacingForRow.size()), rowSize, rowWidth, childNumInRow));
				} else {
					mHorizontalSpacingForRow.add(
							getSpacingForRow(childSpacing, rowSize, rowWidth, childNumInRow));
				}

				mChildNumForRow.add(childNumInRow);
				mHeightForRow.add(maxChildHeightInRow);
				if (mHorizontalSpacingForRow.size() <= mMaxRows) {
					measuredHeight += maxChildHeightInRow;
				}
				measuredWidth = Math.max(measuredWidth, rowWidth);

				childNumInRow = 1;
				rowWidth = childWidth + (int) tmpSpacing;
				maxChildHeightInRow = childHeight;
			} else {

				childNumInRow++;
				if (isWeight) {
//					if()
					Log.e("GHF", "width===" + mChildWeightSpacing.get(mHorizontalSpacingForRow.size()));
					rowWidth += childWidth + mChildWeightSpacing.get(mHorizontalSpacingForRow.size());
				} else {

					rowWidth += childWidth + tmpSpacing;
				}
				maxChildHeightInRow = Math.max(maxChildHeightInRow, childHeight);
			}
		}

		if (mChildSpacingForLastRow == SPACING_ALIGN) {
			if (mHorizontalSpacingForRow.size() >= 1) {
				mHorizontalSpacingForRow.add(
						mHorizontalSpacingForRow.get(mHorizontalSpacingForRow.size() - 1));
			} else {
				mHorizontalSpacingForRow.add(
						getSpacingForRow(childSpacing, rowSize, rowWidth, childNumInRow));
			}
		} else if (mChildSpacingForLastRow != SPACING_UNDEFINED) {
			mHorizontalSpacingForRow.add(
					getSpacingForRow(mChildSpacingForLastRow, rowSize, rowWidth, childNumInRow));
		} else {
			if (isWeight) {
				mHorizontalSpacingForRow.add(getSpacingForRow(mChildWeightSpacing.get(mHorizontalSpacingForRow.size()), rowSize, rowWidth, childNumInRow));

			} else {


				mHorizontalSpacingForRow.add(
						getSpacingForRow(childSpacing, rowSize, rowWidth, childNumInRow));
			}
		}

		mChildNumForRow.add(childNumInRow);
		mHeightForRow.add(maxChildHeightInRow);
		if (mHorizontalSpacingForRow.size() <= mMaxRows) {
			measuredHeight += maxChildHeightInRow;
		}
		measuredWidth = Math.max(measuredWidth, rowWidth);

		if (childSpacing == SPACING_AUTO) {
			measuredWidth = widthSize;
		} else if (widthMode == MeasureSpec.UNSPECIFIED) {
			measuredWidth = measuredWidth + getPaddingLeft() + getPaddingRight();
		} else {
			measuredWidth = Math.min(measuredWidth + getPaddingLeft() + getPaddingRight(), widthSize);
		}

		measuredHeight += getPaddingTop() + getPaddingBottom();
		int rowNum = Math.min(mHorizontalSpacingForRow.size(), mMaxRows);
		float rowSpacing = mRowSpacing == SPACING_AUTO && heightMode == MeasureSpec.UNSPECIFIED
				? 0 : mRowSpacing;
		if (rowSpacing == SPACING_AUTO) {
			if (rowNum > 1) {
				mAdjustedRowSpacing = (heightSize - measuredHeight) / (rowNum - 1);
			} else {
				mAdjustedRowSpacing = 0;
			}
			measuredHeight = heightSize;
		} else {
			mAdjustedRowSpacing = rowSpacing;
			if (rowNum > 1) {
				measuredHeight = heightMode == MeasureSpec.UNSPECIFIED
						? ((int) (measuredHeight + mAdjustedRowSpacing * (rowNum - 1)))
						: (Math.min((int) (measuredHeight + mAdjustedRowSpacing * (rowNum - 1)),
						heightSize));
			}
		}

		measuredWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : measuredWidth;
		measuredHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : measuredHeight;

		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	private int getChildSpacing(int widthMeasureSpec, int heightMeasureSpec, int measuredHeight, int childSpacing, int windowWidth, int mode, int startIndex) {
		if (isWeight && weightNum > 0 ) {
			if (mode == MeasureSpec.EXACTLY) {
				final int i = setChildRow(startIndex, (startIndex + weightNum) < getChildCount() ?
								startIndex + weightNum : getChildCount(), widthMeasureSpec,
						heightMeasureSpec, measuredHeight, windowWidth);
			}
		}
		return childSpacing;
	}

	private int setChildRow(int startIndex, int endIndex, int widthMeasureSpec, int heightMeasureSpec, int measuredHeight, int windowWidth) {
		int allViewWidth = 0;
		int childSpacing = 0;
		Log.e("mn", "个数===" + getChildCount());
		for (int j = startIndex; j < endIndex; j++) {
			Log.e("Test", "spec==" + j);

			View ch = getChildAt(j);

			if (ch.getVisibility() == GONE) {
				continue;
			}
			final LayoutParams layoutParams = ch.getLayoutParams();
			if (layoutParams instanceof MarginLayoutParams) {
				measureChildWithMargins(ch, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
				MarginLayoutParams params = (MarginLayoutParams) layoutParams;
				allViewWidth = allViewWidth + ch.getMeasuredWidth() + params.leftMargin + params.rightMargin;
			} else {
				measureChild(ch, widthMeasureSpec, heightMeasureSpec);
				allViewWidth += ch.getMeasuredWidth();
			}
		}
		childSpacing = (windowWidth - allViewWidth) / (weightNum - 1);
		LogUtils.d("weigh   childSpacing===>" + childSpacing);
		mChildWeightSpacing.add(childSpacing);
		if (endIndex < getChildCount()) {
			setChildRow(endIndex, (endIndex + weightNum) < getChildCount() ? endIndex + weightNum :
					getChildCount
							(), widthMeasureSpec, heightMeasureSpec, measuredHeight, windowWidth);
		}
//        mChildSpacing = childSpacing;
		Log.e("Test", "spec==" + childSpacing);
		return endIndex;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int x = mRtl ? (getWidth() - paddingRight) : paddingLeft;
		int y = paddingTop;

		int rowCount = mChildNumForRow.size(), childIdx = 0;
		for (int row = 0; row < rowCount; row++) {
			int childNum = mChildNumForRow.get(row);
			int rowHeight = mHeightForRow.get(row);
			float spacing = mHorizontalSpacingForRow.get(row);
			for (int i = 0; i < childNum && childIdx < getChildCount(); ) {
				View child = getChildAt(childIdx++);
				if (child.getVisibility() == GONE) {
					continue;
				} else {
					i++;
				}

				LayoutParams childParams = child.getLayoutParams();
				int marginLeft = 0, marginTop = 0, marginRight = 0;
				if (childParams instanceof MarginLayoutParams) {
					MarginLayoutParams marginParams = (MarginLayoutParams) childParams;
					marginLeft = marginParams.leftMargin;
					marginRight = marginParams.rightMargin;
					marginTop = marginParams.topMargin;
				}

				int childWidth = child.getMeasuredWidth();
				int childHeight = child.getMeasuredHeight();
				if (mRtl) {
					child.layout(x - marginRight - childWidth, y + marginTop,
							x - marginRight, y + marginTop + childHeight);
					x -= childWidth + spacing + marginLeft + marginRight;
				} else {
					child.layout(x + marginLeft, y + marginTop,
							x + marginLeft + childWidth, y + marginTop + childHeight);
					x += childWidth + spacing + marginLeft + marginRight;
				}
			}
			x = mRtl ? (getWidth() - paddingRight) : paddingLeft;
			y += rowHeight + mAdjustedRowSpacing;
		}
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	public boolean isFlow() {
		return mFlow;
	}

	public void setFlow(boolean flow) {
		mFlow = flow;
		requestLayout();
	}

	public int getChildSpacing() {
		return mChildSpacing;
	}

	public void setChildSpacing(int childSpacing) {
		mChildSpacing = childSpacing;
		requestLayout();
	}

	public int getChildSpacingForLastRow() {
		return mChildSpacingForLastRow;
	}

	public void setChildSpacingForLastRow(int childSpacingForLastRow) {
		mChildSpacingForLastRow = childSpacingForLastRow;
		requestLayout();
	}

	public float getRowSpacing() {
		return mRowSpacing;
	}

	public void setRowSpacing(float rowSpacing) {
		mRowSpacing = rowSpacing;
		requestLayout();
	}

	public int getMaxRows() {
		return mMaxRows;
	}

	public void setMaxRows(int maxRows) {
		mMaxRows = maxRows;
		requestLayout();
	}

	private float getSpacingForRow(int spacingAttribute, int rowSize, int usedSize, int childNum) {
		float spacing;
		if (spacingAttribute == SPACING_AUTO) {
			if (childNum > 1) {
				spacing = (rowSize - usedSize) / (childNum - 1);
			} else {
				spacing = 0;
			}
		} else {
			spacing = spacingAttribute;
		}
		return spacing;
	}

	private float dpToPx(float dp) {
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	@SuppressLint("UseSparseArrays")
	public void setDefaultCheckd(Map<Integer, Integer> defaultCheck) {
		if (defaultState == null) {
			defaultState = new HashMap<>();
		}
		defaultState.clear();
		if (!isAllMultiSelect&&defaultCheck.size()>1) {
			throw new RuntimeException("sign mulit not  more default");
		}
		defaultState.putAll(defaultCheck);
	}

	class DataObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			super.onChanged();
			//刷新数据
			removeAllViews();
			mHorizontalSpacingForRow.clear();
			mChildNumForRow.clear();
			mHeightForRow.clear();
			mChildWeightSpacing.clear();
			listAllCheckedIndex.clear();
			initView();
		}

		/**
		 * 刷新局部数据
		 *
		 * @param position
		 */
		public void onChangeed(int position) {
			refreshview(position);
		}

		public void onChangeedInvid(int position) {
			int childCount = getChildCount();
			for (int index = position; index < childCount; index++) {
				refreshview(index);
			}
		}
	}

	private void refreshview(int position) {
		View childAt = getChildAt(position);
		if (childAt == null) {
			return;
		}
		if (adapter == null) {
			return;
		}
		View view = adapter.getView(FlowChooseLayout.this, null, position);
		view.setTag(KEY_LOCK, key);
		view.setTag(childAt.getTag());
		adapter.onChangeState(view, position, (Integer) view.getTag());
		removeViewAt(position);
		addView(view, position);
	}


	@IntDef({CHECK_TYPE_START, CHECK_TYPE_CENTER, CHECK_TYPE_END})
	@Retention(RetentionPolicy.SOURCE)
	public @interface FlowState {
	}


}

