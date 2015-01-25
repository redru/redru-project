package com.redru.engine.input;

import com.redru.Redru;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by l.zenobi on 29/12/2014.
 */
public class UserInputHandler {

    private static final String TAG = "UserInputHandler";

    private static UserInputHandler instance = null;
    private boolean rotationHandled = true;
    private boolean scaleHandled = true;

    private UserGestureHandler ugh = new UserGestureHandler();
    private ScaleGestureDetector sgd = new ScaleGestureDetector(Redru.getContext(), ugh);
    private float scale = 0.0f;
    public boolean scaling = false;

    private Point p1 = new Point(0, 0);
    private Point p2 = new Point(0, 0);
    private Point rotation = new Point(0, 0);

    /**
     * 
     */
    private UserInputHandler() {
        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     * @return
     */
    public static UserInputHandler getInstance() {
        if (instance == null) {
            instance = new UserInputHandler();
        }

        return instance;
    }

    /**
     * 
     * @param event
     * @return
     */
    public boolean handleMotionEvent(MotionEvent event) {
        sgd.onTouchEvent(event);

        if (ugh.getScale() != 0.0f) {
            scale = ugh.getScale();
            scaleHandled = false;
        } else if (!scaling) {
            int action = event.getAction();
            switch (action) {

                case MotionEvent.ACTION_MOVE:
                    p2.set((int) event.getX(), (int) event.getY());

                    rotation.set(p2.x - p1.x, p2.y - p1.y);

                    //Log.i(TAG, "x: " + distance.x);
                    //Log.i(TAG, "y: " + distance.y);
                    rotationHandled = false;

                    p1.set(p2.x, p2.y); // p1 prende il valore di p2

                    break;

                case MotionEvent.ACTION_DOWN:
                    // finger touches the screen
                    p1.set((int) event.getX(), (int) event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                        /*p2.set( (int) event.getX(), (int) event.getY() );

                        distance.set(p2.x - p1.x, p2.y - p1.y);

                        Log.i("UserInputHandler", "x: " + distance.x);
                        Log.i("UserInputHandler", "y: " + distance.y);
                        handled = false;*/
                    break;

                default:
                    break;
            }

        }

        return true;
    }

    /**
     * 
     * @return
     */
    public boolean isRotationHandled() {
        return rotationHandled;
    }

    /**
     * 
     * @return
     */
    public boolean isScaleHandled() {
        return scaleHandled;
    }

    /**
     * 
     * @return
     */
    public Point getRotation() {
        rotationHandled = true;
        return rotation;
    }

    /**
     * 
     * @return
     */
    public float getScale() {
        scaleHandled = true;
        return scale;
    }
}
