package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 发光闪烁滤镜
 * 周期性的闪烁变亮
 */
public class LightupFilter extends BaseFilter {
    public static String  LIGHTUP_SHADER=""+
            "precision mediump float;"+
            "varying vec2 v_textureD;"+
            "uniform sampler2D u_TextureUnit;"+
           "uniform float uTime;"+
//            " uniform float intensity;"+
            "void main() {"+
            "float lightUpValue = sin(uTime / 1000.0) / 4.0;"+
            "vec4 src = texture2D(u_TextureUnit, v_textureD);"+
//            "vec4 addColor = vec4(intensity, intensity, intensity, 1.0);"+
            "vec4 addColor = vec4(lightUpValue, lightUpValue, lightUpValue, 1.0);"+
            "gl_FragColor = src + addColor;"+
            "}";

    /**
     * 优化，将计算，放到cpu中去，不在opengl中进行计算。
     */
    public static String  LIGHTUP_SHADER2=""+
            "precision mediump float;"+
            "varying vec2 v_textureD;"+
            "uniform sampler2D u_TextureUnit;"+
            " uniform float intensity;"+
            "void main() {"+
            "vec4 src = texture2D(u_TextureUnit, v_textureD);"+
            "vec4 addColor = vec4(intensity, intensity, intensity, 1.0);"+
            "gl_FragColor = src + addColor;"+
            "}";
    private int uTimeHandle;
    private int uIndensityHandle;//优化后的新句柄
    private long startTime;
    public LightupFilter(Context mContext) {
        super(mContext);
    }

    public LightupFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
//        uTimeHandle= GLES20.glGetUniformLocation(mProgram,"uTime");
        uIndensityHandle= GLES20.glGetUniformLocation(mProgram,"intensity");

    }

    @Override
    public void onDraw() {
        super.onDraw();
//        GLES20.glUniform1f(uTimeHandle, (System.currentTimeMillis() - startTime));
        double intensity = Math.abs(Math.sin((System.currentTimeMillis() - startTime) / 1000.0)) / 4.0;
        GLES20.glUniform1f(uIndensityHandle, (float) intensity);

    }
}
