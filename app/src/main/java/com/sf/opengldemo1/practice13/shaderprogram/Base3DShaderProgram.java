package com.sf.opengldemo1.practice13.shaderprogram;

import android.content.Context;
import android.opengl.GLES20;

import com.sf.opengldemo1.practice13.shaderhelper.ShaderHelper;
import com.sf.opengldemo1.practice13.shaderhelper.TextResourceReader;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class Base3DShaderProgram {
//    public static final String A_POSITION="a_Position";
//    public static final String A_COLOR="a_Color";
//    public static final String U_MATRIX="u_Matrix";
//    public  static  final String A_COORD="a_Coordinate";
//    public  static  final String U_TEXTURE="u_Texture";

    public static final String A_POSITION="vPosition";
    public  static  final String A_COORD="vCoord";
    public  static  final String A_NORMAL ="vNormal";
    public static final String U_MATRIX="vMatrix";
    public  static  final String U_TEXTURE="vTexture";


    public  int mProgram;
    private Context  context;
    public Base3DShaderProgram(Context context) {
        this.context = context;
        loadProgram();
    }
    private void  loadProgram(){
        mProgram = ShaderHelper.buildProgram(TextResourceReader.readFromAssets(context,
                "3dobj/practice_13_obj.vert"), TextResourceReader.readFromAssets(context,
                "3dobj/practice_13_obj.frag"));
    }

    public void  userProgram(){
        GLES20.glUseProgram(mProgram);
    }
}
