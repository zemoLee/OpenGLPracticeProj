package com.sf.opengldemo1.practice5;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by 01373577 on 2018/6/11.
 */

public class PracticeSurfaceView5 extends GLSurfaceView {
    int [] screenSize;
    public PracticeSurfaceView5(Context context) {
        super(context);
        init(context);

    }

    public PracticeSurfaceView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        screenSize=getScreenSize(context);
    }

    /**
     * 方法描述：获取屏幕宽高
     */
    public static int[] getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay()
                .getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        int[] size = {mScreenWidth, mScreenHeight};
        return size;
    }
}
