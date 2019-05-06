package com.sf.opengldemo1.practice5;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice5.program.ColorTriangleShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class ColorTriangle {
    /**
     * 顶点缓冲
     */
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;
    /**
     * 片元缓冲
     */
    private FloatBuffer mTextureBuffer;
    /**
     * 顶点个数
     */
    static final int COORDS_PER_VERTEX = 3;
    /**
     * 顶点坐标
     */
    static float mVertex[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
    /**
     * 纹理坐标
     */
    float[] mTextureCoord = new float[]{
            0f, 0f,
            0f, 1f,
            1f, 1f,

            1f, 1f,
            1f, 0f,
            0f, 0f
    };

    /**
     * 顶点颜色
     */
    float color[] = {
            0.0f, 1.0f, 0.0f, 1.0f ,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };
    //    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    /**
     * 顶点总个数
     */
    private final int vertexCount = mVertex.length / COORDS_PER_VERTEX;
    /**
     * 步长，顶点之间的偏移量
     */
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    /**
     * 构造
     */
    public ColorTriangle() {
        vertexBuffer = ByteBuffer.allocateDirect(mVertex.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(mVertex);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length * 4).order(ByteOrder.nativeOrder())
                .asFloatBuffer().put(color);
        colorBuffer.position(0);
        //        mTextureBuffer = ByteBuffer.allocateDirect(mTextureCoord.length * 4).order(ByteOrder.nativeOrder())
//                .asFloatBuffer().put(mTextureCoord);
//        mTextureBuffer.position(0);
    }



    public  void  drawSelf(ColorTriangleShaderProgram mProgram,float[] mMVPMatrix){
      //获取句柄,应用数据到句柄
        GLES20.glVertexAttribPointer(mProgram.getPositionAttribHandle(), COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GLES20.glVertexAttribPointer(mProgram.getColorAttribHandle(), 4, GLES20.GL_FLOAT, false, 0, colorBuffer);
        GLES20.glUniformMatrix4fv(mProgram.getMatrixUniformHandle(),1,false,mMVPMatrix,0);

      //启用三角形顶点的句柄 和color句柄
        GLES20.glEnableVertexAttribArray(mProgram.getPositionAttribHandle());
        GLES20.glEnableVertexAttribArray(mProgram.getColorAttribHandle());

        //绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount);

        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mProgram.getPositionAttribHandle());
    }





}
