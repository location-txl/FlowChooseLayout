package com.loction.choose.flowchooselibrary.weight;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


import com.loction.choose.flowchooselibrary.R;
import com.loction.choose.flowchooselibrary.listener.CustomDataListener;
import com.loction.choose.flowchooselibrary.listener.DataListener;
import com.loction.choose.flowchooselibrary.listener.OnChooseItemClick;

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
    private int buttonBorderWidth;
    /**
     * 子view默认的背景色
     */
    private ColorStateList buttonBackGroundColor;
    /**
     * 子view默认的文字颜色
     */
    private int buttonTextColor;

    /**
     * 子view默认的描边颜色
     */
    private ColorStateList buttonBorderColor;
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
    private int buttonRadio;

    /**
     * 子view的左上圆角大小
     */
    private int buttonTopLeftRadio;

    /**
     * 子view的右上圆角大小
     */
    private int buttonTopRightRadio;

    /**
     * 子view的左下方圆角大小
     */
    private int buttonBottomLeftRadio;
    /**
     * 子view的右下方圆角大小
     */
    private int buttonBottomRightRadio;


    /**
     * 子view选中的情况下文字颜色
     */
    private int buttonCheckTextColor;
    /**
     * 子view选中的情况下背景颜色
     */
    private ColorStateList buttonCheckBackGgroundColor;
    /**
     * 子view选中的情况下描边颜色
     */
    private ColorStateList buttonCheckBoradColor;

    /**
     * 子view是否允许多选
     * 默认单选
     */
    private boolean isAllMultiSelect;


    /**
     * 子view的点击事件
     */
    private OnChooseItemClick onChooseItemClick;


    /**
     * 获取最后一个选择的控件  单选时有效
     */
    private QMUIRoundButton lastQmuiRoundButton;


    private List<Integer> listAllCheckedIndex;
    private List<Object> listAllCheckData;


    public void setWeight(boolean weight) {
        isWeight = weight;
    }

    public void setWeightNum(int weightNum) {
        this.weightNum = weightNum;
    }

    public void setButtonBorderWidth(int buttonBorderWidth) {
        this.buttonBorderWidth = buttonBorderWidth;
    }

    public void setButtonBackGroundColor(ColorStateList buttonBackGroundColor) {
        this.buttonBackGroundColor = buttonBackGroundColor;
    }

    public void setButtonTextColor(int buttonTextColor) {

        this.buttonTextColor = buttonTextColor;
    }

    public void setButtonBorderColor(ColorStateList buttonBorderColor) {
        this.buttonBorderColor = buttonBorderColor;
    }

    public void setButtonIsRadiusAdjustBounds(boolean buttonIsRadiusAdjustBounds) {
        this.buttonIsRadiusAdjustBounds = buttonIsRadiusAdjustBounds;
    }

    public void setButtonRadio(int buttonRadio) {
        this.buttonRadio = buttonRadio;
    }

    public void setButtonTopLeftRadio(int buttonTopLeftRadio) {
        this.buttonTopLeftRadio = buttonTopLeftRadio;
    }

    public void setButtonTopRightRadio(int buttonTopRightRadio) {
        this.buttonTopRightRadio = buttonTopRightRadio;
    }

    public void setButtonBottomLeftRadio(int buttonBottomLeftRadio) {
        this.buttonBottomLeftRadio = buttonBottomLeftRadio;
    }

    public void setButtonBottomRightRadio(int buttonBottomRightRadio) {
        this.buttonBottomRightRadio = buttonBottomRightRadio;
    }

    public void setButtonCheckTextColor(int buttonCheckTextColor) {
        this.buttonCheckTextColor = buttonCheckTextColor;
    }

    public void setButtonCheckBackGgroundColor(ColorStateList buttonCheckBackGgroundColor) {
        this.buttonCheckBackGgroundColor = buttonCheckBackGgroundColor;
    }

    public void setButtonCheckBoradColor(ColorStateList buttonCheckBoradColor) {
        this.buttonCheckBoradColor = buttonCheckBoradColor;
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
        listAllCheckData = new ArrayList<>();
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
            buttonBackGroundColor = a.getColorStateList(R.styleable.FlowChooseLayout_backgroundColor);
            //获取默认的文字颜色
            buttonTextColor = a.getColor(R.styleable.FlowChooseLayout_text_color, Color.parseColor("#000000"));
            //获取默认的边框颜色
            buttonBorderColor = a.getColorStateList(R.styleable.FlowChooseLayout_borderColor);
            //获取边框宽度
            buttonBorderWidth = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_borderWidth, 0);
            //获取圆角自适应
            buttonIsRadiusAdjustBounds = a.getBoolean(R.styleable.FlowChooseLayout_isRadiusAdjustBounds, false);
            //获取4个圆角大小
            buttonRadio = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_radius, 0);
            buttonTopLeftRadio = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_radiusTopLeft, 0);
            buttonTopRightRadio = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_radiusTopRight, 0);
            buttonBottomLeftRadio = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_radiusBottomLeft, 0);
            buttonBottomRightRadio = a.getDimensionPixelSize(R.styleable.FlowChooseLayout_radiusBottomRight, 0);
            //获取view选中的情况下的文字颜色
            buttonCheckTextColor = a.getColor(R.styleable.FlowChooseLayout_checked_text_color, Color.parseColor("#000000"));
            //获取view选中的情况下的背景色
            buttonCheckBackGgroundColor = a.getColorStateList(R.styleable.FlowChooseLayout_checked_back_ground);
            //获取view选中的情况下的边框颜色
            buttonCheckBoradColor = a.getColorStateList(R.styleable.FlowChooseLayout_checked_back_border_color);
            //设定是否多选
            isAllMultiSelect = a.getBoolean(R.styleable.FlowChooseLayout_isMultiSelect, false);

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
            QMUIRoundButton qmuiRoundButton = getQmuiButton(dataListener.setData(list.get(i)), i, list.get(i));
            addView(qmuiRoundButton);
        }
    }

    public <T> void setList(List<T> list, CustomDataListener<T> customDataListener) {

        for (int i = 0; i < list.size(); i++) {
            QMUIRoundButton qmuiRoundButton = getQmuiButton(customDataListener.setListItemData(list.get(i)), i, list.get(i));
            addView(qmuiRoundButton);
        }
    }


    /**
     * 内部设置content参数
     *
     * @param content
     * @return
     */
    private QMUIRoundButton getQmuiButton(String content, final int position, final Object data) {
        final QMUIRoundButton qmuiRoundButton = new QMUIRoundButton(mContext);
        qmuiRoundButton.setText(content);
        qmuiRoundButton.setTextColor(buttonTextColor);
        QMUIRoundButtonDrawable qmuiRoundButtonDrawable = new QMUIRoundButtonDrawable();
        //设置背景色
        qmuiRoundButtonDrawable.setBgData(buttonBackGroundColor);
        qmuiRoundButtonDrawable.setStrokeData(buttonBorderWidth, buttonBorderColor);
        /**
         * 设置圆角
         */
        if (buttonIsRadiusAdjustBounds) {
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(buttonIsRadiusAdjustBounds);
        } else if (buttonRadio != 0) {
            qmuiRoundButtonDrawable.setCornerRadius(buttonRadio);
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(false);
        } else if (buttonBottomLeftRadio > 0
                || buttonBottomRightRadio > 0
                || buttonTopRightRadio > 0
                || buttonTopLeftRadio > 0) {
            float[] radios = new float[]{
                    buttonTopLeftRadio, buttonTopLeftRadio,
                    buttonTopRightRadio, buttonTopRightRadio,
                    buttonBottomRightRadio, buttonBottomRightRadio,
                    buttonBottomLeftRadio, buttonBottomLeftRadio
            };
            qmuiRoundButtonDrawable.setCornerRadii(radios);
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(false);
        } else {
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(false);
        }
        qmuiRoundButton.setTag(false);
        qmuiRoundButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = (boolean) qmuiRoundButton.getTag();
                qmuiRoundButton.setTag(!isChecked);
                if (onChooseItemClick != null) {
                    onChooseItemClick.onItemDataListener(position, view, !isChecked);
                }
                if (!isAllMultiSelect) {
                    if (lastQmuiRoundButton != null) {
                        listAllCheckData.clear();
                        listAllCheckedIndex.clear();
                        QMUIRoundButtonDrawable lastDrawable = (QMUIRoundButtonDrawable) lastQmuiRoundButton.getBackground();
                        setbuttonFalse(lastQmuiRoundButton, lastDrawable);
                        lastQmuiRoundButton.setTag(false);
                        lastQmuiRoundButton.setBackground(lastDrawable);
                    }
                }
                QMUIRoundButtonDrawable drawable = (QMUIRoundButtonDrawable) qmuiRoundButton.getBackground();
                if (!isChecked) {
                    //选中状态  TODO  增加文字颜色
                    listAllCheckData.add(data);
                    listAllCheckedIndex.add(position);
                    setButtonTrue(drawable, qmuiRoundButton);
                    lastQmuiRoundButton = qmuiRoundButton;
                } else {
                    //未选中状态 TODO  增加文字颜色
                    setbuttonFalse(qmuiRoundButton, drawable);
                    listAllCheckData.remove(data);
                    Integer integer = new Integer(position);

                    boolean boo = listAllCheckedIndex.remove(integer);
                }
                qmuiRoundButton.setBackground(drawable);
            }
        });
        qmuiRoundButton.setBackground(qmuiRoundButtonDrawable);
        MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        qmuiRoundButton.setLayoutParams(params);
        return qmuiRoundButton;
    }

    private void setButtonTrue(QMUIRoundButtonDrawable drawable, QMUIRoundButton qmuiRoundButton) {
        drawable.setBgData(buttonCheckBackGgroundColor);
        qmuiRoundButton.setTextColor(buttonCheckTextColor);
        drawable.setStrokeData(buttonBorderWidth, buttonCheckBoradColor);
    }

    /**
     * 获取所有选中的下标集合
     *
     * @return
     */
    public List<Integer> getAllCheckedIndex() {
        return listAllCheckedIndex;
    }

    /**
     * 获取所有选中的数据源集合
     *
     * @param tClass 泛型标志
     * @param <T>
     * @return 数据源集合
     */
    public <T> List<T> getAllCheckData(Class<T> tClass) {

        List<T> list = new ArrayList<>();
        for (Object o : listAllCheckData) {
            T t = null;
            t = (T) o;
            list.add(t);
        }
        return list;
    }

    /**
     * 清除所有item的选中效果
     */
    public void clearAllItemChecked() {
        listAllCheckedIndex.clear();
        listAllCheckData.clear();
        lastQmuiRoundButton = null;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            final QMUIRoundButton button = (QMUIRoundButton) getChildAt(i);
            button.setTag(false);
            QMUIRoundButtonDrawable qmuiRoundButtonDrawable = (QMUIRoundButtonDrawable) button.getBackground();
            setbuttonFalse(button, qmuiRoundButtonDrawable);
        }
    }

    private void setbuttonFalse(QMUIRoundButton button, QMUIRoundButtonDrawable qmuiRoundButtonDrawable) {
        button.setTextColor(buttonTextColor);
        qmuiRoundButtonDrawable.setBgData(buttonBackGroundColor);
        qmuiRoundButtonDrawable.setStrokeData(buttonBorderWidth, buttonBorderColor);
    }

    /**
     * 设置单个item默认选中
     *
     * @param position
     */
    public void setDefaultItemCheck(int position) {
        if (position >= getChildCount()) {
            return;
        }
        final QMUIRoundButton childAt = (QMUIRoundButton) getChildAt(position);
        QMUIRoundButtonDrawable drawable = (QMUIRoundButtonDrawable) childAt.getBackground();
        setButtonTrue(drawable, childAt);
        childAt.setTag(true);
        childAt.setBackground(drawable);
        if (!isAllMultiSelect) {
            if(lastQmuiRoundButton!=null){
                QMUIRoundButtonDrawable lastDrawable = (QMUIRoundButtonDrawable) lastQmuiRoundButton.getBackground();
                setbuttonFalse(lastQmuiRoundButton, lastDrawable);
                lastQmuiRoundButton.setTag(false);
                lastQmuiRoundButton.setBackground(lastDrawable);
            }
            lastQmuiRoundButton = childAt;
        }
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

