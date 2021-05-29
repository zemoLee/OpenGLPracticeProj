package com.sf.openglpractice2.practice11_imagetransition

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import com.sf.openglpractice2.Practice9_recoder.CameraRecorderRender
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ImageTransitionRender(val context: Context) : GLSurfaceView.Renderer {

    companion object {
        val TAG = "ImageTransitionRender"
        val V_SHADER = "uniform mat4 textureTransform;\n" +
                "attribute vec2 a_textureCoordinate;\n" +
                "attribute vec4 a_position;            \n" +
                "varying   vec2 vTextureCoord; \n" +
                "\n" +
                " void main() {\n" +
                "     gl_Position = a_position;\n" +
                "     vTextureCoord = a_textureCoordinate;\n" +
                " }"

//        var SHADER_METHOD =
////                "uniform float speed=2.0; // = 2.0;\n" +
//                "\n" +
//                "vec4 transition(vec2 uv) {\n" +
//                "  \n" +
//                "  vec2 p = uv.xy / vec2(1.0).xy;\n" +
//                "  \n" +
//                "  float circPos = atan(p.y - 0.5, p.x - 0.5) + progress * speed;\n" +
//                "  float modPos = mod(circPos, 3.1415 / 4.);\n" +
//                "  float signed = sign(progress - modPos);\n" +
//                "  \n" +
//                "  return mix(getToColor(p), getFromColor(p), step(signed, 0.5));\n" +
//                "  \n" +
//                "}\n"
//
//        val T_SHADER = "precision mediump float;\n" +
//                "uniform float speed=2.0; "+
//                "uniform sampler2D from, to;\n" +
//                "uniform float progress;\n" +
//                "varying vec2 vTextureCoord;\n" +
//                "\n" +
//                "// HSV functions are from http://lolengine.net/blog/2013/07/27/rgb-to-hsv-in-glsl\n" +
//                "\n" +
//                "vec3 hsv2rgb(vec3 c) {\n" +
//                "    const vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n" +
//                "    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n" +
//                "    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n" +
//                "}\n" +
//                "\n" +
//                "vec3 rgb2hsv(vec3 c) {\n" +
//                "    const vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n" +
//                "    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n" +
//                "    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n" +
//                "\n" +
//                "    float d = q.x - min(q.w, q.y);\n" +
//                "    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + 0.001)), d / (q.x + 0.001), q.x);\n" +
//                "}\n" +
//
//                SHADER_METHOD +
//
//                "\n" +
//                "void main() {\n" +
//                "  vec3 a = rgb2hsv(texture2D(from, vTextureCoord).rgb);\n" +
//                "  vec3 b = rgb2hsv(texture2D(to, vTextureCoord).rgb);\n" +
//                "  vec3 m = mix(a, b, progress);\n" +
////                "  gl_FragColor = vec4(hsv2rgb(m), 1.0);\n" +
//                "  gl_FragColor = transition(vTextureCoord.x,vTextureCoord.y);\n" +
//                "}"


//        val T_SHADER="precision mediump float;\n" +
//                "uniform sampler2D from,to;\n" +
//                "uniform float progress;\n" +
//                "varying vec2 vTextureCoord;\n" +
//                "\n" +
//                "vec2 offset(float progress, float x, float theta) {\n" +
//                "  float phase = progress*progress + progress + theta;\n" +
//                "  float shifty = 0.03*progress*cos(10.0*(progress+x));\n" +
//                "  return vec2(0, shifty);\n" +
//                "}\n" +
//                "\n" +
//                "void main(){\n" +
//                " gl_FragColor = mix(texture2D(from,vTextureCoord+offset(progress,vTextureCoord.x,0.0)),texture2D(to,vTextureCoord+offset(1.0-progress,vTextureCoord.x,3.14)),progress);\n" +
//                "}"

//        val T_SHADER =
//                "precision mediump float;\n" +
//                        "uniform sampler2D from,to;\n" +
//                        "uniform float progress;\n" +
//                        "varying vec2 vTextureCoord;\n" +
//                        "uniform int steps=50; // = 50;\n" +
//                        "uniform float horizontalHexagons=20; //= 20;\n" +
//                        "\n" +
//                        "struct Hexagon {\n" +
//                        "  float q;\n" +
//                        "  float r;\n" +
//                        "  float s;\n" +
//                        "};\n" +
//                        "\n" +
//                        "Hexagon createHexagon(float q, float r){\n" +
//                        "  Hexagon hex;\n" +
//                        "  hex.q = q;\n" +
//                        "  hex.r = r;\n" +
//                        "  hex.s = -q - r;\n" +
//                        "  return hex;\n" +
//                        "}\n" +
//                        "\n" +
//                        "Hexagon roundHexagon(Hexagon hex){\n" +
//                        "  \n" +
//                        "  float q = floor(hex.q + 0.5);\n" +
//                        "  float r = floor(hex.r + 0.5);\n" +
//                        "  float s = floor(hex.s + 0.5);\n" +
//                        "\n" +
//                        "  float deltaQ = abs(q - hex.q);\n" +
//                        "  float deltaR = abs(r - hex.r);\n" +
//                        "  float deltaS = abs(s - hex.s);\n" +
//                        "\n" +
//                        "  if (deltaQ > deltaR && deltaQ > deltaS)\n" +
//                        "    q = -r - s;\n" +
//                        "  else if (deltaR > deltaS)\n" +
//                        "    r = -q - s;\n" +
//                        "  else\n" +
//                        "    s = -q - r;\n" +
//                        "\n" +
//                        "  return createHexagon(q, r);\n" +
//                        "}\n" +
//                        "\n" +
//                        "Hexagon hexagonFromPoint(vec2 point, float size) {\n" +
//                        "  \n" +
//                        "  point.y /= ratio;\n" +
//                        "  point = (point - 0.5) / size;\n" +
//                        "  \n" +
//                        "  float q = (sqrt(3.0) / 3.0) * point.x + (-1.0 / 3.0) * point.y;\n" +
//                        "  float r = 0.0 * point.x + 2.0 / 3.0 * point.y;\n" +
//                        "\n" +
//                        "  Hexagon hex = createHexagon(q, r);\n" +
//                        "  return roundHexagon(hex);\n" +
//                        "  \n" +
//                        "}\n" +
//                        "\n" +
//                        "vec2 pointFromHexagon(Hexagon hex, float size) {\n" +
//                        "  \n" +
//                        "  float x = (sqrt(3.0) * hex.q + (sqrt(3.0) / 2.0) * hex.r) * size + 0.5;\n" +
//                        "  float y = (0.0 * hex.q + (3.0 / 2.0) * hex.r) * size + 0.5;\n" +
//                        "  \n" +
//                        "  return vec2(x, y * ratio);\n" +
//                        "}\n" +
//                        "\n" +
//                        "vec4 transition (vec2 uv) {\n" +
//                        "  \n" +
//                        "  float dist = 2.0 * min(progress, 1.0 - progress);\n" +
//                        "  dist = steps > 0 ? ceil(dist * float(steps)) / float(steps) : dist;\n" +
//                        "  \n" +
//                        "  float size = (sqrt(3.0) / 3.0) * dist / horizontalHexagons;\n" +
//                        "  \n" +
//                        "  vec2 point = dist > 0.0 ? pointFromHexagon(hexagonFromPoint(uv, size), size) : uv;\n" +
//                        "\n" +
//                        "  return mix(getFromColor(point), getToColor(point), progress);\n" +
//                        "  \n" +
//                        "}\n" +
//                        "void main(){\n" +
//                        " gl_FragColor =transition(vTextureCoord);\n" +
//                        "}\n"

        val T_SHADER =
//                "precision mediump float;\n" +
//                "\n" +
//                "// General parameters\n" +
//                "uniform sampler2D from,to;\n" +
//                "uniform float progress;\n" +
//                "varying vec2 vTextureCoord;\n" +
//                "\n" +
//                "const float amplitude = 30.0;\n" +
//                "const float speed = 30.0;\n" +
//                "\n" +
//                "void main()\n" +
//                "{\n" +
//                "  vec2 dir = vTextureCoord - vec2(.5);\n" +
//                "  float dist = length(dir);\n" +
//                "\n" +
//                "  if (dist > progress) {\n" +
//                "    gl_FragColor = mix(texture2D(from, vTextureCoord), texture2D(to, vTextureCoord), progress);\n" +
//                "  } else {\n" +
//                "    vec2 offset = dir * sin(dist * amplitude - progress * speed);\n" +
//                "    gl_FragColor = mix(texture2D(from, vTextureCoord + offset), texture2D(to, vTextureCoord), progress);\n" +
//                "  }\n" +
//                "}"
                        "precision mediump float;\n" +
                        "uniform sampler2D from,to;\n" +
                        "uniform float progress;\n" +
                        "varying vec2 vTextureCoord;\n" +
                        "const float amplitude = 30.0;\n" +
                        "const float speed = 30.0;\n" +
                        "\n" +


                        "void transition3(vec2 p) {\n" +
                        "  vec2 dir = p - vec2(.5);\n" +
                        "  float dist = length(dir);\n" +
                        "\n" +
                        "  if (dist > progress) {\n" +
                        "    gl_FragColor= mix(texture2D(from, p), texture2D(to p), progress);\n" +
                        "  } else {\n" +
                        "    vec2 offset = dir * sin(dist * amplitude - progress * speed);\n" +
                        "    gl_FragColor= mix(texture2D(from, p + offset), texture2D(to, p), progress);\n" +
                        "  }\n" +
                        "}\n" +

                        "void main(){\n" +
//                        " gl_FragColor =transition(vTextureCoord);\n" +
                        " transition3(vTextureCoord);\n" +

//                        "  vec2 dir = vTextureCoord - vec2(.5);\n" +
//                        "  float dist = length(dir);\n" +
//                        "\n" +
//                        "  if (dist > progress) {\n" +
//                        "    gl_FragColor = mix(texture2D(from, vTextureCoord), texture2D(to, vTextureCoord), progress);\n" +
//                        "  } else {\n" +
//                        "    vec2 offset = dir * sin(dist * amplitude - progress * speed);\n" +
//                        "    gl_FragColor = mix(texture2D(from, vTextureCoord + offset), texture2D(to, vTextureCoord), progress);\n" +
//                        "  }\n" +


                        "}\n"


    }

    var aPoint = 1f

    //顶点坐标
    var vertexArray = floatArrayOf(
            -aPoint, aPoint,
            -aPoint, -aPoint,
            aPoint, -aPoint,
            aPoint, -aPoint,
            aPoint, aPoint,
            -aPoint, aPoint
    )
    var textCoordArray = floatArrayOf(
            0f, 0f,
            0f, 1f,
            1f, 1f,

            1f, 1f,
            1f, 0f,
            0f, 0f
    )
    var vertexBuffer: FloatBuffer? = null
    var textCoordBuffer: FloatBuffer? = null
    var mProgram: Int = 0

    var a_Position = 0
    var a_TextureCood = 0
    var u_ColorLocation = 0
    var u_Progress = 0
    var u_From = 0
    var u_To = 0


    private var resArray = IntArray(2)
    private var textureIds: IntArray? = null

    private var progress: Float = 0f


    fun setImageResources(res: IntArray) {
        resArray = res
    }

    fun setProgress(gress: Float) {
        this.progress = gress
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.i("draw", "onDrawFrame。。。")
        //清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glUseProgram(mProgram)

        //传值
        GLES20.glUniform1f(u_Progress, progress)

        //激活0号纹理单元（0层）
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        //把0号纹理单元，传递给gpu的采样器uniform、  from纹理
        GLES20.glUniform1i(u_From, 0)
        //绑定上纹理1，前一张图片纹理
        textureIds?.get(0)?.let { GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, it) }

        //激活1号纹理单元 1层
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1)
        //将1号纹理单元，传递给gpu
        GLES20.glUniform1i(u_To, 1)
        textureIds?.get(1)?.let { GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, it) }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexArray.size / 2)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //清屏
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDisable(GLES20.GL_CULL_FACE)

        //加载纹理
        loadTexturesByRes()

        //program
        creatProgram()

        //use
        useProgram()
    }

    private fun loadTexturesByRes() {
        textureIds = TextureHelper.loadTexture(context, resArray)
    }

    private fun creatProgram() {
        Log.d(TAG, "[creatProgram]")

        //先编译shader代码资源
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, V_SHADER)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, T_SHADER)

        //创建opengl 可执行程序
        mProgram = GLES20.glCreateProgram()

        //添加着色器程序到opengl程序
        mProgram.let { GLES20.glAttachShader(it, vertexShader) }
        mProgram.let { GLES20.glAttachShader(it, fragmentShader) }

        //编译链接opengl程序
        mProgram.let { GLES20.glLinkProgram(it) }

        //释放shader资源
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader)

    }


    /**
     *  使用程序
     */
    private fun useProgram() {
        Log.d(CameraRecorderRender.TAG, "[useProgram]")

        mProgram.let { GLES20.glUseProgram(it) }

        //句柄
        a_Position = mProgram.let { GLES20.glGetAttribLocation(it, "a_position") }
        a_TextureCood = mProgram.let { GLES20.glGetAttribLocation(it, "a_textureCoordinate") }
        u_ColorLocation = GLES20.glGetUniformLocation(mProgram, "u_color")
        u_Progress = GLES20.glGetUniformLocation(mProgram, "progress")
        u_From = GLES20.glGetUniformLocation(mProgram, "from")
        u_To = GLES20.glGetUniformLocation(mProgram, "to")

        //数据
        vertexBuffer = convertToFloatBuffer(vertexArray)
        textCoordBuffer = convertToFloatBuffer(textCoordArray)

        //往gpu传递数据
        a_Position.let { GLES20.glVertexAttribPointer(it, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer) }
        a_TextureCood.let { GLES20.glVertexAttribPointer(it, 2, GLES20.GL_FLOAT, false, 0, textCoordBuffer) }

        // 启用顶点位置的句柄
        a_Position.let { GLES20.glEnableVertexAttribArray(it) }
        a_TextureCood.let { GLES20.glEnableVertexAttribArray(it) }
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

}