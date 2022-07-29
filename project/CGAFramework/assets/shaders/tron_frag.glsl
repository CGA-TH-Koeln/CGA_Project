#version 330 core

//input from vertex shader
in struct VertexData
{
    vec3 position;
    vec2 tc;
    vec3 normal;

} vertexData;

// Spotlight Uniforms
in vec3 toSpotLight1;
in vec3 toSpotLight2;
uniform vec3 spotDir1;
uniform vec3 spotDir2;
uniform vec3 spotColor1;
uniform vec3 spotColor2;
uniform float outerAngle1;
uniform float outerAngle2;
uniform float innerAngle1;
uniform float innerAngle2;

//Pointlight Uniforms
in vec3 toLight;
in vec3 toCamera;
uniform vec3 lightColor;
uniform float shininess;

// Texture Uniforms
uniform sampler2D diff;
uniform sampler2D emit;
uniform sampler2D spec;

uniform vec3 staticColor;

//fragment shader output
out vec4 color;


float g = 2.2;

vec3 gamma( vec3 C_linear){
     return pow(C_linear.rgb, vec3(1.0/g));
}

vec3 invgamma( vec3 C_gamma){
    return pow(C_gamma.rgb, vec3(g));
}

vec4 diffterm(vec3 n,vec3 l,vec3 col){
     float cosa = max(0.0, dot(n,l));
     vec3 diffTex=invgamma(texture(diff,vertexData.tc).rgb)*col;
     vec4 result= vec4(diffTex*cosa,1.0f);
     return vec4(result);
}

vec4 specterm(vec3 n,vec3 l,vec3 col){
   // vec3 r=normalize(reflect(-l,n));
   vec3 v=normalize(toCamera);
   vec3 halfway= normalize(v+l);
   float sCol = pow(max(dot(n, halfway), 0.0), shininess);
   // float cosb = max(0.0, dot(r,v));
    vec3 specTex=invgamma(texture(spec,vertexData.tc).rgb)*col;

    vec4 result= vec4(vec3(specTex*sCol),0f);
    return vec4(result);
}

void spotlight1(vec3 n,vec3 sl,vec3 col,vec3 sd){
    float distance=length(toSpotLight1);
    float attenuation = 1.0 / (distance * distance);
    //float attenuation = 1.0 / (1+(0.45*distance)+(0.0075*distance * distance));

    float theta = dot(sl,-sd);
    float epsilon= innerAngle1-outerAngle1;
    float intensity=clamp((theta-outerAngle1)/epsilon,0.0,1.0);
    color+=(diffterm(n,sl,col)+specterm(n,sl,col))*intensity*attenuation;
}
void spotlight2(vec3 n,vec3 sl,vec3 col,vec3 sd){
    float distance=length(toSpotLight2);
    //float attenuation = 1.0 / (distance * distance);
    float attenuation = 1.0 / (1+(0.022*distance)+(0.0019*distance * distance));

    float theta = dot(sl,-sd);
    float epsilon= innerAngle2-outerAngle2;
    float intensity=clamp((theta-outerAngle2)/epsilon,0.0,1.0);
    color+=(diffterm(n,sl,col)+specterm(n,sl,col))*intensity*attenuation;
}
void pointlight(vec3 n,vec3 l,vec3 col){
    float distance=length(toLight);
    float attenuation = 1.0 / (distance * distance);
   // float attenuation = 1.0 / (1+(0.09*distance)+(0.032*distance * distance));
    color+=(specterm(n,l,col));
  //  diffterm(n,l,col)+*attenuation
}

void main(){
    //color = vec4(0, (0.5f + abs(vertexData.position.z)), 0, 1.0f);
    //color = vec4(abs(vertexData.tc), 1.0f);

    // Alle Vektoren durch Normalisieren korrigieren

    vec3 sl1=normalize(toSpotLight1);
    vec3 sl2=normalize(toSpotLight2);
    vec3 sd1=normalize(spotDir1);
    vec3 sd2=normalize(spotDir2);
    vec3 n=normalize(vertexData.normal);
    vec3 l=normalize(toLight);

    color += vec4(invgamma(texture(emit,vertexData.tc).rgb)*staticColor,1.0f);

    spotlight1(n,sl1,spotColor1,sd1);
    spotlight2(n,sl2,spotColor2,sd2);
    pointlight(n,l,lightColor);

    color=vec4(gamma(color.rgb),1.0f);

}


