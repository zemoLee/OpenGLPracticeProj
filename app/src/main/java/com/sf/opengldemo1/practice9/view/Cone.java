package com.sf.opengldemo1.practice9.view;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice7.program.SqureShaderProgram;
import com.sf.opengldemo1.practice9.program.ConeShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

/**
 * Created by 01373577 on 2018/6/13.
 */

/**
 * 圆锥体= 锥体部分+底部圆
 */
public class Cone {
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;
    private float[] mConeVertexs;
    /**
     * 顶点颜色
     */
    private final  float[] color=new float[]{1.0f,1.0f,1.0f,1.0f};
    private static  final int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    private int  mVertexCount;

    private float  mConeHight=2.0f;
    private int n=360;
    private float  radius=1.0f;


    public Cone() {
        mConeVertexs= createConeVertexs();
        mVertexCount=mConeVertexs.length/COORD_PER_VERTEX;
        vertexBuffer= ByteBuffer.allocateDirect(mConeVertexs.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mConeVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(color);
        colorBuffer.position(0);

    }

    public void  drawSelf(ConeShaderProgram shaderProgram , float[]  mMatrix){

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);//步长改为0
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixHandle(),1,false,mMatrix,0);
        //color
//        GLES20.glVertexAttribPointer(shaderProgram.getaColorHandle(),4,GLES20.GL_FLOAT,false,vertexStride,colorBuffer);
//        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorHandle());

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,mVertexCount);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionHandle());
    }

    private float[]   createConeVertexs(){
        ArrayList<Float> data=new ArrayList<>();
        data.add(0.0f);
        data.add(0.0f);
        data.add(mConeHight); //圆心离水平面的距离，往上凸起，形成锥面
        float angleDegree=360f/n;
        for(float i=0f;i<360+angleDegree;i=i+angleDegree){
            data.add((float)(radius*Math.sin(i*Math.PI/180f))); //x
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));//y
            data.add(0.0f);//z
        }

        float []  vertexs=new float[data.size()]; // 360*3+ 3
        for (int i=0;i<vertexs.length;i++){
            vertexs[i]=data.get(i);
        }
        mVertexCount=vertexs.length/COORD_PER_VERTEX;

        return  vertexs;
    }
}
