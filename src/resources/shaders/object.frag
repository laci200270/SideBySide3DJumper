#version 120

in vec4 vertColor;

void main(){
    gl_FragColor = vec4(vertColor.x,1,1,1);
}