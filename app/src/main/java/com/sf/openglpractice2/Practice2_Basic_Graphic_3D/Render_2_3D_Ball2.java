package com.sf.openglpractice2.Practice2_Basic_Graphic_3D;

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
 * @Des: //绘制球
 */
public class Render_2_3D_Ball2 implements GLSurfaceView.Renderer {
    private float angle = 0;

    Ball ball;
    GL10 gl;
    public Render_2_3D_Ball2() {
        ball = new Ball();
    }

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

    public Buffer bufferOfByteUtil(  byte[] arr) {

        ByteBuffer   byteBuffer=ByteBuffer.allocateDirect(arr.length);  //由于indices是byte型的，索引不用乘以4
//        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(arr);
        byteBuffer.position(0);
        return byteBuffer;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.gl=gl;
        //修正透视
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NEAREST);
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 复位深度缓存
        gl.glClearDepthf(1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float radio = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-radio, radio, -1, 1, 1, 1000);
//        GLU.gluPerspective(gl, 90.0f, (float) width / height, 0.1f, 50.0f);
    }

    float[] colors = {0.1f, 0.1f, 0.1f, 0.1f};
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 3, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(angle, 1.0f, 1, 1);
        gl.glScalef(2.0f, 2.0f, 2.0f);
        gl.glTranslatef(0, 0, -1.8f);  //把坐标系往z轴负方向平移2.0f个单位

        setLight(gl);
        setMeterial(gl);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

//        ball.draw2(gl);
        ball.drawSelf(gl);
//        ball.draw(gl);
        angle += 0.5f;

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

    }



    /**
     * 设置光源参数
     *
     * @param gl
     */
    private void setLight(GL10 gl) {
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);//只给光到这的话，已经可以看见球，但是看不见阴影变化，也就是旋转的时候，会根据环境光进行阴影变化
        //蓝光
        FloatBuffer lightAmbient = FloatBuffer.wrap(new float[]{0.2f, 0.3f, 0.4f, 1.0f});
        FloatBuffer lightDiffuse = FloatBuffer.wrap(new float[]{0.4f, 0.6f, 0.8f, 1.0f});
        FloatBuffer lightSpecular = FloatBuffer.wrap(new float[]{0.2f * 0.4f, 0.2f * 0.6f, 0.2f * 0.8f, 1.0f});
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, lightSpecular);
        //光位置
        FloatBuffer lightPosition = FloatBuffer.wrap(new float[]{10.0f, 10.0f, 10.0f, 0.0f});
        //光方向：朝屏幕里面
        FloatBuffer lightDirect = FloatBuffer.wrap(new float[]{0.0f, 0.0f, -1.0f});
        //光角度
        float lightAngle = 45.0f;
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPosition);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, lightDirect);
        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, lightAngle);

    }

    /**
     * 设置材质相关参数
     *
     * @param gl
     */
    private void setMeterial(GL10 gl) {
        //材质环境：蓝
        FloatBuffer meterialAmbient = FloatBuffer.wrap(new float[]{0.2f, 0.3f, 0.4f, 1.0f});
        //材质散射：红
        FloatBuffer meterialDiffuse = FloatBuffer.wrap(new float[]{0.4f, 0.6f, 0.8f, 1.0f});
        //材质高光：
        FloatBuffer meterialSpecular = FloatBuffer.wrap(new float[]{0.2f * 0.4f, 0.2f * 0.6f, 0.2f * 0.8f, 1.0f});
        //自发光：绿
        FloatBuffer meterialEmission = FloatBuffer.wrap(new float[]{0.0f, 0.4f, 0.0f, 1.0f});

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, meterialAmbient);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, meterialDiffuse);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, meterialSpecular);
        //设置了高光，所以高光比较特殊，还有个特殊因素，反射程度，也是材质表面的粗糙度
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 90.0f);

        //自发光
