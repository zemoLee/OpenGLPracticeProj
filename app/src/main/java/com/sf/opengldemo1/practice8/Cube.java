package com.sf.opengldemo1.practice8;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice7.program.SqureShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by 01373577 on 2018/6/13.
 */

/**
 * 立方体
 */
public class Cube {
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;
    private ShortBuffer indexBuffer;
    private float[] mCubeVertexs=new float[]{
            -1.0f,1.0f,1.0f,    //正面左上0
            -1.0f,-1.0f,1.0f,   //正面左下1
            1.0f,-1.0f,1.0f,    //正面右下2
            1.0f,1.0f,1.0f,     //正面右上3
            -1.0f,1.0f,-1.0f,    //反面左上4
            -1.0f,-1.0f,-1.0f,   //反面左下5
            1.0f,-1.0f,-1.0f,    //反面右下6
            1.0f,1.0f,-1.0f,     //反面右上7
    };

    static short index[]={
            0,3,2,0,2,1,    //正面
            0,1,5,0,5,4,    //左面
            0,7,3,0,4,7,    //上面
            6,7,4,6,4,5,    //后面
            6,3,7,6,2,3,    //右面
            6,5,1,6,1,2     //下面
    };
    /**
     * 8个顶点不同颜色
     */
    private float []  color=new float[]{
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
    };
    private static  final int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    private int  mVertexCount=mCubeVertexs.length/COORD_PER_VERTEX;
    public Cube() {
        vertexBuffer= ByteBuffer.allocateDirect(mCubeVertexs.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mCubeVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(color);
        colorBuffer.position(0);
        indexBuffer =ByteBuffer.allocateDirect(index.length*2).order(ByteOrder.nativeOrder()).asShortBuffer().put(index);
        indexBuffer.position(0);
    }

    public void  drawSelf(SqureShaderProgram shaderProgram , float[]  mMatrix){

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,0,vertexBuffer);//步长改为0
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixHandle(),1,false,mMatrix,0);
        //color
        GLES20.glVertexAttribPointer(shaderProgram.getaColorHandle(),4,GLES20.GL_FLOAT,false,0,colorBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorHandle());

        //
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionHandle());
    }
}
