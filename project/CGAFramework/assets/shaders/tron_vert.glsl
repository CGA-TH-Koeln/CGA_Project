#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 tc;
layout(location = 2) in vec3 normal;


// translation object to world
// translation Uniforms
uniform mat4 model_matrix;
uniform mat4 view_matrix;
uniform mat4 projection_matrix;

// Texture Uniforms
uniform vec2 tcMultiplier;

// Pointlight Uniforms
uniform vec3 lightPos;
uniform vec3 lightColor;

// Spotlight Uniforms
uniform vec3 spotPos1;
uniform vec3 spotPos2;


// out struct
out struct VertexData
{
    vec3 position;
    vec2 tc;
    vec3 normal;
} vertexData;

out vec3 toLight; // Texel to pointlightsource
out vec3 toSpotLight1; // Texel to spotlightsource
out vec3 toSpotLight2;
out vec3 toCamera; // Texel to Camera

//main
void main(){

    vec4 pos = projection_matrix*view_matrix*model_matrix * vec4(position, 1.0f);
    gl_Position =vec4(pos.x,pos.y, pos.z,pos.w);

    // Nach Tranformationen Normale korrigieren
    vec4 nor = transpose(inverse(view_matrix*model_matrix)) * vec4(normal, 0.0f);


    vec4 lp = view_matrix * vec4(lightPos, 1.0f); // Position der Lichtquelle im Viewspace
    vec4 P = (view_matrix * model_matrix * vec4(position, 1.0f)); // Position des Vertex im Viewspace
    toLight = (lp - P).xyz; // Richtungsvektor von Vertex zur Lichtquelle
    toCamera=-P.xyz;    // Da die Kamera sich im Ursprung befindet ist der Vektor die negative Vertex Position
    toSpotLight1=(view_matrix * vec4(spotPos1,1.0f) - P).xyz;
    toSpotLight2=(view_matrix * vec4(spotPos2,1.0f) - P).xyz;

    //Filling Outstruck with new Data
    vertexData.tc=tc*tcMultiplier; // Komponentenweise Multiplication
    vertexData.position=pos.xyz;
    vertexData.normal=nor.xyz;


}