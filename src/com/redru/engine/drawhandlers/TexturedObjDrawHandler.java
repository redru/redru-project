package com.redru.engine.drawhandlers;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.redru.engine.elements.GameActor;
import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.ResourceUtils;
import com.redru.engine.utils.ShaderFactory;
import com.redru.engine.view.Camera;

/**
 * Created by Luca on 22/01/2015.
 */
public class TexturedObjDrawHandler implements IntDrawHandler {
    private static final String TAG = "TexturedObjDrawHandler";

    private GameActor actor;

    private FloatBuffer vertexBuffer;
    private FloatBuffer scaleBuffer;
    private FloatBuffer rotationBuffer;
    private FloatBuffer translationBuffer;

    private int[] VBOIds = new int[1];
    private int[] VAOIds = new int[1];
    private int[] textureId = new int[1];

    /**
     * 
     * @param obj
     */
    public TexturedObjDrawHandler(GameActor actor) {
        this.actor = actor;

        this.setup();
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    @Override
    public void setup() {
    	// If the advanced logs are actived, log the unifiedData of the object
    	if (Boolean.parseBoolean(ResourceUtils.getApplicationProperty("advanced_logs"))) {
	    	StringBuilder str = new StringBuilder();
	    	
	    	for (int i = 0, x = 0, y = 0; i < actor.getModel().getUnifiedData().length; i++) {
	    		str.append(actor.getModel().getUnifiedData()[i] + ", ");
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
    	
        // initialize vertex byte buffer for shape coordinates------------------------------------
        vertexBuffer = ByteBuffer.allocateDirect(actor.getModel().getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(actor.getModel().getUnifiedData()).position(0);
        //VERTEX DATA CONFIGURATION------------------------------------------------------------------
        GLES30.glGenBuffers(1, VBOIds, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, actor.getModel().getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT,
                vertexBuffer, GLES30.GL_STATIC_DRAW);
        //TEXTURE CONFIGURATION----------------------------------------------------------------------
        GLES30.glGenTextures(1, textureId, 0);
        
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, actor.getModel().getTexture().getBitmap(), 0);
        
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);       
      //----------------------------------------------------------------------------------------------
        // Vertex Array Object (VAO) configuration
        GLES30.glGenVertexArrays(1, VAOIds, 0);
        
        GLES30.glBindVertexArray(VAOIds[0]);

        // TEXTURES
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId[0]);
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
    }
    
    /**
     * 
     */
    @Override
    public void updateBuffers() {
    	GLES30.glBindVertexArray(VAOIds[0]);
    	
//    	GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBOIds[0]);
//    	vertexBuffer = ( ( ByteBuffer ) GLES30.glMapBufferRange (
//                GLES30.GL_ARRAY_BUFFER, 0, obj.getUnifiedData().length * OpenGLConstants.BYTES_PER_FLOAT,
//                GLES30.GL_MAP_WRITE_BIT | GLES30.GL_MAP_INVALIDATE_BUFFER_BIT )
//           ).order ( ByteOrder.nativeOrder() ).asFloatBuffer();
//    	vertexBuffer.put(obj.getUnifiedData());    	    	
//    	
//    	if (!GLES30.glUnmapBuffer(GLES30.GL_ARRAY_BUFFER)) {
//    		Log.i(TAG, "Problems unmapping buffer in object: " + obj.getName());
//    	}
    	scaleBuffer = ByteBuffer.allocateDirect(this.actor.getScalationMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    	scaleBuffer.put(this.actor.getScalationMatrix()).position(0);
        
        rotationBuffer = ByteBuffer.allocateDirect(this.actor.getRotationMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        rotationBuffer.put(this.actor.getRotationMatrix()).position(0);
        
        translationBuffer = ByteBuffer.allocateDirect(this.actor.getPositionMatrix().length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        translationBuffer.put(this.actor.getPositionMatrix()).position(0);
    	
    	GLES30.glBindVertexArray(0);
    }

    /**
     * 
     */
    @Override
    public void draw() {
        GLES30.glUseProgram(ShaderFactory.getInstance().complexObjectProgram);

        GLES30.glUniform1i(ShaderFactory.getInstance().COMP_PROG_SAMPLER_S_TEXTURE, 0);
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().COMP_PROG_MVP_LOC, 1, false, Camera.getInstance().getMvpMatrixAsFloatBuffer());
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().COMP_PROG_SCA_LOC, 1, false, scaleBuffer);
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().COMP_PROG_ROT_LOC, 1, false, rotationBuffer);
        GLES30.glUniformMatrix4fv(ShaderFactory.getInstance().COMP_PROG_TRA_LOC, 1, false, translationBuffer);

        // Bind this object Vertex Array Object (VAO) state and then draw the object
        GLES30.glBindVertexArray(VAOIds[0]);
        
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, actor.getModel().getUnifiedData().length);

        GLES30.glBindVertexArray(0);
    }
}
