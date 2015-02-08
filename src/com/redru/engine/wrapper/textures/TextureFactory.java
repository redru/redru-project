package com.redru.engine.wrapper.textures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

import com.redru.Redru;
import com.redru.engine.utils.ResourceUtils;

public class TextureFactory {
	private static final String TAG = "TextureFactory";
	
	private static TextureFactory instance;
	private Map<String, Texture> textureStock = new Hashtable<String, Texture>();
	private ArrayList<String> texFiles;
	
	private TextureFactory() {
		this.texFiles = ResourceUtils.getFilesList("tex_");
		this.loadTextures();
		
		Log.i(TAG, "Startup completed.");
	}
	
	private void loadTextures() {
		for (int fIndex = 0; fIndex < texFiles.size(); fIndex++) {
            int id = Redru.getContext().getResources().getIdentifier(texFiles.get(fIndex), "raw", Redru.getContext().getPackageName());
            textureStock.put(texFiles.get(fIndex), ResourceUtils.importTexture(Redru.getContext(), id));
        }
		
		if (texFiles.size() == 0) {
            Log.i(TAG, "There was not files tex_");
        } else {
            Log.i(TAG, "Texture stock loading complete. Correctly loaded '" + texFiles.size() + "' files.");
        }
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
