package com.redru.engine.wrapper;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Luca on 22/01/2015.
 */
public class EvoObj implements Cloneable {
    private static final String TAG = "EvoObj";
    public static final float[][] EMPTY_FLOAT_ARRAY = { { 0.0f } };
    public static final short[] EMPTY_SHORT_ARRAY = { 0 };
    public static final String DEFAULT_NAME = "EVO_OBJ";

    private int vertexes = -1;
    private int textures = -1;
    private int normals = -1;
    private int indices = -1;

    private int singlePositionSize = -1;
    private int singleTextureSize = -1;
    private int singleNormalSize = -1;
    private int[] indicesStride;

    private float[][] positionData;
    private float[][] textureCoordinatesData;
    private float[][] normalData;

    private short[] positionIndexData;
    private short[] textureCoordinatesIndexData;
    private short[] normalIndexData;

    public EvoObj() {
        this(EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, DEFAULT_NAME);
    }

    public EvoObj(String name) {
        this(EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, name);
    }

    public EvoObj(float[][] positionData, String name) {
        this(positionData, EMPTY_FLOAT_ARRAY, EMPTY_FLOAT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, name);
    }

    public EvoObj(float[][] positionData, float[][] textureCoordinatesData, String name) {
        this(positionData, textureCoordinatesData, EMPTY_FLOAT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, name);
    }

    public EvoObj(float[][] positionData, float[][] textureCoordinatesData, float[][] normalData, String name) {
        this(positionData, textureCoordinatesData, normalData, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, name);
    }
    public EvoObj(float[][] positionData, float[][] textureCoordinatesData, float[][] normalData, short[] positionIndexData, String name) {
        this(positionData, textureCoordinatesData, normalData, positionIndexData, EMPTY_SHORT_ARRAY, EMPTY_SHORT_ARRAY, name);
    }
    public EvoObj(float[][] positionData, float[][] textureCoordinatesData, float[][] normalData, short[] positionIndexData, short[] textureCoordinatesIndexData, String name) {
        this(positionData, textureCoordinatesData, normalData, positionIndexData, textureCoordinatesIndexData, EMPTY_SHORT_ARRAY, name);
    }

    public EvoObj(float[][] positionData, float[][] textureCoordinatesData, float[][] normalData, short[] positionIndexData, short[] textureCoordinatesIndexData, short[] normalIndexData, String name) {
        if (name == null || name.equals("")) {
            name = DEFAULT_NAME;
        }

        Log.i(TAG, "Creating new EvoObj '" + name + "'.");

        // Init Arrays
        this.setPositionData(positionData);
        this.setTextureCoordinatesData(textureCoordinatesData);
        this.setNormalData(normalData);
        this.setPositionIndexData(positionIndexData);
        this.setTextureCoordinatesIndexData(textureCoordinatesIndexData);
        this.setNormalIndexData(normalIndexData);

        // Log Object Data
        logEvoObjInformation();
        Log.i(TAG, "EvoObj '" + name + "' was successfully created.");
    }

    public int getVertexes() {
        return vertexes;
    }

    public int getTextures() {
        return textures;
    }

    public int getNormals() {
        return normals;
    }

    public int getIndices() {
        return indices;
    }

    public int getSinglePositionSize() {
        return singlePositionSize;
    }

    public int getSingleTextureSize() {
        return singleTextureSize;
    }

    public int getSingleNormalSize() {
        return singleNormalSize;
    }

    public int[] getIndicesStride() {
        return indicesStride;
    }

    public float[] getPositionData() {
        float[] tmp = new float[positionData.length * singlePositionSize];

        for (int i = 0, x = 0; i < positionData.length; i++) {
            for (int e = 0; e < singlePositionSize; e++, x++) {
                tmp[x] = positionData[i][e];
            }
        }

        return tmp;
    }

    public void setPositionData(float[][] positionData) {
        this.vertexes = positionData.length;
        this.singlePositionSize = positionData[0].length;
        this.positionData = positionData;
    }

    public float[] getTextureCoordinatesData() {
        float[] tmp = new float[textureCoordinatesData.length * singleTextureSize];

        for (int i = 0, x = 0; i < textureCoordinatesData.length; i++) {
            for (int e = 0; e < singleTextureSize; e++, x++) {
                tmp[x] = textureCoordinatesData[i][e];
            }
        }

        return tmp;
    }

    public void setTextureCoordinatesData(float[][] textureCoordinatesData) {
        if (textureCoordinatesData.length > 0 && textureCoordinatesData[0] != null) {
            this.textures = textureCoordinatesData.length;
            this.singleTextureSize = textureCoordinatesData[0].length;
            this.textureCoordinatesData = textureCoordinatesData;
        }
    }

    public float[] getNormalData() {
        float[] tmp = new float[normalData.length * singleNormalSize];

        for (int i = 0, x = 0; i < normalData.length; i++) {
            for (int e = 0; e < singleNormalSize; e++, x++) {
                tmp[x] = normalData[i][e];
            }
        }

        return tmp;
    }

    public void setNormalData(float[][] normalData) {
        if (normalData.length > 0 && normalData[0] != null) {
            this.normals = normalData.length;
            this.singleNormalSize = normalData[0].length;
            this.normalData = normalData;
        }
    }

    public short[] getPositionIndexData() {
        return positionIndexData.clone();
    }

    public void setPositionIndexData(short[] positionIndexData) {
        defineIndexStride(positionIndexData);
        this.positionIndexData = positionIndexData;
    }

    public short[] getTextureCoordinatesIndexData() {
        return textureCoordinatesIndexData.clone();
    }

    public void setTextureCoordinatesIndexData(short[] textureCoordinatesIndexData) {
        this.textureCoordinatesIndexData = textureCoordinatesIndexData;
    }

    public short[] getNormalIndexData() {
        return normalIndexData.clone();
    }

    public void setNormalIndexData(short[] normalIndexData) {
        this.normalIndexData = normalIndexData;
    }

    //---------------------------------------------------------------------------------------------
    private void defineIndexStride(short[] data) {
        indices = 0;
        ArrayList<Integer> tmp = new ArrayList<Integer>();

        for (int i = 0, x = 0; i < data.length; i++, x++) {
            if (data[i] == Short.MAX_VALUE) {
                tmp.add(x);
                x = -1;
            } else {
                this.indices++;
            }
        }

        this.indicesStride = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            this.indicesStride[i] = tmp.get(i);
        }

    }

    /*
     * Writes to the log the object most important information
     */
    public void logEvoObjInformation() {
        StringBuilder info = new StringBuilder();
        info.append("EvoObj Info Data:");
        info.append("\n-----------------------------------");
        info.append("\nTOTAL VERTICES: " + vertexes);
        info.append("\nTOTAL TEXTURE COORDINATES: " + textures);
        info.append("\nTOTAL NORMALS: " + normals);
        info.append("\nTOTAL INDICES: " + indices);
        info.append("\n-----------------------------------");
        Log.i(TAG, info.toString());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
