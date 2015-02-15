package com.redru.application.scene.simple;

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
	public Bullet clone() throws CloneNotSupportedException {
		float[] unifData = super.getUnifiedData();
		float[] tmpUnifiedData = new float[unifData.length];
		
		for (int i = 0; i < tmpUnifiedData.length; i++) {
			tmpUnifiedData[i] = unifData[i];
		}
		
		Bullet tmp = new Bullet(tmpUnifiedData, type);
		tmp.copyUnifiedDataToStartingUnifiedData();
		
		return tmp;
	}
// -----------------------------------------------------------------------------------------------------------------

}
