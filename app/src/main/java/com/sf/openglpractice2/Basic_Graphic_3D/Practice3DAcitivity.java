package com.sf.openglpractice2.Basic_Graphic_3D;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView=new GLSurfaceView(this);
        Render_2_3D  render_2_3D=new Render_2_3D();
        mSurfaceView.setRenderer(render_2_3D);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        setContentView(mSurfaceView);

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
