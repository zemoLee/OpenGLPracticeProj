package com.sf.opengldemo1.practice9;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_9_Activity extends AppCompatActivity {
    private PracticeSurfaceView9 surfaceView9;
    private PracticeRender9 render9;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习7：绘制圆锥体");
        surfaceView9=new PracticeSurfaceView9(this);
        render9=new PracticeRender9(this);
        if(isSupportGLES20()){
            surfaceView9.setEGLContextClientVersion(2);
            surfaceView9.setRenderer(render9);
            surfaceView9.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        }
        setContentView(surfaceView9);
    }

    private boolean  isSupportGLES20(){

        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x2000;
    }
}
