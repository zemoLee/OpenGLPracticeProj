

//片元着色器
precision mediump float; //声明精度是浮点
varying vec2 v_texCoor;
uniform sampler2D s_baseMap;
uniform sampler2D s_lightMap;


//---->>>>函数定义练习
//参数一： 3维向量：法向量
//参数二： 3维向量： 光方向向量
//参数三： 4维向量： 颜色值 颜色由4位组成
vec4 getDiffuse(vec3 normal,vec3 light,vec4 baseColor){
    //dot(向量1，向量2) ： 返回向量1 和向量 2 的乘积 。
    //法向量和光的入射角度向量相乘== 入射角度后的反射向量 ？？？
    //基本颜色 向量 乘以  反射后的方向向量 ===>最终看到的漫反射颜色
    return baseColor*dot(normal,light);
}



void main(void){

    vec4 baseColor; //声明一个局部变量 ： 向量型  basecolor 颜色类
    vec4 lightColor;

    //texture :参数一：sampler2d, 参数二：纹理坐标
    baseColor=texture2D(s_baseMap,v_texCoord);//纹理查询的最终目的是----从sampler中提取--->>>>指定纹理坐标的颜色信息
    lightColor=texture2D(s_lightMap,v_texCoord);
    gl_FragColor=baseColor*(lightColor+0.25);//最终纹理的混合颜色---->>片元颜色= 采样器baseColor 取出的基本纹理颜色 ---  乘以---  4维向量 光的颜色 lightColor

}



