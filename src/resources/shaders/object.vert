#version 330

layout (location=0) in vec4 pos;
layout (location=1) in vec3 color;
layout (location=2) in vec2 texIn;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
out vec4 passColor;
out texOut;
void main()
{

    gl_Position = projectionMatrix*viewMatrix*modelMatrix* pos;
    passColor=vec4(color,0.25);
    texOut=texIn;
}