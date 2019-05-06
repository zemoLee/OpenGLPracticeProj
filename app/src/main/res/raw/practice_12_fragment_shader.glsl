precision mediump float;
varying vec4 v_Color;
uniform sampler2D u_Texture; //取样器
varying vec2 v_Coordinate;

void main() {
 gl_FragColor=texture2D(u_Texture,v_Coordinate);//根据纹理取样器和纹理坐标，可以得到当前纹理取样得到的像素颜色
}
