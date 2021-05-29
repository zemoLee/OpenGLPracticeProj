package com.sf.opengldemo1.practice14;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.sf.opengldemo1.practice14.objhelper.ObjReader;
import com.sf.opengldemo1.practice14.shaderprogram.ObjFilter2;
import com.sf.opengldemo1.practice14.view.Obj3D;
import com.sf.openglpractice2.Practice7_camera.GLTextureView;
import com.sf.utils.Gl2Utils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;

/**
 * Created by 01373577 on 2018/6/19.
 */

public class PracticeRender14 implements GLTextureView.Renderer {

    private Context context;
//    private Obj3DShaderProgram mObjProgram;
List<Obj3D> model3dObjs;
    private List<ObjFilter2> filters;
    private float[] mMatrix = new float[]{
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
    };

    Resources mRes;
    public PracticeRender14(Context context,Resources res,List<ObjFilter2> filters) {
        this.context = context;
        this.mRes=res;
        this.filters=filters;
    }
    public PracticeRender14(Context context,Resources res) {
        this.context = context;
        this.mRes=res;
    }

    public void  setFilters(List<ObjFilter2>  objFilter2s){
        this.filters=objFilter2s;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        GLES20.glClearColor(1, 0, 0, 0.5f);
//        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        gl.glClearColor(0.1f,0.0f,0.0f,0.0f);

        GLES20.glEnable(GL10.GL_DEPTH_TEST);
        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);//修正函数

//        GLES20. glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        initModelObj();//加载模型资源
//        mObjProgram = new Obj3DShaderProgram(context);
        for(int i=0;i<filters.size();i++){
             GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            filters.get(i).onCreate(context);
        }
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        GLES20.glViewport(0, 0, width, height);
//        Matrix.translateM(mMatrix,0,0,-0.3f,0);
//        Matrix.scaleM(mMatrix, 0, 0.2f, 0.2f * width / height, 0.2f); //0缩放因子，也就是系数，x,y,z轴

        for (ObjFilter2 f:filters){
            f.onSizeChanged(width, height);
//            float[] matrix= mMatrix;
            float[] matrix= Gl2Utils.getOriginalMatrix();
            Matrix.translateM(matrix,0,0,-0.3f,0);
            Matrix.scaleM(matrix,0,0.008f,0.008f*width/height,0.008f);
            f.setMatrix(matrix);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        onClear();
//        for(int i=0;i<model3dObjs.size();i++){
//            mObjProgram.userProgram();
//            Matrix.rotateM(mMatrix, 0, 0.3f, 0, 1, 0);//0是偏移量。 0.3f是旋转角度，x y z轴
//            model3dObjs.get(i).drawSelf(mObjProgram, mMatrix);
//        }

        Log.i("PracticeRender14","onDrawFrame....");
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        for (ObjFilter2 f:filters){
            Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
            f.draw();
        }
    }

    @Override
    public void onSurfaceDestroyed() {

    }

    /**
     * 读取3D模型素材资源
     */

    private void initModelObj() {
         model3dObjs= ObjReader.readMultiObj(context,"assets/3dobj/practice_14_pikachu.obj");
        filters=new ArrayList<>();
        for (int i=0;i<model3dObjs.size();i++){
            ObjFilter2 f=new ObjFilter2(context);
            f.setObj3D(model3dObjs.get(i));
            filters.add(f);
        }

    }

    protected void onClear() {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }


}
