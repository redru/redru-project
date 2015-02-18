package com.redru.application.actions;

import java.util.Random;

import android.view.MotionEvent;

import com.redru.application.scene.complex.Starship;
import com.redru.engine.actions.Action;
import com.redru.engine.actions.ActionContext;
import com.redru.engine.actions.ActionsManager;
import com.redru.engine.drawhandlers.TexturedObjDrawHandler;
import com.redru.engine.wrapper.models.Model;
import com.redru.engine.wrapper.models.ModelFactory;
import com.redru.engine.wrapper.textures.TextureFactory;

public class TouchInputAction extends Action {
	private static TouchInputAction instance;

// CONSTRUCTOR -----------------------------------------------------------------------------
	private TouchInputAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
	}
	
	public static TouchInputAction getInstance() {
		if (instance == null) {
			instance = new TouchInputAction("TouchInputAction", false);
		}
		
		return instance;
	}
// METHOD IMPLEMENTATION -------------------------------------------------------------------
	@Override
	public void execute(ActionContext<?> context) {
		MotionEvent event = (MotionEvent) context.getValues().get(0);
		int action = event.getAction();
		
        switch (action) {

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_DOWN:
            	Random rand = new Random();
            	
            	float x = rand.nextFloat() * 21.0f - 10.0f;
            	float y = rand.nextFloat() * 11.0f - 4.0f;
            	float z = rand.nextFloat() * 101.0f + 100.0f;
            	
            	Model tmpModel = ModelFactory.getInstance().getStockedModel("obj_b2spirit");
    			tmpModel.setTexture(TextureFactory.getInstance().getStockedTexture("tex_b2spirit"));
    			Starship actor = new Starship(tmpModel, "Enemy x");
    			actor.setDrawHandler(new TexturedObjDrawHandler(actor));
    			actor.scale(1.0f, 1.0f, 1.0f);
    	    	actor.rotate(0.0f, 180.0f, 0.0f);
    	    	actor.translate(x, y, z);
    	    	actor.setStaticPosition(x, y, z);
            	
            	ActionsManager.getInstance().addObjectToCreationQueue(actor);
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
	}
// -----------------------------------------------------------------------------------------
}
