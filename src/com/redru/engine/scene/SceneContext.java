package com.redru.engine.scene;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.scene.elements.simple.OriginLines;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 20/01/2015.
 */
public class SceneContext {
    private static final String TAG = "SceneContext";

    private static SceneContext instance;
    private ArrayList<SceneElement> elements;
    private SceneElement originLines;

    private boolean enableOriginLines;

    /**
     * 
     */
    private SceneContext() {
        elements = new ArrayList<SceneElement>();

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

        for (SceneElement element : elements) {
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

        for (SceneElement element : elements) {
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
     * @param element
     */
    public void addElementToScene(SceneElement element) {
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
    public void removeElementFromScene(SceneElement element) {
        this.elements.remove(element);
    }

    /**
     * 
     * @return
     */
    public ArrayList<SceneElement> getElements() {
        return this.elements;
    }
    
    public void originLinesStartup() {
        originLines = new OriginLines();
        setEnableOriginLines(Boolean.parseBoolean(ResourceUtils.getApplicationProperty("origin_lines")));
    }

}
