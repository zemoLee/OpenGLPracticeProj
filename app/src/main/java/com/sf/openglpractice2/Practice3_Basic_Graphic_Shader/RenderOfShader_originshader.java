package com.sf.openglpractice2.Practice3_Basic_Graphic_Shader;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class RenderOfShader_originshader implements GLSurfaceView.Renderer {

    Squre_originshader  squre;
    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    public RenderOfShader_originshader() {
        Log.d("FGLRender", "onSurfaceCreated 线程名1 ="+ Thread.currentThread().getName());
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        squre=new Squre_originshader();
        Log.d("FGLRender", "onSurfaceCreated 线程名2 ="+ Thread.currentThread().getName());
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//修正函数
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectMatrix,0,-ratio,ratio,-1,1,3,20);
        Matrix.setLookAtM(mViewMatrix,0,-5.0f,5f,10.0f,0f,0f,0f,0f,1f,0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
        squre.drawSelf(gl,mMVPMatrix);
    }
}
