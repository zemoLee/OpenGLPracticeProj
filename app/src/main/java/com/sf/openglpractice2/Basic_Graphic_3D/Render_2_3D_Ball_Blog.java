package com.sf.openglpractice2.Basic_Graphic_3D;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class Render_2_3D_Ball_Blog implements GLSurfaceView.Renderer{
    Ball ball=new Ball(4);  //创建圆
    public Render_2_3D_Ball_Blog(){}

    public void onDrawFrame(GL10 gl) {
        gl.glEnable(GL10.GL_CULL_FACE) ; //打开背面剪裁
        gl.glShadeModel(GL10.GL_SMOOTH);  //开始平滑着色
       // if(openLightFlag){
            gl.glEnable(GL10.GL_LIGHTING);
            initLight(gl, GL10.GL_LIGHT0);
            initMaterialWhite(gl);
            //设置light0光源位置
            float [] positionParamsGreen={2,1,0,1};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0);
      //  }else{
       //     gl.glDisable(GL10.GL_LIGHTING);
      //  }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);  //清除缓存
        gl.glMatrixMode(GL10.GL_MODELVIEW); //设置当前矩形为模式矩阵
        gl.glLoadIdentity();   //设置矩阵为单位矩阵
        gl.glTranslatef(0, 0, -1.8f);  //把坐标系往z轴负方向平移2.0f个单位
        ball.drawSelf(gl);
        gl.glLoadIdentity();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);   //设置视口大小和位置
        gl.glMatrixMode(GL10.GL_PROJECTION);   //设置矩阵为投影矩阵
        gl.glLoadIdentity();                   //设置矩阵为单位矩阵
        float ratio=(float)width/height;        //比例大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);  //设置投影模式
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);     //关闭抗抖动
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        //设置模式
        gl.glClearColor(0, 0, 0, 0);  //设置屏幕颜色为黑色
        gl.glEnable(GL10.GL_DEPTH_TEST);  //启用深度检测

    }

    /**
     * 初始化灯
     * @param gl
     * @param   -7代表八盏灯
     */
    private void initLight(GL10 gl,final int LIGHT){
        gl.glEnable(LIGHT); //打开LIGTH+1号灯
        //设置环境光
        float [] ambientParams  ={0.1f,0.1f,0.1f,1.0f};   //光参数RGBA
        gl.glLightfv(LIGHT, GL10.GL_AMBIENT, ambientParams,0);
        //设置散射光
        float [] diffuseParams={0.5f,0.5f,0.5f,1.0f};  //光参数RGBA
        gl.glLightfv(LIGHT, GL10.GL_DIFFUSE, diffuseParams, 0);
        //设置放射光
        float [] specularParams={1.0f,1.0f,1.0f,1.0f};
        gl.glLightfv(LIGHT, GL10.GL_SPECULAR, specularParams,0);
    }

    private void initMaterialWhite(GL10 gl){
        //材质为白色时，什么颜色的光照在上面就将体现出什么颜色
        //设置环境光，为白色材质
        float [] ambientMaterial={0.4f,0.4f,0.4f,1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //设置散射光白色
        float [] diffuseMaterial={0.8f,0.8f,0.8f,1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial, 0);
        //高光材质为白色          //建立镜面光float，镜面光一般设置较高
        float [] specularMaterial={1.0f,1.0f,1.0f,1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial, 0);
        //高光反色区，数越大，高亮区域越小、越暗          //高光反射区域数越大，高亮区域越小
        float [] shininessMaterial={1.5f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
    }

}
