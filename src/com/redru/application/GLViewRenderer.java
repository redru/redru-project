package com.redru.application;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.redru.application.actions.SceneObjectsTranslateAction;
import com.redru.application.actions.SensorInputAction;
import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.elements.GameActor;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.utils.TimeManager;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;
import com.redru.engine.wrapper.textures.TextureFactory;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLViewRenderer implements GLSurfaceView.Renderer {	
    private static final String TAG = "GLViewRenderer";    
    private static GLViewRenderer instance;
    
    private Camera camera = Camera.getInstance();
    private SceneContext scene = SceneContext.getInstance();
    private ModelFactory objFactory = ModelFactory.getInstance();
    private TextureFactory texFactory = TextureFactory.getInstance();
    private ActionsManager actionsManager = ActionsManager.getInstance();
    
    private ArrayList<GameActor> sceneObjects = new ArrayList<GameActor>();
    
    private GLViewRenderer() { }
    
    public static GLViewRenderer getInstance() {
    	if (instance == null) {
    		instance = new GLViewRenderer();
    	}
    	
    	return instance;
    }
// --------------------------------------------------------------------------------------------------------------------
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    	// GL CONTEXT SETUP **************************
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        //********************************************
        // CAMERA SETUP ******************************
        camera.rotate(30.0f, 180.0f, 0.0f);
        camera.move(0.0f, 0.0f, -16.0f);
        //********************************************
        // APPLICATION STARTUP ***********************
        this.elementsStartup();
        this.actionsStartup();
        //********************************************
        Log.i(TAG, "Creation complete.");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	TimeManager.setStart();
    	// TIME ***************************************
    	actionsManager.executeActionsByActiveContexts();
        
        drawShapes();
        // END TIME ***********************************
        TimeManager.setEnd();
        Log.i(TAG, "Time difference: '" + TimeManager.getDifferenceInMilliseconds() + "' microseconds");
        try {
        	if (TimeManager.getDifferenceInMilliseconds() <= Constants.TARGET_SLEEP_TIME) {
        		Thread.sleep(Constants.TARGET_SLEEP_TIME - TimeManager.getDifferenceInMilliseconds());
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        camera.setAspectRatio((float) width / (float) height);
        GLES30.glViewport(0, 0, width, height);
    }

//----------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * 
     */
    private void drawShapes() {
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        scene.drawScene();
    }
    
    /**
     * Set application actions to be executed on every game loop
     */
    private void actionsStartup() {
    	actionsManager.addContext(new ActionContext<GameActor>("SceneElements", sceneObjects, true));
    	actionsManager.addAction(SensorInputAction.getInstance(), "SceneElements");
    	actionsManager.addAction(SceneObjectsTranslateAction.getInstance(), "SceneElements");
    }

    /**
     * 
     */
    private void elementsStartup() {
    	scene.linesStartup();
    	
    	Model b2spirit = objFactory.getStockedModel("obj_b2spirit");
    	b2spirit.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit = new Starship(b2spirit, "B-2 Spirit");
    	objB2Spirit.scale(0.35f, 0.35f, 0.35f);
    	objB2Spirit.translate(0.0f, 2.0f, -9.2f);
    	objB2Spirit.getDrawHandler().updateBuffers();
    	
    	scene.addElementToScene(objB2Spirit);
    	sceneObjects.add(objB2Spirit);
    	
    	Model b2spirit2 = objFactory.getStockedModel("obj_b2spirit");
    	b2spirit2.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit2 = new Starship(b2spirit2, "Enemy 2");
    	objB2Spirit2.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit2.scale(0.5f, 0.5f, 0.5f);
    	objB2Spirit2.translate(-3.0f, -2.0f, 120.0f);
    	objB2Spirit2.setStaticPosition(-3.0f, -2.0f, 120.0f);
    	objB2Spirit2.getDrawHandler().updateBuffers();
    	
    	scene.addElementToScene(objB2Spirit2);
    	sceneObjects.add(objB2Spirit2);
    	
    	Model b2spirit3 = objFactory.getStockedModel("obj_b2spirit");
    	b2spirit3.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit3 = new Starship(b2spirit3, "Enemy 3");
    	objB2Spirit3.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit3.scale(0.5f, 0.5f, 0.5f);
    	objB2Spirit3.translate(8.0f, 7.0f, 185.0f);
    	objB2Spirit3.setStaticPosition(8.0f, 7.0f, 185.0f);
    	objB2Spirit3.getDrawHandler().updateBuffers();
    	
    	scene.addElementToScene(objB2Spirit3);
    	sceneObjects.add(objB2Spirit3);
    }
// --------------------------------------------------------------------------------------------------------------------
}
