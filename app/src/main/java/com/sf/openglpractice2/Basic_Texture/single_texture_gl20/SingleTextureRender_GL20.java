package com.sf.openglpractice2.Basic_Texture.single_texture_gl20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.sf.opengldemo1.R;
import com.sf.openglpractice2.Basic_Graphic_Shader.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_SRC_COLOR;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/6/6
 * @Des: //TODO
 */
public class SingleTextureRender_GL20 implements GLSurfaceView.Renderer {

    private Context mContext;
    //顶点坐标
    private float[] points = new float[]{
            -1.0f, -1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
    };
    //法线坐标
    private float[] normal = new float[]{
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
    };
    //纹理映射坐标
    private float[] textureCoord = new float[]{
            0.0f, 0.0f,
            0.0f, -1.0f,
            1.0f, 0.0f,
            1.0f, -1.0f,
    };

    private float[] mViewMatrix = new float[16];
    private float[] mProjectMatrix = new float[]{
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f,
    };
    private float[] mMVPMatrix = new float[16];
//            {
//            1f, 0f, 0f, 0f,
//            0f, 1f, 0f, 0f,
//            0f, 0f, 1f, 0f,
//            0f, 0f, 0f, 1f,
//    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureCoordBuffer;

    private int mPositionHandle;
    private int mMatrixHandle;
    private int mTextureCoordHandle;
    private int mTextureUnit;
    private Bitmap mBitmap = null;
    private int[] mTexture;//纹理对象数组
    private int mProgram;

    public SingleTextureRender_GL20(Context context) {
        this.mContext = context;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        mTexture = new int[1];//创建一个纹理对象
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 1.0f, 1.0f, 0.0f);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);
        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//修正函数


        vertexBuffer = (FloatBuffer) BufferUtils.bufferFloatIntUtil(points);
        textureCoordBuffer = (FloatBuffer) BufferUtils.bufferFloatIntUtil(textureCoord);
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertext_shader);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragment_shader);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
        GLES20.glUseProgram(mProgram);

        loadTexture(gl);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
//        Matrix.frustumM(mProjectMatrix,0,-ratio,ratio,-1,1,3,20);
//        Matrix.setLookAtM(mViewMatrix,0,-5.0f,5f,10.0f,0f,0f,0f,0f,1f,0f);
        Matrix.orthoM(mProjectMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f);
//        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        drawAndUseShader(gl);
    }


    /**
     * 获取着色器
     *
     * @param type
     * @param shaderCode
     * @return
     */
    public int loadShader(int type, String shaderCode) {
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }


    private void drawAndUseShader(GL10 gl) {
        GLES20.glUseProgram(mProgram);

        mPositionHandle = getUniform("a_vertex");
        mMatrixHandle = getUniform("u_Matrix");
        mTextureCoordHandle = getAttribute("a_texturecoord");
        mTextureUnit = getUniform("u_TextureUnit");

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mProjectMatrix, 0);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
        //加载顶点坐标
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        //加载纹理坐标
        GLES20.glVertexAttribPointer(mTextureCoordHandle, 3, GLES20.GL_FLOAT, false, 0, textureCoordBuffer);
        //将纹理单元传递片段着色器的u_TextureUnit
        GLES20.glUniform1i(mTextureUnit, 0);
        //绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, points.length / 3);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


    private void loadTexture(GL10 gl) {
        //打开贴图开关
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        //混合开关
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GL_SRC_COLOR);
        //创建纹理贴图对象
        IntBuffer intBuffer = IntBuffer.allocate(1);
        GLES20.glGenTextures(1, intBuffer);
        //获取纹理对象,赋值给数组
        mTexture[0] = intBuffer.get();
        //设置活动纹理单元为 0号纹理单元
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);
        //设置纹理参数,放大缩小，都采用线性变化
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        //使用纹理,单级纹理， 并将图片和纹理合在一起，喂给GPU
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);

    }

    protected int getUniform(String name) {
        return GLES20.glGetUniformLocation(mProgram, name);
    }

    protected int getAttribute(String name) {
        return GLES20.glGetAttribLocation(mProgram, name);
    }

    public static String vertext_shader = "" +
            "uniform mat4  u_Matrix;" +  //变换矩阵
            "attribute vec4 a_vertex;" + //顶点坐标数据
            "attribute vec2 a_texturecoord;" + //纹理坐标数据
//            "attribute vec4 a_normal; "+//法线坐标
            "varying vec2  v_textureCoord;" +// 传递给片元的纹理数据
            "void main(){" +
            "v_texturecoord=a_texturecoord;" +
            "gl_Position=u_Matrix * a_vertex;" +
            "}" +
            "";
    public static String fragment_shader = "" +
            "precision mediump float;" +
            "varying vec2 v_texturecoord;" +
            "uniform sampler2D u_TextureUnit;" +
            "void main(){" +
            "gl_FragColor = texture2D(u_TextureUnit, v_texturecoord);" +
            "}" +
            "";


}
