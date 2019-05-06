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
 * @Des: //绘制球
 */
public class Render_2_3D_Ball implements GLSurfaceView.Renderer {
    private float angle = 0;

    Ball ball;
    GL10 gl;
    public Render_2_3D_Ball() {
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

    public Buffer bufferFloatIntUtil(float[] arr) {
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
//        gl.glFrustumf(-radio, radio, -1, 1, 1, 1000);
        GLU.gluPerspective(gl, 90.0f, (float) width / height, 0.1f, 50.0f);
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

        setLight(gl);
        setMeterial(gl);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        ball.draw2(gl);
//        ball.draw(gl);
        angle += 0.5f;

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
//        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
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
//        gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, lightAngle);

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
        public void draw(GL10 gl) {
            float theta, pai;
            float co, si;
            float r1, r2;
            float h1, h2;
            /**
             * 步长为2.0f
             */
            float step = 2.0f;
            /**
             * 顶点数组,总共32个顶点，每个顶点3个坐标值
             */
            float[][] v = new float[32][3];
            /**
             * 顶点   ByteBuffer
             */
            ByteBuffer vbb;
            /**
             * 顶点 的最终Buffer :ByteBuffer.as...转的
             */
            FloatBuffer vBuf;
            vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
            vbb.order(ByteOrder.nativeOrder());
            vBuf = vbb.asFloatBuffer();
            for (pai = -90f; pai <=90.0f; pai += step) {
                int n = 0;
                r1 = (float) Math.cos(pai * Math.PI / 180.0);
                r2 = (float) Math.cos((pai + step) * Math.PI / 180.0);
                h1 = (float) Math.sin(pai * Math.PI / 180.0);
                h2 = (float) Math.sin((pai + step) * Math.PI / 180.0);
                for (theta = 0.0f; theta <= 360.0f; theta += step) {
                    co = (float) Math.cos(theta * Math.PI / 180.0);
                    si = -(float) Math.sin(theta * Math.PI / 180.0);
                    v[n][0] = (r2 * co);
                    v[n][1] = (h2);
                    v[n][2] = (r2 * si);
                    v[n + 1][0] = (r1 * co);
                    v[n + 1][1] = (h1);
                    v[n + 1][2] = (r1 * si);
                    vBuf.put(v[n]);
                    vBuf.put(v[n + 1]);
                    n += 2;
                    if (n > 31) {
                        vBuf.position(0);
                        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                        gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                        n = 0;
                        theta -= step;
                    }
                }
                vBuf.position(0);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
                gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
            }
        }
        /**
         * 用顶点索引数组构造圆
         */
        private void  getIndexArray(){
            ArrayList<Integer> alIndex=new ArrayList<Integer>();
            int row=(180/20)+1; //球面切分的行数，切9次，但是是出来10份
            int col=360/20;  //球面切分的列数
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
            int  iCount=alIndex.size();
            byte indices []=new byte[iCount];
            for (int i = 0; i < iCount; i++) {
                indices[i]=alIndex.get(i).byteValue();
            }
        }




        private static final int COORDS_PER_VERTEX = 3;

        public void draw2(GL10 gl) {
            //顶点的数量= 坐标值数量的1/3，因为一个顶点有3个坐标
            int vertexCount = getBallVertexs().size()/COORDS_PER_VERTEX;
            //顶点数组=顶点个数*3
            float vertices[] = getVertexs(getBallVertexs());
            //顶点坐标buffer
            FloatBuffer vertexBuffer = (FloatBuffer) bufferFloatIntUtil(vertices);
            //顶点法向量buffer
            FloatBuffer normalBuffer = (FloatBuffer) bufferFloatIntUtil(vertices);

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexCount);
        }



        /**
         * 获取圆上所有顶点坐标List
         *
         * @return
         */
        private List<Float> getBallVertexs() {
            List<Float> vertex = new ArrayList<>();
            float step = 20.0f;
            float radius = 10.0f;
            //横切
            for (float rowAngel = -90; rowAngel <= 90; rowAngel += step) {
                //按弧度切
                for (float colAngel = 0; colAngel < 360.0f; colAngel += step) {
                    double xozLength = radius * Math.cos(Math.toRadians(rowAngel));
                    float x = (int) (xozLength * Math.cos(Math.toRadians(colAngel)));
                    float y = (int) (xozLength * Math.sin(Math.toRadians(colAngel)));
                    float z = (int) (radius * Math.sin(Math.toRadians(rowAngel)));
                    vertex.add(x);
                    vertex.add(y);
                    vertex.add(z);
                }
            }
            return vertex;
        }

        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述： 获取所有顶点坐标数组
         */
        private float[]  getVertexs(List<Float> vertexList){
            //顶点数组=顶点个数
            float vertices[] = new float[vertexList.size()];
            for (int i = 0; i <getBallVertexs().size(); i++) {
                vertices[i] = getBallVertexs().get(i);
            }
            return  vertices;
        }

        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述： 获取顶点Buffer
         */
        private  FloatBuffer getVertexBuffer(float vertices[]){
            FloatBuffer  vertexBuffer= (FloatBuffer) bufferFloatIntUtil(vertices);
            return vertexBuffer;
        }

        /**
         *  获取顶点索引序列号集合List
         * @param vertexList
         * @return
         */
        private float[] getVertxIndexs(List<Float> vertexList){
            float[] data_vertex = new float[vertexList.size()];
            for (int i = 0; i < vertexList.size(); i++) {
                data_vertex[i] = vertexList.get(i);
            }
            return data_vertex;
        }
        /**
         * @Author  Jinhuan.Li
         * @method
         * @Params
         * @return
         * @Description: 方法描述： 获取索引缓存Buffer
         */
        private FloatBuffer getIndexBuffer(float[] vertices){
            FloatBuffer  vertexIndexBuffer= (FloatBuffer) bufferFloatIntUtil(vertices);
            return vertexIndexBuffer;
        }


    }



    public  void  roate(float angel,float x,float y,float z){
//        gl.glRotatef(angle, 1.0f, 1, 1);
//        Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
    }
}
