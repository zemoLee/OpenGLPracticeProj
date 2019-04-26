package com.sf.opengldemo1.practice12;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice12.program.SqureShaderProgram;
import com.sf.opengldemo1.practice12.view.Squre;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeRender12 implements GLSurfaceView.Renderer {
    private Context context;
    private Squre mSqure;
    private SqureShaderProgram mProgram;

    private final float[]  mMVPMatrix=new float[16];
    private final float[]  mProjectionMatrix=new float[16];
    private final float[]  mViewMatrix=new float[16];

    private float uXY;
    public PracticeRender12(Context context) {
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glDisable(GLES20.GL_CULL_FACE);//打开背面剪裁
        mSqure=new Squre(context);
        mSqure.setBitmap(mBitmap);
        mProgram=new SqureShaderProgram(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        int w=mBitmap.getWidth();
        int h=mBitmap.getHeight();
        float sWH=w/(float)h;
        float sWidthHeight=width/(float)height;
        uXY=sWidthHeight;
        if(width>height){
            if(sWH>sWidthHeight){
                Matrix.orthoM(mProjectionMatrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 3, 5);//正交投影。保证不变化
            }else{
                Matrix.orthoM(mProjectionMatrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 3, 5);
            }
        }else{
            if(sWH>sWidthHeight){
                Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,3, 5);
            }else{
                Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,3, 5);
            }
        }

        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 5.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        mProgram.userProgram();
        mSqure.drawSelf(mProgram,mMVPMatrix);
    }

    private  Bitmap mBitmap;
    public void setBitmap(Bitmap bitmap){
        this.mBitmap=bitmap;
    }
}
