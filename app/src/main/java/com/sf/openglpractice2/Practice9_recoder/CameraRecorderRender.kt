package com.sf.openglpractice2.Practice9_recoder

import android.graphics.SurfaceTexture
import android.opengl.*
import android.os.Environment
import android.util.Log
import com.sf.openglpractice2.Practice9_recoder.recoder2.TextureMovieEncoder
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * 相机渲染器
 */
class CameraRecorderRender : GLSurfaceView.Renderer {
    companion object {
        val TAG = "CameraRender-->"


        /**
         * 顶点shader
         */
        val vertexShaderCode =
                "uniform mat4 textureTransform;\n" +
                        "attribute vec2 inputTextureCoordinate;\n" +
                        "attribute vec4 position;            \n" +//NDK坐标点
                        "varying   vec2 textureCoordinate; \n" +//纹理坐标点变换后输出
                        "\n" +
                        " void main() {\n" +
                        "     gl_Position = position;\n" +
                        "     textureCoordinate = inputTextureCoordinate;\n" +
                        " }"


        /**
         * 片元shader
         */
        val fragmentShaderCode =
                "#extension GL_OES_EGL_image_external : require\n" +
                        "precision mediump float;\n" +
                        "uniform samplerExternalOES videoTex;\n" +
                        "varying vec2 textureCoordinate;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 tc = texture2D(videoTex, textureCoordinate);\n" +
                        "    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n" +  //所有视图修改成黑白
                        "    gl_FragColor = vec4(color,color,color,1.0);\n" +
//                "    gl_FragColor = vec4(tc.r,tc.g,tc.b,1.0);\n" +
                        "}\n"

    }


    interface SufacetextureListener {
        fun onSufaceTextureInit(surfaceTexture: SurfaceTexture)
    }

    /**
     * surfacetexute监听回调
     */
    var sufacetextureListener: SufacetextureListener? = null
    /**
     * surface纹理，承接相机数据
     */
    var mSurfaceTexture: SurfaceTexture? = null

    /**
     * shader程序
     */
    var mProgram: Int? = null

    /**
     * shader程序的顶点句柄
     */
    var uPosHandle: Int? = null

    /**
     * shader程序的纹理句柄
     */
    var aTexHandle: Int? = null

    /**
     * 视图模型矩阵
     */
    var mMVPMatrixHandle: Int? = null

    /**
     * 顶点buffer
     */
    private var mPosBuffer: FloatBuffer? = null
    /**
     * 纹理buffer
     */
    private var mTexBuffer: FloatBuffer? = null

    /**
     * 顶点坐标
     */
    private var mPosCoordinate = floatArrayOf(-1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f)
    /**
     * 纹理坐标
     */
    private var mTexCoordinateBackRight = floatArrayOf(1f, 1f, 0f, 1f, 1f, 0f, 0f, 0f)
    private var mTexCoordinateForntRight = floatArrayOf(0f, 1f, 1f, 1f, 0f, 0f, 1f, 0f)

    /**
     * 矩阵
     */
    private var mProjectMatrix = FloatArray(16)
    private var mCameraMatrix = FloatArray(16)
    private var mMVPMatrix = FloatArray(16)
    private var mTempMatrix = FloatArray(16)


    /**
     * 录制
     */
    private var mVideoEncoder: TextureMovieEncoder? = null
    private var mRecordingEnabled = false
    private var mRecordingStatus = 0
    private val RECORDING_OFF = 0
    private val RECORDING_ON = 1
    private val RECORDING_RESUMED = 2
    private var mOutputFile: File? = null
    private var mPreviewWidth = 0
    private var mPreviewHeight = 0

    init {
        Matrix.setIdentityM(mProjectMatrix, 0);
        Matrix.setIdentityM(mCameraMatrix, 0);
        Matrix.setIdentityM(mMVPMatrix, 0);
        Matrix.setIdentityM(mTempMatrix, 0);

        mVideoEncoder = TextureMovieEncoder()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG, "[onSurfaceCreated]")

        mRecordingEnabled = mVideoEncoder?.isRecording ?: false
        mRecordingStatus = if (mRecordingEnabled) {
            RECORDING_RESUMED
        } else {
            RECORDING_OFF
        }


        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        //创建oes外部纹理，给出回调，外部获取sufaceTexure
        mSurfaceTexture = createSufaceTexure()

        //创建、编译、链接shader程序
        creatProgram()