//        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_EMISSION,meterialEmission);
    }



    /**
     * 球体
     */
    public class Ball {
        private IntBuffer vertexBuffer;  //顶点坐标数据缓冲
        private IntBuffer nomalBuffer;  //顶点法向量数据缓冲
        private ByteBuffer indexBuffer; //顶点构建索引数据缓冲
        public float angleX;  //沿x轴旋转角度
        int vertexCount =0;
        int indexCount =0;
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：初始化
         */
        public  Ball(){
            //1.顶点坐标初始化数据
            ArrayList<Integer> alVertex=getVertexList();
            vertexCount =alVertex.size()/3;  //顶点数量为坐标值数量的三分之一，因为一个顶点有三个坐标
            //2.将alVertix中的坐标值转存到一个int数组中
            int[] vertices  =getVertexArrFromList(alVertex);
            //3.创建顶点坐标 数据缓冲
             vertexBuffer= (IntBuffer) bufferOfIntUtil(vertices);

            //1.创建顶点法线 数据缓冲
             nomalBuffer=(IntBuffer) bufferOfIntUtil(vertices);

            //1获取顶点 索引列表 List
            ArrayList<Integer> vertexIndexList=getVertexIndexList();
            //2.获取顶点 索引数组 Arr
            byte vertexIndexArr []=getVertexIndexArrFromList(vertexIndexList);
            indexCount =vertexIndexList.size();
            //3.三角形构造数据索引缓冲
            indexBuffer= (ByteBuffer) bufferOfByteUtil(vertexIndexArr);
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
            gl.glVertexPointer(
                    3 , //顶点坐标数量，三个坐标一个顶点
                    GL10.GL_FIXED ,  //顶点坐标数据类型
                    0, //连续顶点之间的数据间隔
                    vertexBuffer //顶点坐标数据
            );
            //为画笔指定顶点法线向量数据
            gl.glNormalPointer(GL10.GL_FIXED, 0, nomalBuffer);
            //绘制图形，索引数组绘图
            gl.glDrawElements(
                    GL10.GL_TRIANGLES,  //以三角形的方式填充
                    indexCount, GL10.GL_UNSIGNED_BYTE, indexBuffer);

        }


        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点坐标(x,y,z) 的列表 List
         */
        final int angleSpan=20;
        private ArrayList<Integer>  getVertexList(){
            final int UNIT_SIZE=10000;
            int scale=10;
            ArrayList<Integer> alVertex=new ArrayList<Integer>();

            for (int vAngle = -90; vAngle <= 90; vAngle=vAngle+angleSpan) {  //垂直方向angleSpan度一份
                for (int hAngle = 0; hAngle <360; hAngle=hAngle+angleSpan ) { //水平方向angleSpan度一份
                    //纵向横向各到一个角度后计算对应的此点在球面上的坐标
                    double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
                    int x=(int) (xozLength*Math.cos(Math.toRadians(hAngle)));
                    int y=(int) (xozLength*Math.sin(Math.toRadians(hAngle))) ;
                    int z=(int) (scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
                    alVertex.add(x);
                    alVertex.add(y);
                    alVertex.add(z);
                }
            }
            return alVertex;
        }
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点 数组数据，List转 Arr
         */
        private  int[] getVertexArrFromList(List<Integer> alVertex){
            //将alVertix中的坐标值转存到一个int数组中
            int vertices []=new int[alVertex.size()];
            for (int i = 0; i < alVertex.size(); i++) {
                vertices[i]=alVertex.get(i);
            }
            return vertices;
        }


        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点索引列表List
         */
        private   ArrayList<Integer> getVertexIndexList(){
            ArrayList<Integer> alIndex=new ArrayList<Integer>();
            int row=(180/angleSpan)+1; //球面切分的行数
            int col=360/angleSpan;  //球面切分的列数
            for (int i = 0; i < row; i++) {  //对每一行循环
                if(i>0 && i<row-1){
                    //中间行
                    for (int j = -1; j < col; j++) {
                        //中间行的两个相邻点与下一行的对应点构成三角形
                        int k=i*col+j;
                        alIndex.add(k+col);
                        alIndex.add(k+1);
                        alIndex.add(k);
                    }
                    for (int j = 0; j < col+1; j++) {
                        //中间行的两个相邻点与上一行的对应点构成三角形
                        int k=i*col+j;
                        alIndex.add(k-col);
                        alIndex.add(k-1);
                        alIndex.add(k);
                    }
                }
            }
            return alIndex;
        }
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述：获取顶点索引数组Arr ,由List转化Arr
         */
        private byte  [] getVertexIndexArrFromList(List<Integer> vertexIndexs){
            byte vertexIndexArr []=new byte[vertexIndexs.size()];
            for (int i = 0; i < vertexIndexs.size(); i++) {
                vertexIndexArr[i]=vertexIndexs.get(i).byteValue();
            }
            return vertexIndexArr;
        }


    }

}
