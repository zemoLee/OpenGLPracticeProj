package com.sf.openglpractice2.Basic_Graphic_Shader;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class RenderOfShader implements GLSurfaceView.Renderer {

   public Squre  squre;

    public RenderOfShader() {
        squre=new Squre();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float radio=width/height;
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-radio,radio,-1,1,3,100);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        this.gl=gl;
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0,0,3,0,0,0,0,1,0);
        gl.glTranslatef(-0,0,-5);
        gl.glRotatef(angle2,xDirect,yDirect,0);
        LightUtils.setLight(gl);
        LightUtils.setMeterial(gl);

        squre.drawSelf(gl);
//        angle+=0.5;
    }

    //旋转变换
    public float angleX,angleY;
    float angle2;
    private GL10 gl;
    float xDirect,yDirect;
    public void rotate(float angle,float x,float y,float z){

        xDirect=x;
        yDirect=y;
        if(xDirect>0){
            angleY+=angle;
            angle2=angleY;
            Log.d("RenderOfShader","X轴旋转(上下)");
        }
        else if(yDirect>0){
            angleX+=angle;
            angle2=angleX;
            Log.d("RenderOfShader","Y轴旋转(左右)");
        }
        Log.d("RenderOfShader","angleX="+angleX+" angleY="+angleY);

    }

//    //旋转变换
//    public float angleX,angleY;
//    private GL10 gl;
//    float xDirect,yDirect;
//    public void rotate(float angle,float x,float y,float z){
//        if(x>0){
//            yDirect=0;
//            angleX+=angle;
//            Log.d("render","X轴旋转");
//            gl.glRotatef(angleX,1,0,0);
//        }else {
//            xDirect=0;
//            angleY+=angle;
//            Log.d("render","Y轴旋转");
//            gl.glRotatef(angleY,0,1,0);
//        }
//        Log.d("RenderOfShader","angleX="+angleX+" angleY="+angleY);
////        if(xDirect>0){
////            Log.d("render","X轴旋转2");
////            gl.glRotatef(angleX,1,0,0);
////        }
////        else if(yDirect>0){
////            Log.d("render","Y轴旋转2");
////            gl.glRotatef(angleY,0,1,0);
////        }
//    }
}
