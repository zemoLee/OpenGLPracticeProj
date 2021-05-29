package com.sf.opengldemo1.practice1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sf.opengldemo1.R;

public class Practice_1_Activity extends AppCompatActivity {
    private PracticeSurfaceView1 mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_1);
        setTitle("练习1：绘制纯色背景");
        mySurfaceView = new PracticeSurfaceView1(this);
        mySurfaceView.requestFocus();
        @SuppressLint("WrongViewCast") RelativeLayout llRoot = findViewById(R.id.main_linear);
        llRoot.addView(mySurfaceView);
//        init();
    }


    private void init() {
        ExpandMenuView2 expandMenuView = findViewById(R.id.expanded_menu);
        expandMenuView.setOnExpandMenuItemClickListener(new ExpandMenuView2.OnExpandMenuItemClickListener() {
            @Override
            public void onItemClick(View v) {
                switch (v.getId()) {
                    case R.id.btn1:
                        Toast.makeText(Practice_1_Activity.this, "btn1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn2:
                        Toast.makeText(Practice_1_Activity.this, "btn2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn3:
                        Toast.makeText(Practice_1_Activity.this, "btn3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.onResume();
    }
}

