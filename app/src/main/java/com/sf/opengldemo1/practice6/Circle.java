package com.sf.opengldemo1.practice6;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice6.program.CircleShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by 01373577 on 2018/6/12.
 */

public class Circle {
    /**
     * 顶点缓冲
     */
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    /**
     * 顶点坐标
     */
    private float [] mVertexs;
    /**
     * 一个顶点的坐标个数
     */
    public  static  final  int  COORD_PER_VERTEX=3;
    /**
     * 顶点个数
     */

    public  int  mVertexCount;
    /**
     * 步长
     */
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
    /**
     * 颜色
     */
    private final  float[] color=new float[]{1.0f,1.0f,1.0f,1.0f};
//    float color[] = {
//            0.0f, 1.0f, 0.0f, 1.0f ,
//            1.0f, 0.0f, 0.0f, 1.0f,
//            0.0f, 0.0f, 1.0f, 1.0f
//    };

    private float  radius=1.0f;
    private int  n=360;//360份
    private float  height=0.0f;

    public Circle() {
        mVertexs = createPositions();
        mVertexCount=mVertexs.length/COORD_PER_VERTEX;
        vertexBuffer = ByteBuffer.allocateDirect(mVertexs.length * 4).order(ByteOrder.nativeOrder()) .asFloatBuffer().put(mVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length * 4).order(ByteOrder.nativeOrder()) .asFloatBuffer().put(color);
        colorBuffer.position(0);
    }

    /**
     *绘制圆上的360个顶点
     */
    private float[]  createPositions(){
        ArrayList<Float> data=new ArrayList<>();
        //圆心 x y z
        data.add(0.0f);
        data.add(0.0f);
        data.add(0.0f);

        float angleDegree=360f/n; //每个角度为1 。0~360
        for(float i=0;i<360+angleDegree;i+=angleDegree){
            data.add((float)(radius*Math.sin(i*Math.PI/180f))); //x
            data.add((float)(radius*Math.cos(i*Math.PI/180f)));//y
            data.add(0.0f);//z
        }

        float[] f= new float[data.size()];
        for(int i=0;i<f.length;i++){
            f[i]=data.get(i);
        }
        return  f;

    }


    public  void  drawSelf(CircleShaderProgram shaderProgram, float[]  mMatrix){
        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionAttribHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionAttribHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixUniformHandle(),1,false,mMatrix,0);
        //color
        GLES20.glVertexAttribPointer(shaderProgram.getaColorAttribHandle(),4,GLES20.GL_FLOAT,false,0,colorBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorAttribHandle());


        //color2 作为uniform变量，程序传入顶点或者片元的，一般作为顶点中相同的量，不被shader修改
//      int  mColorHandle=GLES20.glGetUniformLocation(shaderProgram.mProgram, "v_Color");
//        GLES20.glUniform4fv(shaderProgram.getVColorUniformHandle(), 1, color, 0);

        //draw
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mVertexCount);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionAttribHandle());



    }

    public void setRadius(float radius){
        this.radius=radius;
    }
}
