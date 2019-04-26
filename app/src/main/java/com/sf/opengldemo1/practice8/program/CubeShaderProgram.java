package com.sf.opengldemo1.practice8.program;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/13.
 */

/**
 * 正方体
 */
public class CubeShaderProgram extends BaseShaderProgram {
    private final  int aPositionHandle;
    private  final  int aColorHandle;
    private final   int uMatrixHandle;
    public CubeShaderProgram(Context context) {
        super(context);
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
