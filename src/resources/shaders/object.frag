#version 330

struct Light{
    vec3 color;
    vec3 location;
    float strenght;
    int type;
    };
#define MAXLIGHTS 64
in vec2 texOut;
out vec4 fragColor;
vec4 color;
uniform sampler2D tex;
uniform mat4 modelMatrix;
in vec3 fragVert;

uniform int lightCount=0;
uniform Light lights[MAXLIGHTS];
uniform Light[MAXLIGHTS] array;
uniform Light[MAXLIGHTS] array2;
uniform Light[MAXLIGHTS] array3;

in vec4 worldOutPos;
in vec3 outNormal;

vec3 hdr(vec3 input){
    float multiplier=1;
        if(input.r>1){
            float tempMultiplier=1/input.r;
            if(tempMultiplier<multiplier)
                multiplier=tempMultiplier;
        }
        if(input.g>1){
                float tempMultiplier=1/input.g;
                if(tempMultiplier<multiplier)
                    multiplier=tempMultiplier;
        }
        if(input.b>1){
                float tempMultiplier=1/input.b;
                if(tempMultiplier<multiplier)
                    multiplier=tempMultiplier;
        }
       return input*multiplier;
}

void main()
{

    color = texture(tex, texOut);
    	if (color.w < 1.0)
    		discard;

//color=vec4(1);
    vec3 diffuse=vec3(0);
    for(int i=0;i<lightCount;i++){
        Light currentLight=lights[i];
        float dist=distance(vec4(currentLight.location,1),worldOutPos);
        //if(dist<currentLight.strenght){
            vec3 normalOut=(modelMatrix*vec4(outNormal,0)).xyz;
            vec3 toLightVector=currentLight.location-worldOutPos.xyz;
            vec3 normalVector=normalize(normalOut);
            vec3 normalLightVector=normalize(toLightVector);
            float dotOut=dot(normalLightVector,normalVector);
            float brightness=max(dotOut,0);
           // float strMult=1/(dist/currentLight.strenght);

            diffuse+=currentLight.color*brightness/**strMult*/;
        }

    float multiplier=1;

   // diffuse=hdr(diffuse);
    //color=color*vec4(diffuse,1);


    fragColor=color;
}

