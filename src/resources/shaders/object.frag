#version 330

struct Light{
    vec3 color;
    vec3 location;
    int type;
    };
#define MAXLIGHTS 128
in vec2 texOut;
out vec4 fragColor;
vec4 color;
uniform sampler2D tex;
uniform mat4 modelMatrix;
in vec3 fragVert;

uniform int lightCount=0;
uniform Light lights[MAXLIGHTS];
uniform Light lights2[MAXLIGHTS];
in vec4 worldOutPos;
in vec3 outNormal;
void main()
{
    color = texture(tex, texOut);
    	if (color.w < 1.0)
    		discard;
     if(texOut==vec2(0,0))
        fragColor=vec4(1);
       else
        fragColor=color;
    vec3 diffuse=vec3(0);
    for(int i=0;i<lightCount;i++){
        Light currentLight=lights[i];
        vec3 normalOut=(modelMatrix*vec4(outNormal,0)).xyz;
        vec3 toLightVector=currentLight.location-worldOutPos.xyz;
        vec3 normalVector=normalize(normalOut);
        vec3 normalLightVector=normalize(toLightVector);
        float dotOut=dot(normalLightVector,normalVector);
        float brightness=max(dotOut,0);
        diffuse+=currentLight.color*brightness;
    }
    float multiplier=1;
    if(diffuse.r>1){
        float tempMultiplier=1/diffuse.r;
        if(tempMultiplier<multiplier)
            multiplier=tempMultiplier;
    }
    if(diffuse.g>1){
            float tempMultiplier=1/diffuse.g;
            if(tempMultiplier<multiplier)
                multiplier=tempMultiplier;
    }
    if(diffuse.b>1){
            float tempMultiplier=1/diffuse.b;
            if(tempMultiplier<multiplier)
                multiplier=tempMultiplier;
    }
    diffuse=diffuse*multiplier;
    color= color*vec4(diffuse,1);


    fragColor=color;
}