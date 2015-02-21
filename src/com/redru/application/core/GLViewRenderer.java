package com.redru.application.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.redru.application.Constants;
import com.redru.application.actions.SceneObjectsTranslateAction;
import com.redru.application.actions.SensorInputAction;
import com.redru.application.actors.complex.Starship;
import com.redru.application.actors.simple.Bullet;
import com.redru.application.actors.simple.Bullet.BulletType;
import com.redru.application.actors.simple.CustomObjectsData;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.elements.GameActor;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.time.TimeManager;
import com.redru.engine.time.TimeUtils;
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
    private ModelFactory modelFactory = ModelFactory.getInstance();
    private TextureFactory texFactory = TextureFactory.getInstance();
    private ActionsManager actionsManager = ActionsManager.getInstance();
    private TimeManager timeManager = TimeManager.getInstance();
    
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
        this.camera.rotate(30.0f, 180.0f, 0.0f);
        this.camera.move(0.0f, 0.0f, -16.0f);
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
    	this.timeManager.updateTimeObjects();
    	this.actionsManager.executeActionsByActiveContexts();
    	this.actionsManager.createAllInQueue();
    	this.actionsManager.destroyAllInQueue();
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
    	this.camera.setAspectRatio((float) width / (float) height);
        GLES30.glViewport(0, 0, width, height);
    }

//----------------------------------------------------------------------------------------------------------------------------------
    
    /**
     * Draw the scene context
     */
    private void drawShapes() {
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        this.scene.drawScene();
    }
    
    /**
     * Set application actions to be executed on every game loop
     */
    private void actionsStartup() {
    	this.actionsManager.addContext(new ActionContext<IntSceneElement>("SceneElements", this.scene.getElements(), true));
    	this.actionsManager.addAction(SensorInputAction.getInstance(), "SceneElements");
    	this.actionsManager.addAction(SceneObjectsTranslateAction.getInstance(), "SceneElements");
    }
    
    /**
     * Startup of the starting time objects
     */
    private void timeObjectsStartup() {
    	// A TimeObject is created to spawn enemies every 800 milliseconds, and set it as active
    	EnemySpawner spawner = new EnemySpawner("enemy_spawner", 800L, 0L, true);
    	this.timeManager.addTimeObjectToList(spawner);
    }
    
    /**
     * Startup and load in the model factory custom models
     */
    private void customModelsStartup() {
    	Bullet bullet = new Bullet(CustomObjectsData.getInstance().simpleBulletData, BulletType.SIMPLE);
    	this.modelFactory.addModelToStock("cust_simple_bullet", bullet);
    }

    /**
     * Startup of the initial objects
     */
    private void elementsStartup() {
    	scene.linesStartup();
    	
    	Model model = modelFactory.getStockedModel("obj_b2spirit");
    	model.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	GameActor actor = new Starship(model, "B-2 Spirit");
    	actor.setDrawHandler(new TexturedObjDrawHandler(actor));
    	actor.setup();
    	actor.scale(0.35f, 0.35f, 0.35f);
    	actor.translate(0.0f, 2.0f, -9.2f);
    	actor.getDrawHandler().updateTransformBuffers();
    	this.scene.addElementToScene(actor);
    }
// --------------------------------------------------------------------------------------------------------------------
}
