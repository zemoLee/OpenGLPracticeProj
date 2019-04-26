package com.sf.opengldemo1.practice11;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sf.opengldemo1.practice10.PracticeRender10;
import com.sf.opengldemo1.practice10.PracticeSurfaceView10;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_11_Activity extends AppCompatActivity {
    private PracticeSurfaceView11 surfaceView11;
    private PracticeRender11 render11;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习11：绘制正圆球体");
        surfaceView11=new PracticeSurfaceView11(this);
        render11=new PracticeRender11(this);
        if(isSupportGLES20()){
            surfaceView11.setEGLContextClientVersion(2);
            surfaceView11.setRenderer(render11);
            surfaceView11.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        setContentView(surfaceView11);
    }

    private boolean  isSupportGLES20(){

        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x2000;
    }
}
