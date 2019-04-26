package com.sf.opengldemo1.practice9;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice9.program.BaseShaderProgram;
import com.sf.opengldemo1.practice9.program.CircleShaderProgram;
import com.sf.opengldemo1.practice9.program.ConeShaderProgram;
import com.sf.opengldemo1.practice9.view.Circle;
import com.sf.opengldemo1.practice9.view.Cone;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeRender9 implements GLSurfaceView.Renderer {
    private Context context;
    private Cone mCone;
    private Circle mCircle;
    private ConeShaderProgram mConeProgram;
    private CircleShaderProgram  mCircleProgram;
    private final float[]  mMVPMatrix=new float[16];
    private final float[]  mProjectionMatrix=new float[16];
    private final float[]  mViewMatrix=new float[16];


    public PracticeRender9(Context context) {
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mCone =new Cone();
        mCircle=new Circle();
        mConeProgram =new ConeShaderProgram(context, BaseShaderProgram.CONE);
        mCircleProgram=new CircleShaderProgram(context,BaseShaderProgram.CIRCLE);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,20);
        Matrix.setLookAtM(mViewMatrix,0,1.0f,-10.0f,-4.0f,0f,0f,0f,0f,1f,0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
        //锥体
        //圆
        mConeProgram.userProgram();
        mCone.drawSelf(mConeProgram,mMVPMatrix);
        mCircleProgram.userProgram();
        mCircle.drawSelf(mCircleProgram,mMVPMatrix);
    }
}
