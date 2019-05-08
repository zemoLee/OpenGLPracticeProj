package com.sf.openglpractice2.Basic_Graphic_Shader;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * @Author: Jinhuan.Li
 * @Date: 2019/5/8
 * @Des: //TODO
 */
public class BufferUtils {
    /**
     *  int buffer
     * @param arr
     * @return
     */
    public static Buffer bufferOfIntUtil(int[] arr) {
        IntBuffer mBuffer;
        //先初始化buffer,数组的长度*4,因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());

        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);

        return mBuffer;
    }

    /**
     * float buffer
     * @param arr
     * @return
     */
    public static Buffer bufferFloatIntUtil(float[] arr) {
        FloatBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    /**
     * short buffer
     * @param arr
     * @return
     */
    public static Buffer bufferShorUtil(short[] arr) {
        ShortBuffer mBuffer;
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 2);
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }
    /**
     * @Author  Jinhuan.Li
     * @method
     * @Params   byte buffer
     * @return
     * @Description: 方法描述：
     */
    public static Buffer bufferByteUitl(byte[] arr){
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length);
        qbb.order(ByteOrder.nativeOrder());
        qbb.put(arr);
        qbb.position(0);
        return qbb;
    }
}
