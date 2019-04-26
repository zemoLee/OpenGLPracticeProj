package com.sf.opengldemo1.practice5.program;

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
    public static final String U_MATRIX="u_Matrix";
    public  int mProgram;
    private Context  context;
    public BaseShaderProgram(Context context) {
        this.context = context;
        loadProgram();
    }
    //2:加载代码资源，就会用到资源util
    private void  loadProgram(){
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readTextResourceFromRaw(context,
                R.raw.practice_5_vertext_shader), TextResourceReader.readTextResourceFromRaw(context,
                R.raw.practice_5_fragment_shader));
    }

    public void  userProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
