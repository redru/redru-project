package com.redru.application.actors.complex;

import android.util.Log;

import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.IntModelDrawHandler;
import com.redru.engine.elements.CollisionGameActor;
import com.redru.engine.particles.ParticleSystem;
import com.redru.engine.wrapper.models.Model;

public class Starship extends CollisionGameActor {
	private static final String TAG = "Starship";
	private int life = 0;
	private int damage = 0;
	
// CONSTRUCTORS --------------------------------------------------------------------------------------------------
	public Starship() {
		super(null, null, "");
		Log.i(TAG, "Creation complete.");
	}
	
	public Starship(Model obj, String identifier) {
		super(obj, null, identifier);
		Log.i(TAG, "Creation complete.");
	}
	
	public Starship(Model obj, IntModelDrawHandler drawHandler, String identifier) {
		super(obj, drawHandler, identifier);
		Log.i(TAG, "Creation complete.");
	}
	
	public Starship(Model obj, IntModelDrawHandler drawHandler, String identifier, int life, int damage) {
		super(obj, drawHandler, identifier);
		this.life = life;
		this.damage = damage;
		Log.i(TAG, "Creation complete.");
	}
// FUNCTIONS -----------------------------------------------------------------------------------------------------
	@Override
	public void onCollision(CollisionGameActor actor) {
		if (actor instanceof Bullet) {
			if (this.life <= 0) {
				ActionsManager.getInstance().addObjectToDestroyQueue(this);
				
				ParticleSystem ps = ParticleSystem.getInstance();
				float[] pos = { this.xPos, this.yPos, this.zPos };
				float[] color = { 1.0f, 0.0f, 0.0f, 0.5f };
				float[] direction = new float[3];
				
				for (int i = 0; i < 200; i++) {
					direction[0] = (float) Math.random() * 2.0f - 1.0f;
					direction[1] = (float) Math.random() * 2.0f - 1.0f;
					direction[2] = (float) Math.random() * 2.0f - 1.0f;
					ps.addParticle(pos, color, direction, System.nanoTime() / 1000000000f);
				}
				
				ps.setup();
			} else {
				this.life -= ((Bullet) actor).getDamage();
			}
		}
	}
// GETTERS AND SETTERS -------------------------------------------------------------------------------------------
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
// ---------------------------------------------------------------------------------------------------------------
}
