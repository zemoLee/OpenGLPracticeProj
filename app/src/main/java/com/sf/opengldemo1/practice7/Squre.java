package com.sf.opengldemo1.practice7;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice7.program.SqureShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Squre {
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;
    private ShortBuffer indexBuffer;
    private float[] mVertexs=new float[]{
            -0.5f,  0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f,  0.5f, 0.0f  // top right
    };

    static short index[]={
            0,1,2,0,2,3
    };

    private float []  color=new float[]{
      1.0f,1.0f,1.0f,1.0f
    };
    private static  final int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    private int  mVertexCount=mVertexs.length/COORD_PER_VERTEX;
    public Squre() {
        vertexBuffer= ByteBuffer.allocateDirect(mVertexs.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(color);
        colorBuffer.position(0);
        indexBuffer =ByteBuffer.allocateDirect(index.length*2).order(ByteOrder.nativeOrder()).asShortBuffer().put(index);
        indexBuffer.position(0);
    }

    public void  drawSelf(SqureShaderProgram shaderProgram , float[]  mMatrix){

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixHandle(),1,false,mMatrix,0);
        //color
        GLES20.glVertexAttribPointer(shaderProgram.getaColorHandle(),4,GLES20.GL_FLOAT,false,4,colorBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorHandle());

//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mVertexCount);
        //索引法绘制，避免大量重复的点，根据索引序列，在顶点序列中找到对应的顶点，并根据绘制的方式，组成相应的图元进行绘制
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionHandle());
    }
}
