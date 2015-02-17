#version 300 es
invariant gl_Position;

const vec4 col = vec4(0.0, 0.5, 0.5, 1.0);
mat4 testTranslation;

uniform mat4 u_mvpMatrix;
uniform mat4 scaVector;
uniform mat4 rotVector;
uniform mat4 traVector;

vec4 supVect;

layout(location = 0) in vec4 a_position;
layout(location = 1) in vec2 a_texCoord;
out vec2 v_texCoord;

void main()
{
	// Only for translation test
	/*if (testTranslation[3][0] != 2.0) {
		testTranslation[0] = vec4(1.0, 0.0, 0.0, 0.0);
		testTranslation[1] = vec4(0.0, 1.0, 0.0, 0.0);
		testTranslation[2] = vec4(0.0, 0.0, 1.0, 0.0);
		testTranslation[3] = vec4(2.0, 2.0, 2.0, 1.0);
	}*/

	supVect =  traVector * rotVector * scaVector * a_position;
    gl_Position = u_mvpMatrix * supVect;
    v_texCoord = a_texCoord;
}