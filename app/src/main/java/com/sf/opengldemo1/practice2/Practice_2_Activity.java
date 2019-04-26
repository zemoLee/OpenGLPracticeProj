package com.sf.opengldemo1.practice2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.sf.opengldemo1.R;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class Practice_2_Activity extends AppCompatActivity {
    private PracticeSurfaceView2  surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习3：绘制旋转屏幕不变形的三角形");
        setContentView(R.layout.practice_2);
       LinearLayout  rootView= findViewById(R.id.practice_2_linear);
        surfaceView=new PracticeSurfaceView2(this);
        rootView.addView(surfaceView);
    }
}
