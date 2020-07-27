package com.sf.openglpractice2.Practice8_egl

import android.graphics.SurfaceTexture
import android.opengl.EGL14.*
import android.opengl.EGLConfig
import android.opengl.EGLContext
import android.opengl.EGLDisplay
import android.opengl.EGLSurface
import android.view.Surface


class EGLHelper {

    var eglDisplay: EGLDisplay? = null
    var eglConfig: EGLConfig? = null
    var eglContext: EGLContext? = null
    var mEglSurface: EGLSurface? = null

    fun createEGL(): EGLContext? {
        //1.获取显示器
        eglDisplay = eglGetDisplay(EGL_DEFAULT_DISPLAY)
        if (eglDisplay === EGL_NO_DISPLAY) {
            throw RuntimeException("eglGetDisplay error:" + eglGetError())
        }

        //2.初始化EGL
        val version = IntArray(2)
        if (!eglInitialize(eglDisplay, version, 0, version, 1)) {
            throw RuntimeException("eglInitialize error:" + eglGetError())
        }

        //3.EGL选择配置,构造需要的配置列表
        val configAttribList = intArrayOf(
                EGL_BUFFER_SIZE, 32,
                EGL_ALPHA_SIZE, 8,
                EGL_BLUE_SIZE, 8,
                EGL_GREEN_SIZE, 8,
                EGL_RED_SIZE, 8,
                EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT,
                EGL_SURFACE_TYPE, EGL_WINDOW_BIT,
                EGL_NONE
        )
        val configsNum = IntArray(1)
        val configs: Array<EGLConfig?> = arrayOfNulls<EGLConfig>(1)
        ////EGL根据attributes选择最匹配的配置
        if (!eglChooseConfig(eglDisplay,
                        configAttribList, 0,
                        configs, 0, configs.size,
                        configsNum, 0)) {
            throw RuntimeException("eglChooseConfig error:" + eglGetError())
        }
        eglConfig = configs[0]


        //创建glsurface，渲染表面


        //4.指定哪个版本的OpenGL ES上下文，2.0
        val contextAttribList = intArrayOf(
                EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL_NONE
        )
        //创建上下文，EGL10.EGL_NO_CONTEXT表示不和别的上下文共享资源
        eglContext = eglCreateContext(eglDisplay, eglConfig, EGL_NO_CONTEXT, contextAttribList, 0)
        if (eglContext === EGL_NO_CONTEXT) {
            throw RuntimeException("egl error:" + eglGetError())
        }
        return eglContext
    }

    /**
     * 根据surfaceTexture创建渲染表面
     * @param surface Surface?
     * @return EGLSurface
     */
    fun createSurface(surfaceTexture: SurfaceTexture?): EGLSurface? {
        //创建屏幕上渲染区域：EGL窗口
        val surfaceAttribList = intArrayOf(EGL_NONE)
        mEglSurface = eglCreateWindowSurface(eglDisplay, eglConfig, surfaceTexture, surfaceAttribList, 0)
        return mEglSurface


    }


    /**
     * 指定mEGLContext为当前系统的EGL上下文，你可能发现了使用两个mEglSurface，第一个表示绘图表面，第二个表示读取表面
     * @param eglSurface EGLSurface
     */
    fun makeCurrent() {
        eglMakeCurrent(eglDisplay, mEglSurface, mEglSurface, eglContext)
    }

    /**
     * 交换显存(将surface显存和显示器的显存交换)
     * @param eglSurface EGLSurface
     */
    fun swapBufferSufaceToDisplayShow() {
        //交换 surface 和显示器缓存
        eglSwapBuffers(eglDisplay, mEglSurface)
    }

    /**
     * 释放 suface
     */
    fun releaseDispalySurface() {
        eglDestroySurface(eglDisplay, mEglSurface)

    }

    fun destroyEGL() {
        eglDestroyContext(eglDisplay, eglContext);
        eglContext = EGL_NO_CONTEXT;
        eglDisplay = EGL_NO_DISPLAY;
    }


}