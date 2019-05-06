package com.sf.opengldemo1.practice11;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice10.program.BaseShaderProgram;
import com.sf.opengldemo1.practice10.program.CircleShaderProgram;
import com.sf.opengldemo1.practice10.program.CylinderShaderProgram;
import com.sf.opengldemo1.practice10.view.Circle;
import com.sf.opengldemo1.practice10.view.Cylinder;
import com.sf.opengldemo1.practice11.program.BallShaderProgram;
import com.sf.opengldemo1.practice11.view.Ball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeRender11 implements GLSurfaceView.Renderer {
    private Context context;
    private Ball   mBall;
    private BallShaderProgram  mBallShaderProgram;
    private final float[]  mMVPMatrix=new float[16];
    private final float[]  mProjectionMatrix=new float[16];
    private final float[]  mViewMatrix=new float[16];


    public PracticeRender11(Context context) {
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mBall =new Ball();//圆柱
        mBallShaderProgram=new BallShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,20);
        Matrix.setLookAtM(mViewMatrix, 0, 1.0f, -10.0f, -4.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
        mBallShaderProgram.userProgram();
        mBall.drawSelf(mBallShaderProgram,mMVPMatrix);
    }
}
