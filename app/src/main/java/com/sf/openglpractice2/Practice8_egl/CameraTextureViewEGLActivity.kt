package com.sf.openglpractice2.Practice8_egl

import android.graphics.Color
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import com.sf.opengldemo1.R
import com.sf.openglpractice2.Practice7_camera.CameraHelper

class CameraTextureViewEGLActivity : AppCompatActivity(), View.OnClickListener, TextureView.SurfaceTextureListener, SurfaceTexture.OnFrameAvailableListener {

    val TAG = "EGLRender-->"

    private val cameraBigPreview by lazy {
        findViewById<TextureView>(R.id.preview)
    }
    private val cameraLittlePreview by lazy {
        findViewById<TextureView>(R.id.little_preview)
    }
    private val startPreviewBtn by lazy {
        findViewById<Button>(R.id.startPreviewBtn)
    }
    private val stopreviewBtn by lazy {
        findViewById<Button>(R.id.stopPreviewBtn)
    }
    private val switchFrontBackBtn by lazy {
        findViewById<Button>(R.id.switchFontBackBtn)
    }
    private val switchPreviewBtn by lazy {
        findViewById<Button>(R.id.switchPreviewBtn)
    }

    val clearPreviewBtn by lazy {
        findViewById<Button>(R.id.clearBtn)
    }


    var render: EGLRender? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_8_camera_egl_textureview)

        render = EGLRender("my-egl-render")
        cameraBigPreview.surfaceTextureListener = this

        startPreviewBtn.setOnClickListener(this)
        stopreviewBtn.setOnClickListener(this)
        switchFrontBackBtn.setOnClickListener(this)
        switchPreviewBtn.setOnClickListener(this)
        clearPreviewBtn.setOnClickListener(this)

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
            R.id.switchPreviewBtn -> {
                switchBigOrSmall()
            }
            R.id.clearBtn->{
//                val canvas=cameraBigPreview.lockCanvas()
//                canvas.drawColor(Color.BLACK)
//                cameraBigPreview.unlockCanvasAndPost(canvas)
                render?.clear()
//                cameraBigPreview.isOpaque = false
            }
        }
    }

    private fun switchBigOrSmall(){
        if(render?.mTextureView==cameraBigPreview){
            Log.d(TAG, "[switchBigOrSmall] cameraLittlePreview")
            render?.switchPreview(cameraLittlePreview)
        }else{
            Log.d(TAG, "[switchBigOrSmall] cameraBigPreview")
            render?.switchPreview(cameraBigPreview)

        }
    }


    /**
     * TextureView内置的SurfaceTexture用来配合EGL来将图像显示到屏幕上
     * 自定义的SurfaceTexture用来接收Camera的预览图像
     * @param surface SurfaceTexture
     * @param width Int
     * @param height Int
     */
    var mOESSurfaceTexture: SurfaceTexture? = null

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        val oesTextureId = render?.createOESTextureObject() ?: -1

        mOESSurfaceTexture = render?.createSufaceTexure(oesTextureId)

        render?.initRender(this, cameraBigPreview, oesTextureId)

        //开启预览
        mOESSurfaceTexture?.let { CameraHelper.setPreviewTexture(it) }

        //设置帧回调监听
        mOESSurfaceTexture?.setOnFrameAvailableListener(this)
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        CameraHelper.stopCamera()
        return true
    }

    /**
     * 可用帧回调
     * @param surfaceTexture SurfaceTexture
     */
    override fun onFrameAvailable(surfaceTexture: SurfaceTexture?) {
        Log.d(TAG, "[onFrameAvailable]")
        render?.onRender(mOESSurfaceTexture)
    }


}