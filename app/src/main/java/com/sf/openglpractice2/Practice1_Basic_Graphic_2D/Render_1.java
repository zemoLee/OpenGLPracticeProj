package com.sf.openglpractice2.Practice1_Basic_Graphic_2D;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/28
 * @Des: //TODO
 */
public class Render_1 implements GLSurfaceView.Renderer {

    private int one_unit=0x10000;
    /**
     * 三角形顶点坐标
     */
    private IntBuffer traingleBuffer=IntBuffer.wrap(new int[]{
       0,one_unit,0,
       -one_unit,-one_unit,0,
       one_unit,-one_unit,0
    });


    private int[] traingleArrys=new int[]{
            0,one_unit,0,
            -one_unit,-one_unit,0,
            one_unit,-one_unit,0
    };

    private int[] colorsArrays =new int[]{
      one_unit,0,0,one_unit,
            0,one_unit,0,one_unit,
            0,0,one_unit,one_unit,

    };
    public Buffer bufferUtil(int []arr){
        IntBuffer mBuffer ;
        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }

    public Render_1() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //修正透视
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NEAREST);
        //清屏
        gl.glClearColor(0,0,0,1);
        //开启深度缓存
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float  radio=(float) width/height;
        //视口
        gl.glViewport(0,0,width,height);
        //设置为矩阵模式 ：透视投影
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化
        gl.glLoadIdentity();
        //创建透视矩阵
//        gl.glFrustumf(-radio,radio,-1,1,1,10);
        //正交矩阵
        gl.glOrthof(-radio,radio,-1,1,1,1000);


    }

    private  float angle=0;
    @Override
    public void onDrawFrame(GL10 gl) {
        //清理屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        //设置一个模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl,0,0,1,0,0,0,0,1,0);

        //平移 视图模型矩阵
//        gl.glTranslatef(-3.0f,0.0f,-4.0f);
        gl.glTranslatef(0.0f,0.0f,-100.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置顶点数据
        gl.glVertexPointer(3,GL10.GL_FIXED,0,bufferUtil(traingleArrys));

        //旋转
        gl.glRotatef(angle,0.0f,0.0f,1.0f);
        //放大
//        gl.glScalef(2.0f,2.0f,2.0f);

        //颜色
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4,GL10.GL_FIXED,GL10.GL_TRIANGLES,bufferUtil(colorsArrays));

        //绘制
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        angle+=0.5;
    }
}
