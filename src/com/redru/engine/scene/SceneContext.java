package com.redru.engine.scene;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.particles.ParticleSystem;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 20/01/2015.
 */
public class SceneContext {
    private static final String TAG = "SceneContext";
    private static SceneContext instance;
    
    private ParticleSystem particleSystem = ParticleSystem.getInstance();
    
    private ArrayList<IntSceneElement> elements;
    private IntSceneElement originLines;
    private IntSceneElement gridLines;

    private boolean enableOriginLines;
    private boolean enableGridLines;

    /**
     * 
     */
    private SceneContext() {
        elements = new ArrayList<IntSceneElement>();

        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     * @return
     */
    public synchronized static SceneContext getInstance() {
        if (instance == null) {
            instance = new SceneContext();
        }

        return instance;
    }

    /**
     * 
     */
    public void raiseSceneElements() {
        originLines.setup();
        gridLines.setup();

        for (IntSceneElement element : elements) {
            element.setup();
        }
    }

    /**
     * 
     */
    public synchronized void drawScene() {
        if (this.enableOriginLines) {
            this.originLines.draw();
        }
        
        if (this.enableGridLines) {
        	this.gridLines.draw();
        }

        for (IntSceneElement element : this.elements) {
            element.draw();
        }
        
        this.particleSystem.draw();
    }

    /**
     * 
     * @param value
     */
    public void setEnableOriginLines(boolean value) {
    	this.enableOriginLines = value;
        Log.i(TAG, "enableOriginLines: " + value);
    }
    
    /**
     * 
     * @param value
     */
    public void setEnableGridLines(boolean value) {
    	this.enableGridLines = value;
        Log.i(TAG, "enableGridLines: " + value);
    }

    /**
     * 
     * @param element
     */
    public synchronized void addElementToScene(IntSceneElement element) {
        this.elements.add(element);
    }

    /**
     * 
     * @param index
     */
    public synchronized IntSceneElement removeElementFromScene(int index) {
        return this.elements.remove(index);
    }

    /**
     * 
     * @param element
     */
    public synchronized boolean removeElementFromScene(IntSceneElement element) {
        return this.elements.remove(element);
    }

    /**
     * 
     * @return
     */
    public synchronized ArrayList<IntSceneElement> getElements() {
        return this.elements;
    }
    
    public void linesStartup() {
    	this.originLines = new OriginLines();
    	this.setEnableOriginLines(Boolean.parseBoolean(ResourceUtils.getApplicationProperty("origin_lines")));
        
    	this.gridLines = new GridLines();
    	this.setEnableGridLines(Boolean.parseBoolean(ResourceUtils.getApplicationProperty("grid_lines")));
    }

}
