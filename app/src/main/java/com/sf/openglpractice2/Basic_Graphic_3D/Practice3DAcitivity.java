package com.sf.openglpractice2.Basic_Graphic_3D;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/29
 * @Des: //TODO
 */
public class Practice3DAcitivity extends AppCompatActivity {
    private GLSurfaceView  mSurfaceView;

    public boolean openLightFlag=false;  //开灯标记，false为关灯，true为开灯
    private float previousX,previousY;  //上次触控的横纵坐标
    private final float TOUCH_SCALE_FACTOR=0.5f;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView=new GLSurfaceView(this);
//        final Render_2_3D  render_2_3D=new Render_2_3D();
      //  final Render_2_3D_Ball  render_2_3D_ball=new Render_2_3D_Ball();
        final Render_2_3D_Ball_Blog  render_2_3D_ball_blogl=new Render_2_3D_Ball_Blog();
//        final Render_2_3D_Ball_with_touch  render_2_3D_ball_with_touch=new Render_2_3D_Ball_with_touch();
//        mSurfaceView.setRenderer(render_2_3D_ball_with_touch);
        mSurfaceView.setRenderer(render_2_3D_ball_blogl);
//        mSurfaceView.setRenderer(render_2_3D);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        setContentView(mSurfaceView);
        mSurfaceView. setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x=event.getX();
                //float y=event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        //float dy=y-previousY;  //计算触控笔移动Y位移
                        float dx=x-previousX;   //计算触控笔移动X位移
                        render_2_3D_ball_blogl.ball.angleX +=dx*TOUCH_SCALE_FACTOR;  //设置沿x轴旋转角度
                        mSurfaceView.requestRender();                               //渲染画面
                        break;
                }
                previousX=x;                                   //前一次触控位置x坐标
                return true;                                      //事件成功返回true

            }
       //         switch (event.getAction()) {
         //           case MotionEvent.ACTION_DOWN://检测到点击事件时
//                        render_2_3D_ball_with_touch.roate(20f, 0, 1, 0); //绕y轴旋转
           //     }
             //   return true;
           // }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }





}
