package com.sf.opengldemo1.practice1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.sf.opengldemo1.R;

public class Practice_1_Activity extends AppCompatActivity {
    private PracticeSurfaceView1 mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_1);
        setTitle("练习1：绘制纯色背景");
        mySurfaceView=new PracticeSurfaceView1(this);
        mySurfaceView.requestFocus();
        LinearLayout  llRoot=findViewById(R.id.main_linear);
        llRoot.addView(mySurfaceView);
    }
        @Override
        protected void onPause () {
            super.onPause();
        mySurfaceView.onPause();
        }

        @Override
        protected void onResume () {
            super.onResume();
        mySurfaceView.onResume();
        }
    }

