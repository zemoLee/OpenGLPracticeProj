package com.sf.openglpractice2.Practice7_camera

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.PixelFormat
import android.graphics.SurfaceTexture
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.VectorEnabledTintResources
import android.util.Log
import android.view.View
import android.widget.Button
import com.sf.opengldemo1.R
import com.sf.opengldemo1.practice14.PracticeRender14
import com.sf.openglpractice2.Practice4_Basic_Texture.single_texture_gl20.SingleTextureRender_GL20

/**
 *  相机预览画面
 */
class CameraGlsurfaceviewActivity : AppCompatActivity(), View.OnClickListener, CameraRender.SufacetextureListener, SurfaceTexture.OnFrameAvailableListener {

    val TAG = "CameraRender-->"

//    val cameraPreviewBottom by lazy {
//        findViewById<GLSurfaceView>(R.id.preview_bottom)
//    }

    val cameraPreviewTop by lazy {
        findViewById<GLTextureView>(R.id.preview_top)
    }

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
    val jumpBtn by lazy {
        findViewById<Button>(R.id.jumpAty)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice_7_camera_glsurfaceview)

        var render = CameraRender()
        cameraPreview.setEGLContextClientVersion(2)
        cameraPreview.setRenderer(render)
        cameraPreview.holder.setFormat(PixelFormat.TRANSPARENT);
        cameraPreview.setZOrderOnTop(false)
        cameraPreview.renderMode = RENDERMODE_CONTINUOUSLY
        render.sufacetextureListener = this

        secondFrontRender()

        startPreview.setOnClickListener(this)
        stopreview.setOnClickListener(this)
        switchPreview.setOnClickListener(this)
        jumpBtn.setOnClickListener(this)
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
            R.id.jumpAty -> {
                //cameraPreview.setZOrderOnTop(true)
                //测试离屏渲染
//                startActivity(Intent(this, SecondTopActivity::class.java))
                //测试覆盖双层GLSurfaceView
                secondFrontRender()
            }

        }
    }


    fun secondFrontRender() {
        //        val bottomRender = CameraRender()
//        cameraPreviewTop.setEGLContextClientVersion(2)
//        cameraPreviewTop.setRenderer(bottomRender)
////        cameraPreview.setZOrderOnTop(true)
//        cameraPreviewTop.renderMode = RENDERMODE_CONTINUOUSLY
//        bottomRender.sufacetextureListener = this


       val bottomRender = SingleTextureRender_GL20(this)
//        val bottomRender = PracticeRender14(this, mResources)
//        cameraPreviewTop.isOpaque = false
        cameraPreviewTop. setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        cameraPreviewTop.setEGLContextClientVersion(2)
        cameraPreviewTop.setRenderer(bottomRender)

//        cameraPreviewTop.setBackgroundColor(Color.TRANSPARENT)
//        cameraPreviewTop.setEGLConfigChooser(8, 8, 8, 8, 8)
        cameraPreviewTop.renderMode = RENDERMODE_CONTINUOUSLY
        cameraPreviewTop.onResume()
        isHasFrontRender = !isHasFrontRender
    }

    var isHasFrontRender = false

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
        if (isHasFrontRender) {
            cameraPreviewTop.requestRender()
        }
    }

    private var mResources: Resources? = null

    @SuppressLint("RestrictedApi")
    override fun getResources(): Resources? {
        if (mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            mResources = VectorEnabledTintResources(this, super.getResources())
        }
        return if (mResources == null) super.getResources() else mResources
    }
}