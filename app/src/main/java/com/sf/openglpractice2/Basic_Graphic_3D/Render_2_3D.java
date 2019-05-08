package com.sf.openglpractice2.Basic_Graphic_3D;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/29
 * @Des: //TODO
 */
public class Render_2_3D implements GLSurfaceView.Renderer {

    //    private static int X = 0x10000;
//    private int Y = 0x10000;
//    private static int Z = 0x10000;
    static final float X = 0.525731f;
    static final float Z = 0.850651f;
    private float angle = 0;
    /**
     * 顶点数组
     * 一行，就是一个顶点坐标
     * 数组的长度= 顶点的个数*3
     */
    static float vertices[] = new float[]{
//            0,-X, Z,
//            Z, 0.0f, X,
//            Z, 0.0f, -X,
//            -Z, 0.0f, -X,
//            -Z, 0.0f, X,
//            -X, Z, 0,
//            X, -Z, 0,
//            X, Z, 0,
//            -X, -Z, 0,
//            0, -X, -Z,
//            0, X, -Z,
//            0, X, Z,
            0, -X, Z,
            Z, 0.0f, X,
            Z, 0.0f, -X,
            -Z, 0.0f, -X,
            -Z, 0.0f, X,
            -X, Z, 0,
            X, Z, 0,
            X, -Z, 0,
            -X, -Z, 0,
//            0, -X, Z,
            0, -X, -Z,
            0, X, -Z,
            0, X, Z,

    };

    /**
     * 索引数组
     */
    static short indices[] = new short[]{
//            0, 4, 1,
//            0, 9, 4,
//            9, 5, 4,
//            4, 5, 8,
//            4, 8, 1,
//            8, 10, 1,
//            8, 3, 10,
//            5, 3, 8,
//            5, 2, 3,
//            2, 7, 3,
//            7, 10, 3,
//            7, 6, 10,
//            7, 11, 6,
//            11, 0, 6,
//            0, 1, 6,
//            6, 1, 10,
//            9, 0, 11,
//            9, 11, 2,
//            9, 2, 5,
//            7, 2, 11
            1, 2, 6,
            1, 7, 2,
            3, 4, 5,
            4, 3, 8,
            6, 5, 11,
            5, 6, 10,
            9, 10, 2,
            10, 9, 3,
            7, 8, 9,
            8, 7, 0,
            11, 0, 1,
            0, 11, 4,
            6, 2, 10,
            1, 6, 11,
            3, 5, 10,
            5, 4, 11,
            2, 7, 9,
            7, 1, 0,
            3, 9, 8,
            4, 1, 0
    };
    /**
     * 颜色数组
     */
    float[] colors = {
            0f, 0f, 0f,
            1f, 0f, 0f,
            1f, 1f, 0f,
            1f, 0f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            0f, 1f, 1f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            1f, 1f, 1f,
            1f, 1f, 1f,
            0f, 0f, 1f,
            0f, 1f, 0f,
            1f, 0f, 0f,
            1f, 1f, 1f,
            0f, 1f, 1f};

    public Buffer bufferOfIntUtil(int[] arr) {
        IntBuffer mBuffer;
        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }

    public Buffer bufferOfFloatUtil(float[] arr) {
        FloatBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    public Buffer bufferShorIntUtil(short[] arr) {
        ShortBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    Ball  ball;
    public Render_2_3D( ) {
        ball=new Ball();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //修正透视
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NEAREST);
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glClearColor(0f, 0f, 0f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float radio = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-radio, radio, -1, 1, 1, 1000);

//        gl.glViewport(width / 4, width / 2, width / 2, height / 2);

    }
    float[] point=new float[]{
            0.0f,0.5f,0.0f
    };
    FloatBuffer pointBuffer;
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, 0, 0, 3, 0, 0, 0, 0, 1, 0);
        //不停旋转
        gl.glRotatef(angle, 20.0f, 1, 1);
        gl.glScalef(2.0f, 2.0f, 2.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//        //顶点
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, bufferFloatIntUtil(vertices));
//        //颜色
//        gl.glColorPointer(4, GL10.GL_FLOAT, 0, bufferFloatIntUtil(colors));
//        //索引绘图
//        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, bufferShorIntUtil(indices));



        gl.glClearColor(1f, 1f, 1.0f, 0.0f);
        gl.glPointSize(10.0f);
        gl.glColor4f(0.5f, 0.5f, 0.7f, 1.0f);
        pointBuffer=(FloatBuffer)bufferOfFloatUtil(point);
        gl.glVertexPointer(3 ,GL10.GL_FLOAT ,0, pointBuffer);

        gl.glDrawArrays(GL10.GL_POINTS,0,1);
//        ball.drawSelf(gl);
        angle += 0.5;
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
//        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);


//        pointBuffer=(FloatBuffer)bufferOfFloatUtil(point);
//        // 清除屏幕
//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//
//
//        //设置点的颜色为绿色
//        gl.glColor4f(0f, 1f, 0f, 0f);
//
//        //设置点的大小
//        gl.glPointSize(80f);
//        // 设置顶点
//
//        gl.glTranslatef(0,0,-4);
//        // 允许设置顶点 // GL10.GL_VERTEX_ARRAY顶点数组
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pointBuffer);
//
//        // 绘制点
//        gl.glDrawArrays(GL10.GL_POINTS, 0, 3);
//
//        // 禁止顶点设置
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);





