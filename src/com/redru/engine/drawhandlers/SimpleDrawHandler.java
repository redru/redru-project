package com.redru.engine.drawhandlers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.elements.GameActor;
import com.redru.engine.shader.ShaderFactory;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.view.Camera;

public class SimpleDrawHandler implements IntDrawHandler {
	private static final String TAG = "SimpleDrawHandler";

	private GameActor actor;

    private FloatBuffer vertexBuffer;
    private FloatBuffer scaleBuffer;
    private FloatBuffer rotationBuffer;
    private FloatBuffer translationBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];

    /**
     * 
     */
	public SimpleDrawHandler(GameActor actor) {
		this.actor = actor;
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    @Override
    public void setup() {
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(this.actor.getModel().getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(this.actor.getModel().getUnifiedData()).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        //vertexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, this.actor.getModel().getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT, vertexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);

        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_COLOR);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_VERTEX, 3,
                        GLES30.GL_FLOAT, false, 7 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_COLOR, 4,
                        GLES30.GL_FLOAT, false, 7 * OpenGLConstants.BYTES_PER_FLOAT, 3 * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
    }
    
    @Override
	public void updateTransformBuffers() {
    	this.scaleBuffer = ByteBuffer.allocateDirect(this.actor.getScalationMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.scaleBuffer.put(this.actor.getScalationMatrix()).position(0);
        
    	this.rotationBuffer = ByteBuffer.allocateDirect(this.actor.getRotationMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.rotationBuffer.put(this.actor.getRotationMatrix()).position(0);
        
    	this.translationBuffer = ByteBuffer.allocateDirect(this.actor.getPositionMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	this.translationBuffer.put(this.actor.getPositionMatrix()).position(0);
	}

    /**
     * 
     */
    @Override
    public void draw() {
        GLES30.glUseProgram(ShaderFactory.getInstance().simpleProgram);
        
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().SIMP_PROG_MVP_LOC, 1, false, Camera.getInstance().getMvpMatrixAsFloatBuffer());
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().SIMP_PROG_SCA_LOC, 1, false, this.scaleBuffer);
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().SIMP_PROG_ROT_LOC, 1, false, this.rotationBuffer);
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().SIMP_PROG_TRA_LOC, 1, false, this.translationBuffer);

        GLES30.glBindVertexArray(VAOIds[0]);
        
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLES30.glBindVertexArray(0);
    }
}
