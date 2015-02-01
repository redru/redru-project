package com.redru.engine.wrapper.objects;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.OpenGLUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class EvoObj implements Cloneable {
    private static final String TAG = "EvoObj";
    public static final float[] EMPTY_FLOAT_ARRAY = { 0.0f };
    public static final String DEFAULT_NAME = "EVO_OBJ";
    
    private String name;
    private int totalVertexes = -1;
    private int totalTextures = -1;
    private int totalNormals = -1;
    private int totalIndices = -1;
    private int totalFaces = -1;

    private float[] positions;
    private float[] textures;
    private float[] normals;
    private float[] unifiedData;
    
    private Texture texture;

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
    public EvoObj(float[] positions,
    			  float[] textures,
    			  float[] normals,
    			  float[] unifiedData,
    			  String name) {
        if (name == null || name.equals("")) {
        	this.name = DEFAULT_NAME;
        } else {
        	this.name = name;
        }

        Log.i(TAG, "Creating new EvoObj '" + this.name + "'.");

        this.setPositions(positions);
        this.setTotalVertexes(positions.length / OpenGLConstants.SINGLE_V_SIZE);
        this.setTextures(textures);
        this.setTotalTextures(textures.length / OpenGLConstants.SINGLE_VT_SIZE);
        this.setNormals(normals);
        this.setTotalNormals(normals.length / OpenGLConstants.SINGLE_VN_SIZE);
        this.setUnifiedData(unifiedData);
        this.setTotalFaces(unifiedData.length / 8 / 3);
        
        // Log Object Data
        logEvoObjInformation();
        Log.i(TAG, "EvoObj '" + this.name + "' was successfully created.");
    }

    /**
     * 
     * @return
     */
    public int getTotalVertexes() {
		return totalVertexes;
	}

    /**
     * 
     * @param totalVertexes
     */
	public void setTotalVertexes(int totalVertexes) {
		this.totalVertexes = totalVertexes;
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalTextures() {
		return totalTextures;
	}

	/**
	 * 
	 * @param totalTextures
	 */
	public void setTotalTextures(int totalTextures) {
		this.totalTextures = totalTextures;
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalNormals() {
		return totalNormals;
	}

	/**
	 * 
	 * @param totalNormals
	 */
	public void setTotalNormals(int totalNormals) {
		this.totalNormals = totalNormals;
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalIndices() {
		return totalIndices;
	}

	/**
	 * 
	 * @param totalIndices
	 */
	public void setTotalIndices(int totalIndices) {
		this.totalIndices = totalIndices;
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalFaces() {
		return totalFaces;
	}

	/**
	 * 
	 * @param totalFaces
	 */
	public void setTotalFaces(int totalFaces) {
		this.totalFaces = totalFaces;
	}

	/**
     * 
     * @return
     */
    public float[] getPositions() {
		return positions;
	}

    /**
     * 
     * @param positions
     */
	public void setPositions(float[] positions) {
		this.positions = positions;
	}

	/**
	 * 
	 * @return
	 */
	public float[] getTextures() {
		return textures;
	}

	/**
	 * 
	 * @param textures
	 */
	public void setTextures(float[] textures) {
		this.textures = textures;
	}

	/**
	 * 
	 * @return
	 */
	public float[] getNormals() {
		return normals;
	}

	/**
	 * 
	 * @param normals
	 */
	public void setNormals(float[] normals) {
		this.normals = normals;
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
	 * 
	 * @return
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * 
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
    
    /**
     * Writes to the log the object most important information
     */
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
	
    public EvoObj clone(String name) throws CloneNotSupportedException {

        float[] tmpPositions = new float[this.positions.length];
        float[] tmpTextures = new float[this.textures.length];
        float[] tmpNormals = new float[this.normals.length];
        float[] tmpUnifiedData = new float[this.unifiedData.length];
    	
    	for (int i = 0; i < this.positions.length; i++) {
    		tmpPositions[i] = this.positions[i];
    	}
    	
    	for (int i = 0; i < this.textures.length; i++) {
    		tmpTextures[i] = this.textures[i];
    	}
    	
    	for (int i = 0; i < this.normals.length; i++) {
    		tmpNormals[i] = this.normals[i];
    	}
    	
    	for (int i = 0; i < this.unifiedData.length; i++) {
    		tmpUnifiedData[i] = this.unifiedData[i];
    	}
    	
    	EvoObj tmp = new EvoObj(tmpPositions, tmpTextures, tmpNormals, tmpUnifiedData, name);
    	tmp.setTotalTextures(this.totalTextures);
    	tmp.setTotalTextures(this.totalTextures);
    	tmp.setTotalNormals(this.totalNormals);
    	tmp.setTotalIndices(this.totalIndices);
    	tmp.setTotalFaces(this.totalFaces);
        
        return tmp;
    }

}
