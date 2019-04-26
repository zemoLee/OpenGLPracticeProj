package com.sf.opengldemo1.practice6.program;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/12.
 */

public class CircleShaderProgram extends BaseShaderProgram {

    private final  int  aPositionAttribHandle;
    private final int  uMatrixUniformHandle;
    private final int  aColorAttribHandle;
    private final int  VColorUniformHandle;
    public CircleShaderProgram(Context context) {
        super(context);
        aPositionAttribHandle= GLES20.glGetAttribLocation(mProgram,A_POSITION);
        uMatrixUniformHandle= GLES20.glGetUniformLocation(mProgram,U_MATRIX);
        aColorAttribHandle=GLES20.glGetAttribLocation(mProgram,A_COLOR);
        VColorUniformHandle= GLES20.glGetUniformLocation(mProgram,V_COLOR);
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

    public int getVColorUniformHandle() {
        return VColorUniformHandle;
    }
}
