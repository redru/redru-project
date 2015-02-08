package com.redru.engine.wrapper.textures;

import android.graphics.Bitmap;
import android.util.Log;

public class Texture {
	private static final String TAG = "Texture";
	private String name = "";
	
	private byte[] textureData;
	private Bitmap bitmap;
	
	public Texture(String name) {
		this(null, name);
	}
	
	public Texture(byte[] textureData, String name) {
		this.name = name;
		this.textureData = textureData;
		
		Log.i(TAG, "Texture '" + name + "' has been created.");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getTextureData() {
		return textureData;
	}

	public void setTextureData(byte[] textureData) {
		this.textureData = textureData;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
