package com.redru.application.actions;

import android.util.Log;

import com.redru.engine.actions.Action;
import com.redru.engine.actions.Context;

public class TestAction extends Action {
	
	public TestAction(String identifier, boolean executeOnce) {
		super(identifier, executeOnce);
	}

	@Override
	public void execute(Context context) {
		Log.i("Test action", "This is a oneTimeAction");
	}

}
