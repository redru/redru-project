package com.redru.application.scene.complex;

import android.util.Log;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.wrapper.objects.Obj;

public class StarshipObject implements IntSceneElement {
	private static String TAG = "StarshipObject";
	
	private String identifier = "";
	
	private Obj obj;
	private IntDrawHandler drawHandler;
	
	private float startX = 0.0f, startY = 0.0f, startZ = 0.0f;
	private float xUpset = 0.0f, yUpset = 0.0f, zUpset = 0.0f;
	private float xVel = 0.0f, yVel = 0.0f, zVel = 0.0f;
	private float xAcc = 0.0f, yAcc = 0.0f, zAcc = 0.0f;
	
	private boolean moving = false;

	public StarshipObject() {
		this(null, "");
	}
	
	public StarshipObject(Obj obj, String identifier) {
		this.obj = obj;
		this.drawHandler = new TexturedObjDrawHandler(obj);
		
		if (identifier != null) {
			this.identifier = identifier;
		}
		
		Log.i(TAG, "Creation complete.");
	}

	@Override
	public void setup() {
		
	}

	@Override
	public void draw() {
		drawHandler.draw();
	}
	
	@Override
	public void translate(float xUpset, float yUpset, float zUpset) {
    	this.xUpset += xUpset;
    	this.yUpset += yUpset;
    	this.zUpset += zUpset;
    	
		obj.translate(xUpset, yUpset, zUpset);
		drawHandler.updateBuffers();
	}

	@Override
	public void scale(float xScale, float yScale, float zScale) {
		obj.scale(xScale, yScale, zScale);
		drawHandler.updateBuffers();
	}

	@Override
	public void rotate() {
		obj.rotate();
	}

	@Override
	public void move() {
		if (moving) {
	    	this.xUpset += xUpset;
	    	this.yUpset += yUpset;
	    	this.zUpset += zUpset;
	    	
			obj.translate(xUpset, yUpset, zUpset);
			drawHandler.updateBuffers();
		}
	}
	
	@Override
	public void toStartingPositions() {
		obj.moveToPosition(this.startX, this.startY, this.startZ);
		drawHandler.updateBuffers();
		
		this.xUpset = 0;
    	this.yUpset = 0;
    	this.zUpset = 0;
	}
	
	@Override
	public void setStartingPositions(float startX, float startY, float startZ) {
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Obj getObj() {
		return obj;
	}

	public void setObj(Obj obj) {
		this.obj = obj;
		this.resetDrawHandler();
	}

	public IntDrawHandler getDrawHandler() {
		return drawHandler;
	}

	public void resetDrawHandler() {
		this.drawHandler = new TexturedObjDrawHandler(this.obj);
	}

	public float getStartX() {
		return startX;
	}

	public float getStartY() {
		return startY;
	}

	public float getStartZ() {
		return startZ;
	}

	public void setVel(float xVel, float yVel, float zVel) {
		this.xVel = xVel;
		this.yVel = yVel;
		this.zVel = zVel;
	}
	
	public float getxVel() {
		return xVel;
	}

	public float getyVel() {
		return yVel;
	}

	public float getzVel() {
		return zVel;
	}

	public float getxUpset() {
		return xUpset;
	}

	public float getyUpset() {
		return yUpset;
	}

	public float getzUpset() {
		return zUpset;
	}
	
	public void setAcc(float xAcc, float yAcc, float zAcc) {
		this.xAcc = xAcc;
		this.yAcc = yAcc;
		this.zAcc = zAcc;
	}

	public float getxAcc() {
		return this.xAcc;
	}

	public float getyAcc() {
		return this.yAcc;
	}

	public float getzAcc() {
		return this.zAcc;
	}

	public boolean isMoving() {
		return this.moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
}
