package com.redru.engine.elements;

import android.opengl.Matrix;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.wrapper.models.Model;

public abstract class GameActor implements IntTransformable, IntSceneElement {
	
	private String identifier;
	private Model model;
	private IntDrawHandler drawHandler;
	
	private float[] rotationMatrix = new float[16];
	
	private float xStart = 0.0f, yStart = 0.0f, zStart = 0.0f;
	private float xPos = 0.0f, yPos = 0.0f, zPos = 0.0f;
	private float xVel = 0.0f, yVel = 0.0f, zVel = 0.0f;
	private float xAcc = 0.0f, yAcc = 0.0f, zAcc = 0.0f;
	private float xRot = 0.0f, yRot = 0.0f, zRot = 0.0f;
	private float xSca = 0.0f, ySca = 0.0f, zSca = 0.0f;
// CONTRUCTORS ---------------------------------------------------------------------------------
	public GameActor() {
		this(null, null, "");
	}
	
	public GameActor(Model model, String identifier) {
		this(model, null, identifier);
	}
	
	public GameActor(Model model, IntDrawHandler drawHandler, String identifier) {
		this.model = model;
		this.drawHandler = new TexturedObjDrawHandler(this);
		this.identifier = identifier;
	}
// INTERFACES METHODS --------------------------------------------------------------------------
	@Override
	public void setup() {
		this.drawHandler.setup();
	}

	@Override
	public final void draw() {
		this.drawHandler.draw();
	}

	@Override
	public final void scale(float xScale, float yScale, float zScale) {
		this.xSca += xScale;
		this.ySca += yScale;
		this.zSca += zScale;
	}

	@Override
	public final void rotate(float xAxis, float yAxis, float zAxis) {
		this.xRot += xAxis;
		this.yRot += yAxis;
		this.zRot += zAxis;
	}

	@Override
	public final void translate(float xUpset, float yUpset, float zUpset) {
		this.xPos += xUpset;
		this.yPos += yUpset;
		this.zPos += zUpset;
	}
	
	@Override
	public void translateToPosition(float xPos, float yPos, float zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
// FUNCTIONS -----------------------------------------------------------------------------------
	public float[] getCurrentPosition() {
		float[] tmp = { this.xStart + this.xPos, this.yStart + this.yPos, this.zStart + this.zPos };
		return tmp;
	}
// SETTERS AND GETTERS -------------------------------------------------------------------------
	public final String getIdentifier() {
		return identifier;
	}

	public final void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public final Model getModel() {
		return model;
	}

	public final void setModel(Model model) {
		this.model = model;
	}

	public final IntDrawHandler getDrawHandler() {
		return drawHandler;
	}

	public final void setDrawHandler(IntDrawHandler drawHandler) {
		this.drawHandler = drawHandler;
	}
	
	public void setStartPosition(float xStart, float yStart, float zStart) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;
	}
	
	public float[] getStartPosition() {
		float[] tmp = { this.xStart, this.yStart, this.zStart };
		return tmp;
	}
	
	public void addPosition(float xPos, float yPos, float zPos) {
		this.xPos += xPos;
		this.yPos += yPos;
		this.zPos += zPos;
	}
	
	public float[] getPosition() {
		float[] tmp = { 
					 1.0f, 		0.0f, 	   0.0f, 0.0f,
				 	 0.0f, 		1.0f, 	   0.0f, 0.0f,
				 	 0.0f, 		0.0f, 	   1.0f, 0.0f,
				this.xPos, this.yPos, this.zPos, 1.0f
				};
		
		return tmp;
	}
	
	public void addVelocity(float xVel, float yVel, float zVel) {
		this.xVel += xVel;
		this.yVel += yVel;
		this.zVel += zVel;
	}
	
	public float[] getVelocity() {
		float[] tmp = { this.xVel, this.yVel, this.zVel };
		
		return tmp;
	}
	
	public void addAcceleration(float xAcc, float yAcc, float zAcc) {
		this.xAcc += xAcc;
		this.yAcc += yAcc;
		this.zAcc += zAcc;
	}
	
	public float[] getAcceleration() {
		float[] tmp = { this.xAcc, this.yAcc, this.zAcc };
		
		return tmp;
	}
	
	public void addRotation(float xRot, float yRot, float zRot) {
		this.xRot += xRot;
		this.yRot += yRot;
		this.zRot += zRot;
	}
	
	public float[] getRotation() {
		Matrix.setIdentityM(this.rotationMatrix, 0);
		
		if (this.xRot != 0.0f) {
			Matrix.rotateM(this.rotationMatrix, 0, this.xRot, 1.0f, 0.0f, 0.0f);
		}
		
		if (this.yRot != 0.0f) {
			Matrix.rotateM(this.rotationMatrix, 0, this.yRot, 0.0f, 1.0f, 0.0f);
		}
		
		if (this.zRot != 0.0f) {
			Matrix.rotateM(this.rotationMatrix, 0, this.zRot, 0.0f, 0.0f, 1.0f);
		}
		
		return this.rotationMatrix;
	}
	
	public void addScalation(float xSca, float ySca, float zSca) {
		this.xSca += xSca;
		this.ySca += ySca;
		this.zSca += zSca;
	}
	
	public float[] getScalation() {
		float[] tmp = { 
				this.xSca,      0.0f,      0.0f, 0.0f,
				     0.0f, this.ySca,      0.0f, 0.0f,
				     0.0f,      0.0f, this.zSca, 0.0f,
				     0.0f,      0.0f,      0.0f, 1.0f
				};
		
		return tmp;
	}

	public float getxStart() {
		return xStart;
	}

	public void setxStart(float xStart) {
		this.xStart = xStart;
	}

	public float getyStart() {
		return yStart;
	}

	public void setyStart(float yStart) {
		this.yStart = yStart;
	}

	public float getzStart() {
		return zStart;
	}

	public void setzStart(float zStart) {
		this.zStart = zStart;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getzPos() {
		return zPos;
	}

	public void setzPos(float zPos) {
		this.zPos = zPos;
	}

	public float getxVel() {
		return xVel;
	}

	public void setxVel(float xVel) {
		this.xVel = xVel;
	}

	public float getyVel() {
		return yVel;
	}

	public void setyVel(float yVel) {
		this.yVel = yVel;
	}

	public float getzVel() {
		return zVel;
	}

	public void setzVel(float zVel) {
		this.zVel = zVel;
	}

	public float getxAcc() {
		return xAcc;
	}

	public void setxAcc(float xAcc) {
		this.xAcc = xAcc;
	}

	public float getyAcc() {
		return yAcc;
	}

	public void setyAcc(float yAcc) {
		this.yAcc = yAcc;
	}

	public float getzAcc() {
		return zAcc;
	}

	public void setzAcc(float zAcc) {
		this.zAcc = zAcc;
	}

	public float getxRot() {
		return xRot;
	}

	public void setxRot(float xRot) {
		this.xRot = xRot;
	}

	public float getyRot() {
		return yRot;
	}

	public void setyRot(float yRot) {
		this.yRot = yRot;
	}

	public float getzRot() {
		return zRot;
	}

	public void setzRot(float zRot) {
		this.zRot = zRot;
	}

	public float getxSca() {
		return xSca;
	}

	public void setxSca(float xSca) {
		this.xSca = xSca;
	}

	public float getySca() {
		return ySca;
	}

	public void setySca(float ySca) {
		this.ySca = ySca;
	}

	public float getzSca() {
		return zSca;
	}

	public void setzSca(float zSca) {
		this.zSca = zSca;
	}
//----------------------------------------------------------------------------------------------
}
