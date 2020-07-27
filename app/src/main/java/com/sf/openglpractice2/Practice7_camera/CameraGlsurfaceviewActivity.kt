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
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay

/**
 *  相机预览画面
 */
class CameraGlsurfaceviewActivity : AppCompatActivity(), View.OnClickListener, CameraRender.SufacetextureListener, SurfaceTexture.OnFrameAvailableListener {

    val TAG="CameraRender-->"

    val cameraPreview by lazy {
        findViewById<GLSurfaceView>(R.id.preview)
    }
    val startPreview by lazy {
        findViewById<Button>(R.id.startPreviewBtn)
    }
    val stopreview by lazy {
        findViewById<Button>(R.id.stopPreviewBtn)
    }
    val switchPreview by lazy {
        findViewById<Button>(R.id.switchFontBackBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_7_camera_glsurfaceview)

        val render = CameraRender()
        cameraPreview.setEGLContextClientVersion(2)
        cameraPreview.setRenderer(render)
        cameraPreview.renderMode = RENDERMODE_WHEN_DIRTY

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
            R.id.switchFontBackBtn -> {
                CameraHelper.switchCamera()
            }

        }
    }

    /**
     * 回调回来的是自定义的oes外部纹理
     * @param surfaceTexture SurfaceTexture
     */
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
        cameraPreview.requestRender()
    }


}