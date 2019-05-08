
//顶点着色器 ：vertex shader

//--->>>>attribute 变量声明练习1：
// 一般存储顶点的信息：坐标，纹理，顶点颜色，顶点大小，法向量
uniform mat4 u_matViewProjection;//投影矩阵
attribute vec4 a_Position; //顶点坐标
attribute vec2 a_texCoord;//纹理坐标，纹理坐标2D的，只有(s t)
varying vec2  v_texCoord; //传值用的纹理坐标--->>>片元着色器中也需要对应声明，一抹一样


//说明1：  前面带a的意思是 attribute变量，顶点着色器中使用 ,只可读， 代码中获取到句柄后传值进来
//说明2:   前面带v的意思是 varying变量， 在顶点着色器和片元着色器中使用 ，可读可写
//a_position和a_texCoord0由应用程序加载数值。
//attribute 与uniform相似，可使用的最大attribute数量也是有上限的，可以使用 gl_MaxVertexAttribs来获取
void main (void ){
    gl_Position=u_matViewProjection*a_Position; //投影矩阵 乘以 顶点坐标向量= gl中的顶点坐标
    v_texCoord=a_texCoord;
}


//片元着色器
precision mediump float; //声明精度是浮点
varying vec2 v_texCoor;
uniform sampler2D s_baseMap;
uniform sampler2D s_lightMap;

void main(void){

    vec4 baseColor; //声明一个局部变量 ： 向量型  basecolor 颜色类
    vec4 lightColor;

    //texture :参数一：sampler2d, 参数二：纹理坐标
    baseColor=texture2D(s_baseMap,v_texCoord);//纹理查询的最终目的是----从sampler中提取--->>>>指定纹理坐标的颜色信息
    lightColor=texture2D(s_lightMap,v_texCoord);
    gl_FragColor=baseColor*(lightColor+0.25);//最终纹理的混合颜色---->>片元颜色= 采样器baseColor 取出的基本纹理颜色 ---  乘以---  4维向量 光的颜色 lightColor

}