        //添加shader程序到opengl环境中，并获取句柄
        useProgram()
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG, "[onSurfaceChanged] width:$width  height:$height")

        mPreviewWidth = width
        mPreviewHeight = height

        //准备好一些离屏buffer ,
        prepareFramebuffer(mPreviewWidth,mPreviewHeight)

        GLES20.glViewport(0, 0, width, height)
        Matrix.scaleM(mMVPMatrix, 0, 1f, -1f, 1f)
        val ratio = width * 1f / height * 1f

        Matrix.orthoM(mProjectMatrix, 0, -1f, 1f, -ratio, ratio, 1f, 7f)// 3和7代表远近视点与眼睛的距离，非坐标点
        Matrix.setLookAtM(mCameraMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)// 3代表眼睛的坐标点
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mCameraMatrix, 0)
    }


    private val transfrom = FloatArray(16)

    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG, "[onDrawFrame]")

//        useProgram()

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)



        //srufaceTexture 获取新的纹理数据
        mSurfaceTexture?.updateTexImage()
        mSurfaceTexture?.getTransformMatrix(transfrom)



        /**
         * 录制
         */
        if (mRecordingEnabled) {
            when (mRecordingStatus) {
                RECORDING_OFF -> {
                    Log.d(TAG, "START recording")
                    mOutputFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "mycamera-record-test" + System.currentTimeMillis() + ".mp4")
                    Log.d(TAG, "file path = " + mOutputFile?.getAbsolutePath())
                    // start recording
                    mVideoEncoder!!.startRecording(TextureMovieEncoder.EncoderConfig(
                            mOutputFile, mPreviewHeight, mPreviewWidth, 1000000, EGL14.eglGetCurrentContext()))
                    mRecordingStatus = RECORDING_ON
                }
                RECORDING_RESUMED -> {
                    Log.d(TAG, "RESUME recording")
                    mVideoEncoder!!.updateSharedContext(EGL14.eglGetCurrentContext())
                    mRecordingStatus = RECORDING_ON
                }
                RECORDING_ON -> {
                }
                else -> throw RuntimeException("unknown status $mRecordingStatus")
            }
        } else {
            when (mRecordingStatus) {
                RECORDING_ON, RECORDING_RESUMED -> {
                    // stop recording
                    Log.d(TAG, "STOP recording")
                    mVideoEncoder!!.stopRecording()
                    mRecordingStatus = RECORDING_OFF
                }
                RECORDING_OFF -> {
                }
                else -> throw RuntimeException("unknown status $mRecordingStatus")
            }
        }

        //绑定上。就会绘制到fbo中， ---》重新切换到绑定的framebuffer，后续的绘制，就会绘制到这个缓冲区中
//        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer)
//
//        //原始oes绘制
//        mMVPMatrixHandle?.let { GLES20.glUniformMatrix4fv(it, 1, false, mMVPMatrix, 0) }
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mPosCoordinate.size / 2)
//
//        //解除绑定
//        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)


//        //原始oes绘制
        mMVPMatrixHandle?.let { GLES20.glUniformMatrix4fv(it, 1, false, mMVPMatrix, 0) }
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mPosCoordinate.size / 2)


        mVideoEncoder!!.setTextureId(mTextureId)
