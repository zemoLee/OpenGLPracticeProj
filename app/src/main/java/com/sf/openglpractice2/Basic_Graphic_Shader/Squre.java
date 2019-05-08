package com.sf.openglpractice2.Basic_Graphic_Shader;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class Squre {
    /**
     * @Author  Jinhuan.Li
     * @method
     * @Params
     * @return   顶点数组
     * @Description: 方法描述：
     */
    final float vertexArr[] = {
            -1.0f,1.0f,1.0f,    //正面左上0
            -1.0f,-1.0f,1.0f,   //正面左下1
            1.0f,-1.0f,1.0f,    //正面右下2
            1.0f,1.0f,1.0f,     //正面右上3
            -1.0f,1.0f,-1.0f,    //反面左上4
            -1.0f,-1.0f,-1.0f,   //反面左下5
            1.0f,-1.0f,-1.0f,    //反面右下6
            1.0f,1.0f,-1.0f,     //反面右上7
    };
    /**
     * @Author  Jinhuan.Li
     * @method
     * @Params
     * @return   索引数组
     * @Description: 方法描述：
     */
    final short indexArr[]={
            0,3,2,0,2,1,    //正面
            0,1,5,0,5,4,    //左面
            0,7,3,0,4,7,    //上面
            6,7,4,6,4,5,    //后面
            6,3,7,6,2,3,    //右面
            6,5,1,6,1,2     //下面
    };

    //八个顶点的颜色，与顶点坐标一一对应
    float colorArr[] = {
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
    };
    /**
     * @Author  Jinhuan.Li
     * @method
     * @Params
     * @return
     * @Description: 方法描述：初始化
     */
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer nomalBuffer;
    private ShortBuffer indexBuffer;
    public float angle;
    int vertexCount =0;
    int indexCount =0;
    public Squre() {
        vertexBuffer= (FloatBuffer) BufferUtils.bufferFloatIntUtil(vertexArr);
        colorBuffer= (FloatBuffer) BufferUtils.bufferFloatIntUtil(colorArr);
        indexBuffer= (ShortBuffer) BufferUtils.bufferShorUtil(indexArr);

    }



    public void  drawSelf(GL10 gl){
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexBuffer);
        gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);
        gl.glDrawElements(GL10.GL_TRIANGLES,indexArr.length,GL10.GL_UNSIGNED_SHORT,indexBuffer);
        gl.glRotatef(angle,1,1,1.0f);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        angle+=0.5;

    }






}
