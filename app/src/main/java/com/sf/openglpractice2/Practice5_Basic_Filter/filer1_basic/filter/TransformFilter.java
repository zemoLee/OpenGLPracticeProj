package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.filter;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic.BaseFilter;

/**
 * 位移滤镜.左右周期移动
 */
public class TransformFilter extends BaseFilter {

    public static String TRANSFORM_SHADER1 = "" +
            "precision mediump float;" +
            "varying vec2 v_textureD;" +
            "uniform sampler2D u_TextureUnit;" +
            "uniform float xV;" +
            "uniform float yV;" +

            "vec2 translate(vec2 srcCoord, float x, float y) {" +            //用到的函数需要声明在前面
            " return vec2(srcCoord.x + x, srcCoord.y + y);" +  //移动= 纹理坐标的向量分别加x和Y方向上的偏移量，由外部就算好传递进来
            "}" +
            " void main() {" +            //入口函数

            " vec2 offsetTexCoord = translate(v_textureD, xV, yV);" +            //--->>>得到偏移后的纹理坐标地址

            //--->>>如果新的纹理坐标的x>0 并且 <1 ， 并且 y也是>0 <1  --->>>才进行最终的渲染。要保证在规则的0---1范围内
            " if (offsetTexCoord.x >= 0.0 && offsetTexCoord.x <= 1.0 &&" +
            "offsetTexCoord.y >= 0.0 && offsetTexCoord.y <= 1.0) {" +
            //使用最终的结果渲染
            " gl_FragColor = texture2D(u_TextureUnit, offsetTexCoord);" +
            "}" +
            " }";

    public static String TRANSFORM_SHADER2 = "" +
           "precision mediump float;"+
            "varying vec2 v_textureD;"+
            "uniform sampler2D u_TextureUnit;"+
            "uniform float xV;"+
            "uniform float yV;"+

            " vec2 translate(vec2 srcCoord, float x, float y) {"+
                 "return vec2(srcCoord.x + x, srcCoord.y + y);"+
              "}"+

            "void main() {"+
              "vec2 offsetTexCoord = translate(v_textureD, xV, yV);"+
              "if (offsetTexCoord.x >= 0.0 && offsetTexCoord.x <= 1.0 && offsetTexCoord.y >= 0.0 && offsetTexCoord.y <= 1.0) {"+
                "gl_FragColor = texture2D(u_TextureUnit, offsetTexCoord);"+
              "}"+
            "}";

    private int xOffsetHandle;
    private int yOffsetHandle;
    private long startTime;

    public TransformFilter(Context mContext) {
        super(mContext);
    }

    public TransformFilter(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = System.currentTimeMillis();
        xOffsetHandle = GLES20.glGetUniformLocation(mProgram, "xV");
        yOffsetHandle = GLES20.glGetUniformLocation(mProgram, "yV");
    }

    @Override
    public void onDraw() {
        super.onDraw();
        //根据当前时间，进行周期变化， *0.5 --->>>表示在 0---0.5范围内变化，  因为正玄的值的范围就是-1---0---1
        float intensity = (float) (Math.sin((System.currentTimeMillis() - startTime) / 1000.0) * 0.5f);
        Log.d("TAG", "indensity=" + intensity);
        GLES20.glUniform1f(xOffsetHandle, (float) intensity);
        GLES20.glUniform1f(yOffsetHandle, 0.0f);
    }
}
