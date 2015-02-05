package com.redru.application.scene.complex;

import android.util.Log;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.scene.IntDynamicElement;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.wrapper.objects.Obj;

public class StarshipObject implements IntSceneElement, IntDynamicElement {
	private static String TAG = "StarshipObject";
	
	private String identifier = "";
	
	private Obj obj;
	private IntDrawHandler drawHandler;
	
	private float startX = 0.0f, startY = 0.0f, startZ = 0.0f;
	private float xPos = 0.0f, yPos = 0.0f, zPos = 0.0f;
	private float xVel = 0.0f, yVel = 0.0f, zVel = 0.0f;
	private float xAcc = 0.0f, yAcc = 0.0f, zAcc = 0.0f;
	private float xRot = 0.0f, yRot = 0.0f, zRot = 0.0f;
	
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
	public void scale(float xScale, float yScale, float zScale) {
		obj.scale(xScale, yScale, zScale);
		drawHandler.updateBuffers();
	}

	@Override
	public void rotate(int angle, float xAxis, float yAxis, float zAxis) {
		obj.rotate(angle, xAxis, yAxis, zAxis);
	}
	
	@Override
	public void rotateAndTranslate(int angle, float xAxis, float yAxis, float zAxis, float xUpset, float yUpset, float zUpset) {
		this.xPos += xUpset;
    	this.yPos += yUpset;
    	this.zPos += zUpset;
		this.addRotation(angle * xAxis, angle * yAxis, angle * zAxis);
		
		obj.rotate(1000, this.xRot, this.yRot, this.zRot);
		obj.translate(this.startX + this.xPos, this.startY + this.yPos, this.startZ + this.zPos);
		drawHandler.updateBuffers();
	}
	
	@Override
	public void rotateAndMoveTo(int angle, float xAxis, float yAxis, float zAxis, float xUpset, float yUpset, float zUpset) {
		this.addRotation(angle * xAxis, angle * yAxis, angle * zAxis);
		obj.rotateAndMoveTo(this.xRot, this.yRot, this.zRot, xUpset, yUpset, zUpset);
		drawHandler.updateBuffers();
	}
	
	@Override
	public void translate(float xUpset, float yUpset, float zUpset) {
    	this.xPos += xUpset;
    	this.yPos += yUpset;
    	this.zPos += zUpset;
    	
		obj.translate(xUpset, yUpset, zUpset);
		drawHandler.updateBuffers();
	}

	@Override
	public void move() {
		if (moving) {
	    	
		}
		
		this.xPos += xVel;
    	this.yPos += yVel;
    	this.zPos += zVel;
    	
    	if (xVel != 0.0f && yVel != 0.0f && zVel != 0.0f) {
			obj.translate(xPos, yPos, zPos);
			drawHandler.updateBuffers();
    	}
	}
	
	@Override
	public void setStartingPositions(float startX, float startY, float startZ) {
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
	}
	
	@Override
	public void toStartingPositions() {
		obj.moveToPosition(this.startX, this.startY, this.startZ);
		drawHandler.updateBuffers();
		
		this.xPos = 0;
    	this.yPos = 0;
    	this.zPos = 0;
	}

	@Override
	public void setup() {
		
	}

	@Override
	public void draw() {
		drawHandler.draw();
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

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public float getzPos() {
		return zPos;
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
	
	public void addRotation(float xRot, float yRot, float zRot) {
		this.xRot += xRot;
		this.yRot += yRot;
		this.zRot += zRot;
		
		if (xRot > 360) {
			xRot -= 360;
		} else if (xRot < 0) {
			xRot += 360;
		}
		
		if (yRot > 360) {
			yRot -= 360;
		} else if (yRot < 0) {
			yRot += 360;
		}
		
		if (zRot > 360) {
			zRot -= 360;
		} else if (zRot < 0) {
			zRot += 360;
		}
		
	}
	
}
