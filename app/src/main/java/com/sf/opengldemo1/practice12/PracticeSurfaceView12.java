package com.sf.opengldemo1.practice12;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeSurfaceView12 extends GLSurfaceView {
    private    Context  context;
    public PracticeSurfaceView12(Context context) {
        this(context,null);
    }

    public PracticeSurfaceView12(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void  init(Context context){
        this.context=context;
    }
}
