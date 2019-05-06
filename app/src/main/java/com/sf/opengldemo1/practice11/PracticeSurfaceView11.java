package com.sf.opengldemo1.practice11;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeSurfaceView11 extends GLSurfaceView {
    private    Context  context;
    public PracticeSurfaceView11(Context context) {
        this(context,null);
    }

    public PracticeSurfaceView11(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void  init(Context context){
        this.context=context;
    }
}
