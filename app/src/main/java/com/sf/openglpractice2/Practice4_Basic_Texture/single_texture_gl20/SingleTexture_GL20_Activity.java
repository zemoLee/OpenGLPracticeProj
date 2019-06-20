package com.sf.openglpractice2.Practice4_Basic_Texture.single_texture_gl20;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/6/6
 * @Des: //TODO
 */
public class SingleTexture_GL20_Activity extends AppCompatActivity {
    GLSurfaceView  mSurfaceView;
    SingleTextureRender_GL20 render;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTextureRender();
    }
    private void initTextureRender(){
        mSurfaceView=new GLSurfaceView(this);
        render=new SingleTextureRender_GL20(this);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(render);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

}
