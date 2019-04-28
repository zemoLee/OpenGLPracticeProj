package com.sf.openglpractice2.Basic_Graphic_2D;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/28
 * @Des: //TODO
 */
public class Practice1Acitivity extends AppCompatActivity {
    GLSurfaceView surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        surfaceView=new GLSurfaceView(this);
        Render_1 render_1=new Render_1();
        surfaceView.setRenderer(render_1);
        setContentView(surfaceView);
        initGLSurfaceView(surfaceView);

    }


    private void initGLSurfaceView(GLSurfaceView surfaceView){
        //设置版本
//        surfaceView.setEGLContextClientVersion(2);
        //不停的渲染
        surfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
