package com.redru.engine.shader;

import java.nio.IntBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.R;
import com.redru.Redru;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 16/01/2015.
 */
public class ShaderFactory {
    private static final String TAG = "ShaderFactory";
    private static ShaderFactory instance;
    
    public final int LAYOUT_VERTEX = 0;
    public final int LAYOUT_COLOR = 1;
    public final int LAYOUT_TEXTURE = 1;
    
    public final int LAYOUT_DIRECTION_VEC = 2;
    public final int LAYOUT_START_TIME = 3;
    
    // UNIFORMS --------------------------------------------------
    public int DEF_PROG_MVP_LOC;
    
    public int COMP_PROG_MVP_LOC;
    public int COMP_PROG_SCA_LOC;
    public int COMP_PROG_ROT_LOC;
    public int COMP_PROG_TRA_LOC;
    public int COMP_PROG_SAMPLER_S_TEXTURE;
    
    public int SIMP_PROG_MVP_LOC;
    public int SIMP_PROG_SCA_LOC;
    public int SIMP_PROG_ROT_LOC;
    public int SIMP_PROG_TRA_LOC;
    
    public int PART_PROG_MVP_LOC;
    public int PART_PROG_TIME;
    // -----------------------------------------------------------
    
    public int defaultProgram;
    public int complexObjectProgram;
    public int simpleProgram;
    public int particlesProgram;

    private final String vertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_default);
    private final String complexObjectVertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_complex_object);
    private final String simpleObjectVertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_simple_object);
    private final String particlesVertexShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_vertex_particles);
    
    private final String fragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_default);
    private final String complexObjectFragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_complex_object);
    private final String simpleFragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_simple_object);
    private final String particlesFragmentShaderCode = ResourceUtils.readTextFileFromResource(Redru.getContext(), R.raw.shader_fragment_particles);
    
    private static IntBuffer shaderCompileError = IntBuffer.allocate(1);
    private static String shaderCompileInfoLog;

    /**
     * 
     */
    private ShaderFactory() {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int complexObjectVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, complexObjectVertexShaderCode);
        int simpleObjectVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, simpleObjectVertexShaderCode);
        int particlesVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, particlesVertexShaderCode);
        
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int complexFragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, complexObjectFragmentShaderCode);
        int simpleFragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, simpleFragmentShaderCode);
        int particlesFragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, particlesFragmentShaderCode);

        defaultProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(defaultProgram, vertexShader);
        GLES30.glAttachShader(defaultProgram, fragmentShader);
        GLES30.glLinkProgram(defaultProgram);
        DEF_PROG_MVP_LOC = GLES30.glGetUniformLocation (defaultProgram, "u_mvpMatrix");

        complexObjectProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(complexObjectProgram, complexObjectVertexShader);
        GLES30.glAttachShader(complexObjectProgram, complexFragmentShader);
        GLES30.glLinkProgram(complexObjectProgram);
        COMP_PROG_MVP_LOC = GLES30.glGetUniformLocation (complexObjectProgram, "u_mvpMatrix");
        COMP_PROG_SCA_LOC = GLES30.glGetUniformLocation (complexObjectProgram, "scaVector");
        COMP_PROG_ROT_LOC = GLES30.glGetUniformLocation (complexObjectProgram, "rotVector");
        COMP_PROG_TRA_LOC = GLES30.glGetUniformLocation (complexObjectProgram, "traVector");
        COMP_PROG_SAMPLER_S_TEXTURE = GLES30.glGetUniformLocation (complexObjectProgram, "s_texture");
        
        simpleProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(simpleProgram, simpleObjectVertexShader);
        GLES30.glAttachShader(simpleProgram, simpleFragmentShader);
        GLES30.glLinkProgram(simpleProgram);
        SIMP_PROG_MVP_LOC = GLES30.glGetUniformLocation (simpleProgram, "u_mvpMatrix");
        SIMP_PROG_SCA_LOC = GLES30.glGetUniformLocation (simpleProgram, "scaVector");
        SIMP_PROG_ROT_LOC = GLES30.glGetUniformLocation (simpleProgram, "rotVector");
        SIMP_PROG_TRA_LOC = GLES30.glGetUniformLocation (simpleProgram, "traVector");
        
        particlesProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(particlesProgram, particlesVertexShader);
        GLES30.glAttachShader(particlesProgram, particlesFragmentShader);
        GLES30.glLinkProgram(particlesProgram);
        PART_PROG_MVP_LOC = GLES30.glGetUniformLocation (particlesProgram, "u_mvpMatrix");
        PART_PROG_TIME = GLES30.glGetUniformLocation (particlesProgram, "u_Time");
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