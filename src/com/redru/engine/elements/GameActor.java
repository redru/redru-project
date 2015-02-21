package com.redru.engine.elements;

import android.opengl.Matrix;

import com.redru.engine.drawhandlers.IntDrawHandler;
import com.redru.engine.scene.IntSceneElement;
import com.redru.engine.wrapper.models.Model;

public abstract class GameActor implements IntTransformable, IntSceneElement {
	
	private String identifier;
	private Model model;
	private IntDrawHandler drawHandler;
	
	private float[] translationMatrix = new float[16];
	private float[] rotationMatrix = new float[16];
	private float[] scalationMatrix = new float[16];
	
	private float[] collisionInfo = new float[6];
	
	private float xStatic = 0.0f, yStatic = 0.0f, zStatic = 0.0f;
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
		 Matrix.setIdentityM(translationMatrix, 0);
		 Matrix.setIdentityM(rotationMatrix, 0);
		 Matrix.setIdentityM(scalationMatrix, 0);
		
		this.model = model;
		this.drawHandler = drawHandler;
		this.identifier = identifier;
		
		System.arraycopy(model.getCollisionInfo(), 0, this.collisionInfo, 0, this.collisionInfo.length); // Copy values from the model collision
	}
// INTERFACES METHODS --------------------------------------------------------------------------
	@Override
	public void setup() {
		this.drawHandler.setup(this.model);
	}

	@Override
	public final void draw() {
		this.drawHandler.draw(this.model);
	}
	
	@Override
	public void updateTransformBuffers() {
		this.updateCollisionInfo(); // Update the collisionInfo
		this.drawHandler.updateTransformBuffers(this.getScalationMatrix(), this.getRotationMatrix(), this.getTranslationMatrix()); // Upload transformation matrices into drawHandler
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
	private void updateCollisionInfo() {
		float[] tmp = model.getCollisionInfo();
		
		this.collisionInfo[0] = (tmp[0] * this.xSca) + this.xPos;
		this.collisionInfo[1] = (tmp[1] * this.xSca) + this.xPos;
		this.collisionInfo[2] = (tmp[2] * this.ySca) + this.yPos;
		this.collisionInfo[3] = (tmp[3] * this.ySca) + this.yPos;
		this.collisionInfo[4] = (tmp[4] * this.zSca) + this.zPos;
		this.collisionInfo[5] = (tmp[5] * this.zSca) + this.zPos;
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
	
	public void setStaticPosition(float xStatic, float yStatic, float zStatic) {
		this.xStatic = xStatic;
		this.yStatic = yStatic;
		this.zStatic = zStatic;
	}
	
	public float[] getStaticPosition() {
		float[] tmp = { this.xStatic, this.yStatic, this.zStatic };
		return tmp;
	}
	
	public float[] getTranslationMatrix() {
		this.translationMatrix[12] = this.xPos;
		this.translationMatrix[13] = this.yPos;
		this.translationMatrix[14] = this.zPos;
		
		return this.translationMatrix;
	}
	
	public float[] getVelocity() {
		float[] tmp = { this.xVel, this.yVel, this.zVel };
		
		return tmp;
	}
	
	public float[] getAcceleration() {
		float[] tmp = { this.xAcc, this.yAcc, this.zAcc };
		
		return tmp;
	}
	
	public float[] getRotationMatrix() {
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
	
	public float[] getScalationMatrix() {
		this.scalationMatrix[0] = this.xSca;
		this.scalationMatrix[5] = this.ySca;
		this.scalationMatrix[10] = this.zSca;
		
		return this.scalationMatrix;
	}

	public float getxStart() {
		return xStatic;
	}

	public void setxStart(float xStart) {
		this.xStatic = xStart;
	}

	public float getyStart() {
		return yStatic;
	}

	public void setyStart(float yStart) {
		this.yStatic = yStart;
	}

	public float getzStart() {
		return zStatic;
	}

	public void setzStart(float zStart) {
		this.zStatic = zStart;
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
