package com.redru.application.actors.simple;

import com.redru.engine.wrapper.models.Model;

public class Bullet extends Model {
	
	public enum BulletType {
		SIMPLE
	}
	
	private BulletType type;
// CONSTRUCTORS ----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private Bullet(float[] positions, float[] textures, float[] normals, float[] unifiedData, float[] collisionInfo, String name) {
		super(positions, textures, normals, unifiedData, collisionInfo, name);
	}
	
	public Bullet(float[] unifiedData, float[] collisionInfo, BulletType type) {
		super.setUnifiedData(unifiedData);
		super.copyUnifiedDataToStartingUnifiedData();
		super.setCollisionInfo(collisionInfo);
		this.type = type;
	}
	
// GETTERS AND SETTERS ---------------------------------------------------------------------------------------------
	public BulletType getType() {
		return type;
	}

	public void setType(BulletType type) {
		this.type = type;
	}
// CLONE -----------------------------------------------------------------------------------------------------------
	@Override
	public Bullet clone() {
		return this;
	}
// -----------------------------------------------------------------------------------------------------------------

}
