package com.sf.opengldemo1.practice14.shaderprogram;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.sf.opengldemo1.practice14.shaderhelper.ShaderHelper;
import com.sf.opengldemo1.practice14.shaderhelper.TextResourceReader;
import com.sf.opengldemo1.practice14.shaderhelper.TextureHelper;
import com.sf.opengldemo1.practice14.view.Obj3D;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by 01373577 on 2018/6/25.
 */

public class ObjFilter2  extends Base3DShaderProgram{
//    private int mHNormal;
//    private int mHKa;
//    private int mHKd;
//    private int mHKs;
//    protected int mHPosition;
//    protected int mHTexture;
//    protected int mHCoord;
//protected int mHMatrix;


    private   int aPositionHandle;
    private    int uMatrixHandle;
    private   int uTextureHandle;
    private   int aCoordinateHandle;
    private   int  mNormalHandle;//法向量handle
    private   int uKaHandle;
    private   int uKdHandle;
    private   int uKsHandle;

    private int textureId;
    private int textureType=0;
    private float[] matrix;
    private Obj3D obj;
    private Context  context;
    public ObjFilter2(Context context) {
        super(context);
        this.context=context;
    }




    public void setObj3D(Obj3D obj){
        this.obj=obj;
    }
    public  int getTextureId(){
        return textureId;
    }
    public  void setTextureId(int textureId){
        this.textureId=textureId;
    }
    public void setMatrix(float[] matrix){
        this.matrix=matrix;
    }

    public float[] getMatrix(){
        return matrix;
    }
    /**
     * surfaceview  oncreate
     */
    public void  onCreate(  Resources mRes) {
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readFromAssets(context,
                "3dobj/practice_14_obj.vert"), TextResourceReader.readFromAssets(context,
                "3dobj/practice_14_obj.frag"));
        aPositionHandle= GLES20.glGetAttribLocation(mProgram, "vPosition");
        aCoordinateHandle=GLES20.glGetAttribLocation(mProgram,"vCoord");
        uMatrixHandle=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        uTextureHandle=GLES20.glGetUniformLocation(mProgram,"vTexture");
        mNormalHandle=GLES20.glGetAttribLocation(mProgram,"vNormal");

        uKaHandle=GLES20.glGetUniformLocation(mProgram,"vKa");
        uKdHandle=GLES20.glGetUniformLocation(mProgram,"vKd");
        uKsHandle=GLES20.glGetUniformLocation(mProgram,"vKs");
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        if(obj!=null&&obj.mtl!=null){
            try {
                textureId= TextureHelper.createTexture(BitmapFactory.decodeStream(mRes.getAssets().open("3dobj/"+obj.mtl.map_Kd)));
//                setTextureId(textureId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void  onCreate(Context context) {
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readFromAssets(context,
                "3dobj/practice_14_obj.vert"), TextResourceReader.readFromAssets(context,
                "3dobj/practice_14_obj.frag"));
        aPositionHandle= GLES20.glGetAttribLocation(mProgram, "vPosition");
        aCoordinateHandle=GLES20.glGetAttribLocation(mProgram,"vCoord");
        uMatrixHandle=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        uTextureHandle=GLES20.glGetUniformLocation(mProgram,"vTexture");
        mNormalHandle=GLES20.glGetAttribLocation(mProgram,"vNormal");

        uKaHandle=GLES20.glGetUniformLocation(mProgram,"vKa");
        uKdHandle=GLES20.glGetUniformLocation(mProgram,"vKd");
        uKsHandle=GLES20.glGetUniformLocation(mProgram,"vKs");
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        if(obj!=null&&obj.mtl!=null){
            try {
                textureId= TextureHelper.createTexture(BitmapFactory.decodeStream(context.getAssets().open("3dobj/"+obj.mtl.map_Kd)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * surfaceview  onSizeChanged
     */
    public void onSizeChanged(int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    /**
     *  surfaceview  onDraw
     */
    public void draw(){
        onClear();
        onUseProgram();
        onSetExpandData();
        onBindTexture();
        onDraw();
    }

    protected void onClear(){
//        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }
    protected void onUseProgram(){
        GLES20.glUseProgram(mProgram);
    }
    protected void onSetExpandData(){
        GLES20.glUniformMatrix4fv(uMatrixHandle,1,false,matrix,0);
        if(obj!=null&&obj.mtl!=null){
            GLES20.glUniform3fv(uKaHandle,1,obj.mtl.Ka,0);
            GLES20.glUniform3fv(uKdHandle,1,obj.mtl.Kd,0);
            GLES20.glUniform3fv(uKsHandle,1,obj.mtl.Ks,0);
        }
    }
    protected void onBindTexture(){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0+textureType);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,getTextureId());
        GLES20.glUniform1i(uTextureHandle,textureType);
    }
    protected void onDraw(){
//        GLES20.glEnableVertexAttribArray(aPositionHandle);
//        GLES20.glVertexAttribPointer(aPositionHandle,2, GLES20.GL_FLOAT, false, 0,obj.vertexBuffer);
//        GLES20.glEnableVertexAttribArray(aCoordinateHandle);
//        GLES20.glVertexAttribPointer(aCoordinateHandle, 2, GLES20.GL_FLOAT, false, 0, obj.vertexBuffer);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
//        GLES20.glDisableVertexAttribArray(aPositionHandle);
//        GLES20.glDisableVertexAttribArray(aCoordinateHandle);

        GLES20.glEnableVertexAttribArray(aPositionHandle);
        GLES20.glVertexAttribPointer(aPositionHandle,3, GLES20.GL_FLOAT, false,0,obj.vertexBuffer);
        GLES20.glEnableVertexAttribArray(mNormalHandle);
        GLES20.glVertexAttribPointer(mNormalHandle,3, GLES20.GL_FLOAT, false, 0,obj.normalBuffer);
        GLES20.glEnableVertexAttribArray(aCoordinateHandle);
        GLES20.glVertexAttribPointer(aCoordinateHandle,2,GLES20.GL_FLOAT,false,0,obj.textureBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,obj.vertCount);
        GLES20.glDisableVertexAttribArray(aPositionHandle);
        GLES20.glDisableVertexAttribArray(mNormalHandle);
        GLES20.glDisableVertexAttribArray(aCoordinateHandle);
    }
}
