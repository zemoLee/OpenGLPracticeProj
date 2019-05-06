package com.sf.opengldemo1.practice3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.sf.opengldemo1.R;
import com.sf.opengldemo1.practice2.PracticeSurfaceView2;

/**
 * Created by 01373577 on 2018/6/6.
 */

public class Practice_3_Activity extends AppCompatActivity {
    private PracticeSurfaceView3 surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("练习3：绘制自动旋转三角形");
//        setContentView(R.layout.practice_3);
//       LinearLayout  rootView= findViewById(R.id.practice_3_linear);
//        surfaceView=new PracticeSurfaceView3(this);
//        rootView.addView(surfaceView);

        surfaceView = new PracticeSurfaceView3(this);
        setContentView(surfaceView);
    }
}
