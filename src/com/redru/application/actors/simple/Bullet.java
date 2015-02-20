package com.redru.application.actors.simple;

import com.redru.engine.wrapper.models.Model;

public class Bullet extends Model {
	
	public enum BulletType {
		SIMPLE
	}
	
	private BulletType type;
// CONSTRUCTORS ----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private Bullet(float[] positions, float[] textures, float[] normals, float[] unifiedData, String name) {
		super(positions, textures, normals, unifiedData, name);
	}
	
	public Bullet(float[] unifiedData, BulletType type) {
		super.setUnifiedData(unifiedData);
		super.copyUnifiedDataToStartingUnifiedData();
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
