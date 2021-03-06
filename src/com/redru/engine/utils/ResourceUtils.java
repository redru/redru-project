package com.redru.engine.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Properties;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.redru.R;
import com.redru.Redru;
import com.redru.engine.wrapper.textures.Texture;

/**
 * Created by Luca on 16/01/2015.
 */
public class ResourceUtils {
	
	private static Properties props;

	private static final String TAG = "ResourceUtils";

	/**
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public static String readTextFileFromResource(Context context,
			int resourceId) {
		StringBuilder body = new StringBuilder();

		try {
			InputStream inputStream = context.getResources().openRawResource(resourceId);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String nextLine;
			while ((nextLine = bufferedReader.readLine()) != null) {
				body.append(nextLine);
				body.append('\n');
			}

			bufferedReader.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not open resource: " + resourceId, e);
		} catch (Resources.NotFoundException nfe) {
			throw new RuntimeException("Resource not found: " + resourceId, nfe);
		}

		return body.toString();
	}

	/**
	 * 
	 * @param suffix
	 * @return
	 */
	public static ArrayList<String> getFilesList(String suffix) {
		Field[] fields = R.raw.class.getFields();
		ArrayList<String> tmp = new ArrayList<String>();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().startsWith(suffix)) {
				tmp.add(fields[i].getName());
				Log.i(TAG, suffix + " file: " + fields[i].getName());
			}
		}

		return tmp;
	}

	/**
	 * 
	 * @param context
	 * @param resourceId
	 * @return
	 */
	public static Texture importTexture(Context context, int resourceId) {
    	Texture tex = null;
    	
//    	final BitmapFactory.Options options = new BitmapFactory.Options();
//    	options.inScaled = false;
//    	final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
    	
    	InputStream inputStream = context.getResources().openRawResource(resourceId);
    	Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
    	
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
    	
    	tex = new Texture("Spirit Texture");
    	tex.setBitmap(bitmap);
    	tex.setTextureData(stream.toByteArray());
    	
    	return tex;
    }
	
	/**
	 * 
	 * @param property
	 * @return
	 */
	public static String getApplicationProperty(String property) {
		try {
			if (props == null) {
				props = new Properties();
				InputStream inputStream = Redru.getContext().getResources().openRawResource(R.raw.application);
				props.load(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return props.getProperty(property);
	}
	
}