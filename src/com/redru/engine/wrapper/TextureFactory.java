package com.redru.engine.wrapper;

import java.util.Hashtable;
import java.util.Map;

import com.redru.R;
import com.redru.Redru;
import com.redru.engine.utils.ResourceUtils;

import android.util.Log;

public class TextureFactory {
	private static final String TAG = "TextureFactory";
	private static TextureFactory instance;
	
	private Map<String, Texture> textureStock = new Hashtable<String, Texture>();
	
	private TextureFactory() {
		this.loadTextures();
		Log.i(TAG, "Startup completed.");
	}
	
	private void loadTextures() {
		Texture tmp = ResourceUtils.importTexture(Redru.getContext(), R.raw.tex_b2spirit);
		textureStock.put("tex_b2spirit", tmp);
	}
	
	public static TextureFactory getInstance() {
		if (instance == null) {
			instance = new TextureFactory();
		}
		
		return instance;
	}
	
	public Texture getStockedTexture(String tex) {
		Texture tmp = textureStock.get(tex);
		
		return tmp;
	}
	
}
