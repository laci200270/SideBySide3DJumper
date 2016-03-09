#version 330
in vec2 texCoord;
out vec4 fragColor;
uniform sampler2d;
void main()
{
    color = texture(tex, texCoord);
    	if (color.w < 1.0)
    		discard;
}