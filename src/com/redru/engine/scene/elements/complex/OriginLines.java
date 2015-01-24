package com.redru.engine.scene.elements.complex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.scene.SceneElement;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.ShaderFactory;
import com.redru.engine.view.Camera;

/**
 * Created by Luca on 20/01/2015.
 */
public class OriginLines implements SceneElement {

    private static final String TAG = "OriginLines";

    private static int VERTEX_SIZE = 3;
    private static int COLOR_SIZE = 4;
    private static int STRIDE = VERTEX_SIZE + COLOR_SIZE;
    private static int NUM_VERTICES = 6;
    private static int NUM_INDICES = 6;

    private float[] vertexData = {
            /* VERTEX */ 0.0f, 0.0f, 0.0f,   /* COLOR */ 0.0f, 1.0f, 0.0f, 1.0f,
            /* VERTEX */ 100.0f, 0.0f, 0.0f, /* COLOR */ 0.0f, 1.0f, 0.0f, 1.0f,
            /* VERTEX */ 0.0f, 0.0f, 0.0f,   /* COLOR */ 1.0f, 0.0f, 0.0f, 1.0f,
            /* VERTEX */ 0.0f, 100.0f, 0.0f, /* COLOR */ 1.0f, 0.0f, 0.0f, 1.0f,
            /* VERTEX */ 0.0f, 0.0f, 0.0f,   /* COLOR */ 0.0f, 0.0f, 1.0f, 1.0f,
            /* VERTEX */ 0.0f, 0.0f, 100.0f, /* COLOR */ 0.0f, 0.0f, 1.0f, 1.0f
    };

    private short[] indexData = {
            0, 1,
            2, 3,
            4, 5
    };

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private int[] VBOIds = new int[2];
    private int[] VAOIds = new int[1];

    public OriginLines() {
        setup();
        Log.i(TAG, "Creation complete.");
    }

    @Override
    public void setup() {
        Log.i(TAG, "Buffers setup: start.");
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexData).position(0);

        // initialize indices byte buffer for shape coordinates-----------------------------------
        indexBuffer = ByteBuffer.allocateDirect(indexData.length * OpenGLConstants.BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(indexData).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(2, VBOIds, 0);

        //vertexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, NUM_VERTICES * STRIDE * OpenGLConstants.BYTES_PER_FLOAT,
                vertexBuffer, GLES30.GL_STATIC_DRAW);

        //indexBuffer.position( 0 );
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, VBOIds[1]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, NUM_INDICES * OpenGLConstants.BYTES_PER_SHORT,
                indexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, VBOIds[1]);

        GLES30.glEnableVertexAttribArray(ShaderFactory.LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(ShaderFactory.LAYOUT_COLOR);

        GLES30.glVertexAttribPointer(ShaderFactory.LAYOUT_VERTEX, VERTEX_SIZE,
                        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(ShaderFactory.LAYOUT_COLOR, COLOR_SIZE,
                        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, VERTEX_SIZE * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
        Log.i(TAG, "Buffers setup: complete.");
    }

    @Override
    public void draw() {
        GLES30.glUseProgram (ShaderFactory.defaultProgram);

        // Load the MVP matrix
        GLES30.glUniformMatrix4fv(ShaderFactory.MVP_LOC, 1, false,
                Camera.getInstance().getMvpMatrixAsFloatBuffer());

        // Bind Vertex Buffer Objects (VBO)
        //GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        //GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, VBOIds[1]);

        // Enable attribute arrays
        //GLES30.glEnableVertexAttribArray(ShaderFactory.LAYOUT_VERTEX);
        //GLES30.glEnableVertexAttribArray(ShaderFactory.LAYOUT_COLOR);

        // Process vertex buffer
        //GLES30.glVertexAttribPointer(ShaderFactory.LAYOUT_VERTEX, VERTEX_SIZE,
        //        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, 0);

        // Process color buffer
        //GLES30.glVertexAttribPointer(ShaderFactory.LAYOUT_COLOR, COLOR_SIZE,
        //        GLES30.GL_FLOAT, false, STRIDE * OpenGLConstants.BYTES_PER_FLOAT, VERTEX_SIZE * OpenGLConstants.BYTES_PER_FLOAT);

        GLES30.glBindVertexArray(VAOIds[0]);

        // Draw the triangle based on the indices
        GLES30.glDrawElements(GLES30.GL_LINES, 6,
                GLES30.GL_UNSIGNED_SHORT, 0);

        GLES30.glBindVertexArray(0);

        // Disable attribute arrays and unbind Vertex Buffer Objects (VBO)
        //GLES30.glDisableVertexAttribArray(ShaderFactory.LAYOUT_VERTEX);
        //GLES30.glDisableVertexAttribArray(ShaderFactory.LAYOUT_COLOR);

        //GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        //GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void translate() {

    }

    @Override
    public void rotate() {

    }

}
