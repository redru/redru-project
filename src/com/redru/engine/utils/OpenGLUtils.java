package com.redru.engine.utils;


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
	 * @param unifiedData
	 * @param xUpset
	 * @param yUpset
	 * @param zUpset
	 */
	public static void translateUnifiedMatrixData(float[] unifiedData, float xUpset, float yUpset, float zUpset) {
		for (int i = 0; i < unifiedData.length; i++) {
			for (int e = 0; e < 3; e++) {
				if (e == 0) {
					unifiedData[i] += xUpset;
				} else if (e == 1) {
					unifiedData[i] += yUpset;
				} else if (e == 2) {
					unifiedData[i] += zUpset;
				}
				
				i++;
			}
			
			i += 4;
		}
	}
	
	/**
	 * 
	 * @param unifiedData
	 * @param startingUnifiedData
	 * @param xUpset
	 * @param yUpset
	 * @param zUpset
	 */
	public static void translateUnifiedMatrixDataToPosition(float[] unifiedData, float[] startingUnifiedData, float xUpset, float yUpset, float zUpset) {
		for (int i = 0; i < unifiedData.length; i++) {
			for (int e = 0; e < 3; e++) {
				if (e == 0) {
					unifiedData[i] = startingUnifiedData[i] + xUpset;
				} else if (e == 1) {
					unifiedData[i] = startingUnifiedData[i] + yUpset;
				} else if (e == 2) {
					unifiedData[i] = startingUnifiedData[i] + zUpset;
				}
				
				i++;
			}
			
			i += 4;
		}
	}
	
	/**
	 * 
	 * @param result
	 * @param unifiedData
	 * @param xAxis
	 * @param yAxis
	 * @param zAxis
	 */
	public static void rotateUnifiedMatrixData(float[] result, float[] startingUnifiedData, float xAxis, float yAxis, float zAxis) {
		double sin;
		double cos;
		
		// X AXIS ROTATION
		if (xAxis != 0.0f) {
			sin = Math.sin(Math.toRadians(xAxis));
			cos = Math.cos(Math.toRadians(xAxis));
			
			for (int i = 0; i < result.length; i++) {
				for (int e = 0; e < 3; e++) {
					if (e == 0) {
						result[i] = startingUnifiedData[i];
					} else if (e == 1) {
						result[i] = (float) ( (startingUnifiedData[i] * cos) + (startingUnifiedData[i + 1] * -sin) );
					} else if (e == 2) {
						result[i] = (float) ( (startingUnifiedData[i - 1] * sin) + (startingUnifiedData[i] * cos) );
					}
					
					i++;
				}
				
				i += 4;
			}
		}
		
		// Y AXIS ROTATION
		if (yAxis != 0.0f) {
			sin = Math.sin(Math.toRadians(yAxis));
			cos = Math.cos(Math.toRadians(yAxis));
			
			for (int i = 0; i < result.length; i++) {
				for (int e = 0; e < 3; e++) {
					if (e == 0) {
						result[i] = (float) ( (startingUnifiedData[i] * cos) + (startingUnifiedData[i + 2] * sin) );
					} else if (e == 1) {
						result[i] = startingUnifiedData[i];
					} else if (e == 2) {
						result[i] = (float) ( (startingUnifiedData[i - 2] * -sin) + (startingUnifiedData[i] * cos) );
					}
					
					i++;
				}
				
				i += 4;
			}
		}
		
		// Z AXIS ROTATION
		if (zAxis != 0.0f) {
			sin = Math.sin(Math.toRadians(zAxis));
			cos = Math.cos(Math.toRadians(zAxis));
			
			for (int i = 0; i < result.length; i++) {
				for (int e = 0; e < 3; e++) {
					if (e == 0) {
						result[i] = (float) ( (startingUnifiedData[i] * cos) + (startingUnifiedData[i + 1] * -sin) );
					} else if (e == 1) {
						result[i] = (float) ( (startingUnifiedData[i - 1] * sin) + (startingUnifiedData[i] * cos) );
					} else if (e == 2) {
						result[i] = startingUnifiedData[i];
					}
					
					i++;
				}
				
				i += 4;
			}
		}
	}
	
	/**
	 * 
	 * @param unifiedData
	 * @param xUpset
	 * @param yUpset
	 * @param zUpset
	 */
	public static void scaleUnifiedMatrixData(float[] result, float[] startingUnifiedData, float xScale, float yScale, float zScale) {
		for (int i = 0; i < result.length; i++) {
			for (int e = 0; e < 3; e++) {
				if (e == 0 && xScale != 0) {
					result[i] = startingUnifiedData[i] * xScale;
				} else if (e == 1 && yScale != 0) {
					result[i] = startingUnifiedData[i] * yScale;
				} else if (e == 2 && zScale != 0) {
					result[i] = startingUnifiedData[i] * zScale;
				}
				
				i++;
			}
			
			i += 4;
		}
	}
	
	/**
	 * 
	 * @param positions
	 * @param textures
	 * @param normals
	 * @param indices
	 * @return
	 */
	public static float[] generateUnifiedData(float[] positions, float[] textures, float[] normals, short[][] indices) {
		float[] tmp = new float[(OpenGLConstants.SINGLE_V_SIZE + OpenGLConstants.SINGLE_VT_SIZE + OpenGLConstants.SINGLE_VN_SIZE) * indices.length];
		
		for (int i = 0, x = 0; i < indices.length; i++) {
			for (int e = 0; e < indices[i].length; e++) {
				if (e == 0) {
					for (int w = 0; w < OpenGLConstants.SINGLE_V_SIZE; w++) {
						tmp[x] = positions[indices[i][e] * OpenGLConstants.SINGLE_V_SIZE + w];
						x++;
					}
				} else if (e == 1) {
					for (int w = 0; w < OpenGLConstants.SINGLE_VT_SIZE; w++) {
						tmp[x] = textures[indices[i][e] * OpenGLConstants.SINGLE_VT_SIZE + w];
						x++;
					}
				} else if (e == 2) {
					for (int w = 0; w < OpenGLConstants.SINGLE_VN_SIZE; w++) {
						tmp[x] = normals[indices[i][e] * OpenGLConstants.SINGLE_VN_SIZE + w];
						x++;
					}
				}
			}
		}
		
		return tmp;
	}
	
}
