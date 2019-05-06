package com.sf.opengldemo1.practice10;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_10_Activity extends AppCompatActivity {
    private PracticeSurfaceView10 surfaceView10;
    private PracticeRender10 render10;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习11：绘制圆柱体");
        surfaceView10=new PracticeSurfaceView10(this);
        render10=new PracticeRender10(this);
        if(isSupportGLES20()){
            surfaceView10.setEGLContextClientVersion(2);
            surfaceView10.setRenderer(render10);
            surfaceView10.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        setContentView(surfaceView10);
    }

    private boolean  isSupportGLES20(){

        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x2000;
    }
}
