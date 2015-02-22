package com.redru.application.models;

import com.redru.engine.wrapper.models.Model;

public class BulletModel extends Model {
// CONSTRUCTORS ----------------------------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private BulletModel(float[] positions, float[] textures, float[] normals, float[] unifiedData, float[] collisionInfo, String name) {
		super(positions, textures, normals, unifiedData, collisionInfo, name);
	}
	
	public BulletModel(float[] unifiedData, float[] collisionInfo) {
		super.setUnifiedData(unifiedData);
		super.copyUnifiedDataToStartingUnifiedData();
		super.setCollisionInfo(collisionInfo);
	}
	
// GETTERS AND SETTERS ---------------------------------------------------------------------------------------------
// CLONE -----------------------------------------------------------------------------------------------------------
	@Override
	public BulletModel clone() {
		return this;
	}
// -----------------------------------------------------------------------------------------------------------------

}
