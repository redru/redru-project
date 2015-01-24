package com.redru.engine.input;

import android.view.ScaleGestureDetector;

/**
 * Created by l.zenobi on 30/12/2014.
 */
class UserGestureHandler extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    public static final String TAG = "UserGestureHandler";

    private float scale = 0.0f;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        double scaleVal = detector.getScaleFactor();

        if (scale != scaleVal) {
            UserInputHandler.getInstance().scaling = true;
            if (scaleVal < 1) {
                scale = 2.0f;
            } else {
                scale = -2.0f;
            }
        }

        //Log.i(TAG, "scale: " + scale);
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        scale = 0.0f;
        UserInputHandler.getInstance().scaling = false;
    }

    public float getScale() {
        return scale;
    }
}
