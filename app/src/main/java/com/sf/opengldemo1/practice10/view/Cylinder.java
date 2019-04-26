package com.sf.opengldemo1.practice10.view;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice10.program.CylinderShaderProgram;
import com.sf.opengldemo1.practice7.program.SqureShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

/**
 * Created by 01373577 on 2018/6/13.
 */

/**
 * 圆柱体
 */
public class Cylinder {
    private FloatBuffer  vertexBuffer;
    private FloatBuffer  colorBuffer;
    private float[] mCubeVertexs;
    /**
     * 颜色
     */
    private final  float[] color=new float[]{1.0f,1.0f,1.0f,1.0f};
    private static  final int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    private int  mVertexCount;
    public Cylinder() {
        mCubeVertexs=  createCylinderVertexs();
        vertexBuffer= ByteBuffer.allocateDirect(mCubeVertexs.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(mCubeVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(color);
        colorBuffer.position(0);

    }

    public void  drawSelf(CylinderShaderProgram shaderProgram , float[]  mMatrix){

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,0,vertexBuffer);//步长改为0
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixHandle(),1,false,mMatrix,0);
        //color
//        GLES20.glVertexAttribPointer(shaderProgram.getaColorHandle(),4,GLES20.GL_FLOAT,false,0,colorBuffer);
//        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorHandle());

        //
//        GLES20.glDrawElements(GLES20.GL_TRIANGLES);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,mVertexCount);//GL_TRIANGLE_STRIP，将传入的顶点作为三角条带绘制，绘成三角带
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionHandle());
    }

    private  int  n=360;
    private  float  radius=1.0f;
    private  float  hight=3.0f;
    private  float[]  createCylinderVertexs(){
        ArrayList<Float>  data=new ArrayList<>();
        float angleDegree=360f/n; //每个角度为1 。0~360
        for(float i=0;i<360+angleDegree;i=i+angleDegree){
            //顶圆上每个顶点坐标，带高度
            data.add((float) (radius*Math.sin(i*Math.PI/180f)));
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));
            data.add(hight);
            //底圆上每个顶点坐标，高度为0
            data.add((float) (radius*Math.sin(i*Math.PI/180f)));
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));
            data.add(0.0f);
        }

        float []  vertexs=new float[data.size()];
        for (int i=0;i<vertexs.length;i++){
            vertexs[i]=data.get(i);
        }
        mVertexCount=vertexs.length/COORD_PER_VERTEX;
        return  vertexs;
    }

}
