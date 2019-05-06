package com.sf.opengldemo1.practice11.program;

import android.content.Context;
import android.opengl.GLES20;


/**
 * Created by 01373577 on 2018/6/12.
 */

public class BallShaderProgram extends BaseShaderProgram {

    private final  int  aPositionAttribHandle;
    private final int  uMatrixUniformHandle;
    private final int  aColorAttribHandle;
    public BallShaderProgram(Context context) {
        super(context);
        aPositionAttribHandle= GLES20.glGetAttribLocation(mProgram,A_POSITION);
        uMatrixUniformHandle= GLES20.glGetUniformLocation(mProgram,U_MATRIX);
        aColorAttribHandle=GLES20.glGetAttribLocation(mProgram,A_COLOR);
    }


    public int getaPositionAttribHandle() {
        return aPositionAttribHandle;
    }

    public int getuMatrixUniformHandle() {
        return uMatrixUniformHandle;
    }

    public int getaColorAttribHandle() {
        return aColorAttribHandle;
    }

}
