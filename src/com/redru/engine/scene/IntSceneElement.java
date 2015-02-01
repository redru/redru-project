package com.redru.engine.scene;

/**
 * Created by Luca on 20/01/2015.
 */
public interface IntSceneElement {

	public void setup();

    public void draw();

    public void translate(float xUpset, float yUpset, float zUpset);

    public void rotate();

}
