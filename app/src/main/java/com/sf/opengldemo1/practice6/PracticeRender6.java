package com.sf.opengldemo1.practice6;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice6.program.CircleShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/12.
 */

public class PracticeRender6 implements GLSurfaceView.Renderer {

    private Context context;

    private final float[]  mMVPMatrix=new float[16];
    private final float[]  mProjectionMatrix=new float[16];
    private final float[]  mViewMatrix=new float[16];

    private CircleShaderProgram  mProgram;
    private Circle  mCircle;
    public PracticeRender6(Context context) {
    this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glDisable(GLES20.GL_CULL_FACE);//打开背面剪裁
        mCircle =new Circle();
        mProgram=new CircleShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7);
        Matrix.setLookAtM(mViewMatrix,0,0f,0f,-7.0f,0f,0f,0f,0f,1f,0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mProgram.userProgram();
        mCircle.drawSelf(mProgram,mMVPMatrix);
    }
}
