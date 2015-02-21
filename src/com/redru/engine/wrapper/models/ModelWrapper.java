package com.redru.engine.wrapper.models;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.OpenGLUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class ModelWrapper {
    private static final String TAG = "ObjWrapper";

    private static ModelWrapper instance = new ModelWrapper();

 // CONSTRUCTOR ------------------------------------------------------------------------------------------------
    private ModelWrapper() {
        Log.i(TAG, "Creation complete.");
    }

    public static ModelWrapper getInstance() {
        return instance;
    }
// FUNCTIONS ---------------------------------------------------------------------------------------------------
    public Model createModelFromFile(String dataFile, String fileName) {
        Log.i(TAG, "Wrapping the following file: " + fileName + ".obj");
        /*----------------------------------------------------------------------*/
        String[] lines = dataFile.split("\n");

        Model model = this.wrap(lines, fileName);
        /*----------------------------------------------------------------------*/
        Log.i(TAG, "Wrapping completed. File: " + fileName + ".obj");
        
        return model;
    }

    /**
     * 
     * @param lines
     * @param name
     * @return
     */
    private Model wrap(String[] lines, String name) {
        String[] lineParts;
        String[] indicesParts;

        ArrayList<Float> positions = new ArrayList<Float>();
        ArrayList<Float> textures = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();
        ArrayList<short[]> indices = new ArrayList<short[]>();
        
        float[] collisionInfo = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f ,0.0f }; // Rapresents in order: xMin, xMax, yMin, yMax, zMin, zMax
        
        for (int index = 0; index < lines.length; index++) {

            if (lines[index].startsWith("v ")) { // Check if the line is a position data
            	lines[index] = lines[index].substring(2);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_V_SIZE; i++) {
                	Float tmp = Float.parseFloat(lineParts[i]);
                    positions.add(tmp);
                    
                    this.evaluateMinMax(collisionInfo, tmp, i); // Set into the collision info the min and max values for this coordinate
                }

            } else if (lines[index].startsWith("vt ")) { // Check if the line is a texture data
            	lines[index] = lines[index].substring(3);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_VT_SIZE; i++) {
                	textures.add(Float.parseFloat(lineParts[i]));
                }

            } else if (lines[index].startsWith("vn ")) { // Check if the line is a normal data
            	lines[index] = lines[index].substring(3);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_VN_SIZE; i++) {
                	normals.add(Float.parseFloat(lineParts[i]));
                }
                
            } else if (lines[index].startsWith("f ")) { // Check if the line is an index data
                lines[index] = lines[index].substring(2);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_F_SIZE; i++) {
                    indicesParts = lineParts[i].split("/");

                    if (indicesParts.length > 0) {
                    	short[] tmp = new short[3];
                    	
                        for (int e = 0; e < OpenGLConstants.SINGLE_F_INDICES_SIZE; e++) {
                            tmp[e] = Short.parseShort(indicesParts[e]);
                        }
                        
                        indices.add(tmp);
                    }

                }

            }

        }
        
        float[] positionsTmp = new float[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
        	positionsTmp[i] = positions.get(i);
        }
        
        float[] texturesTmp = new float[textures.size()];
        for (int i = 0; i < textures.size(); i++) {
        	texturesTmp[i] = textures.get(i);
        }
        
        float[] normalsTmp = new float[normals.size()];
        for (int i = 0; i < normals.size(); i++) {
        	normalsTmp[i] = normals.get(i);
        }
        
        short[][] indicesTmp = new short[indices.size()][indices.get(0).length];
        for (int i = 0; i < indices.size(); i++) {
        	for (int e = 0; e < indices.get(0).length; e++) {
        		indicesTmp[i][e] = (short) (indices.get(i)[e] - 1);
        	}
        }
        
        float[] unifiedData = OpenGLUtils.generateUnifiedData(positionsTmp, texturesTmp, normalsTmp, indicesTmp);
        Model obj  = new Model(positionsTmp, texturesTmp, normalsTmp, unifiedData, collisionInfo, name);

        return obj;
    }
    
    private void evaluateMinMax(float[] target, float value, int coordType) {
    	// Coord type: 0 equals X, 1 equals Y, 2 equals Z
    	switch (coordType) {
	    	case 0:
	        	if (value < target[0]) {
	        		target[0] = value;
	        	} else if (value > target[1]) {
	        		target[1] = value;
	        	}
	        	break;
	        	
	        case 1:
	        	if (value < target[2]) {
	        		target[2] = value;
	        	} else if (value > target[3]) {
	        		target[3] = value;
	        	}
	        	break;
	        	
	        case 2:
	        	if (value < target[4]) {
	        		target[4] = value;
	        	} else if (value > target[5]) {
	        		target[5] = value;
	        	}
	        	break;
	        	
	        default:
	        	break;
    	}
    }
// -----------------------------------------------------------------------------------------------------------------------
}