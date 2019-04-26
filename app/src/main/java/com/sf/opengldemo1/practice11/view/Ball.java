package com.sf.opengldemo1.practice11.view;

import android.opengl.GLES20;

import com.sf.opengldemo1.practice10.program.CircleShaderProgram;
import com.sf.opengldemo1.practice11.program.BallShaderProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by 01373577 on 2018/6/14.
 */

public class Ball {
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




    public Ball() {
        this(0.0f);
    }


    public Ball(float  centerHight) {
        mVertexs = createBallPositions();
        vertexBuffer = ByteBuffer.allocateDirect(mVertexs.length * 4).order(ByteOrder.nativeOrder()) .asFloatBuffer().put(mVertexs);
        vertexBuffer.position(0);
        colorBuffer=ByteBuffer.allocateDirect(color.length * 4).order(ByteOrder.nativeOrder()) .asFloatBuffer().put(color);
        colorBuffer.position(0);
    }

    /**
     */
    private float step=5f;
    private float[]  createBallPositions(){
        ArrayList<Float> data=new ArrayList<>();
        float  r1,r2;
        float  h1,h2;
        float sin,cos;

        for(float i=-90;i<90+step;i=i+step){  //从下半球到上半球。 step为间隔维度
            r1 = (float)Math.cos(i * Math.PI / 180.0);
            r2 = (float)Math.cos((i + step) * Math.PI / 180.0);
            h1 = (float)Math.sin(i * Math.PI / 180.0);
            h2 = (float)Math.sin((i + step) * Math.PI / 180.0);
         float step2=step*2; // 固定纬度, 360 度旋转遍历一条纬线
         for(float j=0.0f;j<360f+step;j+=step2){
             cos = (float) Math.cos(j * Math.PI / 180.0);
             sin = -(float) Math.sin(j * Math.PI / 180.0);
             data.add(r2 * cos);
             data.add(h2);
             data.add(r2 * sin);
             data.add(r1 * cos);
             data.add(h1);
             data.add(r1 * sin);
         }

        }
        float[] f=new float[data.size()];
        for(int i=0;i<f.length;i++){
            f[i]=data.get(i);
        }
        mVertexCount=f.length/COORD_PER_VERTEX;
        return  f;

    }


    public  void  drawSelf(BallShaderProgram shaderProgram, float[]  mMatrix){

        //vertex
        GLES20.glVertexAttribPointer(shaderProgram.getaPositionAttribHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride,vertexBuffer);
        GLES20.glEnableVertexAttribArray(shaderProgram.getaPositionAttribHandle());
        //matrix
        GLES20.glUniformMatrix4fv(shaderProgram.getuMatrixUniformHandle(),1,false,mMatrix,0);
        //color
//        GLES20.glVertexAttribPointer(shaderProgram.getaColorAttribHandle(),4,GLES20.GL_FLOAT,false,vertexStride,colorBuffer);
//        GLES20.glEnableVertexAttribArray(shaderProgram.getaColorAttribHandle());


        //draw
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mVertexCount);
        GLES20.glDisableVertexAttribArray(shaderProgram.getaPositionAttribHandle());



    }

}
