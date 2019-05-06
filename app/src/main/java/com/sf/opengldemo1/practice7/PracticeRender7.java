package com.sf.opengldemo1.practice7;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice7.program.SqureShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeRender7 implements GLSurfaceView.Renderer {
    private Context context;
    private Squre  mSqure;
    private SqureShaderProgram mProgram;

    private final float[]  mMVPMatrix=new float[16];
    private final float[]  mProjectionMatrix=new float[16];
    private final float[]  mViewMatrix=new float[16];


    public PracticeRender7(Context context) {
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glDisable(GLES20.GL_CULL_FACE);//打开背面剪裁
        mSqure=new Squre();
        mProgram=new SqureShaderProgram(context);
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
        mSqure.drawSelf(mProgram,mMVPMatrix);
    }
}
