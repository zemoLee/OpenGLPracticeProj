package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 高斯模糊滤镜
 * 通常图片模糊处理是采集周边多个点，
 * 然后利用这些点的色彩和这个点自身的色彩进行计算，得到一个新的色彩值作为目标色彩。
 */
public class GaussBlurFilter extends BaseFilter {
    public static String GAUSS_BLUR_SHADER = "" +
            "precision mediump float;" +
            "uniform sampler2D u_TextureUnit;" +
            "varying vec2 v_textureD;" +
            "void main(){" +
            "vec4 color=vec4(0.0f);" +
            "int coreSize=3;" +
            "int halfSize=coreSize/2;" +
            "float texelOffset=0.01f;" +
            "float kernel[9];" +
            "kernel[6] = 1.0; kernel[7] = 2.0; kernel[8] = 1.0;\n" +
            "kernel[3] = 2.0; kernel[4] = 4.0; kernel[5] = 2.0;\n" +
            "kernel[0] = 1.0; kernel[1] = 2.0; kernel[2] = 1.0;" +
            "int  index=0;" +
            "for(int y=0;y<coreSize;y++){" +
            "for(int x=0;x<coreSize;x++){" +
            "vec4 currentColor=texture2D(u_TextureUnit,v_textureD +vec2(float((-1+x))*texelOffset,float((-1+y))*texelOffset)));" +
            "color+=currentColor*kernel[index];" +
            "index++;" +
            "}" +
            "}" +
            //归一处理
            "color/=16.0;" +
            "gl_FragColor=color;" +
            "}";

    public GaussBlurFilter(Context mContext) {
        super(mContext);
    }

    public GaussBlurFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public void onDraw() {
//        super.onDraw();
//    }
}
