package com.sf.opengldemo1.practice4;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class Practice_4_Activity extends AppCompatActivity {
    private PracticeSurfaceView4 surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习4：根据手势旋转");
//        setContentView(R.layout.practice_3);
//       LinearLayout  rootView= findViewById(R.id.practice_3_linear);
//        surfaceView=new PracticeSurfaceView3(this);
//        rootView.addView(surfaceView);

        surfaceView = new PracticeSurfaceView4(this);
        setContentView(surfaceView);
    }

    /**
     *判断是否支持Opengl 2.0
     */
    public boolean   isSupportOpenGlEs20(){
         return ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion>=0x20000;

    }
}
