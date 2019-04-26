package com.sf.opengldemo1.practice14;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.VectorEnabledTintResources;

import com.sf.opengldemo1.practice14.objhelper.ObjReader;
import com.sf.opengldemo1.practice14.shaderprogram.ObjFilter2;
import com.sf.opengldemo1.practice14.view.Obj3D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class Practice_14_Activity extends AppCompatActivity {
    private PracticeSurfaceView14 surfaceView14;
    private PracticeRender14 render14;
    private List<ObjFilter2> filters;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习14：绘制OBJ带皮肤纹理的模型");
//        List<Obj3D> model3dObjs= ObjReader.readMultiObj(this,"assets/3dobj/practice_14_pikachu.obj");
//        filters=new ArrayList<>();
//        for (int i=0;i<model3dObjs.size();i++){
//            ObjFilter2 f=new ObjFilter2(this);
//            f.setObj3D(model3dObjs.get(i));
//            filters.add(f);
//        }
//        render14. setFilters(filters);
        surfaceView14 = new PracticeSurfaceView14(this);
        render14 = new PracticeRender14(this,getResources());
        if (isSupportGLES20()) {
            surfaceView14.setEGLContextClientVersion(2);
            surfaceView14.setRenderer(render14);
            surfaceView14.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        }
        setContentView(surfaceView14);
    }

    private boolean isSupportGLES20() {

        return ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 0x2000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView14.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView14.onPause();
    }


    private Resources mResources;
    @SuppressLint("RestrictedApi")
    @Override
    public Resources getResources() {
        if (mResources == null && VectorEnabledTintResources.shouldBeUsed()) {
            mResources = new VectorEnabledTintResources(this, super.getResources());
        }
        return mResources == null ? super.getResources() : mResources;
    }
}
