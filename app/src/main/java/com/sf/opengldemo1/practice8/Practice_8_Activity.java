package com.sf.opengldemo1.practice8;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_8_Activity extends AppCompatActivity {
    private PracticeSurfaceView8  surfaceView8;
    private PracticeRender8  render8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习7：绘制正方体");
        surfaceView8=new PracticeSurfaceView8(this);
        render8=new PracticeRender8(this);
        if(isSupportGLES20()){
            surfaceView8.setEGLContextClientVersion(2);
            surfaceView8.setRenderer(render8);
            surfaceView8.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        setContentView(surfaceView8);
    }

    private boolean  isSupportGLES20(){

        return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x2000;
    }
}
