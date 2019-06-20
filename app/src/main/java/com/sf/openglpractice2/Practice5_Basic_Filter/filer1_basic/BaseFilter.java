package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic;

import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES10.GL_SRC_COLOR;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;

public class BaseFilter {
    public static String VERTEX_SHADER = "" +
            "uniform mat4 u_Matrix;" +
            "attribute vec4 a_VertexPos;" +
            "attribute vec2 a_texturePos;" +
            "varying    vec2 v_texturePos;" +
            "void main(){" +
            "v_TexCoord=a_texturePos;" +
            "gl_Position=u_Matrix*a_VertexPos;" +
            "}";
    public static String FRAGMENT_SHADER = "" +
            " precision mediump float;" +
            " varying vec2 v_texturePos; " +
            " uniform sampler2D u_TextureUnit; " +
            " void main() { " +
            "  gl_FragColor = texture2D(u_TextureUnit, v_texturePos); " +
            " } ";

    protected float[] points = new float[]{
            -1.0f, -1.0f,0.0f, //左上
            -1.0f, 1.0f, 0.0f,//左下
            1.0f, 1.0f, 0.0f,//右上
            1.0f, -1.0f,0.0f,//右下
    };
    protected  float[] texcoods=new float[]{
            0f, 1f, //左上
            0f, 0f, //坐下  --原点
            1f, 0f, //右下
            1f, 1f,//右上
    };

    protected FloatBuffer mVertexBuffer;
    protected FloatBuffer mTextureBuffer;
    private int mProgram;

    int mMatrixHandle;
    int mVertexHandle;
    int mTextureHandle;
    int mTextureUnitHandle;
    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];

    public int[] mTexture;//纹理对象数组

    public BaseFilter() {
        mVertexBuffer= (FloatBuffer) BufferUtils.bufferFloatIntUtil(points);
        mTextureBuffer= (FloatBuffer) BufferUtils.bufferFloatIntUtil(texcoods);
//        mTexture = new int[1];//创建一个纹理对象
    }

    public int loadShader(int type, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
    /**
     * 对应render的onCreate
     */
    public void onCreate() {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        GLES20.glUseProgram(mProgram);
        //获取句柄
        initHandle();
        //加载纹理
        initTextureConfig();
    }

    private void initHandle() {
        mMatrixHandle=GLES20.glGetUniformLocation(mProgram,"u_Matrix");
        mVertexHandle=GLES20.glGetAttribLocation(mProgram,"a_VertexPos");
        mTextureHandle=GLES20.glGetAttribLocation(mProgram,"a_texturePos");
        mTextureUnitHandle = GLES20.glGetUniformLocation(mProgram, "u_TextureUnit");

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mMVPMatrix, 0);

        GLES20.glEnableVertexAttribArray(mVertexHandle);
        GLES20.glEnableVertexAttribArray(mTextureHandle);
        //加载顶点坐标
        GLES20.glVertexAttribPointer(mVertexHandle, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        //加载纹理坐标
        GLES20.glVertexAttribPointer(mTextureHandle, 2, GLES20.GL_FLOAT, false, 0, mTextureBuffer);
        //将纹理单元传递片段着色器的u_TextureUnit
        GLES20.glUniform1i(mTextureUnitHandle, 0);

    }


    /**
     * 对应render的change
     */
    public void onSizeChanged(int width,int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectMatrix,0,-ratio,ratio,-1,1,1,20);
        Matrix.setLookAtM(mViewMatrix,0,0.0f,0.0f,2.0f,0f,0f,0f,0f,1f,0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);

    }

    /**
     * 对应render的draw
     */
    public void onDraw() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //创建纹理
//        IntBuffer intBuffer = IntBuffer.allocate(1);
//        GLES20.glGenTextures(1, intBuffer);
//        //获取纹理对象,赋值给数组
//        mTexture[0] = intBuffer.get();
        //绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, points.length / 3);

    }
    /**
     * 对应render的onDestroy
     */
    public void onDestroy() {
        GLES20.glDeleteProgram(mProgram);
        mProgram = 0;
    }


    /**
     * 加载纹理配置
     */
    private void initTextureConfig() {
        //打开贴图开关
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        //混合开关
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    }
}
