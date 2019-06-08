package com.sf.openglpractice2.Basic_Texture.single_texture_gl10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

import com.sf.opengldemo1.R;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES10.GL_SRC_COLOR;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/6/6
 * @Des: //TODO
 */
public class SingleTextureRender_GL10 implements GLSurfaceView.Renderer {

    private Bitmap  mBitmap=null;
    private int[] mTexture;//纹理对象数组

    //顶点坐标
    private float[] points=new float[]{
        -1.0f,-1.0f,0.0f,
         -1.0f,1.0f,0.0f,
            1.0f,-1.0f,0.0f,
            1.0f,1.0f,0.0f,
    };
    //法线坐标
    private float[] normal=new float[]{
            0.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,
    };
    //纹理映射坐标
    private float[]  textureCoord=new float[]{
            0.0f,0.0f,
            0.0f,-1.0f,
            1.0f,0.0f,
            1.0f,-1.0f,
    };

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

    public Buffer bufferOfFloatUtil(float[] arr) {
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

    public Buffer bufferOfByteUtil(  byte[] arr) {

        ByteBuffer   byteBuffer=ByteBuffer.allocateDirect(arr.length);  //由于indices是byte型的，索引不用乘以4
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(arr);
        byteBuffer.position(0);
        return byteBuffer;
    }

    public SingleTextureRender_GL10(Context context) {
        mBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.test);
        mTexture=new int[1];//创建一个纹理对象
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(1.0f,1.0f,1.0f,0.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        loadTexture(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float radio=width/height;
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL10.GL_PROJECTION);//镜头
        gl.glLoadIdentity();
        gl.glFrustumf(-radio,radio,-1,1,3,100);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);// 摆放模型
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0,0,3,0,0,0,0,1,0);
        gl.glTranslatef(-0,0,-1);
//        loadTexture(gl);
        drawSqureAndTexture(gl);
    }

    private void  drawSqureAndTexture(GL10 gl){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //加载顶点数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,bufferOfFloatUtil(points));
        //加载法线数据
        gl.glNormalPointer(GL10.GL_FLOAT,0,bufferOfFloatUtil(normal));
        //加载纹理坐标
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,bufferOfFloatUtil(textureCoord));
        //绘制
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    private void  loadTexture(GL10 gl){
        //打开贴图开关
        gl.glEnable(GL10.GL_TEXTURE_2D);
        //混合开关
        gl.glEnable(GL10.GL_BLEND);
        gl. glBlendFunc(GL10.GL_ONE,GL_SRC_COLOR);
        //创建纹理贴图对象
        IntBuffer  intBuffer=IntBuffer.allocate(1);
        gl.glGenTextures(1,intBuffer);
        //获取纹理对象,赋值给数组
        mTexture[0]=intBuffer.get();

        //绑定纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D,mTexture[0]);
        //设置纹理参数,放大缩小，都采用线性变化
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        //使用纹理,单级纹理， 并将图片和纹理合在一起，喂给GPU
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,mBitmap,0);

    }
}
