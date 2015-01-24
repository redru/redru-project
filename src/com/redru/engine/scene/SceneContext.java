package com.redru.engine.scene;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.scene.elements.complex.OriginLines;

/**
 * Created by Luca on 20/01/2015.
 */
public class SceneContext {

    private static final String TAG = "SceneContext";

    private static SceneContext instance;

    private ArrayList<SceneElement> elements;
    private SceneElement originLines;

    private boolean enableOriginLines = true;

    private SceneContext() {
        elements = new ArrayList<SceneElement>();
        originLines = new OriginLines();
        setEnableOriginLines(true);

        Log.i(TAG, "Creation complete.");
    }

    public static SceneContext getInstance() {
        if (instance == null) {
            instance = new SceneContext();
        }

        return instance;
    }

    public void raiseSceneElements() {
        originLines.setup();

        for (SceneElement element : elements) {
            element.setup();
        }
    }

    public void drawScene() {
        // Draw originLines only if requested
        if (enableOriginLines) {
            originLines.draw();
        }

        for (SceneElement element : elements) {
            element.draw();
        }
    }

    public void setEnableOriginLines(boolean value) {
        enableOriginLines = value;
        Log.i(TAG, "enableOriginLines: " + value);
    }

    public void addElementToScene(SceneElement element) {
        this.elements.add(element);
    }

    public void removeElementFromScene(int index) {
        this.elements.remove(index);
    }

    public void removeElementFromScene(SceneElement element) {
        this.elements.remove(element);
    }

    public ArrayList<SceneElement> getElements() {
        return this.elements;
    }

}
