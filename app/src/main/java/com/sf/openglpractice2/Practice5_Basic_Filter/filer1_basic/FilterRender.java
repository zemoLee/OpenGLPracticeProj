package com.sf.openglpractice2.Practice5_Basic_Filter.filer1_basic;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.sf.opengldemo1.R;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 滤镜渲染器
 */
public class FilterRender implements GLSurfaceView.Renderer {
    private List<BaseFilter> filterList=new ArrayList<>();
    int drawIndex;
    boolean isChange;
    BaseFilter mCurrentFilter;
//   TextureHelperKt.TextureBean textureBean;
   int[] mTexture;
    Context context;

    int outputWidth;
    int outputHeight;
    public FilterRender(Context context) {
        this.context=context;
        filterList.add(new BaseFilter());
        filterList.add(new GrayFilter());
        mCurrentFilter = filterList.get(0);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCurrentFilter.onCreate();
        mTexture = TextureHelper.loadTexture(context,new int[]{R.drawable.test});
        mCurrentFilter.mTexture = mTexture;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mCurrentFilter.onSizeChanged(width, height);
        outputWidth = width;
        outputHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (isChange) {
            mCurrentFilter = filterList.get(drawIndex);
            for (BaseFilter filter : filterList) {
                if(filter!=mCurrentFilter){
                    filter.onDestroy();
                }
            }
            mCurrentFilter.onCreate();
            mCurrentFilter.onSizeChanged(outputWidth, outputHeight);
            mCurrentFilter.mTexture = mTexture;
            isChange = false;
        }
        mCurrentFilter.onDraw();
    }

    public  void onClick() {
        drawIndex++;
    if (drawIndex >= filterList.size()){
        drawIndex= 0;
    } else{
        isChange = true;
    }

    }
}
