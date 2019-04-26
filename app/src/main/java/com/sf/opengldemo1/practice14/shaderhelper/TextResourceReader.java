package com.sf.opengldemo1.practice14.shaderhelper;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 文件读取
 */
public class TextResourceReader {
    /**
     * 读取raw下文本 ，会被映射到R根据下，就可以直接R.id...调用
     */
    public static String readTextResourceFromRaw(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("无法打开raw中的资源resourceId：" + resourceId);
        }
        return body.toString();

    }

    /**
     * 读取assets下面的文本内容，必须通过AssetManager .getAssets()后进行操作 getResources().getAssets()
     */
    public static String readTextFromAssets(Resources mRes, String path) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = mRes.getAssets().open(path);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        return result.toString().replaceAll("\\r\\n", "\n");
    }


    /********************封装2********************/
    /**
     * 从assets中读取txt
     */
    public static String readFromAssets(Context context, String filePath) {
        try {
            InputStream is = context.getAssets().open(filePath);
            String text = readTextFromSDcard(is);

            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从raw中读取txt
     */
    public static String readFromRaw(Context context, int resId) {
        try {
            InputStream is = context.getResources().openRawResource(resId);
            String text = readTextFromSDcard(is);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
