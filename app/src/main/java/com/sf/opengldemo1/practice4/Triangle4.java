package com.sf.opengldemo1.practice4;

/**
 * Created by 01373577 on 2018/6/6.
 */

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangle4 {

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;"+  //变换矩阵
                    "attribute vec4 vPosition;" +  // 应用程序传入顶点着色器的顶点位置 vPosition
                    "void main() {" +
                    "  gl_Position = uMVPMatrix*vPosition;" + // 设置此次绘制此顶点位置 * 变换矩阵， 可以动态计算出对应的Position
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +  // 设置工作精度
                    "uniform vec4 vColor;" +  // 应用程序传入着色器的颜色变量    vColor
                    "void main() {" +
                    "  gl_FragColor = vColor;" + // 颜色值传给 gl_FragColor内建变量，完成片元的着色
                    "}";
    static final int TRIANGLE_VERTEX_COUNT = 3;

//    private ByteBuffer byteBuffer;//绘制顺序缓冲区
    private FloatBuffer vertexBuffer;//顶点的缓冲
    static float triangleVertexs[] = new float[]{ //逆时针顺序定义
            0.0f, 0.622008459f, 0.0f,  // top
            -0.5f, -0.311004243f, 0.0f, // bottom r
            0.5f, -0.311004243f, 0.0f   // bottom l
    };
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private final int mProgram;

    private final int vertexCount = triangleVertexs.length / TRIANGLE_VERTEX_COUNT; //顶点总个数= 顶点集合长度/每个顶点包含的3个坐标个数
    private final int vertexStride = TRIANGLE_VERTEX_COUNT * 4;//渲染时buffer中顶点的步长，4为字节数

    public Triangle4() {
        ByteBuffer  byteBuffer = ByteBuffer.allocateDirect(triangleVertexs.length * 4); //一个float  4个字节
        byteBuffer.order(ByteOrder.nativeOrder());  // 设置缓冲区使用设备硬件的原本字节顺序进行读取;
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(triangleVertexs);
        vertexBuffer.position(0);


        //加载编译顶点着色器
        int vertexShader = PracticeRender4.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        //加载编译偏于着色器
        int fragmentShader = PracticeRender4.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建 opengl program
        mProgram = GLES20.glCreateProgram();
        //添加顶点着色器和片元着色器到 program
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        //创建可执行程序
        GLES20.glLinkProgram(mProgram);
    }



    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandle;
    //调用opengl 相关接口，告诉 它的渲染流程管道程序，如何绘制以及绘制的内容是什么
    public void draw(float[] mvpMatrix) {
        //添加program到Opengl 的环境中
        GLES20.glUseProgram(mProgram);


        //2:顶点着色器：获取顶点着色器中成员vPosition的 handle  句柄资源
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //将handle作用于顶点着色器， 绑定开启
        GLES20.glEnableVertexAttribArray(mPositionHandle); //使用数据
        //载入顶点坐标缓存资源
        GLES20.glVertexAttribPointer(mPositionHandle, TRIANGLE_VERTEX_COUNT, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        //3:片元着色器：获取片元着色器中的成员vColor的handler
        mColorHandle=GLES20.glGetUniformLocation(mProgram,"vColor");
        //将color资源传递载入给片元着色器
        GLES20.glUniform4fv(mColorHandle,1,color,0);//参数 location  ,count  颜色数组,offset

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        //1:变换矩阵变量的handle获取
        mMatrixHandle=  GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        //将投影和视图转换的矩阵 传递给着色器//指定mvpMatrix的值
        GLES20.glUniformMatrix4fv(mMatrixHandle,1,false,mvpMatrix,0);

        //绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);

        //释放。禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }


}
