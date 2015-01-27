package com.redru.engine;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.redru.engine.input.UserInputHandler;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.scene.elements.complex.DefaultSceneObject;
import com.redru.engine.utils.TimeManager;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.EvoObj;
import com.redru.engine.wrapper.ObjFactory;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLViewRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GLViewRenderer";

    private ObjFactory objFactory;
    private Camera camera;
    private SceneContext scene;
    private UserInputHandler handler = UserInputHandler.getInstance();

    private ArrayList<EvoObj> sceneObjects = new ArrayList<EvoObj>();

    /**
     * 
     */
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);

        // Initialize Object Factory
        objFactory = ObjFactory.getInstance();

        // Initialize and setup the Camera
        camera = Camera.getInstance();
        camera.move(0.0f, 0.0f, -20.0f);

        // Initialize Scene Context
        scene = SceneContext.getInstance();

        // Load .obj elements
        this.elementsStartup();

        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    public void onDrawFrame(GL10 unused) {
        handleUserInput();
        scene.getElements().get(0).translate(0.0f, 0.0f, 0.1f);
        drawShapes();

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    private void drawShapes() {
    	TimeManager.setStart();
    	
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        // Draw the scene
        scene.drawScene();
        TimeManager.setEnd();
        TimeManager.getDifferenceInMicroseconds();
        Log.i(TAG, "Time average: '" + TimeManager.getAverage() + "' microseconds");
    }

    /**
     * 
     */
    private void handleUserInput() {
        if (!handler.isRotationHandled()) {
            Point rotation = handler.getRotation();

            if (rotation.x > 0) {
                rotation.x = -2;
            } else if (rotation.x < 0) {
                rotation.x = 2;
            }

            if (rotation.y > 0) {
                rotation.y = -2;
            } else if (rotation.y < 0) {
                rotation.y = 2;
            }

            camera.rotate(-rotation.y, -rotation.x, 0.0f);
        }

        if (!handler.isScaleHandled()) {
            camera.move(0.0f, 0.0f, -handler.getScale());
        }
    }

    /**
     * 
     */
    private void elementsStartup() {
    	//EvoObj ak = objFactory.getStockedObject(objFactory.getObjFiles()[0]);
        EvoObj obj = objFactory.getStockedObject(objFactory.getObjFiles()[1]);
        EvoObj objII = objFactory.getStockedObject(objFactory.getObjFiles()[1]);
        //EvoObj farm = objFactory.getStockedObject(objFactory.getObjFiles()[2]);

        sceneObjects.add(obj);
        scene.addElementToScene(new DefaultSceneObject(obj, "Car"));
        
        sceneObjects.add(objII);
        scene.addElementToScene(new DefaultSceneObject(objII, "Car 2"));
        objII.translate(3.0f, 0.0f, 0.0f);
            
        /*sceneObjects.add(ak);
        scene.addElementToScene(new DefaultSceneObject(ak, "AK"));
        ak.translate(5, 0, 0);
            
        sceneObjects.add(farm);
        scene.addElementToScene(new DefaultSceneObject(farm, "Farm"));
        farm.translate(20, 0, 20);*/

    }

    /**
     * 
     */
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Reset object buffers after app minimize
        scene.raiseSceneElements();

        camera.setAspectRatio((float) width / (float) height);
        GLES30.glViewport(0, 0, width, height);
    }

}
