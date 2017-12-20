package com.loction.choose.flowchooselibrary.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.loction.choose.flowchooselibrary.R;
import com.loction.choose.flowchooselibrary.listener.DataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: 新医疗(HD)
 * 类描述:  自定义流体布局
 * 创建人: localadmin（龙）
 * 创建时间: 2017/8/16 10:52
 * 修改人:
 * 修改内容:
 * 修改时间:
 */
public class FlowChooseLayout extends ViewGroup implements DataListener {
    private static final String LOG_TAG = FlowChooseLayout.class.getSimpleName();

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
    private boolean isNoMea;
    /**
     * 是否权重
     */
    private boolean isWeight;


    /**
     * @see #isWeight  为ture时才生效
     * 一行显示几个
     */
    private int weightNum;
    private List<Float> mHorizontalSpacingForRow = new ArrayList<>();
    private List<Integer> mHeightForRow = new ArrayList<>();
    private List<Integer> mChildNumForRow = new ArrayList<>();


    private Context mContext;

    /**
     * 子控件需要的属性集
     */
    private AttributeSet attributeSet;

    /**
     * 数据源设置方式
     */
    private DataListener dataListener;


    /**
     * 边框宽度
     */
    private float buttonBorderWidth;
    /**
     * 子view默认的背景色
     */
    private int buttonBackGroundColor;
    /**
     * 子view默认的文字颜色
     */
    private int buttonTextColor;

    /**
     * 子view默认的描边颜色
     */
    private int buttonBorderColor;
    /**
     * 子view的圆角高度是否为自适应为高度的一半
     * 设置此属性为true后
     *
     * @see #buttonRadio
     * 则上述属性无作用
     */
    private boolean buttonIsRadiusAdjustBounds;

    /**
     * 子view的四个圆角大小
     *
     * @see #buttonIsRadiusAdjustBounds  设置此属性为true后  此属性无作用
     */
    private float buttonRadio;

    /**
     * 子view的左上圆角大小
     */
    private float buttonTopLeftRadio;

    /**
     * 子view的右上圆角大小
     */
    private float buttonTopRightRadio;

    /**
     * 子view的左下方圆角大小
     */
    private float buttonBottomLeftRadio;
    /**
     * 子view的右下方圆角大小
     */
    private float buttonBottomRightRadio;


    /**
     * 子view选中的情况下文字颜色
     */
    private int buttonCheckTextColor;
    /**
     * 子view选中的情况下背景颜色
     */
    private int buttonCheckBackGgroundColor;
    /**
     * 子view选中的情况下描边颜色
     */
    private int buttonCheckBoradColor;

    /**
     * 子view是否允许多选
     * 默认单选
     */
    private boolean isALlChecked;


    public FlowChooseLayout(Context context) {
        this(context, null);
    }

    public FlowChooseLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.dataListener = this;
        this.mContext = context;
        this.attributeSet = attrs;
        isNoMea = true;
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
            //获取默认背景色
            buttonBackGroundColor = a.getColor(R.styleable.FlowChooseLayout_backgroundColor, 0);
            //获取默认的文字颜色
            buttonTextColor = a.getColor(R.styleable.FlowChooseLayout_text_color, 0);
            //获取默认的边框颜色
            buttonBorderColor = a.getColor(R.styleable.FlowChooseLayout_borderColor, 0);
            //获取边框宽度
            buttonBorderWidth = a.getDimension(R.styleable.FlowChooseLayout_borderWidth, 0);
            //获取圆角自适应
            buttonIsRadiusAdjustBounds = a.getBoolean(R.styleable.FlowChooseLayout_isRadiusAdjustBounds, false);
            buttonRadio = a.getDimension(R.styleable.FlowChooseLayout_radius,0);



        } finally {
            a.recycle();
        }
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    public void setList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            QMUIRoundButton qmuiRoundButton = new QMUIRoundButton(mContext);

            qmuiRoundButton.setText(dataListener.setData(list.get(i)));
            qmuiRoundButton.setTag(false);
            addView(qmuiRoundButton);
        }
    }


    /**
     * 内部设置content参数
     *
     * @param content
     * @return
     */
    private QMUIRoundButton getQmuiButton(String content) {
        QMUIRoundButton qmuiRoundButton = new QMUIRoundButton(mContext);
        qmuiRoundButton.setText(content);
        MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        qmuiRoundButton.setLayoutParams(params);
        return qmuiRoundButton;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("Test", "onMeasure");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        mHorizontalSpacingForRow.clear();
        mChildNumForRow.clear();
        mHeightForRow.clear();

        int measuredHeight = 0, measuredWidth = 0, childCount = getChildCount();
        int rowWidth = 0, maxChildHeightInRow = 0, childNumInRow = 0;
        int rowSize = widthSize - getPaddingLeft() - getPaddingRight();
        boolean allowFlow = widthMode != MeasureSpec.UNSPECIFIED && mFlow;
        int childSpacing = mChildSpacing == SPACING_AUTO && widthMode == MeasureSpec.UNSPECIFIED
                ? 0 : mChildSpacing;
        //获取到测量的宽度
        final int windowWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int mode = MeasureSpec.getMode(widthMeasureSpec);
        Log.e("Test", "size==" + windowWidth);
        if (isWeight && weightNum > 0 && isNoMea) {
            isNoMea = false;
            if (mode == MeasureSpec.EXACTLY) {
                int allViewWidth = 0;
                Log.e("mn", "个数===" + getChildCount());
                for (int j = 0; j < weightNum; j++) {
                    View ch = getChildAt(j);
                    if (ch.getVisibility() == GONE) {
                        continue;
                    }
                    final LayoutParams layoutParams = ch.getLayoutParams();
                    if (layoutParams instanceof MarginLayoutParams) {
                        measureChildWithMargins(ch, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
                        MarginLayoutParams params = (MarginLayoutParams) layoutParams;
                        allViewWidth = allViewWidth + ch.getMeasuredWidth() + params.leftMargin + params.rightMargin;
//                   allViewWidth = params.\
                    }
                }
                childSpacing = (windowWidth - allViewWidth) / (weightNum - 1);
                mChildSpacing = childSpacing;
                Log.e("Test", "spec==" + childSpacing);

            }


        }
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
                Log.e("Test", "else");
            }

            int childWidth = child.getMeasuredWidth() + horizontalMargin;
            Log.e("Test", "dui==" + child.getMeasuredWidth());
            int childHeight = child.getMeasuredHeight() + verticalMargin;
            if (allowFlow && rowWidth + childWidth > rowSize) { // Need flow to next row

                mHorizontalSpacingForRow.add(
                        getSpacingForRow(childSpacing, rowSize, rowWidth, childNumInRow));

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
                rowWidth += childWidth + tmpSpacing;
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
            mHorizontalSpacingForRow.add(
                    getSpacingForRow(childSpacing, rowSize, rowWidth, childNumInRow));
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

    @Override
    public String setData(String string) {
        return string;
    }
}

