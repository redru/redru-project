#version 300 es
invariant gl_Position;

uniform mat4 u_mvpMatrix;
uniform mat4 scaVector;
uniform mat4 rotVector;
uniform mat4 traVector;

vec4 supVect;

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec4 a_color;
out vec4 v_color;

void main()
{
	v_color = a_color;
	supVect =  traVector * rotVector * scaVector * a_position;
    gl_Position = u_mvpMatrix * supVect;
}