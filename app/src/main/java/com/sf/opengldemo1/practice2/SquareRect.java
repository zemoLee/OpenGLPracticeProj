package com.sf.opengldemo1.practice2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class SquareRect {
    private ByteBuffer byteBuffer;
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    static final int RECT_POINT_COUNT = 4;
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    float[] rectVertexs = new float[]{
        -0.5f,0.5f,0.0f,  //左上 0
            -0.5f,-0.5f,0.0f,//坐下 1
            0.5f,-0.5f,0.0f,//右下 2
            0.5f,0.5f,0.0f//右上
    };
    //通过按逆时针顺序为三角形顶点定义坐标来表示这个图形，并将值放入一个 ByteBuffer 中
    ///为了避免由两个三角形重合的那条边的顶点被重复定义，可以使用一个绘制列表（drawing list）来告诉 OpenGL ES 绘制顺序
    private short []  drawOder=new short[]{0,1,2,0,2,3};
    public SquareRect( ) {
        byteBuffer=ByteBuffer.allocateDirect(rectVertexs.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer=byteBuffer.asFloatBuffer();
        vertexBuffer.put(rectVertexs);
        vertexBuffer.position(0);

        //初始化绘制顺序缓冲
        ByteBuffer dlb=ByteBuffer.allocateDirect(drawOder.length*2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer=dlb.asShortBuffer();
        drawListBuffer.put(drawOder);
        drawListBuffer.position(0);
    }
}
