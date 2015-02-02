package com.redru.engine;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.redru.engine.actions.ActionsManager;
import com.redru.engine.actions.impl.SceneObjectsTranslateAction;
import com.redru.engine.actions.impl.SensorInputAction;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.scene.elements.complex.ComplexSceneObject;
import com.redru.engine.utils.TimeManager;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.ObjFactory;
import com.redru.engine.wrapper.TextureFactory;
import com.redru.engine.wrapper.objects.Obj;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLViewRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "GLViewRenderer";
    
    private Camera camera = Camera.getInstance();
    private SceneContext scene = SceneContext.getInstance();
    private ObjFactory objFactory = ObjFactory.getInstance();
    private TextureFactory texFactory = TextureFactory.getInstance();
    private ActionsManager actionsManager = ActionsManager.getInstance();
    
    private ArrayList<IntSceneElement> sceneObjects = new ArrayList<IntSceneElement>();

    /**
     * 
     */
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);

        // Initialize and setup the Camera
        camera.rotate(30.0f, 180.0f, 0.0f);
        camera.move(0.0f, 0.0f, -16.0f);

        // Load .obj elements
        this.elementsStartup();
        
        // Load game actions
        this.actionsStartup();

        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     */
    public void onDrawFrame(GL10 unused) {
        actionsManager.executeAction(SensorInputAction.class.getSimpleName(), sceneObjects);
        actionsManager.executeAction(SceneObjectsTranslateAction.class.getSimpleName(), sceneObjects);
        
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
     * Set application actions to be executed on every game loop
     */
    private void actionsStartup() {
    	//actionsManager.addAction(new UserInputAction());
    	actionsManager.addAction(SensorInputAction.getInstance());
    	actionsManager.addAction(SceneObjectsTranslateAction.getInstance());
    }

    /**
     * 
     */
    private void elementsStartup() {
    	scene.linesStartup();
    	
    	Obj b2spirit = objFactory.getStockedObject(objFactory.getObjFiles().get(0), "B-2 Spirit");
    	b2spirit.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	ComplexSceneObject objB2Spirit = new ComplexSceneObject(b2spirit);
    	scene.addElementToScene(objB2Spirit);
    	sceneObjects.add(objB2Spirit);
    	b2spirit.scale(0.5f, 0.5f, 0.5f);
    	b2spirit.translate(0.0f, 2.0f, -8.8f);
    	
    	Obj b2spirit2 = objFactory.getStockedObject(objFactory.getObjFiles().get(0), "B-2 Spirit2");
    	b2spirit2.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	ComplexSceneObject objB2Spirit2 = new ComplexSceneObject(b2spirit2);
    	scene.addElementToScene(objB2Spirit2);
    	sceneObjects.add(objB2Spirit2);
    	b2spirit2.translate(3.0f, 5.0f, 120.0f);
    	
    	Obj b2spirit3 = objFactory.getStockedObject(objFactory.getObjFiles().get(0), "B-2 Spirit3");
    	b2spirit3.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	ComplexSceneObject objB2Spirit3 = new ComplexSceneObject(b2spirit3);
    	scene.addElementToScene(objB2Spirit3);
    	sceneObjects.add(objB2Spirit3);
    	b2spirit3.translate(10.0f, 2.0f, 100.0f);
    	
    	Obj b2spirit4 = objFactory.getStockedObject(objFactory.getObjFiles().get(0), "B-2 Spirit4");
    	b2spirit4.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	ComplexSceneObject objB2Spirit4 = new ComplexSceneObject(b2spirit4);
    	scene.addElementToScene(objB2Spirit4);
    	sceneObjects.add(objB2Spirit4);
    	b2spirit4.translate(8.0f, 7.0f, 40.0f);
    	
    	Obj b2spirit5 = objFactory.getStockedObject(objFactory.getObjFiles().get(0), "B-2 Spirit5");
    	b2spirit5.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	ComplexSceneObject objB2Spirit5 = new ComplexSceneObject(b2spirit5);
    	scene.addElementToScene(objB2Spirit5);
    	sceneObjects.add(objB2Spirit5);
    	b2spirit5.translate(5.0f, -3.0f, 80.0f);
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
