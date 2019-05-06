package com.sf.opengldemo1.practice9.program;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class ConeShaderProgram extends BaseShaderProgram {
    private final  int aPositionHandle;
    private  final  int aColorHandle;
    private final   int uMatrixHandle;
    public ConeShaderProgram(Context context,int type) {
        super(context, type);
        aPositionHandle= GLES20.glGetAttribLocation(mProgram,A_POSITION);
        aColorHandle=GLES20.glGetAttribLocation(mProgram,A_COLOR);
        uMatrixHandle=GLES20.glGetUniformLocation(mProgram,U_MATRIX);
    }


    public int getaPositionHandle() {
        return aPositionHandle;
    }

    public int getaColorHandle() {
        return aColorHandle;
    }

    public int getuMatrixHandle() {
        return uMatrixHandle;
    }
}
