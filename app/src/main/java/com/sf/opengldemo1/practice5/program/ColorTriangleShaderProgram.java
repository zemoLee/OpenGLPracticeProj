package com.sf.opengldemo1.practice5.program;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class ColorTriangleShaderProgram extends BaseShaderProgram {
    private final int a_ColorLocationHandle;
    private final int a_PositionLocationHandle;
    private final int u_MatrixHandle;

    public ColorTriangleShaderProgram(Context context) {
        //2 加载code， 编译，链接 生成int  program
        super(context);
        //3： 2获取program后， 这里获取相关变量的句柄，后续再和此handle句柄绑定，传入对应的值
        a_PositionLocationHandle =GLES20.glGetAttribLocation(mProgram,A_POSITION);
        a_ColorLocationHandle = GLES20.glGetAttribLocation(mProgram,A_COLOR);
        u_MatrixHandle =GLES20.glGetUniformLocation(mProgram,U_MATRIX);
    }


    public int getPositionAttribHandle(){
        return a_PositionLocationHandle;
    }

    public  int getColorAttribHandle(){
        return a_ColorLocationHandle;
    }

    public int getMatrixUniformHandle(){
        return u_MatrixHandle;
    }
}
