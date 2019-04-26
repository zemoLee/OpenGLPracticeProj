package com.sf.opengldemo1.practice5;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice5.program.ColorTriangleShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class PracticeRender5 implements GLSurfaceView.Renderer {
    private Context context;
    private ColorTriangle  mColoTriangle;
    private ColorTriangleShaderProgram mProgram;

    public final float[] mMVPMatrix = new float[16];//存放结果的总变换矩阵
    public final float[] mProjectionMatrix = new float[16];//投影矩阵，
    public final float[] mViewMatrix = new float[16];  //Camera View 相机视角矩阵

    public PracticeRender5(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //1：创建绘制资源的坐标信息和缓冲
        mColoTriangle= new ColorTriangle();
        //2:获得生成的program
        mProgram = new ColorTriangleShaderProgram(context);
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);      //打开深度检测，会造成图像不完整，三角形变 体形，顶部被截取
        GLES20.glDisable(GLES20.GL_CULL_FACE);//打开背面剪裁
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
       //3：变换矩阵
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(mViewMatrix,0,0,0,-3,0f,0f,0f,0f,1.0f,0.0f);//添加一个相机视角变换作为图形绘制过程的一部分
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //4:
        mProgram.userProgram();
        //5:句柄和Program结合绑定载入资源变量，然后封装绘制中
        mColoTriangle.drawSelf(mProgram,mMVPMatrix);
    }


}
