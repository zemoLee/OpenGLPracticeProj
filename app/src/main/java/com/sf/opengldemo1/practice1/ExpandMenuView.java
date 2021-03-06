package com.sf.opengldemo1.practice1;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/6/7
 * @Des: //TODO
 */
public class ExpandMenuView extends LinearLayout {
    private Context mContext;

    enum State {
        OPEN, CLOSE
    }

    private State mCurrentState = State.OPEN;
    private View mCurrentClickView;
    private int viewLeft;
    private int viewRight;
    private float raseWidth;
    private float totalWidth;
    private float viewWidth;
    private float viewHight;
    private float childMinWidth;
    private int menuBackColor = Color.parseColor("#191919");
    private int menuStrokeSize = 4;
    private int menuStrokeColor = Color.parseColor("#D8C12D");
    private int menuCornerRadius = 40;
    boolean isFirstLayout = true;
    boolean isAnimFinish = false;

    public ExpandMenuView(Context context) {
        this(context, null);
    }

    public ExpandMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    ValueAnimator anim;

    private void init(Context context) {
        this.mContext = context;
        setOrientation(HORIZONTAL);
        anim = ValueAnimator.ofFloat(0.0f, 1.0f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMenuBackground();
        Log.d("ExpandMenuView", "onMeasure ");
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View item = getChildAt(i);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentClickView = v;
                    if (mOnExpandMenuItemClickListener != null) {
                        mOnExpandMenuItemClickListener.onItemClick(v);
                    }
                    if (mCurrentState == State.CLOSE) {
                        mCurrentState = State.OPEN;
                        openMenus();
                    } else {
                        mCurrentState = State.CLOSE;
                        closeMenus();
                    }
                    setCloseShowView(mCurrentClickView);
//                    mHandler.sendEmptyMessageDelayed(0,310);
                }
            });
        }
        int height = measureSize(200, heightMeasureSpec);
        int width = measureSize(40, widthMeasureSpec);
        viewHight = height;
        viewWidth = width;
        setMeasuredDimension((int)viewWidth, (int)viewHight);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("ExpandMenuView", "onSizeChanged ");
//        viewWidth=w;
        setMeasuredDimension((int)w, (int)h);
        if(isAnimFinish){
            setCloseShowView(mCurrentClickView);
        }
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        Log.e("ExpandMenuView", "onLayout " + l + " " + t + " " + r + " " + " " + b);
        if (isFirstLayout) {
            viewRight = getRight();
            viewLeft = getLeft();
            totalWidth = viewRight - viewLeft;
            raseWidth = totalWidth;
            isFirstLayout = false;
            if (childCount > 0) {
                childMinWidth = getChildAt(childCount - 1).getMeasuredWidth();
            }
        }

        Log.e("ExpandMenuView", "onLayout totalWidth=" + totalWidth + " " + "viewLeft=" + viewLeft + "  viewRight=" + viewRight);
//        for (int i = 0; i < childCount; i++) {
//            View item = getChildAt(i);
//            item.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mCurrentClickView = v;
//                    if (mOnExpandMenuItemClickListener != null) {
//                        mOnExpandMenuItemClickListener.onItemClick(v);
//                    }
//                    if (mCurrentState == State.CLOSE) {
//                        mCurrentState = State.OPEN;
//                        openMenus();
//                    } else {
//                        mCurrentState = State.CLOSE;
//                        closeMenus();
//                    }
////                    setCloseShowView(mCurrentClickView);
//                }
//            });
//        }


    }


    private void setMenuBackground() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(menuBackColor);
        gd.setStroke((int) menuStrokeSize, menuStrokeColor);
        gd.setCornerRadius(menuCornerRadius);
        setBackground(gd);
    }


    public void openMenus() {
        if (anim == null) {
            anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        }
        anim.setDuration(300);
        anim.setRepeatCount(0);
       total = viewRight - viewLeft;
        anim.addUpdateListener(animer);
        anim.start();

    }

    int total = 0;

    public void closeMenus() {
        if (anim == null) {
            anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        }
        anim.setDuration(300);
        anim.setRepeatCount(0);
        total = viewRight - viewLeft;
        anim.addUpdateListener(animer);
        anim.start();

    }
    private ValueAnimator.AnimatorUpdateListener animer = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float frac = (Float) animation.getAnimatedValue();
            Log.d("ExpandMenuView","frac="+frac);
            float left = 0;
            raseWidth = (totalWidth) * frac;
            if (mCurrentState == State.CLOSE) {

                left=viewLeft+raseWidth;
                if(left>=viewRight-80-childMinWidth){
                    left=viewRight-80-childMinWidth;
                }
                Log.e("ExpandMenuView", "viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");
            } else {
                left=viewRight-raseWidth;
                if(left>=viewRight-80-childMinWidth){
                    left=viewRight-80-childMinWidth;
                }
                Log.e("ExpandMenuView", "viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");

            }
            if(frac==1){
                isAnimFinish=true;
            }
            layout((int) left, getTop(), viewRight, getBottom());

        }
    };

    Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCloseShowView(mCurrentClickView);
        }
    };

    public void setCloseShowView(View view) {
        Log.e("setCloseShowView", "view ID=" + view.getId());

        int count = getChildCount();
        if (view == null) return;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (mCurrentState == State.CLOSE) {
                if (view.getId() == child.getId()) {
                    child.setVisibility(VISIBLE);
                } else {
                    child.setVisibility(GONE);
                    postInvalidateDelayed(5000);
                }
            } else {
                child.setVisibility(VISIBLE);
            }

        }
    }

    public void addView(View childView) {
        addView(childView, getChildCount());
    }

    interface OnExpandMenuItemClickListener {
        void onItemClick(View v);
    }

    OnExpandMenuItemClickListener mOnExpandMenuItemClickListener;

    public void setOnExpandMenuItemClickListener(OnExpandMenuItemClickListener expandMenuItemClickListener) {
        this.mOnExpandMenuItemClickListener = expandMenuItemClickListener;
    }


}
