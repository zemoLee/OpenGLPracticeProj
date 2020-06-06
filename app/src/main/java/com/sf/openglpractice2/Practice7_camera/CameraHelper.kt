package com.sf.openglpractice2.Practice7_camera

import android.graphics.SurfaceTexture
import android.hardware.Camera

object CameraHelper {

    const val CAMERA_BACK = 0
    const val CAMERA_FRONT = 1
    var CAMERA_CURRENT = CAMERA_FRONT

    var camera: Camera? = null
    /**
     * 打开相机
     */
    fun openCamera() {
        if (camera == null) {
            camera = Camera.open(CAMERA_BACK)
        }
        CAMERA_CURRENT = CAMERA_BACK
    }

    /**
     * 停止相机
     */
    fun stopCamera() {
        camera?.stopPreview()
        camera?.release()
        camera = null
    }

    /**
     * 切换
     */
    fun switchCamera() {
        stopCamera()
        if (CAMERA_CURRENT == CAMERA_BACK) {
            camera = Camera.open(CAMERA_FRONT)
            CAMERA_CURRENT = CAMERA_FRONT
        } else {
            camera = Camera.open(CAMERA_BACK)
            CAMERA_CURRENT = CAMERA_BACK
        }
        startPreview()
    }

    /**
     * 开启预览
     * @param texture SurfaceTexture
     */
    fun startPreview() {
        texture?.let { setPreviewTexture(it) }
        camera?.startPreview()
    }

    /**
     * 设置预览纹理
     * @param texture SurfaceTexture
     */
    var texture: SurfaceTexture? = null

    fun setPreviewTexture(texture: SurfaceTexture) {
        this.texture = texture
        camera?.setPreviewTexture(texture)
    }


}