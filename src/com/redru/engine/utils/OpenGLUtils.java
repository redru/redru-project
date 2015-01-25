package com.redru.engine.utils;

import java.util.ArrayList;

import android.util.Log;

public class OpenGLUtils {
	private static final String TAG = "OpenGLUtils";
	
	/**
	 * Returns a 4 x 4 Matrix from a 1 x 3 Vector
	 * 
	 * @param vector
	 * @return
	 */
	public static float[] get4x4MatrixFrom1x3Vector(float[] vector) {
		float[] tmp = { 
				vector[0],      0.0f,      0.0f, 0.0f,
				0.0f,      vector[1],      0.0f, 0.0f,
				0.0f,           0.0f, vector[2], 0.0f,
				0.0f,           0.0f,      0.0f, 1.0f
		};
		
		return tmp;
	}
	
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	public static float[] get1x3VectorFrom4x4Matrix(float[] matrix) {
		float[] tmp = { matrix[0], matrix[5], matrix[10] };
		
		return tmp;
	}
	
	/**
	 * 
	 * @param matrixOfPositions
	 * @param xUpset
	 * @param yUpset
	 * @param zUpset
	 * @return
	 */
	public static float[][] translateMatrixOfPositions(float[][] matrixOfPositions, float xUpset, float yUpset, float zUpset) {
		float[][] tmp = new float[matrixOfPositions.length][matrixOfPositions[0].length];
    	float[] tmpVec = new float[3];
    	
    	// Per every point, extract the vector and translate. Then set back again
    	for (int i = 0; i < matrixOfPositions.length; i++) {
    		tmpVec[0] = matrixOfPositions[i][0] + xUpset;
    		tmpVec[1] = matrixOfPositions[i][1] + yUpset;
    		tmpVec[2] = matrixOfPositions[i][2] + zUpset;
    		
    		// Set the resulted vector in the function result vector
    		tmp[i] = tmpVec.clone();
    	}
		
		return tmp;
	}
	
	/**
	 * 
	 * @param positions
	 * @param textures
	 * @param normals
	 * @return
	 */
	public static float[] generateUnifiedData(float[][] positions,
											  float[][] textures,
											  float[][] normals,
											  ArrayList<ArrayList<Short>> posIndices,
											  ArrayList<ArrayList<Short>> textIndices,
											  ArrayList<ArrayList<Short>> normalsIndices) {
		float[] tmp = null;
		int counter = 0;
		
		if (posIndices.size() == textIndices.size() && posIndices.size() == normalsIndices.size()) {
			
			// The final float[] length equals to the sum of (VERTEX_DATA) * (INDICES COUNT) - (INDEX_TOKEN) - 1 (ADJUSTMENT)
			tmp = new float[(OpenGLConstants.SINGLE_V_SIZE + OpenGLConstants.SINGLE_VT_SIZE + OpenGLConstants.SINGLE_VN_SIZE) * posIndices.size()];
			
			for (int i = 0, x = 0; i < posIndices.size(); i++) {
				
				/* POSITIONS */
				for (int e = 0; e < OpenGLConstants.SINGLE_V_SIZE; e++) {
					tmp[x] = positions[posIndices.get(i).get(e) - 1][e];
					x++;
				}
				
				/* TEXTURES */
				for (int e = 0; e < OpenGLConstants.SINGLE_VT_SIZE; e++) {
					tmp[x] = textures[textIndices.get(i).get(e) - 1][e];
					x++;
				}
				
				/* NORMALS */
				for (int e = 0; e < OpenGLConstants.SINGLE_VN_SIZE; e++) {
					tmp[x] = normals[normalsIndices.get(i).get(e) - 1][e];
					x++;
				}
				
				counter++;
			}
		}
		
		if (tmp != null) {
			Log.i(TAG, "Total vertices generated: '" + counter + "' from a total of '" + posIndices.size() + "' indices.");
		} else {
			Log.i(TAG, "A null float[] was returned. Indices data lengths are not valid.");
		}
		
		return tmp;
	}
	
}
