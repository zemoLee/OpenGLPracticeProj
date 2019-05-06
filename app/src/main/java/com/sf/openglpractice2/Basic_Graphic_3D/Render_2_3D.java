package com.sf.openglpractice2.Basic_Graphic_3D;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/29
 * @Des: //TODO
 */
public class Render_2_3D implements GLSurfaceView.Renderer {

    //    private static int X = 0x10000;
//    private int Y = 0x10000;
//    private static int Z = 0x10000;
    static final float X = 0.525731f;
    static final float Z = 0.850651f;
    private float angle = 0;
    /**
     * 顶点数组
     * 一行，就是一个顶点坐标
     * 数组的长度= 顶点的个数*3
     */
    static float vertices[] = new float[]{
//            0,-X, Z,
//            Z, 0.0f, X,
//            Z, 0.0f, -X,
//            -Z, 0.0f, -X,
//            -Z, 0.0f, X,
//            -X, Z, 0,
//            X, -Z, 0,
//            X, Z, 0,
//            -X, -Z, 0,
//            0, -X, -Z,
//            0, X, -Z,
//            0, X, Z,
            0, -X, Z,
            Z, 0.0f, X,
            Z, 0.0f, -X,
            -Z, 0.0f, -X,
            -Z, 0.0f, X,
            -X, Z, 0,
            X, Z, 0,
            X, -Z, 0,
            -X, -Z, 0,
//            0, -X, Z,
            0, -X, -Z,
            0, X, -Z,
            0, X, Z,

    };
    /**
     * 索引数组
     */
    static short indices[] = new short[]{
//            0, 4, 1,
//            0, 9, 4,
//            9, 5, 4,
//            4, 5, 8,
//            4, 8, 1,
//            8, 10, 1,
//            8, 3, 10,
//            5, 3, 8,
//            5, 2, 3,
//            2, 7, 3,
//            7, 10, 3,
//            7, 6, 10,
//            7, 11, 6,
//            11, 0, 6,
//            0, 1, 6,
//            6, 1, 10,
//            9, 0, 11,
//            9, 11, 2,
//            9, 2, 5,
//            7, 2, 11
            1, 2, 6,
            1, 7, 2,
            3, 4, 5,
            4, 3, 8,
            6, 5, 11,
            5, 6, 10,
            9, 10, 2,
            10, 9, 3,
            7, 8, 9,
            8, 7, 0,
            11, 0, 1,
            0, 11, 4,
            6, 2, 10,
            1, 6, 11,
            3, 5, 10,
            5, 4, 11,
            2, 7, 9,
            7, 1, 0,
            3, 9, 8,
            4, 1, 0
    };
    /**
     * 颜色数组
     */
    float[] colors = {
            0f, 0f, 0f,
            1f, 0f, 0f,
            1f, 1f, 0f,
            1f, 0f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            0f, 1f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            0f, 0f, 1f,
            0f, 1f, 0f,
            1f, 0f, 0f,
            1f, 1f, 1f,
            0f, 1f, 1f};

    public Buffer bufferOfIntUtil(int[] arr) {
        IntBuffer mBuffer;
        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }

    public Buffer bufferFloatIntUtil(float[] arr) {
        FloatBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    public Buffer bufferShorIntUtil(short[] arr) {
        ShortBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //修正透视
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NEAREST);
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float radio = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-radio, radio, -1, 1, 1, 1000);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, 0, 0, 3, 0, 0, 0, 0, 1, 0);
        //不停旋转
        gl.glRotatef(angle, 20.0f, 1, 1);
        gl.glScalef(2.0f, 2.0f, 2.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //顶点
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferFloatIntUtil(vertices));
        //颜色
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferFloatIntUtil(colors));
        //索引绘图
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, bufferShorIntUtil(indices));
        angle += 0.5;
    }


}
