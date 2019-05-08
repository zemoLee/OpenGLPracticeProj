/*
 *
 * Shape.java
 * 
 * Created by Wuwang on 2016/9/30
 */
package com.sf.openglpractice2.Basic_Graphic_Shader.cube;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.View;

/**
 * Description:
 */
public abstract class Shape implements GLSurfaceView.Renderer {

    protected View mView;

    public Shape(View mView){
        this.mView=mView;
    }


}
