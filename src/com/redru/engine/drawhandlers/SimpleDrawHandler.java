package com.redru.engine.drawhandlers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.context.EngineContext;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.models.Model;

public class SimpleDrawHandler implements IntModelDrawHandler {
	private static final String TAG = "SimpleDrawHandler";

    private FloatBuffer vertexBuffer;
    private FloatBuffer scaleBuffer;
    private FloatBuffer rotationBuffer;
    private FloatBuffer translationBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];

    /**
     * 
     */
	public SimpleDrawHandler() {
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    @Override
    public void setup(Model model) {
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(model.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(model.getUnifiedData()).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        //vertexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, model.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT, vertexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);

        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_COLOR);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_VERTEX, 3,
                        GLES30.GL_FLOAT, false, 7 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_COLOR, 4,
                        GLES30.GL_FLOAT, false, 7 * OpenGLConstants.BYTES_PER_FLOAT, 3 * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
    }
    
    @Override
	public void updateTransformBuffers(float[] scalationMatrix, float[] rotationMatrix, float[] translationMatrix) {
    	this.scaleBuffer = ByteBuffer.allocateDirect(scalationMatrix.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.scaleBuffer.put(scalationMatrix).position(0);
        
    	this.rotationBuffer = ByteBuffer.allocateDirect(rotationMatrix.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.rotationBuffer.put(rotationMatrix).position(0);
        
    	this.translationBuffer = ByteBuffer.allocateDirect(translationMatrix.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.translationBuffer.put(translationMatrix).position(0);
	}

    /**
     * 
     */
    @Override
    public void draw(Model model) {
        GLES30.glUseProgram(EngineContext.shadeFactory.simpleProgram);
        
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.SIMP_PROG_MVP_LOC, 1, false, Camera.getInstance().getMvpMatrixAsFloatBuffer());
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.SIMP_PROG_SCA_LOC, 1, false, this.scaleBuffer);
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.SIMP_PROG_ROT_LOC, 1, false, this.rotationBuffer);
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.SIMP_PROG_TRA_LOC, 1, false, this.translationBuffer);

        GLES30.glBindVertexArray(VAOIds[0]);
        
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glBindVertexArray(0);
    }
}
