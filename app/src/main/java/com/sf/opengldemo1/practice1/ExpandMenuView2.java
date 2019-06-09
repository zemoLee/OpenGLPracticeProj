package com.sf.opengldemo1.practice1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/6/7
 * @Des: //TODO
 */
public class ExpandMenuView2 extends RelativeLayout {
    private Context mContext;

    enum State {
        OPEN, CLOSE
    }

    private State mCurrentState = State.CLOSE;
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

    public ExpandMenuView2(Context context) {
        this(context, null);
    }

    public ExpandMenuView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    ValueAnimator anim;
    AnimatorSet animatorSetOpen;
    AnimatorSet animatorSetClose;

    private void init(Context context) {
        this.mContext = context;
        anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        animatorSetOpen = new AnimatorSet();
        animatorSetClose = new AnimatorSet();
//        animatorSetOpen.setInterpolator(interpolator)   ;
//        animatorSetClose.setInterpolator(interpolator);
        animatorSetOpen.setDuration(300);
        animatorSetClose.setDuration(300);

    }


    private OvershootInterpolator interpolator=new OvershootInterpolator(){
        @Override
        public float getInterpolation(float t) {
            Log.e("ExpandMenuView", "animatorSetOpen1 getInterpolation= "+t);

            float frac=t;
            float left = 0;
            if(frac!=1){

            raseWidth = (totalWidth) * frac;
            if (mCurrentState == State.CLOSE) {

                left = getLeft() + raseWidth;
                if (left >= viewRight - 80 - childMinWidth) {
                    left = viewRight - 80 - childMinWidth;
                }
                Log.e("ExpandMenuView", "left="+left+"   viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");
            } else {
                left = viewRight - raseWidth;
                if (left >= viewRight - 80 - childMinWidth) {
                    left = viewRight - 80 - childMinWidth;
                }
                Log.e("ExpandMenuView", "left="+left+"  viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");

            }

            layout((int) left, getTop(), viewRight, getBottom());
            }

            return super.getInterpolation(t);
        }
    };
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setMenuBackground();
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
//                    setCloseShowView(mCurrentClickView);
                }
            });
        }
    }


    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
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
//            totalWidth = viewRight - viewLeft;
            raseWidth = totalWidth;
            isFirstLayout = false;
            if (childCount > 0) {
                childMinWidth = getChildAt(childCount - 1).getMeasuredWidth();
            }
            totalWidth=childCount*childMinWidth;
        }


    }


    private void setMenuBackground() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(menuBackColor);
        gd.setStroke((int) menuStrokeSize, menuStrokeColor);
        gd.setCornerRadius(menuCornerRadius);
        setBackground(gd);
    }

    List<Animator> objectAnimatorListOpen = new ArrayList<>();
    List<Animator> objectAnimatorListClose = new ArrayList<>();

    public void openMenus() {
        if (objectAnimatorListOpen.isEmpty()) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(child, "translationX", 0, -300 * i);
                objectAnimatorListOpen.add(objectAnimator);
            }
        }
        animatorSetOpen.playTogether(objectAnimatorListOpen);

        animatorSetOpen.start();
    }

    int total = 0;

    public void closeMenus() {
        if (objectAnimatorListClose.isEmpty()) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(child, "translationX", child.getTranslationX(), 0);
                objectAnimator.addUpdateListener(animer);
                objectAnimatorListClose.add(objectAnimator);
            }
        }
        animatorSetClose.playTogether(objectAnimatorListClose);

        animatorSetClose.start();
    }

    private ValueAnimator.AnimatorUpdateListener animer = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float frac = (Float) animation.getAnimatedValue();
            Log.d("ExpandMenuView", "frac=" + frac);
            float left = 0;
            raseWidth = (totalWidth) * frac;
            if (mCurrentState == State.CLOSE) {

                left = viewLeft + raseWidth;
                if (left >= viewRight - 80 - childMinWidth) {
                    left = viewRight - 80 - childMinWidth;
                }
                Log.e("ExpandMenuView", "viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");
            } else {
                left = viewRight - raseWidth;
                if (left >= viewRight - 80 - childMinWidth) {
                    left = viewRight - 80 - childMinWidth;
                }
                Log.e("ExpandMenuView", "viewLeft=" + viewLeft + "  viewRight=" + viewRight + "  totalWidth=" + totalWidth + "raseWidth= " + raseWidth + "   total=" + total + "    dymicvalue=" + frac + "      left=" + left + " ");

            }

            layout((int) left, getTop(), viewRight, getBottom());

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
