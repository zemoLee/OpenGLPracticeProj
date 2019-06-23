package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

public class GrayFilter1_Activity extends AppCompatActivity {
    GLSurfaceView mSurfaceView;
    FilterRender render;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTextureRender();
    }
    private void initTextureRender(){
        mSurfaceView=new GLSurfaceView(this);
        render=new FilterRender(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(render);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                render.onClick();
            }
        });
    }
}
