package com.redru.engine.context;

import com.redru.engine.actions.ActionsManager;
import com.redru.engine.audio.AudioController;
import com.redru.engine.scene.SceneContext;
import com.redru.engine.shader.ShaderFactory;
import com.redru.engine.time.TimeManager;
import com.redru.engine.view.Camera;
import com.redru.engine.wrapper.models.ModelFactory;
import com.redru.engine.wrapper.textures.TextureFactory;

public class EngineContext {
// --------------------------------------------------------------------------------------------
	public static ShaderFactory shadeFactory;
	public static Camera camera;
	public static SceneContext scene;
	public static ModelFactory modelFactory;
	public static TextureFactory texFactory;
	public static ActionsManager actionsManager;
	public static TimeManager timeManager;
	public static AudioController audioController;
// --------------------------------------------------------------------------------------------
	public static void contextStartup() {
		shadeFactory = ShaderFactory.getInstance();
		camera = Camera.getInstance();
		scene = SceneContext.getInstance();
	    modelFactory = ModelFactory.getInstance();
	    texFactory = TextureFactory.getInstance();
	    actionsManager = ActionsManager.getInstance();
	    timeManager = TimeManager.getInstance();
	    audioController = AudioController.getInstance();
	}
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
	
// --------------------------------------------------------------------------------------------
}
