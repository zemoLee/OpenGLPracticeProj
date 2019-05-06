package com.sf.opengldemo1.practice13.shaderprogram;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Obj3DShaderProgram extends Base3DShaderProgram {


    private final  int aPositionHandle;
    private final   int uMatrixHandle;
    private final  int uTextureHandle;
    private final  int aCoordinateHandle;

    private final  int  mNormalHandle;//法向量handle


    public Obj3DShaderProgram(Context context) {
        super(context);
        aPositionHandle= GLES20.glGetAttribLocation(mProgram,A_POSITION);
        aCoordinateHandle=GLES20.glGetAttribLocation(mProgram,A_COORD);
        uMatrixHandle=GLES20.glGetUniformLocation(mProgram,U_MATRIX);
        uTextureHandle=GLES20.glGetUniformLocation(mProgram,U_TEXTURE);
        mNormalHandle=GLES20.glGetAttribLocation(mProgram, A_NORMAL);//法向量handle
    }


    public int getaPositionHandle() {
        return aPositionHandle;
    }

    public int getuMatrixHandle() {
        return uMatrixHandle;
    }

    public int getaCoordinateHandle() {
        return aCoordinateHandle;
    }

    public int getuTextureHandle() {
        return uTextureHandle;
    }

    public int getmNormalHandle() {
        return mNormalHandle;
    }
}
