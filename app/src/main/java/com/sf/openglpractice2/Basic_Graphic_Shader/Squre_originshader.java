package com.sf.openglpractice2.Basic_Graphic_Shader;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class Squre_originshader {
    /**
     * @Author Jinhuan.Li
     * @method
     * @Params
     * @return 顶点数组
     * @Description: 方法描述：
     */
    final float vertex[] = {
            -1.0f,1.0f,1.0f,    //正面左上0
            -1.0f,-1.0f,1.0f,   //正面左下1
            1.0f,-1.0f,1.0f,    //正面右下2
            1.0f,1.0f,1.0f,     //正面右上3
            -1.0f,1.0f,-1.0f,    //反面左上4
            -1.0f,-1.0f,-1.0f,   //反面左下5
            1.0f,-1.0f,-1.0f,    //反面右下6
            1.0f,1.0f,-1.0f,     //反面右上7
            //左面
//            -0.5f, 0.5f, 0.5f,
//            -0.5f, -0.5f, 0.5f,
//            -0.5f, 0.5f, -0.5f,
//            -0.5f, -0.5f, -0.5f,
//
//            //右面
//            0.5f, 0.5f, 0.5f,
//            0.5f, -0.5f, 0.5f,
//            0.5f, -0.5f, -0.5f,
//            0.5f, 0.5f, -0.5f,
//
//            //前面
//            -0.5f, 0.5f, 0.5f,
//            -0.5f, -0.5f, 0.5f,
//            0.5f, -0.5f, 0.5f,
//            0.5f, 0.5f, 0.5f,
//
//            //后面
//            0.5f, -0.5f, -0.5f,
//            0.5f, 0.5f, -0.5f,
//            -0.5f, 0.5f, -0.5f,
//            -0.5f, -0.5f, -0.5f,
//
//            //上面
//            -0.5f, 0.5f, 0.5f,
//            0.5f, 0.5f, 0.5f,
//            0.5f, 0.5f, -0.5f,
//            -0.5f, 0.5f, -0.5f,
//
//            //下面
//            -0.5f, -0.5f, 0.5f,
//            0.5f, -0.5f, 0.5f,
//            0.5f, -0.5f, -0.5f,
//            -0.5f, -0.5f, -0.5f

    };
    /**
     * @Author Jinhuan.Li
     * @method
     * @Params
     * @return 索引数组
     * @Description: 方法描述：
     */
    final short index[] = {
            6,7,4,6,4,5,    //后面
            6,3,7,6,2,3,    //右面
            6,5,1,6,1,2,    //下面
            0,3,2,0,2,1,    //正面
            0,1,5,0,5,4,    //左面
            0,7,3,0,4,7,    //上面
//            0,1,2,
//            0,2,3,
//
//            4,5,6,
//            4,6,7,
//
//            8,9,10,
//            8,10,11,
//
//            12,13,14,
//            12,14,15,
//
//            16,17,18,
//            16,18,19,
//
//            20,21,22,
//            20,22,23,

    };

    //八个顶点的颜色，与顶点坐标一一对应
    float color[] = {
//            0f,1f,0f,1f,
//            0f,1f,0f,1f,
//            0f,1f,0f,1f,
//            0f,1f,0f,1f,
//            1f,0f,0f,1f,
//            1f,0f,0f,1f,
//            1f,0f,0f,1f,
//            1f,0f,0f,1f,

            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,

            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,

            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,

            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,


            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,

            1f,0f,0f,1f ,
            0f,1f,0f,1f,
            0f,0f,1f,1f,
            1f,0f,0f,1f,

    };
    /**
     * @Author Jinhuan.Li
     * @method
     * @Params
     * @return
     * @Description: 方法描述：初始化
     */
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer nomalBuffer;
    private ShortBuffer indexBuffer;
    public float angleX;
    int vertexCount = 0;
    int indexCount = 0;
    private int mProgram;

    public Squre_originshader() {

        vertexBuffer = (FloatBuffer) BufferUtils.bufferFloatIntUtil(vertex);
        colorBuffer = (FloatBuffer) BufferUtils.bufferFloatIntUtil(color);
        indexBuffer = (ShortBuffer) BufferUtils.bufferShorUtil(index);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);

//        ByteBuffer bb = ByteBuffer.allocateDirect(
//                vertex.length * 4);
//        bb.order(ByteOrder.nativeOrder());
//        vertexBuffer = bb.asFloatBuffer();
//        vertexBuffer.put(vertex);
//        vertexBuffer.position(0);
//
//        ByteBuffer dd = ByteBuffer.allocateDirect(
//                color.length * 4);
//        dd.order(ByteOrder.nativeOrder());
//        colorBuffer = dd.asFloatBuffer();
//        colorBuffer.put(color);
//        colorBuffer.position(0);
//
//        ByteBuffer cc= ByteBuffer.allocateDirect(index.length*2);
//        cc.order(ByteOrder.nativeOrder());
//        indexBuffer=cc.asShortBuffer();
//        indexBuffer.put(index);
//        indexBuffer.position(0);
//        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
//                vertexShaderCode);
//        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
//                fragmentShaderCode);
//        //创建一个空的OpenGLES程序
//        mProgram = GLES20.glCreateProgram();
//        //将顶点着色器加入到程序
//        GLES20.glAttachShader(mProgram, vertexShader);
//        //将片元着色器加入到程序中
//        GLES20.glAttachShader(mProgram, fragmentShader);
//        //连接到着色器程序
//        GLES20.glLinkProgram(mProgram);
    }


    public void drawSelf(GL10 gl, float[] mMVPMatrix) {
        this.mMVPMatrix = mMVPMatrix;
        setShader(gl);
    }

    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandler;
    private float[] mMVPMatrix = new float[16];

    public int loadShader(int type, String shaderCode){
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    private void setShader(GL10 gl) {
        GLES20.glUseProgram(mProgram);
        mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);
        GLES20.glUniform4fv(mColorHandle, 2, color, 0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(mColorHandle,4, GLES20.GL_FLOAT,false, 0,colorBuffer);
        //索引法绘制正方体
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);

//        mPositionHandle= GLES20.glGetAttribLocation(mProgram,"vPosition");
//        mColorHandle=GLES20.glGetAttribLocation(mProgram,"aColor");
//        mMatrixHandler=GLES20.glGetUniformLocation(mProgram,"vMatrix");
//
//        //vertex
//        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);//步长改为0
//        GLES20.glEnableVertexAttribArray(mPositionHandle);
//        //matrix
//        GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);
//        //color
//        GLES20.glEnableVertexAttribArray(mColorHandle);
//        GLES20.glVertexAttribPointer(mColorHandle,4,GLES20.GL_FLOAT,false,0,colorBuffer);
//
//        //
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);
//        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 vMatrix;"+
                    "varying  vec4 vColor;"+
                    "attribute vec4 aColor;"+
                    "void main() {" +
                    "  gl_Position = vMatrix*vPosition;" +
                    "  vColor=aColor;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

}
