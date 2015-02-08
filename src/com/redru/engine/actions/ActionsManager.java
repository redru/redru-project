package com.redru.engine.actions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import android.util.Log;

public class ActionsManager {
	private static final String TAG = "ActionsManager";

	private static ActionsManager instance;
	
	private Map<IntAction, String> contextActions = new Hashtable<IntAction, String>();
	private Map<String, ArrayList<?>> contextValues = new Hashtable<String, ArrayList<?>>();

	/**
	 * 
	 */
	private ActionsManager() {
		Log.i(TAG, "Creation complete.");
	}

	/**
	 * 
	 * @return
	 */
	public static ActionsManager getInstance() {
		if (instance == null) {
			instance = new ActionsManager();
		}

		return instance;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
	public void addContextAction(IntAction action, String context, ArrayList<?> values) {
		try {
			Set<IntAction> conAct = this.contextActions.keySet();
			
			for (IntAction tmp : conAct) {
				if (tmp.getClass().getSimpleName().equals(action.getClass().getSimpleName())) {
					throw new ActionAlreadyExistsException();
				}
			}
			
			this.contextActions.put(action, context);
			this.contextValues.put(context, values);
		} catch (ActionAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void addContextAction(IntAction action, String context) {
		try {
			Set<IntAction> conAct = this.contextActions.keySet();
			
			for (IntAction tmp : conAct) {
				if (tmp.getClass().getSimpleName().equals(action.getClass().getSimpleName())) {
					throw new ActionAlreadyExistsException();
				}
			}
			
			this.contextActions.put(action, context);
		} catch (ActionAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<?> addContextValues(String context, ArrayList<?> values) {
		return this.contextValues.put(context, values);
	}
	
	public void executeActionsByContext(String context) {
		Set<IntAction> conAct = this.contextActions.keySet();
		
		for (IntAction tmp : conAct) {
			if (this.contextActions.get(tmp).equals(context)) {
				tmp.execute(this.contextValues.get(context));
			}
		}
	}
	
	public void executeAllContextActions() {
		Set<IntAction> conAct = this.contextActions.keySet();
		
		for (IntAction tmp : conAct) {
			tmp.execute(this.contextValues.get(this.contextActions.get(tmp)));
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	
}
