package com.redru.engine.utils;

import java.nio.IntBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.R;
import com.redru.Redru;

/**
 * Created by Luca on 16/01/2015.
 */
public class ShaderFactory {

    private static final String TAG = "ShaderFactory";

    public static final int LAYOUT_VERTEX = 0;
    public static final int LAYOUT_COLOR = 1;

    private final String vertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_default);
    private final String complexObjectVertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_complex_object);
    private final String fragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_default);

    public static int defaultProgram;
    public static int complexObjectProgram;
    public static int MVP_LOC;

    private static IntBuffer shaderCompileError = IntBuffer.allocate(1);
    private static String shaderCompileInfoLog;

    public ShaderFactory() {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int complexObjectVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, complexObjectVertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        defaultProgram = GLES30.glCreateProgram();             // create empty OpenGL ES Program
        GLES30.glAttachShader(defaultProgram, vertexShader);   // add the vertex shader to program
        GLES30.glAttachShader(defaultProgram, fragmentShader); // add the fragment shader to program
        GLES30.glLinkProgram(defaultProgram);                  // creates OpenGL ES program executables

        complexObjectProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(complexObjectProgram, complexObjectVertexShader);
        GLES30.glAttachShader(complexObjectProgram, fragmentShader);
        GLES30.glLinkProgram(complexObjectProgram);

        // Get the uniform locations
        MVP_LOC = GLES30.glGetUniformLocation (defaultProgram, "u_mvpMatrix");
    }

    private int loadShader(int type, String shaderCode) {
        shaderCompileError.clear();

        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, shaderCompileError);

        if (shaderCompileError.get(0) == GLES30.GL_TRUE) {
            if (type == GLES30.GL_VERTEX_SHADER) {
                Log.i(TAG, "Vertex shader compile status: OK");
            } else {
                Log.i(TAG, "Fragment shader compile status: OK");
            }
        } else {
            shaderCompileInfoLog = GLES30.glGetShaderInfoLog(shader);
            Log.i(TAG, "Shader compile status: FAILURE\n" + shaderCompileInfoLog);
        }

        return shader;
    }

}