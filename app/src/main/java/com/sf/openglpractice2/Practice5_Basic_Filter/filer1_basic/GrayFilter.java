package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic;

public class GrayFilter extends BaseFilter {
    public static final String FRAGMENT_SHADER = "" +
            "precision mediump float;" +
            " varying vec2 v_texturePos;" +
            " uniform sampler2D u_TextureUnit;" +
            " void main() {" +
            "vec4 src = texture2D(u_TextureUnit, v_texturePos);" +
            " float gray = (src.r + src.g + src.b) / 3.0;" +
            "gl_FragColor =vec4(gray, gray, gray, 1.0);" +
            " }"
            + "";
}
