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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView=new GLSurfaceView(this);
//        final Render_2_3D  render_2_3D=new Render_2_3D();
        final Render_2_3D_Ball  render_2_3D_ball=new Render_2_3D_Ball();
//        final Render_2_3D_Ball_with_touch  render_2_3D_ball_with_touch=new Render_2_3D_Ball_with_touch();
//        mSurfaceView.setRenderer(render_2_3D_ball_with_touch);
        mSurfaceView.setRenderer(render_2_3D_ball);
//        mSurfaceView.setRenderer(render_2_3D);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        setContentView(mSurfaceView);
        mSurfaceView. setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://检测到点击事件时
//                        render_2_3D_ball_with_touch.roate(20f, 0, 1, 0); //绕y轴旋转
                }
                return true;
            }
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
