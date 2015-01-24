package com.redru.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Luca on 16/01/2015.
 */
public class GLView extends GLSurfaceView {

    public GLView(Context context){
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new GLViewRenderer());

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}