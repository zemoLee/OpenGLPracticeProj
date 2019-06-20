/*
 *
 * FGLRender.java
 * 
 * Created by Wuwang on 2016/9/29
 */
package com.sf.openglpractice2.Practice3_Basic_Graphic_Shader.cube;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Description:
 */
public class FGLRender implements GLSurfaceView.Renderer {


    Cube cube;
    public FGLRender() {
        cube=new Cube();
        Log.d("FGLRender", "onSurfaceCreated 线程名1 ="+ Thread.currentThread().getName());
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        cube=new Cube();
        Log.d("FGLRender", "onSurfaceCreated 线程名2 ="+ Thread.currentThread().getName());
        GLES20.glClearColor(1,0,0,1.0f);
        //开启深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }
    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 5.0f, 5.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e("wuwang","onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
        cube.onDraw(gl,mMVPMatrix);
    }

}
