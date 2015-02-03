package com.redru.engine.scene;

import java.util.ArrayList;

import android.util.Log;

import com.redru.application.scene.simple.GridLines;
import com.redru.application.scene.simple.OriginLines;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 20/01/2015.
 */
public class SceneContext {
    private static final String TAG = "SceneContext";

    private static SceneContext instance;
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
    public static SceneContext getInstance() {
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
    public void drawScene() {
        // Draw originLines only if requested
        if (enableOriginLines) {
            originLines.draw();
        }
        
        // Draw gridLines only if requested
        if (enableGridLines) {
        	gridLines.draw();
        }

        for (IntSceneElement element : elements) {
            element.draw();
        }
    }

    /**
     * 
     * @param value
     */
    public void setEnableOriginLines(boolean value) {
        enableOriginLines = value;
        Log.i(TAG, "enableOriginLines: " + value);
    }
    
    /**
     * 
     * @param value
     */
    public void setEnableGridLines(boolean value) {
        enableGridLines = value;
        Log.i(TAG, "enableGridLines: " + value);
    }

    /**
     * 
     * @param element
     */
    public void addElementToScene(IntSceneElement element) {
        this.elements.add(element);
    }

    /**
     * 
     * @param index
     */
    public void removeElementFromScene(int index) {
        this.elements.remove(index);
    }

    /**
     * 
     * @param element
     */
    public void removeElementFromScene(IntSceneElement element) {
        this.elements.remove(element);
    }

    /**
     * 
     * @return
     */
    public ArrayList<IntSceneElement> getElements() {
        return this.elements;
    }
    
    public void linesStartup() {
        originLines = new OriginLines();
        setEnableOriginLines(Boolean.parseBoolean(ResourceUtils.getApplicationProperty("origin_lines")));
        
        gridLines = new GridLines();
        setEnableGridLines(Boolean.parseBoolean(ResourceUtils.getApplicationProperty("grid_lines")));
    }

}
