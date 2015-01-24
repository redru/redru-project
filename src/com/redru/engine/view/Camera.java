package com.redru.engine.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.Matrix;
import android.util.Log;

import com.redru.engine.utils.OpenGLConstants;

/**
 * Created by Luca on 20/01/2015.
 */
public class Camera {

    private static final String TAG = "Camera";

    private static Camera instance;

    private float[] modelViewMatrix, perspectiveMatrix, mvpMatrix;

    private float fov = 60.0f, nearZ = 1.0f, farZ = 500.0f;
    private float aspectRatio = 0.0f;

    private float xCam = 0.0f, yCam = 0.0f, zCam = 0.0f;
    private int xRotation = 0, yRotation = 0, zRotation = 0;



    private Camera() {
        this.modelViewMatrix = new float[16];
        this.perspectiveMatrix = new float[16];
        this.mvpMatrix = new float[16];

        updateCamera();
        Log.i(TAG, "Creation complete.");
    }

    /*
     * Updates the camera, doing again the calculations on the mvpMatrix
     */
    private void updateCamera() {
        Matrix.setIdentityM(this.modelViewMatrix, 0);
        Matrix.setIdentityM(this.perspectiveMatrix, 0);
        Matrix.setIdentityM(this.mvpMatrix, 0);

        // Model calculations
        Matrix.translateM(this.modelViewMatrix, 0, this.xCam, this.yCam, this.zCam);
        // View calculations
        Matrix.rotateM(this.modelViewMatrix, 0, this.xRotation, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(this.modelViewMatrix, 0, this.yRotation, 0.0f, 1.0f, 0.0f);
        // Perspective calculation
        Matrix.perspectiveM(this.perspectiveMatrix, 0, this.fov, this.aspectRatio, this.nearZ, this.farZ);

        // MVP Matrix generation
        Matrix.multiplyMM(this.mvpMatrix, 0, this.perspectiveMatrix, 0, this.modelViewMatrix, 0);

        //printMVPMatrix();
        //printCameraData();
    }

    /*
     * Move the camera by the same upset given by the arguments
     */
    public void move(float upsetX, float upsetY, float upsetZ) {
        this.xCam += upsetX;
        this.yCam += upsetY;
        this.zCam += upsetZ;

        this.updateCamera();
    }

    /*
     * Rotate the camera by the same degrees given by the arguments
     */
    public void rotate(float xRotationDegrees, float yRotationDegrees, float zRotationDegrees) {
        this.xRotation += xRotationDegrees;
        this.yRotation += yRotationDegrees;
        this.zRotation += zRotationDegrees;

        this.updateCamera();
    }

    /*
     * Set the aspect ratio used for the perspective matrix
     */
    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;

        this.updateCamera();
    }

    /*
     * Get the MVP Matrix
     * return float[]
     */
    public float[] getMvpMatrix() {
        return this.mvpMatrix;
    }

    public FloatBuffer getMvpMatrixAsFloatBuffer() {
        /*this.mvpMatrix[0] = 2.3048906f;
        this.mvpMatrix[1] = 0.0f;
        this.mvpMatrix[2] = 0.0f;
        this.mvpMatrix[3] = 0.0f;
        this.mvpMatrix[4] = 0.0f;
        this.mvpMatrix[5] = 1.7320509f;
        this.mvpMatrix[6] = 0.0f;
        this.mvpMatrix[7] = 0.0f;
        this.mvpMatrix[8] = 0.0f;
        this.mvpMatrix[9] = 0.0f;
        this.mvpMatrix[10] = -1.004008f;
        this.mvpMatrix[11] = -1.0f;
        this.mvpMatrix[12] = 0.0f;
        this.mvpMatrix[13] = 0.0f;
        this.mvpMatrix[14] = 78.31664f;
        this.mvpMatrix[15] = 80.0f;*/
        FloatBuffer buffer = ByteBuffer.allocateDirect(this.mvpMatrix.length * OpenGLConstants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(this.mvpMatrix).position(0);

        return buffer;
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }

        return instance;
    }

    public void printMVPMatrix() {
        Log.i(TAG,
                "MVP MATRIX" +
                "\n[0] " + this.mvpMatrix[0] + "\n[1] " + this.mvpMatrix[1] + "\n[2] " + this.mvpMatrix[2] + "\n[3] " + this.mvpMatrix[3] +
                "\n[4] " + this.mvpMatrix[4] + "\n[5] " + this.mvpMatrix[5] + "\n[6] " + this.mvpMatrix[6] + "\n[7] " + this.mvpMatrix[7] +
                "\n[8] " + this.mvpMatrix[8] + "\n[9] " + this.mvpMatrix[9] + "\n[10] " + this.mvpMatrix[10] + "\n[11] " + this.mvpMatrix[11] +
                "\n[12] " + this.mvpMatrix[12] + "\n[13] " + this.mvpMatrix[13] + "\n[14] " + this.mvpMatrix[14] + "\n[15] " + this.mvpMatrix[15] +
                "\n--------------------"
        );
    }

    public void printCameraData() {
        Log.i(TAG, "Camera DATA:" +
                   "\nxCam: " + xCam + "   yCam: " + yCam + "   zCam: " + zCam +
                   "\nxRotation: " + xRotation + " yRotation: " + yRotation + " zRotation: " + zRotation);
    }

}