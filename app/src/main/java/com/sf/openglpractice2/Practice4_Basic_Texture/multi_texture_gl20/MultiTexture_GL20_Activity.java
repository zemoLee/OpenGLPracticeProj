package com.sf.openglpractice2.Practice4_Basic_Texture.multi_texture_gl20;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

public class MultiTexture_GL20_Activity extends AppCompatActivity {
    GLSurfaceView  mSurfaceView;
   MultiTexture_Render render;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView=new GLSurfaceView(this);
        render=new MultiTexture_Render(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(render);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
