package com.sf.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by 01373577 on 2018/5/11.
 */

public class IntentUtils {
    private static final String IMAGE_TYPE = "image/*";
    private static final String TAG = "IntentUtil";

    public static void showIntent(Activity context, Class<?> clzz) {
        showIntent(context, clzz, null, null);
    }

    public static void showIntent(Activity context, Class<?> clzz, String[] keys, String[] values) {
        Intent intent = new Intent(context, clzz);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                if (!TextUtils.isEmpty(keys[i]) && !TextUtils.isEmpty(values[i])) {
                    intent.putExtra(keys[i], values[i]);
                }
            }
        }
        context.startActivity(intent);
    }

    /**
     * 打电话
     */
    public static void openCall(Context context, String tel) {
        tel = tel.replaceAll("-", "");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
