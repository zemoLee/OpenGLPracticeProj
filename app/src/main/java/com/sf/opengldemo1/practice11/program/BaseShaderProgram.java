package com.sf.opengldemo1.practice11.program;

import android.content.Context;
import android.opengl.GLES20;

import com.sf.opengldemo1.R;
import com.sf.opengldemo1.practice5.helper.ShaderHelper;
import com.sf.opengldemo1.practice5.helper.TextResourceReader;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class BaseShaderProgram {
    public static final String A_POSITION="a_Position";
    public static final String A_COLOR="a_Color";
    public static final String V_COLOR="v_Color";
    public static final String U_MATRIX="u_Matrix";
    public  int mProgram;
    private Context  context;
    public BaseShaderProgram(Context context) {
        this.context = context;
        loadProgram();
    }
    private void  loadProgram(){
            mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                    R.raw.practice_11_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                    R.raw.practice_11_fragment_shader));

    }

    public void  userProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
