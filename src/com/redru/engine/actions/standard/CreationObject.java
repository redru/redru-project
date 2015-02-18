package com.redru.engine.actions.standard;

public class CreationObject {

	public String modelIdentifier;
	public float xPos, yPos, zPos;
	public float xSca, ySca, zSca;
	public float xRot, yRot, zRot;
	
	public CreationObject(String modelIdentifier, float xPos, float yPos, float zPos, float xSca, float ySca, float zSca, float xRot, float yRot, float zRot) {
		this.modelIdentifier = modelIdentifier;
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.xSca = xSca;
		this.ySca = ySca;
		this.zSca = zSca;
		this.xRot = xRot;
		this.yRot = yRot;
		this.zRot = zRot;
		
	}
	
}
