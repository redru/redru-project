package com.redru.engine.scene.elements.complex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES30;
import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.ShaderFactory;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.EvoObj;

/**
 * Created by Luca on 22/01/2015.
 */
public class EvoObjDrawHandler {

    private static final String TAG = "SceneComplexObject";

    private int VERTEX_SIZE = -1;
    private int COLOR_SIZE = 4;
    private int STRIDE = VERTEX_SIZE + COLOR_SIZE;
    private int NUM_VERTICES = -1;
    private int INDICES_SIZE = -1;

    private EvoObj evoObj;

    private FloatBuffer vertexBuffer;
    private ShortBuffer indexBuffer;

    private int[] VBOIds = new int[2];
    private int[] VAOIds = new int[1];

    /**
     * 
     */
    public EvoObjDrawHandler() {
        this(null);
    }

    /**
     * 
     * @param evoObj
     */
    public EvoObjDrawHandler(EvoObj evoObj) {
        this.evoObj = evoObj;
        VERTEX_SIZE = evoObj.getSinglePositionSize();
        NUM_VERTICES = evoObj.getVertexes();
        evoObj.getIndices();
        INDICES_SIZE = evoObj.getPositionIndexData().length;

        setup();
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    public void setup() {
        Log.i(TAG, "Buffers setup: start.");
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(evoObj.getSingleArrayPositionData().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(evoObj.getSingleArrayPositionData()).position(0);

        // initialize indices byte buffer for shape coordinates-----------------------------------
        indexBuffer = ByteBuffer.allocateDirect(INDICES_SIZE * OpenGLConstants.BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder()).asShortBuffer();
        indexBuffer.put(evoObj.getPositionIndexData()).position(0);
        //---------------------------------------------------------------------------------------
        GLES30.glGenBuffers(2, VBOIds, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, NUM_VERTICES * STRIDE * OpenGLConstants.BYTES_PER_FLOAT,
                vertexBuffer, GLES30.GL_STATIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, VBOIds[1]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, INDICES_SIZE * OpenGLConstants.BYTES_PER_SHORT,
                indexBuffer, GLES30.GL_STATIC_DRAW);
        //----------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, VBOIds[1]);

        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_COLOR);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_VERTEX, VERTEX_SIZE,
                GLES30.GL_FLOAT, false, 3 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_COLOR, COLOR_SIZE,
                GLES30.GL_FLOAT, false, 3 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
        Log.i(TAG, "Buffers setup: complete.");
    }

    /**
     * 
     */
    public void draw() {
        GLES30.glUseProgram(ShaderFactory.getInstance().complexObjectProgram);

        // Load the MVP matrix
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().MVP_LOC, 1, false,
                Camera.getInstance().getMvpMatrixAsFloatBuffer());

        // Bind this object Vertex Array Object (VAO) state and then draw the object
        GLES30.glBindVertexArray(VAOIds[0]);

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, evoObj.getPositionIndexData().length,
               GLES30.GL_UNSIGNED_SHORT, 0);

        GLES30.glBindVertexArray(0);
    }
}
