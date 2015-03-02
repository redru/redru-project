#version 300 es
precision mediump float;

in vec4 v_Color;
in float v_ElapsedTime;

out vec4 o_fragColor;

vec4 supp;

void main() {
	supp = vec4(v_Color.r, v_Color.g, v_Color.b, v_Color.a / v_ElapsedTime);
	
	o_fragColor = supp;
}