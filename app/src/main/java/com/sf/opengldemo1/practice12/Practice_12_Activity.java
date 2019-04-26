package com.sf.opengldemo1.practice12;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_12_Activity extends AppCompatActivity {
    private PracticeSurfaceView12 surfaceView12;
    private PracticeRender12 render12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习12：2D正方形纹理皮肤贴图");
        surfaceView12 = new PracticeSurfaceView12(this);
        render12 = new PracticeRender12(this);
        if (isSupportGLES20()) {
            surfaceView12.setEGLContextClientVersion(2);
            surfaceView12.setRenderer(render12);
            surfaceView12.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            try {
                render12.setBitmap(BitmapFactory.decodeStream(getResources().getAssets().open("texture/test.jpg")));//加载皮肤
                surfaceView12.requestRender();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setContentView(surfaceView12);
    }

    private boolean isSupportGLES20() {

        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x2000;
    }
}
