package com.sf.opengldemo1.practice13;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sf.opengldemo1.practice12.PracticeRender12;
import com.sf.opengldemo1.practice12.PracticeSurfaceView12;

import java.io.IOException;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_13_Activity extends AppCompatActivity {
    private PracticeSurfaceView13 surfaceView13;
    private PracticeRender13 render13;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习13：绘制OBJ模型帽子");
        surfaceView13 = new PracticeSurfaceView13(this);
        render13 = new PracticeRender13(this);
        if (isSupportGLES20()) {
            surfaceView13.setEGLContextClientVersion(2);
            surfaceView13.setRenderer(render13);
            surfaceView13.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
        setContentView(surfaceView13);
    }

    private boolean isSupportGLES20() {

        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x2000;
    }
}
