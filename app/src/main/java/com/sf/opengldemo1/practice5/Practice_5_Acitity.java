package com.sf.opengldemo1.practice5;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/8.
 */

public class Practice_5_Acitity extends AppCompatActivity {
    private PracticeSurfaceView5  surfaceView5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习5：绘制彩色三角形并封装");
        surfaceView5=new PracticeSurfaceView5(this);
        PracticeRender5  render5=new PracticeRender5(this);
        if(isSupportOpenGlEs20()){
            surfaceView5.setEGLContextClientVersion(2);
            surfaceView5.setRenderer(render5);
            surfaceView5.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }else{

        }

        setContentView(surfaceView5);

    }

    /**
     *判断是否支持Opengl 2.0
     */
    public boolean   isSupportOpenGlEs20(){
        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x20000;

    }
}
