#version 330

layout (location=0) in vec4 pos;
layout (location=1) in vec2 texIn;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
out vec4 passColor;
out vec2 texOut;
void main()
{

    gl_Position = projectionMatrix*viewMatrix*modelMatrix* pos;

    texOut=texIn;
}