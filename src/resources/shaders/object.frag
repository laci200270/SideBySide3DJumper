#version 330

in vec2 texOut;
out vec4 fragColor;
vec4 color;
uniform sampler2D tex;
uniform mat4 modelMatrix;
in vec3 fragVert;
uniform vec3 lightColour;

in vec3 normalOut;
in vec3 toLightVector;
void main()
{
    color = texture(tex, texOut);
    	if (color.w < 1.0)
    		discard;
     if(texOut==vec2(0,0))
        fragColor=vec4(1);
       else
        fragColor=color;


    vec3 normalVector=normalize(normalOut);
    vec3 normalLightVector=normalize(toLightVector);

    float dotOut=dot(normalLightVector,normalVector);
    float brightness=max(dotOut,0)*2;
    vec3 diffuse=brightness*lightColour;

   vec4 finalColor = vec4(diffuse,1)*color;
   fragColor=finalColor;
}