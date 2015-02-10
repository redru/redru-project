package com.redru.application;

import com.redru.engine.scene.SceneContext;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLView extends GLSurfaceView {

	/**
	 * 
	 * @param context
	 */
    public GLView(Context context){
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(GLViewRenderer.getInstance());
        setPreserveEGLContextOnPause(true);
        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    
    @Override
    public void onPause() {
    	
    }

    @Override
    public void onResume() {
    	if (SceneContext.getInstance().getElements().size() != 0) {
    		SceneContext.getInstance().raiseSceneElements();
    	}
    }
}