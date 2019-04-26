package com.sf.opengldemo1.practice2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class PracticeRender2 implements GLSurfaceView.Renderer {
    private Triangle  mTriangle;
    private SquareRect  mRect;

    /**
     *
     */

    private final float []  mMVPMatrix=new float[16];//合并后的 投影矩阵+ 相机视角 矩阵
    private final float []  mProjectionMatrix=new float[16];//投影矩阵，
    private final float []  mViewMatrix=new float[16];  //Camera View 相机视角矩阵，通过对绘图对象的变换来模拟相机视角

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mTriangle=new Triangle();
        mRect=new SquareRect();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio=(float) width/height;//glsurfaceview的宽高比,一定要强转float，不然为0
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7);
    }

    @Override//每次重新绘制View时被调用
    public void onDrawFrame(GL10 gl) {
        //        mTriangle.draw();

        //计算相机视角变换,// 改变摄像头在 z 轴上的位置（镜头拉伸），让视图调整到合适的大小。
        Matrix.setLookAtM(mViewMatrix,0,0,0f,-3,0f,0f,0f,0f,1.0f,0.0f);//添加一个相机视角变换作为图形绘制过程的一部分
        //集合投影矩阵和相机视角矩阵，变换出新的矩阵  mMVP矩阵，然后传递给图像进行绘制
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
        mTriangle.draw(mMVPMatrix);
    }
    //要绘制图形前，必须先编译顶点着色器和片元着色器代码并将它们添加至一个 OpenGL ES Program 对象中，然后执行链接方法。
    //编译 OpenGL ES 着色器及链接操作对于 CPU 周期和处理时间而言消耗巨大，所以应该避免重复执行这些事情
    //个操作建议在形状类的构造方法中调用，这样只会执行一次
    public  static int loadShader(int type,String  shadeCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shadeCode); //添加着色器代码，
        GLES20.glCompileShader(shader);//编译
        return  shader;
    }
}
