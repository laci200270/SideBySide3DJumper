#version 330

layout (location=0) in vec4 pos;
layout (location=1) in vec2 texIn;
layout (location=2) in vec3 vertNormal;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
out vec4 passColor;
out vec2 texOut;
out vec3 normalOut;
out vec3
void main()
{

    gl_Position = projectionMatrix*viewMatrix*modelMatrix* pos;
    normalOut=vertNormal;
    texOut=texIn;
}