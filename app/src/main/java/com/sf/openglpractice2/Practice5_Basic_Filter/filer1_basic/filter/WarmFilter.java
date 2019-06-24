package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 暖色调滤镜==增加 红色和黄色通道值
 */
public class WarmFilter extends BaseFilter {
    public static String WARM_SHADER = "" +
            "precision mediump float;" +
            "uniform sampler2D u_TextureUnit;" +
            "uniform vec3 u_ChangeColor;" +
            "varying vec2 v_textureD;" +
            //color限制在rgb
            "void modifyColor(vec4 color){" +
            "color.r=max(min(color.r,1.0),0.0);" + //红   传递进来的颜色值，红色部分的值，这里是-1--1， 先和1做比较， 超过1的，肯定就不需要了，需要限制一下，不然应该会曝光
            "color.g=max(min(color.g,1.0),0.0);" +//绿
            "color.b=max(min(color.b,1.0),0.0);" +//蓝
            "color.a=max(min(color.a,1.0),0.0);" +//透明度
            "}" +
            //暖色调的处理可以增加红绿通道的值。
            "void main(){" +
              "vec4 srcColor=texture2D(u_TextureUnit,v_textureD);" +
              "vec4 deltaColor=srcColor+vec4(u_ChangeColor,0.0);" +// 变成暖色调=原纹理色+ 叠加上 改变后的色
              "modifyColor(deltaColor);" + // 更新后的色调值，进行限制，因为叠加颜色时，必须保证在0-255范围内， 颜色值是16进制的
              "gl_FragColor=deltaColor;" +
            "}";

    public WarmFilter(Context mContext) {
        super(mContext);
    }

    public WarmFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }

    private int changeColorHandle;
    private float[] changeColor=new float[]{0.2f, 0.2f, 0.0f};
    @Override
    public void onCreate() {
        super.onCreate();
        changeColorHandle= GLES20.glGetUniformLocation(mProgram,"u_ChangeColor");
    }

    @Override
    public void onDraw() {
        super.onDraw();
        GLES20.glUniform3fv(changeColorHandle,1,changeColor,0);
    }
}