//        mVideoEncoder!!.setTextureId(mOffscreenTextureId)

        mVideoEncoder!!.frameAvailable(mSurfaceTexture)



    }

    /**
     * 创建surfaceTexure，用于相机数据
     * @return SurfaceTexture
     */
    private var mTextureId: Int = 0

    fun createSufaceTexure(): SurfaceTexture {

        Log.d(TAG, "[createSufaceTexure]")

        //生成纹理
        mTextureId = createOESTextureObject()
        //创建内部的surfaceView
//        mSurfaceTexture = SurfaceTexture(mTextureId)

        val surfaceTexture = SurfaceTexture(mTextureId)

        //初始化sufaceTexure 回调
        surfaceTexture.let { sufacetextureListener?.onSufaceTextureInit(it) }

        return surfaceTexture
    }

    /**
     * 创建opengl可执行程序，
     * 1.顶点程序
     * 2.片元程序
     */
    private fun creatProgram() {
        Log.d(TAG, "[creatProgram]")

        //先编译shader代码资源
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        //创建opengl 可执行程序
        mProgram = GLES20.glCreateProgram()

        //添加着色器程序到opengl程序
        mProgram?.let { GLES20.glAttachShader(it, vertexShader) }
        mProgram?.let { GLES20.glAttachShader(it, fragmentShader) }

        //编译链接opengl程序
        mProgram?.let { GLES20.glLinkProgram(it) }

        //释放shader资源
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader)

    }

    /**
     *  使用程序
     */
    private fun useProgram() {
        Log.d(TAG, "[useProgram]")

        mProgram?.let { GLES20.glUseProgram(it) }

        uPosHandle = mProgram?.let { GLES20.glGetAttribLocation(it, "position") };
        aTexHandle = mProgram?.let { GLES20.glGetAttribLocation(it, "inputTextureCoordinate") }
        mMVPMatrixHandle = mProgram?.let { GLES20.glGetUniformLocation(it, "textureTransform") }

        //顶点数据
        mPosBuffer = convertToFloatBuffer(mPosCoordinate)

        //纹理数据（后置）
        mTexBuffer = convertToFloatBuffer(mTexCoordinateBackRight)

        uPosHandle?.let { GLES20.glVertexAttribPointer(it, 2, GLES20.GL_FLOAT, false, 0, mPosBuffer) }
        aTexHandle?.let { GLES20.glVertexAttribPointer(it, 2, GLES20.GL_FLOAT, false, 0, mTexBuffer) }

        // 启用顶点位置的句柄
        uPosHandle?.let { GLES20.glEnableVertexAttribArray(it) }
        aTexHandle?.let { GLES20.glEnableVertexAttribArray(it) }
    }


    /**
     * 创建外部纹理对象，使用相机时，都是使用外部纹理
     * @return Boolean
     */
    private fun createOESTextureObject(): Int {
        Log.d(TAG, "[createOESTextureObject]")

        //创建一个接收相机数据的纹理
        val textureArray = IntArray(1)
        //生成一个纹理
        GLES20.glGenTextures(1, textureArray, 0)
        //绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureArray[0])
        //设置纹理过滤参数
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST.toFloat())
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE.toFloat())
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE.toFloat())
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0)
        return textureArray[0]
    }

    /**
     * 加载和 编译 shader 程序片段
     * @param type Int
     * @param shaderCode String
     * @return Int
     */
    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

    /**
     * 转换数据为buffer数据
     * @param buffer FloatArray
     * @return FloatBuffer
     */
    fun convertToFloatBuffer(buffer: FloatArray): FloatBuffer {
        val fb = ByteBuffer.allocateDirect(buffer.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        fb.put(buffer)
        fb.position(0)
        return fb
    }


    fun changeRecordingState(isRecording: Boolean) {
        Log.d(TAG, "changeRecordingState: was $mRecordingEnabled now $isRecording")
        mRecordingEnabled = isRecording
    }

    fun setPreviewSize(previewWidth: Int, previewHeight: Int) {
        mPreviewWidth = previewWidth
        mPreviewHeight = previewHeight
//        calculateMatrix()
    }

    private var mOffscreenTextureId = 0
    private var mFrameBuffer = 0
    private var mRenderBuffer = 0
    private fun prepareFramebuffer(width: Int, height: Int) {
        val values = IntArray(1)
        values[0] = 55
        GLES20.glGenTextures(1, values, 0)
        //        GlUtil.checkGlError("glGenTextures");
        mOffscreenTextureId = values[0]
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mOffscreenTextureId)
        //        GlUtil.checkGlError("glBindTexture " + mOffscreenTextureId);
// Create texture storage.
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
        // Set parameters.  We're probably using non-power-of-two dimensions, so
// some values may not be available for use.
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST.toFloat())
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR.toFloat())
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE)
        //        GlUtil.checkGlError("glTexParameter");
// Create framebuffer object and bind it.
        GLES20.glGenFramebuffers(1, values, 0)
        mFrameBuffer = values[0]
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffer)
        //        GlUtil.checkGlError("glBindFramebuffer " + mFrameBuffer);
// Create a depth buffer and bind it.
        GLES20.glGenRenderbuffers(1, values, 0)
        mRenderBuffer = values[0]
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, mRenderBuffer)
        //        GlUtil.checkGlError("glBindFramebuffer " + mRenderBuffer);
// Allocate storage for the depth buffer.
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width, height)
        //        GlUtil.checkGlError("glRenderbufferStorage");
// Attach the depth buffer and the texture (color buffer) to the framebuffer object.
// 将renderBuffer挂载到frameBuffer的depth attachment 上
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, mRenderBuffer)
        //        GlUtil.checkGlError("glFramebufferRenderbuffer");
// 将text2d挂载到frameBuffer的color attachment上
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mOffscreenTextureId, 0)
        //        GlUtil.checkGlError("glFramebufferTexture2D");
// See if GLES is happy with all this.
        val status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER)
        if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            throw java.lang.RuntimeException("Framebuffer not complete, status=$status")
        }
        // 先不使用FrameBuffer
// Switch back to the default framebuffer.
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        //        GlUtil.checkGlError("prepareFramebuffer done");
    }
}