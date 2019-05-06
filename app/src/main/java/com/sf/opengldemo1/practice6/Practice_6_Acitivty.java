package com.sf.opengldemo1.practice6;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/12.
 */

public class Practice_6_Acitivty extends AppCompatActivity {
    PracticeSurfaceView6 surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习6：绘制圆形");
        surfaceView=new PracticeSurfaceView6(this);
        PracticeRender6  render6=new PracticeRender6(this);
        if(isSupportOpenGlEs20()){
            surfaceView.setEGLContextClientVersion(2);
            surfaceView.setRenderer(render6);
            surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }else{
        }

        setContentView(surfaceView);
    }

    public boolean   isSupportOpenGlEs20(){
        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x20000;

    }
}
