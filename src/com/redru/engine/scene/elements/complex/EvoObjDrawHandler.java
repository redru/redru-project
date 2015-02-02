package com.redru.engine.scene.elements.complex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.ResourceUtils;
import com.redru.engine.utils.ShaderFactory;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.objects.Obj;

/**
 * Created by Luca on 22/01/2015.
 */
public class EvoObjDrawHandler {
    private static final String TAG = "SceneComplexObject";

    private Obj evoObj;

    private FloatBuffer vertexBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];
    private int[] textureId = new int[1];

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
    public EvoObjDrawHandler(Obj evoObj) {
        this.evoObj = evoObj;

        this.setup();
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    public void setup() {
    	// If the advanced logs are actived, log the unifiedData of the object
    	if (Boolean.parseBoolean(ResourceUtils.getApplicationProperty("advanced_logs"))) {
	    	StringBuilder str = new StringBuilder();
	    	
	    	for (int i = 0, x = 0, y = 0; i < evoObj.getUnifiedData().length; i++) {
	    		str.append(evoObj.getUnifiedData()[i] + ", ");
	    		if (x % 7 == 0 && x != 0) {
	    			Log.i(TAG, "UNIFIED DATA " + y + ": " + str);
	    			str.delete(0, str.length());
	    			x = 0;
	    			y++;
	    		} else {
	    			x++;
	    		}
	    	}
    	}
    	
        Log.i(TAG, "Buffers setup: start.");
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(evoObj.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(evoObj.getUnifiedData()).position(0);
        //VERTEX DATA CONFIGURATION------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, evoObj.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT,
                vertexBuffer, GLES30.GL_STATIC_DRAW);
        //TEXTURE CONFIGURATION----------------------------------------------------------------------
        GLES30.glGenTextures(1, textureId, 0);
        
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, evoObj.getTexture().getBitmap(), 0);
        
        
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);       
      //----------------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        GLES30.glBindVertexArray(VAOIds[0]);

        // TEXTURES
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
        GLES30.glUniform1i(ShaderFactory.getInstance().SAMPLER_S_TEXTURE, 0);
        // TEXTURES END
        
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        
        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_VERTEX);
        GLES30.glEnableVertexAttribArray(ShaderFactory.getInstance().LAYOUT_TEXTURE);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_VERTEX, 3,
                GLES30.GL_FLOAT, false, 8 * OpenGLConstants.BYTES_PER_FLOAT, 0);

        GLES30.glVertexAttribPointer(ShaderFactory.getInstance().LAYOUT_TEXTURE, 2,
                GLES30.GL_FLOAT, false, 8 * OpenGLConstants.BYTES_PER_FLOAT, 3 * OpenGLConstants.BYTES_PER_FLOAT);
        
        GLES30.glBindVertexArray(0);
        //----------------------------------------------------------------------------------------
        Log.i(TAG, "Buffers setup: complete.");
    }
    
    public void updateBuffers() {
    	GLES30.glBindVertexArray(VAOIds[0]);
    	
    	GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
    	vertexBuffer = ( ( ByteBuffer ) GLES30.glMapBufferRange (
                GLES30.GL_ARRAY_BUFFER, 0, evoObj.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT,
                GLES30.GL_MAP_WRITE_BIT | GLES30.GL_MAP_INVALIDATE_BUFFER_BIT )
           ).order ( ByteOrder.nativeOrder() ).asFloatBuffer();
    	vertexBuffer.put(evoObj.getUnifiedData());    	    	
    	
    	if (!GLES30.glUnmapBuffer(GLES30.GL_ARRAY_BUFFER)) {
    		Log.i(TAG, "Problems unmapping buffer in object: " + evoObj.getName());
    	}
    	
    	GLES30.glBindVertexArray(0);
    	
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
        
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, evoObj.getUnifiedData().length);

        GLES30.glBindVertexArray(0);
    }
}
