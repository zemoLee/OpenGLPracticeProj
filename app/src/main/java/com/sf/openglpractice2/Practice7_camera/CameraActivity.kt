package com.sf.openglpractice2.Practice7_camera

import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.sf.opengldemo1.R

/**
 *  相机预览画面
 */
class CameraActivity : AppCompatActivity(), View.OnClickListener, CameraRender.SufacetextureListener, SurfaceTexture.OnFrameAvailableListener {

    val TAG="CameraRender-->"

    val preview by lazy {
        findViewById<GLSurfaceView>(R.id.preview)
    }
    val startPreview by lazy {
        findViewById<Button>(R.id.startPreviewBtn)
    }
    val stopreview by lazy {
        findViewById<Button>(R.id.stopPreviewBtn)
    }
    val switchPreview by lazy {
        findViewById<Button>(R.id.switchFontBack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_7_camera)

        val render = CameraRender()
        preview.setEGLContextClientVersion(2)
        preview.setRenderer(render)
        preview.renderMode = RENDERMODE_WHEN_DIRTY

        render.sufacetextureListener = this

        startPreview.setOnClickListener(this)
        stopreview.setOnClickListener(this)
        switchPreview.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.startPreviewBtn -> {
                CameraHelper.openCamera()
                CameraHelper.startPreview()
            }
            R.id.stopPreviewBtn -> {
                CameraHelper.stopCamera()
            }
            R.id.switchFontBack -> {
                CameraHelper.switchCamera()
            }
        }
    }

    override fun onSufaceTextureInit(surfaceTexture: SurfaceTexture) {
        Log.d(TAG,"[onSufaceTextureInit]")
//        CameraHelper.openCamera()
//        CameraHelper.startPreview()
        //开启预览
        CameraHelper.setPreviewTexture(surfaceTexture)
        //设置帧回调监听
        surfaceTexture.setOnFrameAvailableListener(this)
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        Log.d(TAG,"[onFrameAvailable]")
        preview.requestRender()
    }


}