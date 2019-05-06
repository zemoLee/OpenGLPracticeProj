package com.sf.opengldemo1.practice2;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class PracticeSurfaceView2 extends GLSurfaceView {
    public PracticeSurfaceView2(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        PracticeRender2 render= new PracticeRender2();
        setRenderer(render);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
