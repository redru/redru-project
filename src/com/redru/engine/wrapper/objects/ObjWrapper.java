package com.redru.engine.wrapper.objects;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;
import com.redru.engine.utils.OpenGLUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class ObjWrapper {
    private static final String TAG = "ObjWrapper";

    private static ObjWrapper instance = new ObjWrapper();

    /**
     * 
     */
    private ObjWrapper() {
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     * @return
     */
    public static ObjWrapper getInstance() {
        return instance;
    }

    /**
     * 
     * @param dataFile
     * @param fileName
     * @return
     */
    public Obj createObjFromFile(String dataFile, String fileName) {
        Log.i(TAG, "Wrapping the following file: " + fileName + ".obj");
        /*----------------------------------------------------------------------*/
        String[] lines = dataFile.split("\n");

        Obj obj = wrap(lines, fileName);
        /*----------------------------------------------------------------------*/
        Log.i(TAG, "Wrapping completed. File: " + fileName + ".obj");
        
        return obj;
    }

    /**
     * 
     * @param lines
     * @param name
     * @return
     */
    private Obj wrap(String[] lines, String name) {
        String[] lineParts;
        String[] indicesParts;

        ArrayList<Float> positions = new ArrayList<Float>();
        ArrayList<Float> textures = new ArrayList<Float>();
        ArrayList<Float> normals = new ArrayList<Float>();
        ArrayList<short[]> indices = new ArrayList<short[]>();
        
        for (int index = 0; index < lines.length; index++) {

            if (lines[index].startsWith("v ")) {
            	lines[index] = lines[index].substring(2);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_V_SIZE; i++) {
                    positions.add(Float.parseFloat(lineParts[i]));
                }

            } else if (lines[index].startsWith("vt ")) {
            	lines[index] = lines[index].substring(3);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_VT_SIZE; i++) {
                	textures.add(Float.parseFloat(lineParts[i]));
                }

            } else if (lines[index].startsWith("vn ")) {
            	lines[index] = lines[index].substring(3);
                lineParts = lines[index].split(" ");

                for (int i = 0; i < OpenGLConstants.SINGLE_VN_SIZE; i++) {
                	normals.add(Float.parseFloat(lineParts[i]));
                }
                
            } else if (lines[index].startsWith("f ")) {
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
        Obj obj  = new Obj(positionsTmp, texturesTmp, normalsTmp, unifiedData, name);

        return obj;
    }

}