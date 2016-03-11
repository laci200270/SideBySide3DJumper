#version 330

in vec2 texOut;
out vec4 fragColor;
vec4 color;
uniform sampler2D tex;

void main()
{
    color = texture(tex, texOut);
    	if (color.w < 1.0)
    		discard;
     if(texOut!=vec2(0,0))
        fragColor=vec4(1,0,1,1);
       else
        fragColor=color;
}