#version 330
#extension GL_ARB_separate_shader_objects : enable
in vec4 passColor;
out vec4 fragColor;

void main()
{
    fragColor = passColor;
}