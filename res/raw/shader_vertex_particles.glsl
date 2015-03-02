#version 300 es
invariant gl_Position;

uniform mat4 u_mvpMatrix;
uniform float u_Time;

layout(location = 0) in vec4 a_Position;
layout(location = 1) in vec4 a_Color;
layout(location = 2) in vec4 a_DirectionVector;
layout(location = 3) in float a_ParticleStartTime;

out vec4 v_Color;
out float v_ElapsedTime;

void main()
{
	v_ElapsedTime = u_Time - a_ParticleStartTime;
	
	if (v_ElapsedTime < 3.0) {
		vec4 currentPosition = a_Position + (a_DirectionVector.xyz * v_ElapsedTime);
		gl_Position = u_mvpMatrix * currentPosition;
		gl_PointSize = 3.0 - v_ElapsedTime; // Reduce the point size while time increments
		v_Color = a_Color;
	} else {
		gl_Position = a_Position;
		gl_PointSize = 0.0;
		v_Color = a_Color;
	}
}