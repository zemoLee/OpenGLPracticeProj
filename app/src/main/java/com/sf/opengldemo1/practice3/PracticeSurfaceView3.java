package com.sf.opengldemo1.practice3;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.sf.opengldemo1.practice2.PracticeRender2;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class PracticeSurfaceView3 extends GLSurfaceView {
    public PracticeSurfaceView3(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        PracticeRender3 render= new PracticeRender3();
        setRenderer(render);
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
