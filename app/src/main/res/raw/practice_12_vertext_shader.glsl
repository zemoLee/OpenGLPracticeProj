attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec2 a_Coordinate; //纹理坐标
uniform mat4 u_Matrix;
varying  vec4 v_Color;
varying vec2 v_Coordinate;
void main() {
    gl_Position = u_Matrix*a_Position;
    v_Coordinate=a_Coordinate;
}


