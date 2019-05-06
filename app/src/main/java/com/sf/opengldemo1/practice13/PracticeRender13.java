package com.sf.opengldemo1.practice13;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sf.opengldemo1.practice13.objhelper.ObjReader;
import com.sf.opengldemo1.practice13.shaderprogram.Obj3DShaderProgram;
import com.sf.opengldemo1.practice13.view.Obj3D;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by 01373577 on 2018/6/19.
 */

public class PracticeRender13 implements GLSurfaceView.Renderer {

    private Context context;
    private Obj3D obj3DModel;
    private Obj3DShaderProgram mObjProgram;

    private float[] mMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };


    public PracticeRender13(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mObjProgram = new Obj3DShaderProgram(context);
        initModelObj();//加载模型资源
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.scaleM(mMatrix, 0, 0.2f, 0.2f * width / height, 0.2f); //0缩放因子，也就是系数，x,y,z轴


    }

    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();
        mObjProgram.userProgram();
        Matrix.rotateM(mMatrix, 0, 0.3f, 0, 1, 0);//0是偏移量。 0.3f是旋转角度，x y z轴
        obj3DModel.drawSelf(mObjProgram, mMatrix);

    }

    /**
     * 读取3D模型素材资源
     */
    private void initModelObj() {
        obj3DModel = new Obj3D(context);
        try {
            ObjReader.read(context.getAssets().open("3dobj/hat.obj"), obj3DModel);//映射资源 到 3d model中。后续在onDraw中将调用此3dmodel自身的绘制
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onClear() {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }


}
