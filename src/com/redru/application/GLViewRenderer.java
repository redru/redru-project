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
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
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

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);

        // Initialize and setup the Camera
        camera.rotate(30.0f, 180.0f, 0.0f);
        camera.move(0.0f, 0.0f, -16.0f);

        this.customObjectsStartup();
        this.elementsStartup();
        this.actionsStartup();

        Log.i(TAG, "Creation complete.");
    }

    @Override
    public void onDrawFrame(GL10 unused) {
    	actionsManager.executeActionsByActiveContexts();
        
        drawShapes();

        try {
            Thread.sleep(12);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Reset object buffers after app minimize
        //scene.raiseSceneElements();

        camera.setAspectRatio((float) width / (float) height);
        GLES30.glViewport(0, 0, width, height);
    }

//----------------------------------------------------------------------------------------------------------------------------------
    
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
     * Add to the object factory the custom objects
     */
    private void customObjectsStartup() {
//    	BulletObject simpleBullet = new BulletObject(CustomObjectsData.getInstance().simpleBulletData, BulletObject.BulletType.SIMPLE);
//    	objFactory.addObjectToStock("SimpleBullet", simpleBullet);
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
    	
    	Model b2spirit = objFactory.getStockedObject("obj_b2spirit");
    	b2spirit.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit = new Starship(b2spirit, new TexturedObjDrawHandler(b2spirit), "B-2 Spirit");
    	objB2Spirit.setStartPosition(0.0f, 2.0f, -8.8f);
    	b2spirit.scale(0.5f, 0.5f, 0.5f);
    	b2spirit.translate(0.0f, 2.0f, -8.8f);
    	
    	scene.addElementToScene(objB2Spirit);
    	sceneObjects.add(objB2Spirit);
    	
    	Model b2spirit2 = objFactory.getStockedObject("obj_b2spirit");
    	b2spirit2.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit2 = new Starship(b2spirit2, new TexturedObjDrawHandler(b2spirit2), "Enemy 1");
    	objB2Spirit2.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit2.translate(3.0f, 5.0f, 120.0f);
    	objB2Spirit2.setStartPosition(3.0f, 5.0f, 120.0f);
    	
    	scene.addElementToScene(objB2Spirit2);
    	sceneObjects.add(objB2Spirit2);
    	
    	Model b2spirit3 = objFactory.getStockedObject("obj_b2spirit");
    	b2spirit3.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit3 = new Starship(b2spirit3, new TexturedObjDrawHandler(b2spirit3), "Enemy 2");
    	objB2Spirit3.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit3.translate(10.0f, 2.0f, 100.0f);
    	objB2Spirit3.setStartPosition(10.0f, 2.0f, 100.0f);
    	
    	scene.addElementToScene(objB2Spirit3);
    	sceneObjects.add(objB2Spirit3);
    	
    	Model b2spirit4 = objFactory.getStockedObject("obj_b2spirit");
    	b2spirit4.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit4 = new Starship(b2spirit4, new TexturedObjDrawHandler(b2spirit4), "Enemy 3");
    	objB2Spirit4.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit4.translate(8.0f, 7.0f, 40.0f);
    	objB2Spirit4.setStartPosition(8.0f, 7.0f, 40.0f);
    	
    	scene.addElementToScene(objB2Spirit4);
    	sceneObjects.add(objB2Spirit4);
    	
    	Model b2spirit5 = objFactory.getStockedObject("obj_b2spirit");
    	b2spirit5.setTexture(texFactory.getStockedTexture("tex_b2spirit"));
    	Starship objB2Spirit5 = new Starship(b2spirit5, new TexturedObjDrawHandler(b2spirit5), "Enemy 4");
    	objB2Spirit5.rotate(0.0f, 180.0f, 0.0f);
    	objB2Spirit5.translate(5.0f, -3.0f, 80.0f);
    	objB2Spirit5.setStartPosition(5.0f, -3.0f, 80.0f);
    	
    	scene.addElementToScene(objB2Spirit5);
    	sceneObjects.add(objB2Spirit5);
    }

}
