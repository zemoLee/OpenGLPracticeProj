uniform mat4 u_Matrix;
varying  vec4 v_Color;
attribute vec4 a_Position;
attribute vec4 a_Color;

void main() {
    gl_Position = u_Matrix*a_Position;
  if(a_Position.z!=0.0){
        v_Color=vec4(0.0,0.0,0.0,1.0);
    }else{
        v_Color=vec4(0.9,0.9,0.9,1.0);
    }
//    if(a_Color.x!=0||a_Color.y!=0||a_Color.z!=0){
//         v_Color=a_Color;
//    }
}


