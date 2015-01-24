package com.redru.engine.utils;

public class OpenGLUtils {
	
	/*
	 * Returns a 4 x 4 Matrix from a 1 x 3 Vector
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
	
	public static float[] get1x3VectorFrom4x4Matrix(float[] matrix) {
		float[] tmp = { matrix[0], matrix[5], matrix[10] };
		
		return tmp;
	}
	
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
	
}
