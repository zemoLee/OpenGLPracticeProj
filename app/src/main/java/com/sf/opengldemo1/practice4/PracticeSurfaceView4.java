package com.sf.opengldemo1.practice4;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class PracticeSurfaceView4 extends GLSurfaceView {
    /**
     *上一次坐标
     */
    private float mPreviousX,mPreviousY;
    // 旋转变换的比例因子
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    PracticeRender4 render;
    public PracticeSurfaceView4(Context context) {
        super(context);
        setEGLContextClientVersion(2);
         render= new PracticeRender4();
        setRenderer(render);
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
//    float  downX = 0;
//    float  downY=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float    downX=event.getX();// 从左往有滑动时: x 值增大，dx 为正；反之则否。
        float  downY=event.getY();// 从上往下滑动时: y 值增大，dy 为正；反之则否。
        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                  downX=event.getX();// 从左往有滑动时: x 值增大，dx 为正；反之则否。
//                  downY=event.getY();// 从上往下滑动时: y 值增大，dy 为正；反之则否。
//                return  true;

            case  MotionEvent.ACTION_MOVE:
                float  dx=downX-mPreviousX;
                float  dy=downY-mPreviousY;

                //// OpenGL 绕 z 轴的旋转符合左手定则，即 z 轴朝屏幕里面为正。
                // 用户面对屏幕时，是从正面向里看（此时 camera 所处的 z 坐标位置为负数），当旋转度数增大时会进行逆时针旋转。

                // 逆时针旋转判断条件1：触摸点处于 view 水平中线以下时，x 坐标应该要符合从右往左移动，此时 x 是减小的，所以 dx 取负数。
                if(downY>(getHeight()/2)){
                    dx=dx*(-1);
                }
                // 逆时针旋转判断条件2：触摸点处于 view 竖直中线以左时，y 坐标应该要符合从下往上移动，此时 y 是减小的，所以 dy 取负数。
                if(downX<(getWidth()/2)){
                    dy=dy*(-1);
                }
                render.setmAngle(render.getmAngle()+((dx+dy)*TOUCH_SCALE_FACTOR));

                requestRender();//角度计算好后，开启渲染
//                mPreviousY=downY;
//                mPreviousX=downX;
//            return  true;

        }
        mPreviousY=downY;
        mPreviousX=downX;
        return true;
    }
}
