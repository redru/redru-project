package com.redru.application.core;

import java.util.Random;

import com.redru.application.actors.complex.Starship;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.elements.GameActor;
import com.redru.engine.time.TimeObject;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;
import com.redru.engine.wrapper.textures.TextureFactory;

public class EnemySpawner extends TimeObject {
	
	private Model tmpModel;
	private GameActor actor;
	private ActionsManager actionsManager;
// CONSTRUCTOR -------------------------------------------------------------------------------------------
	public EnemySpawner(String identifier, int timeLimit, int timeCount, boolean active) {
		super(identifier, timeLimit, timeCount, active);
		this.actionsManager = ActionsManager.getInstance();
	}
	
	public EnemySpawner(String identifier, long timeElapsedLimit, long timeElapsedCount, boolean active) {
		super(identifier, timeElapsedLimit, timeElapsedCount, active);
		this.actionsManager = ActionsManager.getInstance();
	}
// -------------------------------------------------------------------------------------------------------
	@Override
	protected void timeAction() {
		// An enemy is spawned with random x, y and z
		Random rand = new Random();
    	
    	float x = rand.nextFloat() * 41.0f - 20.0f;
    	float y = rand.nextFloat() * 11.0f - 4.0f;
    	float z = rand.nextFloat() * 101.0f + 100.0f;
    	
    	this.tmpModel = ModelFactory.getInstance().getStockedModel("obj_b2spirit");
    	this.tmpModel.setTexture(TextureFactory.getInstance().getStockedTexture("tex_b2spirit"));
    	this.actor = new Starship(this.tmpModel, new TexturedObjDrawHandler(), "Enemy");
    	this.actor.scale(1.0f, 1.0f, 1.0f);
    	this.actor.rotate(0.0f, 180.0f, 0.0f);
    	this.actor.translate(x, 2.0f, z);
    	this.actor.setStaticPosition(x, 2.0f, z);
    	
    	this.actionsManager.addObjectToCreationQueue(this.actor);
    	
    	this.tmpModel = null;
        this.actor = null;
	}
// -------------------------------------------------------------------------------------------------------
}
