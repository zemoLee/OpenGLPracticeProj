package com.sf.opengldemo1.practice14.objhelper;

/**
 * mtl 文件。材质库信息   OBJ文件不包含面的颜色定义信息，不过可以引用材质库
 */

/**
 * 材质库中包含材质的漫射(diffuse)，环境(ambient)，光泽(specular)的RGB(红绿蓝)的定义值，
 * 以及反射(specularity)，折射(refraction)，透明度(transparency)等其它特征
 */
public class MtlInfo {
    public String newmtl;   //材质名字
    public float[] Ka=new float[3];     //阴影色
    public float[] Kd=new float[3];     //固有色
    public float[] Ks=new float[3];     //高光色
    public float[] Ke=new float[3];     //
    public float Ns;                    //shininess
    public String map_Kd;               //固有纹理贴图
    public String map_Ks;               //高光纹理贴图
    public String map_Ka;               //阴影纹理贴图

    //denotes the illumination model used by the material.
    // illum = 1 indicates a flat material with no specular highlights,
    // so the value of Ks is not used.
    // illum = 2 denotes the presence of specular highlights,
    // and so a specification for Ks is required.
    public int illum;   //光照模式：  高光开启模式
}
