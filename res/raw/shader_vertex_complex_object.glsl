#version 300 es
invariant gl_Position;

const vec4 col = vec4(0.0, 0.5, 0.5, 1.0);

uniform mat4 u_mvpMatrix;
layout(location = 0) in vec4 a_position;
layout(location = 1) in vec2 a_texCoord;
out vec2 v_texCoord;

void main()
{
    //v_color = a_color;
    //v_color = col;

    gl_Position = u_mvpMatrix * a_position;
    v_texCoord = a_texCoord;
}