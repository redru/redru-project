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
    private static ShaderFactory instance;
    
    public final int LAYOUT_VERTEX = 0;
    public final int LAYOUT_COLOR = 1;

    private final String vertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_default);
    private final String complexObjectVertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_complex_object);
    private final String fragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_default);

    public int defaultProgram;
    public int complexObjectProgram;
    
    public int MVP_LOC;
    public int ENABLE_COLOR_VECTOR;

    private static IntBuffer shaderCompileError = IntBuffer.allocate(1);
    private static String shaderCompileInfoLog;

    /**
     * 
     */
    private ShaderFactory() {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int complexObjectVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, complexObjectVertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        defaultProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(defaultProgram, vertexShader);
        GLES30.glAttachShader(defaultProgram, fragmentShader);
        GLES30.glLinkProgram(defaultProgram);

        complexObjectProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(complexObjectProgram, complexObjectVertexShader);
        GLES30.glAttachShader(complexObjectProgram, fragmentShader);
        GLES30.glLinkProgram(complexObjectProgram);

        // Get the uniform locations
        //ENABLE_COLOR_VECTOR = GLES30.glGetUniformLocation (complexObjectProgram, "enableColorVector");
        MVP_LOC = GLES30.glGetUniformLocation (complexObjectProgram, "u_mvpMatrix");
    }

    /**
     * 
     * @param type
     * @param shaderCode
     * @return
     */
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
    
    /**
     * 
     * @return
     */
    public static ShaderFactory getInstance() {
    	if (instance == null) {
    		instance = new ShaderFactory();
    	}
    	
    	return instance;
    }

}