package com.sf.opengldemo1.practice14.shaderprogram;

import android.content.Context;
import android.opengl.GLES20;

import com.sf.opengldemo1.practice14.shaderhelper.ShaderHelper;
import com.sf.opengldemo1.practice14.shaderhelper.TextResourceReader;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class Base3DShaderProgram {

    public static final String A_POSITION="vPosition";
    public  static  final String A_COORD="vCoord";
    public  static  final String A_NORMAL ="vNormal";
    public static final String U_MATRIX="vMatrix";
    public  static  final String U_TEXTURE="vTexture";

    public  static  final String U_KA="vKa";
    public  static  final String U_KD="vKd";
    public  static  final String U_KS="vKs";

    public  int mProgram;
    private Context  context;
    public Base3DShaderProgram(Context context) {
        this.context = context;
//        loadProgram();
    }
    private void  loadProgram(){
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readFromAssets(context,
                "assets/3dobj/practice_14_obj.vert"), TextResourceReader.readFromAssets(context,
                "assets/3dobj/practice_14_obj.frag"));
    }

    public void  userProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
