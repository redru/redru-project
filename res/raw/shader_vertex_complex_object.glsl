#version 300 es
invariant gl_Position;

const vec4 col = vec4(0.0, 0.5, 0.5, 1.0);

uniform bool enableColorVector = false;
uniform mat4 u_mvpMatrix;
layout(location = 0) in vec4 a_position;
layout(location = 1) in vec4 a_color;
out vec4 v_color;

void main()
{
    if (enableColorVector) {
        v_color = a_color;
    } else {
        v_color = col;
    }

    gl_Position = u_mvpMatrix * a_position;
}