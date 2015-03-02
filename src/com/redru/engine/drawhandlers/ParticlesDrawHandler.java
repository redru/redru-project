package com.redru.engine.drawhandlers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.context.EngineContext;
import com.redru.engine.particles.ParticleSystem;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.view.Camera;

public class ParticlesDrawHandler implements IntParticleDrawHandler {
	private static final String TAG = "ParticlesDrawHandler";

    private FloatBuffer vertexBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];

    /**
     * 
     */
	public ParticlesDrawHandler() {
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    @Override
    public void setup(float[] particlesData) {
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(particlesData.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(particlesData).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        //vertexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, particlesData.length * OpenGLConstants.BYTES_PER_FLOAT, vertexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);

        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_COLOR);
        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_DIRECTION_VEC);
        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_START_TIME);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_VERTEX, 3,
                        GLES30.GL_FLOAT, false, 11 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_COLOR, 4,
                        GLES30.GL_FLOAT, false, 11 * OpenGLConstants.BYTES_PER_FLOAT, 3 * OpenGLConstants.BYTES_PER_FLOAT);
        
        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_DIRECTION_VEC, 3,
                GLES30.GL_FLOAT, false, 11 * OpenGLConstants.BYTES_PER_FLOAT, (3 + 4) * OpenGLConstants.BYTES_PER_FLOAT);
        
        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_START_TIME, 1,
                GLES30.GL_FLOAT, false, 11 * OpenGLConstants.BYTES_PER_FLOAT, (3 + 4 + 3) * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
    }

    /**
     * 
     */
    @Override
    public void draw() {
        GLES30.glUseProgram(EngineContext.shadeFactory.particlesProgram);
        
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.PART_PROG_MVP_LOC, 1, false, Camera.getInstance().getMvpMatrixAsFloatBuffer());
        GLES30.glUniform1f(EngineContext.shadeFactory.PART_PROG_TIME, System.nanoTime() / 1000000000f);

        GLES30.glBindVertexArray(VAOIds[0]);
        
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, ParticleSystem.getInstance().getParticles().length / ParticleSystem.TOTAL_COMPONENT_COUNT);

        GLES30.glBindVertexArray(0);
    }
}
