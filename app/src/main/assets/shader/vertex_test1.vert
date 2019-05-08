
//--->>>>attribute 变量声明练习1：
// 一般存储顶点的信息：坐标，纹理，顶点颜色，顶点大小，法向量
uniform mat4 u_matViewProjection;//投影矩阵
attribute vec4 a_Position; //顶点坐标
attribute vec2 a_texCoord;//纹理坐标，纹理坐标2D的，只有(s t)
varying vec2  v_texCoord; //传值用的纹理坐标


//说明1：  前面带a的意思是 attribute变量，顶点着色器中使用 ,只可读， 代码中获取到句柄后传值进来
//说明2:   前面带v的意思是 varying变量， 在顶点着色器和片元着色器中使用 ，可读可写
//a_position和a_texCoord0由应用程序加载数值。
//attribute 与uniform相似，可使用的最大attribute数量也是有上限的，可以使用 gl_MaxVertexAttribs来获取
void main (void ){
    gl_Position=u_matViewProjection*a_Position; //投影矩阵 乘以 顶点坐标向量= gl中的顶点坐标
    v_texCoord=a_texCoord;
}

//----->>>>>uniform 变量声明练习2：
//一般用于存储 矩阵、光的参数 和颜色
uniform mat4 u_viewProjection; //投影变换矩阵
uniform mat4 u_viewMatrix;  //视图模型矩阵
uniform vec3 U_lightPosition; //光的位置


//---->>>varying 变量声明练习：
//顶点着色器和片段着色器中都会有varying变量的声明
//：仅限制为float类型的数据
//   float、 vec2 vec3  vec4--->>>里面的数据也必须是float类型
varying vec2  v_texCoord; //纹理一般情况下都是2D纹理，所以是2维向量
varying vec4  v_color; //颜色是四位，所以要用四维向量


