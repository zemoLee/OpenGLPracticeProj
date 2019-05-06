package com.sf.opengldemo1.practice7;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_7_Activity extends AppCompatActivity {

    private PracticeSurfaceView7  surfaceView7;
    private PracticeRender7  render7;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习7：绘制正方形");
        surfaceView7=new PracticeSurfaceView7(this);
        render7=new PracticeRender7(this);
        if(isSupportGlEs20()){
            surfaceView7.setEGLContextClientVersion(2);
            surfaceView7.setRenderer(render7);
            surfaceView7.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        setContentView(surfaceView7);
    }

    private boolean   isSupportGlEs20(){

        return  ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x2000;
    }
}
