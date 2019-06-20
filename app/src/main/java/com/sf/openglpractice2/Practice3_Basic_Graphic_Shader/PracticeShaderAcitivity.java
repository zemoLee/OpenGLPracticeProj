package com.sf.openglpractice2.Practice3_Basic_Graphic_Shader;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.sf.openglpractice2.Practice3_Basic_Graphic_Shader.cube.FGLRender;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/4/29
 * @Des: //TODO
 */
public class PracticeShaderAcitivity extends AppCompatActivity implements View.OnTouchListener{
    private GLSurfaceView  mSurfaceView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init3DSqureCustom();
        init3DSqureShaderCustom();
//        init3DSqureShaderCustom2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSurfaceView.onPause();
    }

    FGLRender fglRender;
    private void init3DSqureShaderCustom2(){
        mSurfaceView=new GLSurfaceView(this);
        fglRender=new FGLRender();
        setContentView(mSurfaceView);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(fglRender);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        mSurfaceView.setOnTouchListener(this);
    }

    RenderOfShader_originshader  renderOfShader;
    private void init3DSqureShaderCustom(){
        mSurfaceView=new GLSurfaceView(this);
        renderOfShader=new RenderOfShader_originshader();
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(renderOfShader);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        mSurfaceView.setOnTouchListener(this);
    }


    RenderOfShader  renderSqureOfShader;
    private void init3DSqureCustom(){
        mSurfaceView=new GLSurfaceView(this);
        renderSqureOfShader=new RenderOfShader();
        mSurfaceView.setRenderer(renderSqureOfShader);
        setContentView(mSurfaceView);
        mSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
        mSurfaceView.setOnTouchListener(this);
    }



    private final float TOUCH_SCALE_FACTOR=0.5f;
    private float previousX,previousY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        float x=event.getX();
//        float y=event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float dy=y-previousY;
//                float dx=x-previousX;
//                dx+=dx;
//                dy+=dy;
//                Log.d("PracticeShaderAcitivity","dy="+dy+" dx="+dx);
//                if(Math.abs(dy)>Math.abs(dx)){
//                    renderOfShader.rotate(dx,1,0,0);
//                    Log.d("PracticeShaderAcitivity","dy>dx--->>>X轴旋转");
//                }else{
//                    renderOfShader.rotate(dy,0,1,0);
//                    Log.d("PracticeShaderAcitivity","dy<dx--->>>Y轴旋转");
//                }
//
//                break;
//        }
//        mSurfaceView.requestRender();
//        previousX=x;
//        previousY=y;

        return true;
    }
}
