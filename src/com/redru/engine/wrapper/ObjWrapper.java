package com.redru.engine.wrapper;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;

/**
 * Created by Luca on 22/01/2015.
 */
public class ObjWrapper {
    private static final String TAG = "ObjWrapper";

    private static ObjWrapper instance = new ObjWrapper();
    private float[][] positionData;
    private float[][] textureCoordinatesData;
    private float[][] normalData;

    private short[] positionIndexData;
    private short[] textureCoordinatesIndexData;
    private short[] normalIndexData;

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
    public EvoObj createObjFromFile(String dataFile, String fileName) {
        Log.i(TAG, "Wrapping the following file: " + fileName + ".obj");
        /*----------------------------------------------------------------------*/
        String[] lines = dataFile.split("\n");

        EvoObj obj = wrap(lines, fileName);
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
    private EvoObj wrap(String[] lines, String name) {
        String[] lineParts;
        String[] indexesParts;
        int indexCount = 0;

        ArrayList<float[]> positions = new ArrayList<float[]>();
        ArrayList<float[]> textures = new ArrayList<float[]>();
        ArrayList<float[]> normals = new ArrayList<float[]>();

        ArrayList<ArrayList<Short>> positionsIndex = new ArrayList<ArrayList<Short>>();
        ArrayList<ArrayList<Short>> texturesIndex = new ArrayList<ArrayList<Short>>();
        ArrayList<ArrayList<Short>> normalsIndex = new ArrayList<ArrayList<Short>>();

        for (int index = 0; index < lines.length; index++) {

            if (lines[index].startsWith("v ")) {
                lineParts = lines[index].split(" ");
                float[] tmp = new float[lineParts.length - 1];

                for (int i = 1; i < lineParts.length; i++) {
                    tmp[i - 1] = Float.parseFloat(lineParts[i]);
                }

                positions.add(tmp);

            } else if (lines[index].startsWith("vt")) {
                lineParts = lines[index].split(" ");
                float[] tmp = new float[lineParts.length - 1];

                for (int i = 1; i < lineParts.length; i++) {
                    tmp[i - 1] = Float.parseFloat(lineParts[i]);
                }

                textures.add(tmp);

            } else if (lines[index].startsWith("vn")) {
                lineParts = lines[index].split(" ");
                float[] tmp = new float[lineParts.length - 1];

                for (int i = 1; i < lineParts.length; i++) {
                    tmp[i - 1] = Float.parseFloat(lineParts[i]);
                }

                normals.add(tmp);

            } else if (lines[index].startsWith("f")) {
                ArrayList<Short> tmp0 = new ArrayList<Short>();
                ArrayList<Short> tmp1 = new ArrayList<Short>();
                ArrayList<Short> tmp2 = new ArrayList<Short>();

                lineParts = lines[index].split(" ");
                indexCount += lineParts.length - 1;

                for (int i = 1; i < lineParts.length; i++) {
                    indexesParts = lineParts[i].split("/");

                    if (indexesParts.length > 0) {
                        for (int e = 0; e < indexesParts.length; e++) {
                            if (e == 0) {
                                tmp0.add(Short.parseShort(indexesParts[0]));
                            } else if (e == 1) {
                                tmp1.add(Short.parseShort(indexesParts[1]));
                            } else if (e == 2) {
                                tmp2.add(Short.parseShort(indexesParts[2]));
                            }
                        }
                    } else {
                        tmp0.add(Short.parseShort(lineParts[i]));
                    }

                }

                positionsIndex.add(tmp0);
                texturesIndex.add(tmp1);
                normalsIndex.add(tmp2);

            }

        }

        positionData = new float[positions.size()][OpenGLConstants.SINGLE_V_SIZE];
        textureCoordinatesData = new float[textures.size()][OpenGLConstants.SINGLE_VT_SIZE];
        normalData = new float[normals.size()][OpenGLConstants.SINGLE_VN_SIZE];

        for (int i = 0; i < positions.size(); i++) {
            for (int e = 0; e < OpenGLConstants.SINGLE_V_SIZE; e++) {
                positionData[i][e] = positions.get(i)[e];
            }
        }

        for (int i = 0; i < textures.size(); i++) {
            for (int e = 0; e < OpenGLConstants.SINGLE_VT_SIZE; e++) {
                textureCoordinatesData[i][e] = textures.get(i)[e];
            }
        }

        for (int i = 0; i < normals.size(); i++) {
            for (int e = 0; e < OpenGLConstants.SINGLE_VN_SIZE; e++) {
                normalData[i][e] = normals.get(i)[e];
            }
        }

        positionIndexData = new short[indexCount + positionsIndex.size() - 1];
        textureCoordinatesIndexData = new short[indexCount + texturesIndex.size() - 1];
        normalIndexData = new short[indexCount + normalsIndex.size() - 1];

        for (int i = 0, x = 0; i < positionsIndex.size(); i++, x++) {
            for (int e = 0; e < positionsIndex.get(i).size(); e++, x++) {

                positionIndexData[x] = positionsIndex.get(i).get(e);
                // Obj indexes starts from '1' and not from 0. Then we have to adjust the indexes
                positionIndexData[x]--;
            }

            if (x < positionIndexData.length) {
                positionIndexData[x] = Short.MAX_VALUE;
            }
        }

        for (int i = 0, x = 0; x < texturesIndex.size(); i++, x++) {
            for (int e = 0; e < texturesIndex.get(i).size(); e++, x++) {
                textureCoordinatesIndexData[x] = texturesIndex.get(i).get(e);
                // Obj indexes starts from '1' and not from 0. Then we have to adjust the indexes
                textureCoordinatesIndexData[x]--;
            }

            if (texturesIndex.get(i).size() != 0) {
                textureCoordinatesIndexData[x] = Short.MAX_VALUE;
                // Obj indexes starts from '1' and not from 0. Then we have to adjust the indexes
                textureCoordinatesIndexData[x]--;
            }
        }

        for (int i = 0, x = 0; x < normalsIndex.size(); i++, x++) {
            for (int e = 0; e < normalsIndex.get(i).size(); e++, x++) {
                normalIndexData[x] = normalsIndex.get(i).get(e);
            }

            if (normalsIndex.get(i).size() != 0) {
                normalIndexData[x] = Short.MAX_VALUE;
            }
        }

        EvoObj obj  = new EvoObj(positionData, textureCoordinatesData, normalData, positionIndexData, textureCoordinatesIndexData, normalIndexData, name);

        return obj;
    }

}