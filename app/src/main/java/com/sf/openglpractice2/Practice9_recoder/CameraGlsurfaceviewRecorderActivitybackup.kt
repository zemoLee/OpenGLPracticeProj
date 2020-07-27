package com.sf.openglpractice2.Practice9_recoder

import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.sf.opengldemo1.R
import com.sf.openglpractice2.Practice9_recoder.camerautil.ICamera

/**
 *  相机预览画面
 */
class CameraGlsurfaceviewRecorderActivitybackup : AppCompatActivity(), View.OnClickListener, CameraRecorderRender.SufacetextureListener, SurfaceTexture.OnFrameAvailableListener {

    val TAG = "CameraRecorderRender-->"

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
    val startRecordBtn by lazy {
        findViewById<Button>(R.id.startRecordBtn)
    }
    val stopRecordBtn by lazy {
        findViewById<Button>(R.id.stopRecordBtn)
    }
    var render:CameraRecorderRender?=null

    private val mCameraApi: ICamera? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_9_camera_glsurfaceview)

         render = CameraRecorderRender()
        cameraPreview.setEGLContextClientVersion(2)
        cameraPreview.setRenderer(render)
        cameraPreview.renderMode = RENDERMODE_WHEN_DIRTY

        render?.sufacetextureListener = this

        startPreview.setOnClickListener(this)
        stopreview.setOnClickListener(this)
        switchPreview.setOnClickListener(this)
        startRecordBtn.setOnClickListener(this)
        stopRecordBtn.setOnClickListener(this)
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
            R.id.startRecordBtn -> {
                render?.changeRecordingState(true)
            }
            R.id.stopRecordBtn -> {
                render?.changeRecordingState(false)
            }
        }
    }

    /**
     * 回调回来的是自定义的oes外部纹理
     * @param surfaceTexture SurfaceTexture
     */
    override fun onSufaceTextureInit(surfaceTexture: SurfaceTexture) {
        Log.d(TAG, "[onSufaceTextureInit]")
//        CameraHelper.openCamera()
//        CameraHelper.startPreview()
        //开启预览
        CameraHelper.setPreviewTexture(surfaceTexture)
        //设置帧回调监听
        surfaceTexture.setOnFrameAvailableListener(this)
    }

    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        Log.d(TAG, "[onFrameAvailable]")
        cameraPreview.requestRender()
    }

    private var mRecordingEnabled = false
    private fun startOrStopToRecord() {
        mRecordingEnabled = !mRecordingEnabled
        render?.changeRecordingState(mRecordingEnabled)
    }

}