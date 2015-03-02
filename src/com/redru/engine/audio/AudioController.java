package com.redru.engine.audio;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.redru.R;
import com.redru.Redru;

@SuppressLint("UseSparseArrays")
public class AudioController {	
	private static AudioController instance;
	
	private SoundPool soundPool;
	private Map<String, Integer> playList = new HashMap<String, Integer>();
// CONSTRUCTOR ----------------------------------------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	private AudioController() {
		// Create the SoundPool Builder
//		SoundPool.Builder builder = new SoundPool.Builder();
//		builder.setMaxStreams(10);
//		builder.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
		// Build a SoundPool
//		this.soundPool = builder.build();
		this.soundPool = new SoundPool(10, AudioAttributes.CONTENT_TYPE_MUSIC, 0);
		// Load the audio and set in the table
		this.playList.put("SHOT", this.soundPool.load(Redru.getContext(), R.raw.audio_shot, 1));
		
	}
	
	public static AudioController getInstance() {
		if (instance == null) {
			instance = new AudioController();
		}
		
		return instance;
	}
// METHODS --------------------------------------------------------------------------------------------------------------
	public void  playSound(String sound) {
		this.soundPool.play(this.playList.get(sound), 0.5f, 0.5f, 1, 0, 2.0f);
	}
// ----------------------------------------------------------------------------------------------------------------------
}
