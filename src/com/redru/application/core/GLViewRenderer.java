package com.redru.application.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.redru.application.Constants;
import com.redru.application.actions.CollisionCheckAction;
import com.redru.application.actions.SceneObjectsTranslateAction;
import com.redru.application.actions.SensorInputAction;
import com.redru.application.actors.complex.Starship;
import com.redru.application.models.BulletModel;
import com.redru.application.models.CustomModelsData;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.context.EngineContext;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.elements.TransformableGameActor;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.time.TimeUtils;
import com.redru.engine.wrapper.models.Model;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLViewRenderer implements GLSurfaceView.Renderer {	
    private static final String TAG = "GLViewRenderer";    
    private static GLViewRenderer instance;
    
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
        // ENGINE CONTEXT STARTUP ********************
        EngineContext.contextStartup();
        // CAMERA SETUP ******************************
        EngineContext.camera.rotate(30.0f, 180.0f, 0.0f);
        EngineContext.camera.move(0.0f, 0.0f, -16.0f);
        //********************************************
        // APPLICATION STARTUP ***********************
        this.customModelsStartup();
        this.elementsStartup();
        this.actionsStartup();
        this.timeObjectsStartup();
        //********************************************
        Log.i(TAG, "Creation complete.");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	TimeUtils.setStart();
    	// TIME ***************************************
    	EngineContext.timeManager.updateTimeObjects();
    	EngineContext.actionsManager.executeActionsByActiveContexts();
    	EngineContext.actionsManager.createAllInQueue();
    	EngineContext.actionsManager.destroyAllInQueue();
        this.drawShapes();
        // END TIME ***********************************
        TimeUtils.setEnd();
//        Log.i(TAG, "Time difference: '" + TimeUtils.getDifferenceInMilliseconds() + "' ms");
        try {
        	if (TimeUtils.getDifferenceInMilliseconds() <= Constants.TARGET_SLEEP_TIME) {
        		Thread.sleep(Constants.TARGET_SLEEP_TIME - TimeUtils.getDifferenceInMilliseconds());
        	}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
    	EngineContext.camera.setAspectRatio((float) width / (float) height);
        GLES30.glViewport(0, 0, width, height);
    }

//----------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Draw the scene context
     */
    private void drawShapes() {
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        EngineContext.scene.drawScene();
    }
    
    /**
     * Set application actions to be executed on every game loop
     */
    private void actionsStartup() {
    	EngineContext.actionsManager.addContext(new ActionContext<IntSceneElement>("SceneElements", EngineContext.scene.getElements(), true));
    	EngineContext.actionsManager.addAction(SensorInputAction.getInstance(), "SceneElements");
    	EngineContext.actionsManager.addAction(SceneObjectsTranslateAction.getInstance(), "SceneElements");
    	EngineContext.actionsManager.addAction(new CollisionCheckAction("CollisionCheckAction", false), "SceneElements");
    }
    
    /**
     * Startup of the starting time objects
     */
    private void timeObjectsStartup() {
    	// A TimeObject is created to spawn enemies every 1000 milliseconds, and set it as active and !executableOnce
    	EnemySpawner spawner = new EnemySpawner("enemy_spawner", 1000L, 0L, true, false);
    	EngineContext.timeManager.addTimeObjectToList(spawner);
    }
    
    /**
     * Startup and load in the model factory custom models
     */
    private void customModelsStartup() {
    	BulletModel bullet = new BulletModel(CustomModelsData.getInstance().simpleBulletData, CustomModelsData.getInstance().simpleBulletMinMax);
    	EngineContext.modelFactory.addModelToStock("cust_simple_bullet", bullet);
    }

    /**
     * Startup of the initial objects
     */
    private void elementsStartup() {
    	EngineContext.scene.linesStartup();
    	
    	Model model = EngineContext.modelFactory.getStockedModel("obj_b2spirit");
    	model.setTexture(EngineContext.texFactory.getStockedTexture("tex_b2spirit"));
    	TransformableGameActor actor = new Starship(model, new TexturedObjDrawHandler(), "B-2 Spirit", 100000, 0);
    	actor.setup();
    	actor.scale(0.35f, 0.35f, 0.35f);
    	actor.translate(0.0f, 2.0f, -9.2f);
    	actor.updateTransformBuffers();
    	EngineContext.scene.addElementToScene(actor);
    }
// --------------------------------------------------------------------------------------------------------------------
}
