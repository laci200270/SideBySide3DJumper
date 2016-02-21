#version 330

out vec4 vertColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec4 vertexPos;

void main(){
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vertexPos;
    vertColor = vec4(vertexPos.x,1,1,1);
}