//        gl.glDrawArrays(GL10.GL_POINTS,0,3);
//        ball.drawSelf(gl);
//        angle += 0.5;
//        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
//        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }






    /**
     * 球体
     */
    public class Ball {

        float[] point=new float[]{
                1.0f,1.0f,0.0f
        };
        private FloatBuffer vertexBuffer;  //顶点坐标数据缓冲
        private FloatBuffer nomalBuffer;  //顶点法向量数据缓冲
        private ByteBuffer indexBuffer; //顶点构建索引数据缓冲
        int vertexCount =0;
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：初始化
         */
        FloatBuffer  pointBuffer;
        public  Ball(){
            //1.顶点坐标初始化数据
            ArrayList<Float> alVertex=getVertexList();
            vertexCount =alVertex.size()/3;
            //2.将alVertix中的坐标值转存到一个int数组中
            float[] vertices  =getVertexArrFromList(alVertex);
            //3.创建顶点坐标 数据缓冲
            vertexBuffer= (FloatBuffer) bufferOfFloatUtil(vertices);

            //1.创建顶点法线 数据缓冲
            nomalBuffer=(FloatBuffer) bufferOfFloatUtil(vertices);
            pointBuffer=(FloatBuffer)bufferOfFloatUtil(point);


            //1获取顶点 索引列表 List
//            ArrayList<Float> vertexIndexList=getVertexIndexList();
            //2.获取顶点 索引数组 Arr
//            byte vertexIndexArr []=getVertexIndexArrFromList(vertexIndexList);
//            indexCount =vertexIndexList.size();
            //3.三角形构造数据索引缓冲
//            indexBuffer= (ByteBuffer) bufferOfByteUtil(vertexIndexArr);
        }
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述： 绘制球
         */
        public void  drawSelf(GL10 gl){
            //为画笔指定顶点坐标数据
//            gl.glVertexPointer(
//                    3 , //顶点坐标数量，三个坐标一个顶点
//                    GL10.GL_FLOAT ,  //顶点坐标数据类型
//                    0, //连续顶点之间的数据间隔
//                    vertexBuffer //顶点坐标数据
//            );

            gl.glVertexPointer(
                    3 , //顶点坐标数量，三个坐标一个顶点
                    GL10.GL_FLOAT ,  //顶点坐标数据类型
                    0, //连续顶点之间的数据间隔
                    pointBuffer //顶点坐标数据
            );



//            gl.glNormalPointer(GL10.GL_FLOAT, 0, nomalBuffer);
//            gl.glDrawArrays(GL10.GL_POINT_SMOOTH,0,3);

            gl.glDrawArrays(GL10.GL_POINTS,0,3);
            //绘制图形，索引数组绘图
//            gl.glDrawElements(
//                    GL10.GL_TRIANGLES,  //以三角形的方式填充
//                    indexCount, GL10.GL_UNSIGNED_BYTE, indexBuffer);

        }

        /**
         * 求sin值
         *
         * @param θ 角度值
         * @return sinθ
         */
        private float sin(float θ) {
            return (float) Math.sin(Math.toRadians(θ));
        }

        /**
         * 求cos值
         *
         * @param θ 角度值
         * @return cosθ
         */
        private float cos(float θ) {
            return (float) Math.cos(Math.toRadians(θ));
        }



        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点坐标(x,y,z) 的列表 List
         */
        private ArrayList<Float>  getVertexList(){
            int r=1000;
            int splitCount=20;
            ArrayList<Float> vertixs = new ArrayList<>();// 存放顶点坐标的ArrayList
            final float dθ = 18.0f;// 将球进行单位切分的角度
            //垂直方向angleSpan度一份
            for (float α = -90; α < 90; α = α + dθ) {
                // 水平方向angleSpan度一份
                for (float β = 0; β <= 360; β = β + dθ) {
                    // 纵向横向各到一个角度后计算对应的此点在球面上的坐标
                    float x0 = r * cos(α) * cos(β);
                    float y0 = r * cos(α) * sin(β);
                    float z0 = r * sin(α);
                    float x1 = r * cos(α) * cos(β + dθ);
                    float y1 = r * cos(α) * sin(β + dθ);
                    float z1 = r * sin(α);
                    float x2 = r * cos(α + dθ) * cos(β + dθ);
                    float y2 = r * cos(α + dθ) * sin(β + dθ);
                    float z2 = r * sin(α + dθ);
                    float x3 = r * cos(α + dθ) * cos(β);
                    float y3 = r * cos(α + dθ) * sin(β);
                    float z3 = r * sin(α + dθ);
                    // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                    vertixs.add(x1);vertixs.add(y1);vertixs.add(z1);//p1
                    vertixs.add(x3);vertixs.add(y3);vertixs.add(z3);//p3
                    vertixs.add(x0);vertixs.add(y0);vertixs.add(z0);//p0
                    vertixs.add(x1);vertixs.add(y1);vertixs.add(z1);//p1
                    vertixs.add(x2);vertixs.add(y2);vertixs.add(z2);//p2
                    vertixs.add(x3);vertixs.add(y3);vertixs.add(z3);//p3
                }
            }
            return vertixs;
        }
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点 数组数据，List转 Arr
         */
        private  float[] getVertexArrFromList(List<Float> alVertex){
            //将alVertix中的坐标值转存到一个int数组中
            float vertices []=new float[alVertex.size()];
            for (int i = 0; i < alVertex.size(); i++) {
                vertices[i]=alVertex.get(i);
            }
            return vertices;
        }


    }
}
