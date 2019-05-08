package com.sf.openglpractice2.Basic_Graphic_Shader;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class LightUtils {
    /**
     * 设置光源参数
     *
     * @param gl
     */
    public static void setLight(GL10 gl) {
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
    public static void setMeterial(GL10 gl) {
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

}
