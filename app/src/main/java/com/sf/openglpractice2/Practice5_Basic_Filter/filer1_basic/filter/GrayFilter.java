package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 黑白滤镜
 */
public class GrayFilter extends BaseFilter {

    public static  String FRAGMENT_SHADER2 = "" +
            "precision mediump float;" +
            " varying vec2 v_textureD;" +
            " uniform sampler2D u_TextureUnit;" +
            " void main() {" +
            "vec4 src = texture2D(u_TextureUnit, v_textureD);" +
            " float gray = (src.r + src.g + src.b) / 3.0;" +
//            " float gray = (src.r/3 + src.g/3 + src.b/3) ;" +
            "gl_FragColor =vec4(gray, gray, gray, 1.0);" +
            " }"
            + "";


    public GrayFilter(Context context) {
        super(context);
//        FRAGMENT_SHADER=FRAGMENT_SHADER2;

    }

    public GrayFilter(Context context, String vertexShader, String fragmentShader) {
        super(context,vertexShader,fragmentShader);
    }

    public void  setFragmentShader(){
        FRAGMENT_SHADER=FRAGMENT_SHADER2;
    }
}
