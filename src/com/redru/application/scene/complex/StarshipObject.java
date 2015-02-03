package com.redru.application.scene.complex;

import android.util.Log;

import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.wrapper.objects.Obj;

public class StarshipObject implements IntSceneElement {
	private static String TAG = "ComplexSceneObject";
	
	private Obj obj;
	private EvoObjDrawHandler drawHandler;
	
	private float xUpset = 0.0f, yUpset = 0.0f, zUpset = 0.0f;
	private float xVel = 0.0f, yVel = 0.0f, zVel = 0.0f;
	private float xAcc = 0.0f, yAcc = 0.0f, zAcc = 0.0f;
	
	private boolean moving = false;

	/**
	 * 
	 */
	public StarshipObject() {
		this(null);
	}
	
	/**
	 * 
	 * @param obj
	 * @param name
	 */
	public StarshipObject(Obj obj) {
		this.obj = obj;
		this.drawHandler = new EvoObjDrawHandler(obj);
		Log.i(TAG, "Creation complete.");
	}

	/**
	 * 
	 */
	@Override
	public void setup() {
		drawHandler.setup();
	}

	/**
	 * 
	 */
	@Override
	public void draw() {
		drawHandler.draw();
	}

	/**
	 * 
	 */
	@Override
	public void translate(float xUpset, float yUpset, float zUpset) {
//		Log.i(TAG, "Starting " + this.obj.getName() + " translation.");
    	this.xUpset += xUpset;
    	this.yUpset += yUpset;
    	this.zUpset += zUpset;
//    	Log.i(TAG, "New positions for " + this.obj.getName() + ":" +
//    			   "\nX: " + this.xUpset +
//    			   "\nY: " + this.yUpset +
//    			   "\nZ: " + this.zUpset);
    	
		obj.translate(xUpset, yUpset, zUpset);
		drawHandler.updateBuffers();
	}

	/**
	 * 
	 */
	@Override
	public void scale(float xScale, float yScale, float zScale) {
		obj.scale(xScale, yScale, zScale);
		drawHandler.updateBuffers();
	}

	/**
	 * 
	 */
	@Override
	public void rotate() {
		obj.rotate();
	}

	@Override
	public void move() {
		if (moving) {
//			Log.i(TAG, "Starting " + this.obj.getName() + " movement.");
	    	this.xUpset += xUpset;
	    	this.yUpset += yUpset;
	    	this.zUpset += zUpset;
//	    	Log.i(TAG, "New positions for " + this.obj.getName() + ":" +
//	    			   "\nX: " + this.xUpset +
//	    			   "\nY: " + this.yUpset +
//	    			   "\nZ: " + this.zUpset);
	    	
			obj.translate(xUpset, yUpset, zUpset);
			drawHandler.updateBuffers();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Obj getObj() {
		return obj;
	}

	/**
	 * 
	 * @param obj
	 */
	public void setObj(Obj obj) {
		this.obj = obj;
	}

	/**
	 * 
	 * @return
	 */
	public EvoObjDrawHandler getDrawHandler() {
		return drawHandler;
	}

	/**
	 * 
	 */
	public void resetDrawHandler() {
		this.drawHandler = new EvoObjDrawHandler(this.obj);
	}

	/**
	 * 
	 * @param xVel
	 * @param yVel
	 * @param zVel
	 */
	public void setVel(float xVel, float yVel, float zVel) {
		this.xVel = xVel;
		this.yVel = yVel;
		this.zVel = zVel;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getxVel() {
		return xVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getyVel() {
		return yVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getzVel() {
		return zVel;
	}

	/**
	 * 
	 * @return
	 */
	public float getxUpset() {
		return xUpset;
	}

	/**
	 * 
	 * @return
	 */
	public float getyUpset() {
		return yUpset;
	}

	/**
	 * 
	 * @return
	 */
	public float getzUpset() {
		return zUpset;
	}
	
	/**
	 * 
	 * @param xAcc
	 * @param yAcc
	 * @param zAcc
	 */
	public void setAcc(float xAcc, float yAcc, float zAcc) {
		this.xAcc = xAcc;
		this.yAcc = yAcc;
		this.zAcc = zAcc;
	}

	/**
	 * 
	 * @return
	 */
	public float getxAcc() {
		return this.xAcc;
	}

	/**
	 * 
	 * @return
	 */
	public float getyAcc() {
		return this.yAcc;
	}

	/**
	 * 
	 * @return
	 */
	public float getzAcc() {
		return this.zAcc;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isMoving() {
		return this.moving;
	}

	/**
	 * 
	 * @param moving
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
}
