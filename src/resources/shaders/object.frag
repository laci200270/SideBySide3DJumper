#version 330

in vec2 texOut;
out vec4 fragColor;
vec4 color;
uniform sampler2D tex;
uniform mat4 modelMatrix;
in vec3 normalOut;

uniform struct Light {
   vec3 position;
   vec3 intensities; //a.k.a the color of the light
} light;


void main()
{
    color = texture(tex, texOut);
    	if (color.w < 1.0)
    		discard;
     if(texOut==vec2(0,0))
        fragColor=vec4(1,0,1,1);
       else
        fragColor=color;

    mat3 normalMatrix = transpose(inverse(mat3(modelMatrix)));
    vec3 normal = normalize(normalMatrix * normalOut);
    vec3 fragPosition = vec3(modelMatrix * vec4(fragVert, 1));
    vec3 surfaceToLight = light.position - fragPosition;

    float brightness = dot(normal, surfaceToLight) / (length(surfaceToLight) * length(normal));
    brightness = clamp(brightness, 0, 1);

   vec4 finalColor = vec4(brightness * light.intensities * color.rgb, color.a);
   fragColor=finalColor;
}