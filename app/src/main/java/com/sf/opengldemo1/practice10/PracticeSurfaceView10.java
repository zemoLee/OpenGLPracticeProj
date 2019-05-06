package com.sf.opengldemo1.practice10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by 01373577 on 2018/6/13.
 */

public class PracticeSurfaceView10 extends GLSurfaceView {
    private    Context  context;
    public PracticeSurfaceView10(Context context) {
        this(context,null);
    }

    public PracticeSurfaceView10(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void  init(Context context){
        this.context=context;
    }
}
