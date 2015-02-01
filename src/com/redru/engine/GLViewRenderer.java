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
import com.redru.engine.scene.SceneElement;
import com.redru.engine.scene.elements.complex.DefaultSceneObject;
import com.redru.engine.utils.TimeManager;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.EvoObj;
import com.redru.engine.wrapper.ObjFactory;
import com.redru.engine.wrapper.TextureFactory;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLViewRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "GLViewRenderer";

    private ObjFactory objFactory;
    private TextureFactory texFactory;
    private Camera camera;
    private SceneContext scene;
    private UserInputHandler handler = UserInputHandler.getInstance();

    private ArrayList<SceneElement> sceneObjects = new ArrayList<SceneElement>();

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
        texFactory = TextureFactory.getInstance();

        // Initialize and setup the Camera
        camera = Camera.getInstance();
        camera.move(0.0f, 0.0f, -16.0f);
        camera.rotate(30.0f, 180.0f, 0.0f);

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
        drawShapes();

        try {
            Thread.sleep(12);
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
        //Log.i(TAG, "Time average: '" + TimeManager.getAverage() + "' microseconds");
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

            if (rotation.x > 0) {
            	camera.move(0.8f, 0.0f, 0.0f);
            	sceneObjects.get(0).translate(0.8f, 0.0f, 0.0f);
            } else {
            	camera.move(-0.8f, 0.0f, 0.0f);
            	sceneObjects.get(0).translate(-0.8f, 0.0f, 0.0f);
            }
        }
    }

    /**
     * 
     */
    private void elementsStartup() {
    	EvoObj b2spirit = objFactory.getStockedObject(objFactory.getObjFiles().get(0));
    	b2spirit.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	DefaultSceneObject objB2Spirit = new DefaultSceneObject(b2spirit, "B-2 Spirit");
    	scene.addElementToScene(objB2Spirit);
    	sceneObjects.add(objB2Spirit);
    	b2spirit.translate(0.0f, 0.0f, -6.0f);
    	
    	EvoObj b2spirit2 = objFactory.getStockedObject(objFactory.getObjFiles().get(0));
    	b2spirit2.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	DefaultSceneObject objB2Spirit2 = new DefaultSceneObject(b2spirit2, "B-2 Spirit2");
    	scene.addElementToScene(objB2Spirit2);
    	sceneObjects.add(objB2Spirit2);
    	b2spirit2.translate(0.0f, 2.0f, 16.0f);

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
