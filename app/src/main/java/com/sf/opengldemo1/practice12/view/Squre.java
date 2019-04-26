package com.sf.opengldemo1.practice12.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

import com.sf.opengldemo1.R;
import com.sf.opengldemo1.practice12.helper.TextureHelper;
import com.sf.opengldemo1.practice12.program.SqureShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by 01373577 on 2018/6/13.
 */
public class Squre {
    private Context context;
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;

    private float[] mVertexs=new float[]{  //123  134
//            -0.5f,  0.5f, 0.0f, // top left
//            -0.5f, -0.5f, 0.0f, // bottom left
//            0.5f, -0.5f, 0.0f, // bottom right
//            0.5f,  0.5f, 0.0f  // top right
            -1.0f,1.0f,0.0f,    //左上角  //  N字形模式，绘制三角带 triangle_strip
            -1.0f,-1.0f,0.0f,   //左下角
            1.0f,1.0f,0.0f, //右上角
            1.0f,-1.0f,0.0f     //右下角
    };
    /**
     * 纹理缓冲
     */
    private FloatBuffer  coordBuffer;

    /**
     * 纹理坐标  纹理坐标的左上角为0 ，0 ，0
     */
    private float[]  mCoordinates=new float[]{
            0.0f,0.0f,
            0.0f,1.0f,
            1.0f,0.0f,
            1.0f,1.0f,
    };


    private float []  color=new float[]{
      1.0f,1.0f,1.0f,1.0f
    };
    private static  final int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    private int  mVertexCount=mVertexs.length/COORD_PER_VERTEX;
    public Squre(Context context) {
        this.context=context;
        vertexBuffer= ByteBuffer.allocateDirect(mVertexs.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(color);
        colorBuffer.position(0);
        //纹理
        coordBuffer= ByteBuffer.allocateDirect(mCoordinates.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mCoordinates);
        coordBuffer.position(0);
    }

    public void  drawSelf(SqureShaderProgram shaderProgram , float[]  mMatrix){
        createTexture(context,mBitmap);

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionHandle());
        //纹理坐标handle
        GLES20.glVertexAttribPointer(shaderProgram.getaCoordinateHandle(),2,GLES20.GL_FLOAT,false,0,coordBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaCoordinateHandle());
        //纹理取样器handle
        GLES20.glUniform1i(shaderProgram.getuTextureHandle(), 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixHandle(),1,false,mMatrix,0);
        //color
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mVertexCount);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionHandle());
    }


//    private int mResources=R.drawable.img_loadings;
    private int textureId;
    private void  createTexture(Context context,Bitmap mBitmap){
        textureId = TextureHelper.createTexture(mBitmap);
    }

    private Bitmap mBitmap;
    public void setBitmap(Bitmap bitmap){
        this.mBitmap=bitmap;
    }
}
