package com.sf.openglpractice2.Practice6_Basic_Video.video1_player;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.FilterRender;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

public class VideoPlayer01_Activity extends AppCompatActivity {
    GLSurfaceView mSurfaceView;
    VedioPlayer01_Render render;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView=new GLSurfaceView(this);
        render=new VedioPlayer01_Render(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(render);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);

    }
}
