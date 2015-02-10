package com.redru.application.actions;

import java.util.ArrayList;

import android.util.Log;

import com.redru.engine.actions.IntAction;

public class TestAction implements IntAction {

	@Override
	public void execute(ArrayList<?> actionObjects) {
		Log.i("Test action", "This is a oneTimeAction");
	}

}
