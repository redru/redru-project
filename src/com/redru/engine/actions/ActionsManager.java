package com.redru.engine.actions;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import com.redru.engine.exceptions.ContextAlreadyExistsException;
import com.redru.engine.exceptions.ContextNotFoundException;

public class ActionsManager {
	private static final String TAG = "ActionsManager";

	private static ActionsManager instance;
	private Map<Action, String> actions = new Hashtable<Action, String>();
	private Map<String, Context> contexts = new Hashtable<String, Context>();
	
// CONSTRUCTOR -------------------------------------------------------------------------------------------------------
	private ActionsManager() {
		Log.i(TAG, "Creation complete.");
	}
	
	public static ActionsManager getInstance() {
		if (instance == null) {
			instance = new ActionsManager();
		}

		return instance;
	}
	
// CONTEXT ACTIONS ---------------------------------------------------------------------------------------------------
	public void addAction(Action action, String context) {
		try {
			Set<String> conAct = this.contexts.keySet();
			boolean found = false;
			
			for (String tmp : conAct) {
				if (tmp.equals(context)) {
					found = true;
					break;
				}
			}
			
			if (found) {
				this.actions.put(action, context);
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void removeAction(String actionIdentifier) {
		Set<Action> conAct = this.actions.keySet();
		Iterator<Action> iterator = conAct.iterator();
		Action tmp = null;
		
		while (iterator.hasNext()) {
			tmp = iterator.next();
			if (tmp.getIdentifier().equals(actionIdentifier)) {
				iterator.remove();
			}
		}
	}
	
	public void addContext(Context context) {
		try {
			if ( !this.contexts.containsKey(context.getIdentifier()) ) {
				this.contexts.put(context.getIdentifier(), context);
			} else {
				throw new ContextAlreadyExistsException();
			}
		} catch (ContextAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void removeContext(String contextIdentifier) {
		try {
			if (this.contexts.containsKey(contextIdentifier)) {
				// Remove the context from the map
				this.contexts.remove(contextIdentifier);
				
				// Remove all actions using the selected context
				Set<Action> conAct = this.actions.keySet();
				Iterator<Action> iterator = conAct.iterator();
				Action tmp = null;
				
				while (iterator.hasNext()) {
					tmp = iterator.next();
					if (this.actions.get(tmp).equals(contextIdentifier)) {
						iterator.remove();
					}
				}
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void replaceContext(String contextIdentifier, Context context) {
		this.contexts.put(contextIdentifier, context);
	}
	
	public void changeContextState(String contextIdentifier, boolean state) {
		try {
			if (this.contexts.containsKey(contextIdentifier)) {
				this.contexts.get(contextIdentifier).setActive(state);
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void executeActionsByContext(String contextIdentifier) {
		Set<Action> conAct = this.actions.keySet();
		
		for (Action tmp : conAct) {
			if (this.actions.get(tmp).equals(contextIdentifier) && !tmp.isExecuteOnce()) {
				tmp.execute(this.contexts.get(contextIdentifier));
			}
		}
	}
	
	public void executeActionsByActiveContexts() {
		Set<Action> conAct = this.actions.keySet();

		for (Action tmp : conAct) {
			if (this.contexts.get(this.actions.get(tmp)).isActive() && !tmp.isExecuteOnce()) {
				tmp.execute(this.contexts.get(this.actions.get(tmp)));
			}
		}
	}
	
	public void executeAllActions() {
		Set<Action> conAct = this.actions.keySet();
		
		for (Action tmp : conAct) {
			if (!tmp.isExecuteOnce()) {
				tmp.execute(this.contexts.get(this.actions.get(tmp)));
			}
		}
	}
// ONE TIME ACTION ---------------------------------------------------------------------------------------------------
	public void executeOneTimeActions() {
		Set<Action> conAct = this.actions.keySet();
		Iterator<Action> iterator = conAct.iterator();
		Action tmp = null;
		
		while (iterator.hasNext()) {
			tmp = iterator.next();
			if (tmp.isExecuteOnce() && this.contexts.get(this.actions.get(tmp)).isActive()) {
				tmp.execute(this.contexts.get(this.actions.get(tmp)));
				iterator.remove();
			}
		}
	}
//--------------------------------------------------------------------------------------------------------------------
	
}
