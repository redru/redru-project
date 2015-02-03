package com.redru.engine.wrapper.objects;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.OpenGLUtils;
import com.redru.engine.wrapper.textures.Texture;

/**
 * Created by Luca on 22/01/2015.
 */
public class Obj implements Cloneable {
    private static final String TAG = "EvoObj";
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
    
    private float[] startingPositions;
    private float[] startingUnifiedData;
    
    private Texture texture;
    
    /**
     * 
     * @param positionIndexData
     * @param textureCoordinatesIndexData
     * @param normalIndexData
     * @param unifiedData
     * @param name
     */
    protected Obj(float[] positions, float[] textures, float[] normals, float[] unifiedData, String name) {
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
        
        this.setStartingPositions(positions);
        this.setStartingUnifiedData(unifiedData);
        
        // Log Object Data
        logEvoObjInformation();
        Log.i(TAG, "EvoObj '" + this.name + "' was successfully created.");
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

    public float[] getPositions() {
		return positions;
	}

	public void setPositions(float[] positions) {
		this.positions = positions;
	}

	public float[] getTextures() {
		return textures;
	}

	public void setTextures(float[] textures) {
		this.textures = textures;
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

    public float[] getUnifiedData() {
		return unifiedData;
	}

	public void setUnifiedData(float[] unifiedData) {
		this.unifiedData = unifiedData;
	}

    public float[] getStartingPositions() {
		return startingPositions;
	}

	public void setStartingPositions(float[] startingPositions) {
		this.startingPositions = new float[startingPositions.length];
		
		for (int i = 0; i < this.startingPositions.length; i++) {
			this.startingPositions[i] = startingPositions[i];
		}
	}

	public float[] getStartingUnifiedData() {
		return startingUnifiedData;
	}

	public void setStartingUnifiedData(float[] startingUnifiedData) {
		this.startingUnifiedData = new float[startingUnifiedData.length];
		
		for (int i = 0; i < this.startingUnifiedData.length; i++) {
			this.startingUnifiedData[i] = startingUnifiedData[i];
		}
	}

    public String getName() {
		return name;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

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
    
    public void translate(float xUpset, float yUpset, float zUpset) {
    	OpenGLUtils.translateUnifiedMatrixData(this.unifiedData, xUpset, yUpset, zUpset);
    }
    
    public void moveToPosition(float xUpset, float yUpset, float zUpset) {
    	OpenGLUtils.translateUnifiedMatrixDataToPosition(unifiedData, startingUnifiedData, xUpset, yUpset, zUpset);
    }
    
    public void scale(float xScale, float yScale, float zScale) {
    	OpenGLUtils.scaleUnifiedMatrixData(this.unifiedData, xScale, yScale, zScale);
    }
	
    /**
     * 
     */
    public void rotate() {
    	
    }
    
    public Obj clone() throws CloneNotSupportedException {

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
    	
    	Obj tmp = new Obj(tmpPositions, tmpTextures, tmpNormals, tmpUnifiedData, this.name);
    	tmp.setTotalTextures(this.totalTextures);
    	tmp.setTotalTextures(this.totalTextures);
    	tmp.setTotalNormals(this.totalNormals);
    	tmp.setTotalIndices(this.totalIndices);
    	tmp.setTotalFaces(this.totalFaces);
        
        return tmp;
    }

}
