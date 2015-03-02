package com.redru.engine.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.context.EngineContext;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.view.Camera;

public class GridLines implements IntSceneElement {

	private static final String TAG = "OriginLines";

    private static int VERTEX_SIZE = 3;
    private static int COLOR_SIZE = 4;
    private static int STRIDE = VERTEX_SIZE + COLOR_SIZE;
    private static int NUM_VERTICES = 240;
    
    private static float MIN_VERTEX = -50.0f;
    private static float MAX_VERTEX = 50.0f;

    private float[] vertexData = new float[NUM_VERTICES * 7];

    private FloatBuffer vertexBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];

    /**
     * 
     */
	protected GridLines() {
		for (int count = -30, i = 0; count < 30; count++) {
			for (int e = 0; e < 4; e++) {
				if (e == 2) {
					vertexData[i] = MIN_VERTEX;
				} else if (e == 3) {
					vertexData[i] = MAX_VERTEX;
				} else {
					vertexData[i] = (float) count;
				}
				
				i++;
				vertexData[i] = 0;
				i++;
				
				if (e == 0) {
					vertexData[i] = MIN_VERTEX;
				} else if (e == 1) {
					vertexData[i] = MAX_VERTEX;
				} else {
					vertexData[i] = (float) count;
				}
				
				i++;
				vertexData[i] = 1.0f;
				i++;
				vertexData[i] = 1.0f;
				i++;
				vertexData[i] = 1.0f;
				i++;
				vertexData[i] = 1.0f;
				i++;
			}
		}
    	
        setup();
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    @Override
    public void setup() {
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexData).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        //vertexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, NUM_VERTICES * STRIDE * OpenGLConstants.BYTES_PER_FLOAT,
                vertexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);

        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(EngineContext.shadeFactory.LAYOUT_COLOR);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_VERTEX, VERTEX_SIZE,
                        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(EngineContext.shadeFactory.LAYOUT_COLOR, COLOR_SIZE,
                        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, VERTEX_SIZE * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
    }

    /**
     * 
     */
    @Override
    public void draw() {
        GLES30.glUseProgram (EngineContext.shadeFactory.defaultProgram);

        // Load the MVP matrix
        GLES30.glUniformMatrix4fv(EngineContext.shadeFactory.DEF_PROG_MVP_LOC, 1, false,
                Camera.getInstance().getMvpMatrixAsFloatBuffer());

        GLES30.glBindVertexArray(VAOIds[0]);

        // Draw the triangle based on the indices
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, NUM_VERTICES);

        GLES30.glBindVertexArray(0);
    }

	@Override
	public void updateTransformBuffers() {
		// TODO Auto-generated method stub
		
	}
	
}
