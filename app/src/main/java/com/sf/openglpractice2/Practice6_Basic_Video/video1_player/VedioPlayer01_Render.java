package com.sf.openglpractice2.Practice6_Basic_Video.video1_player;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 系统视频播放器，渲染用的render
 */
public class VedioPlayer01_Render implements GLSurfaceView.Renderer {
    private Context mContext;
    public VedioPlayer01_Render(Context context) {
        this.mContext=context;

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
