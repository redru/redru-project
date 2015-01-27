package com.redru.engine.wrapper;

import android.util.Log;

import com.redru.engine.utils.OpenGLUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class EvoObj implements Cloneable {
    private static final String TAG = "EvoObj";
    public static final float[] EMPTY_FLOAT_ARRAY = { 0.0f };
    public static final short[] EMPTY_SHORT_ARRAY = { 0 };
    public static final String DEFAULT_NAME = "EVO_OBJ";
    
    private String name;

    private int positionsCount = -1;
    private int texturesCount = -1;
    private int normalsCount = -1;

    private int singlePositionSize = -1;
    private int singleTextureSize = -1;
    private int singleNormalSize = -1;

    private float[] positions;
    private float[] textures;
    private float[] normals;
    private float[] unifiedData;
    private int[] indicesStride;

    /**
     * Default constructor. Initializes all arrays to NULL.
     */
    public EvoObj() {
        this(EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, DEFAULT_NAME);
    }
    
    /**
     * 
     * @param positionIndexData
     * @param name
     */
    public EvoObj(float[] positions,
    			  String name) {
        this(positions, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, name);
    }
    
    /**
     * 
     * @param positionIndexData
     * @param textureCoordinatesIndexData
     * @param name
     */
    public EvoObj(float[] positions,
    			  float[] textures,
    			  String name) {
        this(positions, textures, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, name);
    }

    /**
     * 
     * @param positionIndexData
     * @param textureCoordinatesIndexData
     * @param normalIndexData
     * @param name
     */
    public EvoObj(float[] positions,
    			  float[] textures,
    			  float[] normals,
    			  String name) {
    	this(positions, textures, normals, EMPTY_FLOAT_ARRAY, name);
    }
    
    /**
     * 
     * @param positionIndexData
     * @param textureCoordinatesIndexData
     * @param normalIndexData
     * @param unifiedData
     * @param name
     */
    public EvoObj(float[] positionIndexData,
    			  float[] textureCoordinatesIndexData,
    			  float[] normalIndexData,
    			  float[] unifiedData,
    			  String name) {
        if (name == null || name.equals("")) {
        	this.name = DEFAULT_NAME;
        } else {
        	this.name = name;
        }

        Log.i(TAG, "Creating new EvoObj '" + this.name + "'.");

        // Init Arrays
        this.setUnifiedData(unifiedData);
        
        // Log Object Data
        logEvoObjInformation();
        Log.i(TAG, "EvoObj '" + this.name + "' was successfully created.");
    }

    /**
     * 
     * @return
     */
    public float[] getUnifiedData() {
		return unifiedData;
	}

    /**
     * 
     * @param unifiedData
     */
	public void setUnifiedData(float[] unifiedData) {
		this.unifiedData = unifiedData;
	}

    /**
     * 
     * @return
     */
    public String getName() {
		return name;
	}

    /**
     * 
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}
    
    /**
     * Writes to the log the object most important information
     */
    public void logEvoObjInformation() {
        StringBuilder info = new StringBuilder();
        info.append("EvoObj Info Data:");
        info.append("\n-----------------------------------");
        info.append("\nTOTAL VERTICES: " + "vertices");
        info.append("\nTOTAL TEXTURE COORDINATES: " + textures);
        info.append("\nTOTAL NORMALS: " + normals);
        info.append("\nTOTAL INDICES: " + "indices");
        info.append("\n-----------------------------------");
        Log.i(TAG, info.toString());
    }
    
    /**
     * 
     * @param xUpset
     * @param yUpset
     * @param zUpset
     */
    public void translate(float xUpset, float yUpset, float zUpset) {
    	OpenGLUtils.translateUnifiedMatrixData(this.unifiedData, xUpset, yUpset, zUpset);
    }
    
    /**
     * 
     */
    public void rotate() {
    	
    }
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
