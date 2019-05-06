package com.sf.opengldemo1.practice4;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.sf.opengldemo1.practice2.SquareRect;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class PracticeRender4 implements GLSurfaceView.Renderer {
    private Triangle4 mTriangle;

    /**
     *
     */

    private final float []  mMVPMatrix=new float[16];//合并后的 投影矩阵+ 相机视角 矩阵、、 ///存放结果的总变换矩阵
    private final float []  mProjectionMatrix=new float[16];//投影矩阵，
    private final float []  mViewMatrix=new float[16];  //Camera View 相机视角矩阵，通过对绘图对象的变换来模拟相机视角

    private  float []  mRotationMatrix=new float[16];//旋转矩阵


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建三角形对对象
        mTriangle=new Triangle4();
        //设置屏幕背景色RGBA
        GLES20.glClearColor(0,0,0,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0,0,width,height);
        float ratio=(float) width/height;//glsurfaceview的宽高比,一定要强转float，不然为0
        //调用此方法计算产生透视投影矩阵
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7); //投影变换//
    }

    @Override//每次重新绘制View时被调用
    public void onDrawFrame(GL10 gl) {
        //        mTriangle.draw();

        // 每次先清除已有绘制内容，避免旋转时绘制内容叠加
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //调用此方法产生摄像机9参数位置矩阵
        //计算相机视角变换,// 改变摄像头在 z 轴上的位置（镜头拉伸），让视图调整到合适的大小。  //变换到负轴上面去，遵从人眼的观察角度
        Matrix.setLookAtM(mViewMatrix,0,0,0,-3,0f,0f,0f,0f,1.0f,0.0f);//添加一个相机视角变换作为图形绘制过程的一部分
        //集合投影矩阵和相机视角矩阵，变换出新的矩阵  mMVP矩阵，然后传递给图像进行绘制
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);


        //增加旋转矩阵
        float[] scratch=new float[16]; //合并后的最新矩阵
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle=0.090f* ((int)time);
//        Matrix.setRotateM(mRotationMatrix,0,angle,0,0,-1.0f);//设置旋转矩阵  //这里是自动旋转
        Matrix.setRotateM(mRotationMatrix,0,-mAngle,0,0,-1.0f);//设置旋转矩阵  //这里是根据角度旋转 //    因为相机视角在负轴，所以设置旋转角度 mAngle，此时如果增大旋转角度图形应该是逆时针绕 z 轴旋转
//        变换叠加: 可通过变换矩阵相乘得出想要的变换矩阵
        Matrix.multiplyMM(scratch,0,mMVPMatrix,0,mRotationMatrix,0);//合并  旋转矩阵，+投影矩阵+相机视角

        mTriangle.draw(scratch);

    }
    //要绘制图形前，必须先编译顶点着色器和片元着色器代码并将它们添加至一个 OpenGL ES Program 对象中，然后执行链接方法。
    //编译 OpenGL ES 着色器及链接操作对于 CPU 周期和处理时间而言消耗巨大，所以应该避免重复执行这些事情
    //个操作建议在形状类的构造方法中调用，这样只会执行一次
    public  static int loadShader(int type,String  shadeCode){
        int shader = GLES20.glCreateShader(type);  //根据类型创建shader，然后上传代码，然后编译代码，然后再返回shader
        GLES20.glShaderSource(shader,shadeCode); //添加着色器代码，
        GLES20.glCompileShader(shader);//编译
        return  shader;
    }

    /**
     *    旋转
     */
    public  volatile  float  mAngle;

    public float getmAngle() {
        return mAngle;
    }

    public void setmAngle(float mAngle) {
        this.mAngle = mAngle;
    }
}
