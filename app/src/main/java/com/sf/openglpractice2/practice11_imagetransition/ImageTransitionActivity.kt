package com.sf.openglpractice2.practice11_imagetransition

import android.app.ActionBar
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import com.sf.opengldemo1.R

class ImageTransitionActivity : AppCompatActivity() {

    var previewGLView: GLSurfaceView? = null
    var transitionProgressBar: SeekBar? = null
    var render: ImageTransitionRender? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practice11_image_transition_filter)

        val actionBar: android.support.v7.app.ActionBar? = this.supportActionBar
        actionBar?.setTitle("金蛋蛋demo：电子相册转场效果")
        previewGLView = findViewById(R.id.transition_preview)
        transitionProgressBar = findViewById(R.id.progress_bar)
        initGLView()
        listeners()
        initResources()
    }

    private fun initResources() {
        val imgs = intArrayOf(R.drawable.from, R.drawable.to)
        render?.setImageResources(imgs)
    }

    fun initGLView() {
        render = ImageTransitionRender(this)
        previewGLView?.setEGLContextClientVersion(2)
        previewGLView?.setRenderer(render)
        previewGLView?.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    fun listeners() {
        transitionProgressBar?.max = 100
        transitionProgressBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progressNew = progress.toFloat() / (transitionProgressBar?.max?.toFloat() ?: 1f)
                render?.setProgress(progressNew)
                previewGLView?.requestRender()
                Log.i("progress","progress=${progressNew}")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}