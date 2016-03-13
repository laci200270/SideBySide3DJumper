#version 330

layout (location=0) in vec4 pos;
layout (location=1) in vec2 texIn;
layout (location=2) in vec3 vertNormal;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 lightPos;

out vec3 normalOut;
out vec3 toLightVector;
out vec2 texOut;
void main()
{
    vec4 worldPos=modelMatrix* pos;
    gl_Position = projectionMatrix*viewMatrix*worldPos;
    normalOut=(modelMatrix*vec4(vertNormal,0)).xyz;
    texOut=texIn;
    toLightVector=lightPos-worldPos.xyz;
}