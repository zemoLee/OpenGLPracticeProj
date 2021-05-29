package com.sf

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.sf.menu.MainMenuAdapter
import com.sf.menu.MenuBean
import com.sf.opengldemo1.R
import com.sf.opengldemo1.practice1.Practice_1_Activity
import com.sf.opengldemo1.practice10.Practice_10_Activity
import com.sf.opengldemo1.practice11.Practice_11_Activity
import com.sf.opengldemo1.practice13.Practice_13_Activity
import com.sf.opengldemo1.practice14.Practice_14_Activity
import com.sf.opengldemo1.practice2.Practice_2_Activity
import com.sf.opengldemo1.practice3.Practice_3_Activity
import com.sf.opengldemo1.practice4.Practice_4_Activity
import com.sf.opengldemo1.practice5.Practice_5_Acitity
import com.sf.opengldemo1.practice6.Practice_6_Acitivty
import com.sf.opengldemo1.practice7.Practice_7_Activity
import com.sf.opengldemo1.practice8.Practice_8_Activity
import com.sf.opengldemo1.practice9.Practice_9_Activity
import com.sf.openglpractice2.Practice10_imageToVideo.ImageToVideoActivity
import com.sf.openglpractice2.Practice7_camera.CameraGlsurfaceviewActivity
import com.sf.openglpractice2.practice11_imagetransition.ImageTransitionActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var button4: Button? = null
    var button5: Button? = null
    var button6: Button? = null
    var button7: Button? = null
    var button8: Button? = null
    var button9: Button? = null
    var button10: Button? = null
    var button11: Button? = null
    var button12: Button? = null
    var button13: Button? = null
    var button14: Button? = null
    var imageToVide0Btn: Button? = null
    var imageTransitionFilterBtn: Button? = null
    var cameraPreviewBtn: Button? = null

    private var btns: Array<Button?>? = null
    private var mMenuAdapter: MainMenuAdapter? = null
    private var mFunctionList: RecyclerView? = null
    val data = ArrayList<MenuBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFunctionList = findViewById(R.id.main_recyclerview)
        mFunctionList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bindMenus()
        mMenuAdapter = MainMenuAdapter(this, data)
        mFunctionList?.adapter = mMenuAdapter
        mMenuAdapter?.menuClick = this
    }


    private fun bindMenus() {
        add("绘制纯色背景", Practice_1_Activity::class.java)
        add("绘制旋转屏幕不变形的三角形", Practice_2_Activity::class.java)
        add("绘制自动旋转三角形", Practice_3_Activity::class.java)
        add("根据手势旋转", Practice_4_Activity::class.java)
        add("绘制彩色三角形并封装", Practice_5_Acitity::class.java)
        add("绘制圆形", Practice_6_Acitivty::class.java)
        add("绘制正方形", Practice_7_Activity::class.java)
        add("绘制正方体", Practice_8_Activity::class.java)
        add("绘制圆锥体", Practice_9_Activity::class.java)
        add("绘制圆柱体", Practice_10_Activity::class.java)
        add("绘制正圆球体", Practice_11_Activity::class.java)
        add("绘制OBJ模型帽子", Practice_13_Activity::class.java)
        add("绘制OBJ带皮肤纹理的模型", Practice_14_Activity::class.java)
        add("图片转视频", ImageToVideoActivity::class.java)
        add("图片转场", ImageTransitionActivity::class.java)
        add("相机预览", CameraGlsurfaceviewActivity::class.java)
    }

    private fun add(name: String, clazz: Class<*>) {
        val bean = MenuBean()
        bean.name = name
        bean.clazz = clazz
        data.add(bean)
    }

    override fun onClick(view: View?) {
        val bean = data[view?.tag as Int]
        startActivity(Intent(this, bean.clazz))
    }
}