package com.redru.application.actors.complex;

import android.util.Log;

import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.IntModelDrawHandler;
import com.redru.engine.elements.CollisionGameActor;
import com.redru.engine.wrapper.models.Model;

public class Bullet extends CollisionGameActor {
	private static final String TAG = "Bullet";
	
	public enum BulletType {
		NONE,
		SIMPLE
	}
	
	private int damage = 0;
	private BulletType type;
// CONSTRUCTOR -------------------------------------------------------------------------------------
	public Bullet() {
		super(null, null, "");
		this.type = BulletType.NONE;
		Log.i(TAG, "Creation complete.");
	}
	
	public Bullet(Model obj, String identifier, BulletType type) {
		super(obj, null, identifier);
		this.type = type;
		Log.i(TAG, "Creation complete.");
	}
	
	public Bullet(Model obj, IntModelDrawHandler drawHandler, String identifier, int damage, BulletType type) {
		super(obj, drawHandler, identifier);
		this.damage = damage;
		this.type = type;
		Log.i(TAG, "Creation complete.");
	}
// IMPLEMENTATION ----------------------------------------------------------------------------------
	@Override
	public void onCollision(CollisionGameActor actor) {
		this.setActive(false);
		ActionsManager.getInstance().addObjectToDestroyQueue(this);
	}
// GETTERS AND SETTERS -----------------------------------------------------------------------------
	public BulletType getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setType(BulletType type) {
		this.type = type;
	}
// -------------------------------------------------------------------------------------------------
}
