package com.redru.engine.wrapper.models;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.OpenGLUtils;
import com.redru.engine.wrapper.textures.Texture;

/**
 * Created by Luca on 22/01/2015.
 */
public class Model implements Cloneable {
    private static final String TAG = "Obj";
    public static final String DEFAULT_NAME = "OBJ";
    
    private String name;
    private int totalVertexes = -1;
    private int totalTextures = -1;
    private int totalNormals = -1;
    private int totalIndices = -1;
    private int totalFaces = -1;
    
    private float[] unifiedData;
    private float[] startingUnifiedData;
    private float[] collisionInfo;
    
    private Texture texture;
    
// CONSTRUCTOR ----------------------------------------------------------------------------------------
    protected Model() {  }
    
    protected Model(float[] positions, float[] textures, float[] normals, float[] unifiedData, float[] collisionInfo, String name) {
        if (name == null || name.equals("")) {
        	this.name = DEFAULT_NAME;
        } else {
        	this.name = name;
        }
        
        this.setUnifiedData(unifiedData);
        this.copyUnifiedDataToStartingUnifiedData();
        this.collisionInfo = collisionInfo;

        Log.i(TAG, "Creating new EvoObj '" + this.name + "'.");

        if (positions != null) {
        	this.setTotalVertexes(positions.length / OpenGLConstants.SINGLE_V_SIZE);
        }
        if (textures != null) {
        	this.setTotalTextures(textures.length / OpenGLConstants.SINGLE_VT_SIZE);
        }
        if (normals != null) {
        	this.setTotalNormals(normals.length / OpenGLConstants.SINGLE_VN_SIZE);
        }
        
        this.setTotalFaces(unifiedData.length / 8 / 3);
        
        // Log Object Data
        logEvoObjInformation();
        Log.i(TAG, "EvoObj '" + this.name + "' was successfully created.");
    }
// BASIC TRANSFORMATIONS --------------------------------------------------------------------------------------------
    
    public void translate(float xUpset, float yUpset, float zUpset) {
    	OpenGLUtils.translateUnifiedMatrixData(this.unifiedData, xUpset, yUpset, zUpset);
    }
    
    public void translateToPosition(float xUpset, float yUpset, float zUpset) {
    	OpenGLUtils.translateUnifiedMatrixDataToPosition(unifiedData, startingUnifiedData, xUpset, yUpset, zUpset);
    }
    
    public void scale(float xScale, float yScale, float zScale) {
    	OpenGLUtils.scaleUnifiedMatrixData(this.unifiedData, this.startingUnifiedData, xScale, yScale, zScale);
    }
    
    public void rotate(float xAxis, float yAxis, float zAxis) {
    	OpenGLUtils.rotateUnifiedMatrixData(this.unifiedData, this.startingUnifiedData, xAxis, yAxis, zAxis);
    }
    
    public void loadOriginData() {
		for (int i = 0; i < this.startingUnifiedData.length; i++) {
			this.unifiedData[i] = startingUnifiedData[i];
		}
    }
// LOG --------------------------------------------------------------------------------------------------------------
    public void logEvoObjInformation() {
        StringBuilder info = new StringBuilder();
        info.append("EvoObj Info Data:");
        info.append("\n-----------------------------------");
        info.append("\nTOTAL VERTICES: " + this.totalVertexes);
        info.append("\nTOTAL TEXTURE COORDINATES: " + this.totalTextures);
        info.append("\nTOTAL NORMALS: " + this.totalNormals);
        info.append("\nTOTAL FACES: " + this.totalFaces);
        info.append("\n-----------------------------------");
        Log.i(TAG, info.toString());
    }
// SETTERS AND GETTERS ----------------------------------------------------------------------------------------------
    public String getName() {
		return name;
	}

    public int getTotalVertexes() {
		return totalVertexes;
	}

	public void setTotalVertexes(int totalVertexes) {
		this.totalVertexes = totalVertexes;
	}

	public int getTotalTextures() {
		return totalTextures;
	}

	public void setTotalTextures(int totalTextures) {
		this.totalTextures = totalTextures;
	}

	public int getTotalNormals() {
		return totalNormals;
	}

	public void setTotalNormals(int totalNormals) {
		this.totalNormals = totalNormals;
	}

	public int getTotalIndices() {
		return totalIndices;
	}

	public void setTotalIndices(int totalIndices) {
		this.totalIndices = totalIndices;
	}

	public int getTotalFaces() {
		return totalFaces;
	}

	public void setTotalFaces(int totalFaces) {
		this.totalFaces = totalFaces;
	}

    public float[] getUnifiedData() {
		return unifiedData;
	}

	public void setUnifiedData(float[] unifiedData) {
		this.unifiedData = unifiedData;
	}

	public float[] getStartingUnifiedData() {
		return startingUnifiedData;
	}

	public void setStartingUnifiedData(float[] startingUnifiedData) {
		this.startingUnifiedData = startingUnifiedData;
	}
	
	public float[] getCollisionInfo() {
		return collisionInfo;
	}

	public void setCollisionInfo(float[] collisionInfo) {
		this.collisionInfo = collisionInfo;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public void copyUnifiedDataToStartingUnifiedData() {
		this.startingUnifiedData = new float[unifiedData.length];
		
		for (int i = 0; i < this.unifiedData.length; i++) {
			startingUnifiedData[i] = unifiedData[i];
		}
	}
// CLONE ----------------------------------------------------------------------------------------------
	@Override
	public Model clone() {        
		return this;
    }
// ----------------------------------------------------------------------------------------------------

}
