package com.sf.opengldemo1.practice12.program;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class SqureShaderProgram extends BaseShaderProgram {
    private final  int aPositionHandle;
    private  final  int aColorHandle;
    private final   int uMatrixHandle;
    private final  int uTextureHandle;
    private final  int aCoordinateHandle;
    public SqureShaderProgram(Context context) {
        super(context);
        aPositionHandle= GLES20.glGetAttribLocation(mProgram,A_POSITION);
        aColorHandle=GLES20.glGetAttribLocation(mProgram,A_COLOR);
        aCoordinateHandle=GLES20.glGetAttribLocation(mProgram,A_COORD);
        uMatrixHandle=GLES20.glGetUniformLocation(mProgram,U_MATRIX);
        uTextureHandle=GLES20.glGetUniformLocation(mProgram,U_TEXTURE);
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

    public int getaCoordinateHandle() {
        return aCoordinateHandle;
    }

    public int getuTextureHandle() {
        return uTextureHandle;
    }
}
