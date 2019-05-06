package com.sf.opengldemo1.practice1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/5.
 */

public class PracticeSurfaceView1 extends GLSurfaceView {
    private PracticeRenderer1 myRender;

    public PracticeSurfaceView1(Context context) {
        super(context);
        setEGLContextClientVersion(2);//创建opengl 2.0 context
        myRender=new PracticeRenderer1();//创建
        setRenderer(myRender);//设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//主动渲染 //GLSurfaceView.RENDERMODE_WHEN_DIRTY 绘制数据发生变化时才在视图中进行绘制操作

    }


    private class SceneRender implements  GLSurfaceView.Renderer{

        public SceneRender() {
        }

        @Override//调用一次，用来配置 View 的 OpenGL ES 环境
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//            gl.glEnable(GL10.GL_CULL_FACE);//打开背面剪辑
//            gl.glShadeModel(GL10.GL_SMOOTH);//设置为平滑着色模型
//            gl.glFrontFace(GL10.GL_CCW);//设置绕顺时针旋转为正面
//            gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//清除颜色缓存和深度缓存
//            gl.glMatrixMode(GL10.GL_MODELVIEW);//设置矩阵为模式矩阵
//            gl.glLoadIdentity();//设置矩阵为单位矩阵
//            gl.glTranslatef(0,0,-2.0f);
//            //设置和三角形绑定

            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);//设置背景
        }

        @Override//如果View的几何形态发生变化时会被调用，例如当设备的屏幕方向发生改变时。
        public void onSurfaceChanged(GL10 gl, int width, int height) {
//        gl.glViewport(0,0,width,height);
//        gl.glMatrixMode(GL10.GL_PROJECTION);
//        gl.glLoadIdentity();
//        float ratio=width/height;
//        gl.glFrustumf(-ratio,ratio,-1,1,1,10);


            GLES20.glViewport(0, 0, width, height);//设置宽高
        }

        @Override//每次重新绘制View时被调用
        public void onDrawFrame(GL10 gl) {
//            gl.glDisable(GL10.GL_DITHER);//关闭抗抖动
//            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置为快速模式
//            gl.glClearColor(0,0,0,0);//设置屏幕背景色为黑色
//            gl.glEnable(GL10.GL_DEPTH_TEST);//启用深度检测

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);//重新绘制背景颜色
        }
    }

}
