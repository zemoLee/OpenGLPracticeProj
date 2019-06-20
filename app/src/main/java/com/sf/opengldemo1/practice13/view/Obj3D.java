package com.sf.opengldemo1.practice13.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;

import com.sf.opengldemo1.practice13.objhelper.MtlInfo;
import com.sf.opengldemo1.practice13.shaderhelper.TextureHelper;
import com.sf.opengldemo1.practice13.shaderprogram.Obj3DShaderProgram;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by 01373577 on 2018/6/19.
 */

/**
 * 模型。 相当于前面写的三角形这些类
 */
public class Obj3D {
//    public FloatBuffer vertexBuffer;//顶点缓冲
//    public FloatBuffer textureBuffer;//纹理缓冲
//    public FloatBuffer normalBuffer; //法向量缓冲

    public FloatBuffer vertexBuffer;//顶点缓冲
    public FloatBuffer textureBuffer;//纹理缓冲
    public FloatBuffer normalBuffer; //法向量缓冲
    public int vertCount;//顶点个数

    public MtlInfo mtl; //材质文件


    private ArrayList<Float> tempVert;
    private ArrayList<Float> tempVertNorl;
    public ArrayList<Float> tempVertTexture;


    public int textureSMode;
    public int textureTMode;

    public  static final  int COORD_PER_VERTEX=3;
    private final  int vertexStride=COORD_PER_VERTEX*4;//每个顶点4个字节
//    private int  mVertexCount=mVertexs.length/COORD_PER_VERTEX;

    private Context context;


    /**
     * 1：增加顶点。 从obj文件 readline  逐行读出来一行，直接调用加入vertex队列。然后。 后面通过加入texture。等等。进行渲染
     */
    public void addVert(float d){
        if(tempVert==null){
            tempVert=new ArrayList<>();
        }
        tempVert.add(d);
    }

    /**
     *2 ：增加纹理坐标点
     */
    public void addVertTexture(float d){
        if(tempVertTexture==null){
            tempVertTexture=new ArrayList<>();
        }
        tempVertTexture.add(d);
    }

    /**
     * 3：增加法向量坐标
     */
    public void addVertNorl(float d){
        if(tempVertNorl==null){
            tempVertNorl=new ArrayList<>();
        }
        tempVertNorl.add(d);
    }
    /**
     * data的绑定，初始化各种顶点缓冲器， 设置顶点，纹理 ，法向量的position
      */
    public void dataLock(){

        if(tempVert!=null){
            setVertex(tempVert);//设置顶点缓冲 buffer的position0
            tempVert.clear();
            tempVert=null;
        }
        if(tempVertTexture!=null){
            setTextureBuffer(tempVertTexture);
            tempVertTexture.clear();
            tempVertTexture=null;
        }
        if(tempVertNorl!=null){
            setNormalBuffer(tempVertNorl);
            tempVertNorl.clear();
            tempVertNorl=null;
        }
    }


    /**
     *获取和设置顶点缓冲Buffer & position
     */
    public void setVertex(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer =buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            vertexBuffer.put(data.get(i));
        }
        vertexBuffer.position(0);
        vertCount=size/3;
    }

    /**
     *设置纹理的buffer和position
     */
    public void setTextureBuffer(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        textureBuffer =buffer.asFloatBuffer();
        for (int i=0;i<size;){
            textureBuffer.put(data.get(i));
            i++;
            textureBuffer.put(data.get(i));
            i++;
        }
        textureBuffer.position(0);
    }

    /**
     *设置法向量的position
     */
    public void setNormalBuffer(ArrayList<Float> data){
        int size=data.size();
        ByteBuffer buffer=ByteBuffer.allocateDirect(size*4);
        buffer.order(ByteOrder.nativeOrder());
        normalBuffer =buffer.asFloatBuffer();
        for (int i=0;i<size;i++){
            normalBuffer.put(data.get(i));
        }
        normalBuffer.position(0);
    }

    public Obj3D() {
    }
    public Obj3D(Context context) {
        this.context=context;
    }

    private int textureType=0;
    public  void   drawSelf(Obj3DShaderProgram  mProgram,float[]  mMartrix){
        /**
         * 加载纹理ID
         */
        createTexture(context);
        //matrix
        GLES20.glUniformMatrix4fv(mProgram.getuMatrixHandle(),1,false,mMartrix,0);
        //法向量
        GLES20.glEnableVertexAttribArray(mProgram.getmNormalHandle());
        GLES20.glVertexAttribPointer(mProgram.getmNormalHandle(),3, GLES20.GL_FLOAT, false, 3*4, normalBuffer);
        //vertex
        GLES20.glVertexAttribPointer(mProgram.getaPositionHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mProgram.getaPositionHandle());
        //纹理坐标
//        GLES20.glEnableVertexAttribArray(mProgram.getaCoordinateHandle());
//        GLES20.glVertexAttribPointer(mProgram.getaCoordinateHandle(),COORD_PER_VERTEX,GLES20.GL_FLOAT,false,vertexStride, textureBuffer);
        //纹理取样绑定
        GLES20.glUniform1i(mProgram.getuTextureHandle(),textureType);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0+textureType);//默认纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertCount);
        GLES20.glDisableVertexAttribArray(mProgram.getaPositionHandle());
        GLES20.glDisableVertexAttribArray(mProgram.getmNormalHandle());
    }

    /**
     * 纹理Id
     */
    private int textureId;

    /**
     *获取纹理
     */
    private void  createTexture(Context context){
//        textureId = TextureHelperKt.createTexture(BitmapFactory.decodeStream(context.getAssets().open("3dobj/"+obj.mtl.map_Kd)));
        if(textureBuffer!=null){
                try {
                textureId = TextureHelper.createTexture(BitmapFactory.decodeStream(context.getAssets().open("assets/3dobj/"+this.mtl.map_Kd)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
