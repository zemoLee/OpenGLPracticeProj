package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 反色滤镜
 */
public class InverseFilter extends BaseFilter {

    public static String INVERSE_SHADER = "" +
            "precision mediump float;" +
            "varying vec2 v_textureD;" +
            "uniform sampler2D u_TextureUnit;" +
            "void main() {" +
            "vec4 src = texture2D(u_TextureUnit, v_textureD);" +
            "gl_FragColor = vec4(1.0 - src.r, 1.0 - src.g, 1.0 - src.b, 1.0);" +
            "}";


    public InverseFilter(Context mContext) {
        super(mContext);
    }

    public InverseFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }
}
