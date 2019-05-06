package com.sf.opengldemo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sf.opengldemo1.practice1.Practice_1_Activity;
import com.sf.opengldemo1.practice10.Practice_10_Activity;
import com.sf.opengldemo1.practice11.Practice_11_Activity;
import com.sf.opengldemo1.practice12.Practice_12_Activity;
import com.sf.opengldemo1.practice13.Practice_13_Activity;
import com.sf.opengldemo1.practice14.Practice_14_Activity;
import com.sf.opengldemo1.practice2.Practice_2_Activity;
import com.sf.opengldemo1.practice3.Practice_3_Activity;
import com.sf.opengldemo1.practice4.Practice_4_Activity;
import com.sf.opengldemo1.practice5.Practice_5_Acitity;
import com.sf.opengldemo1.practice6.Practice_6_Acitivty;
import com.sf.opengldemo1.practice7.Practice_7_Activity;
import com.sf.opengldemo1.practice8.Practice_8_Activity;
import com.sf.opengldemo1.practice9.Practice_9_Activity;
import com.sf.utils.IntentUtils;

public class MainActivity extends AppCompatActivity {
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14;
    private   Button []  btns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=   findViewById(R.id.practice_btn1);
        button2= findViewById(R.id.practice_btn2);
        button3=findViewById(R.id.practice_btn3);
        button4=findViewById(R.id.practice_btn4);
        button5=findViewById(R.id.practice_btn5);
        button6=findViewById(R.id.practice_btn6);
        button7=findViewById(R.id.practice_btn7);
        button8=findViewById(R.id.practice_btn8);
        button9=findViewById(R.id.practice_btn9);
        button10=findViewById(R.id.practice_btn10);
        button11=findViewById(R.id.practice_btn11);
        button12=findViewById(R.id.practice_btn12);
        button13=findViewById(R.id.practice_btn13);
        button14=findViewById(R.id.practice_btn14);
        btns=new Button[]{button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14};
        setListeners();
    }

    private void setListeners() {
        for(int i=0;i<btns.length;i++){
            btns[i].setOnClickListener(clickListener);
        }
    }

    private View.OnClickListener  clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.practice_btn1:
                    IntentUtils.showIntent(MainActivity.this,Practice_1_Activity.class);
                break;
                case R.id.practice_btn2:
                    IntentUtils.showIntent(MainActivity.this,Practice_2_Activity.class);
                    break;
                case R.id.practice_btn3:
                    IntentUtils.showIntent(MainActivity.this,Practice_3_Activity.class);
                    break;
                case R.id.practice_btn4:
                    IntentUtils.showIntent(MainActivity.this,Practice_4_Activity.class);
                    break;
                case R.id.practice_btn5:
                    IntentUtils.showIntent(MainActivity.this,Practice_5_Acitity.class);
                    break;
                case R.id.practice_btn6:
                    IntentUtils.showIntent(MainActivity.this,Practice_6_Acitivty.class);
                    break;
                    case R.id.practice_btn7:
                    IntentUtils.showIntent(MainActivity.this,Practice_7_Activity.class);
                    break;
                case R.id.practice_btn8:
                    IntentUtils.showIntent(MainActivity.this,Practice_8_Activity.class);
                    break;
                case R.id.practice_btn9:
                    IntentUtils.showIntent(MainActivity.this,Practice_9_Activity.class);
                    break;
                case R.id.practice_btn10:
                    IntentUtils.showIntent(MainActivity.this,Practice_10_Activity.class);
                    break;
                case R.id.practice_btn11:
                    IntentUtils.showIntent(MainActivity.this,Practice_11_Activity.class);
                    break;
                case R.id.practice_btn12:
                    IntentUtils.showIntent(MainActivity.this,Practice_12_Activity.class);
                    break;
                case R.id.practice_btn13:
                    IntentUtils.showIntent(MainActivity.this,Practice_13_Activity.class);
                    break;

                case R.id.practice_btn14:
                    IntentUtils.showIntent(MainActivity.this,Practice_14_Activity.class);
                    break;

            }
        }
    };

}

