#version 330

layout (location=0) in vec4 pos;
layout (location=1) in vec3 color;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
out vec4 passColor;

void main()
{
    gl_Position = pos;
    gl_Position = projectionMatrix*viewMatrix*modelMatrix* pos;
    passColor=pos*20;
}