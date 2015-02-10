package com.redru.engine.actions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import android.util.Log;

import com.redru.engine.exceptions.ActionAlreadyExistsException;
import com.redru.engine.exceptions.ContextAlreadyExistsException;
import com.redru.engine.exceptions.ContextNotFoundException;
import com.redru.engine.exceptions.IdentifierAlreadyExistsException;
import com.redru.engine.exceptions.IdentifierNotFoundException;

public class ActionsManager {
	private static final String TAG = "ActionsManager";

	private static ActionsManager instance;
	
	private Map<IntAction, String> actions = new Hashtable<IntAction, String>();
	private Map<String, ArrayList<?>> values = new Hashtable<String, ArrayList<?>>();
	private Map<String, Boolean> contexts = new Hashtable<String, Boolean>();
	
	private Map<String, IntAction> oneTimeActions = new Hashtable<String, IntAction>();
	private Map<String, String> assIdentifierContext = new Hashtable<String, String>();
	
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
	public void addAction(IntAction action, String context) {
		try {
			Set<IntAction> conAct = this.actions.keySet();
			
			for (IntAction tmp : conAct) {
				if (tmp.getClass().getSimpleName().equals(action.getClass().getSimpleName())) {
					throw new ActionAlreadyExistsException();
				}
			}
			
			this.actions.put(action, context);
		} catch (ActionAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void addContext(String context, ArrayList<?> values, boolean active) {
		try {
			if (!this.contexts.containsKey(context)) {
				this.contexts.put(context, active);
				this.values.put(context, values);
			} else {
				throw new ContextAlreadyExistsException();
			}
		} catch (ContextAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void removeContext(String context) {
		try {
			if (this.contexts.containsKey(context)) {
				this.contexts.remove(context);
				this.values.remove(context);
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void changeContextValues(String context, ArrayList<?> values) {
		try {
			if (this.contexts.containsKey(context)) {
				this.values.put(context, values);
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void changeContextState(String context, boolean state) {
		try {
			if (this.contexts.containsKey(context)) {
				this.contexts.put(context, state);
			} else {
				throw new ContextNotFoundException();
			}
		} catch (ContextNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void executeActionsByContext(String context) {
		Set<IntAction> conAct = this.actions.keySet();
		
		for (IntAction tmp : conAct) {
			if (this.actions.get(tmp).equals(context)) {
				tmp.execute(this.values.get(context));
			}
		}
	}
	
	public void executeActionsByActiveContexts() {
		Set<IntAction> conAct = this.actions.keySet();

		for (IntAction tmp : conAct) {
			if (this.contexts.get(this.actions.get(tmp))) {
				tmp.execute(this.values.get(this.actions.get(tmp)));
			}
		}
	}
	
	public void executeAllActions() {
		Set<IntAction> conAct = this.actions.keySet();
		
		for (IntAction tmp : conAct) {
			tmp.execute(this.values.get(this.actions.get(tmp)));
		}
	}
// ONE TIME ACTION ---------------------------------------------------------------------------------------------------
	public void addOneTimeContext(String context, ArrayList<?> values) {
		try {
			if (!this.contexts.containsKey(context)) {
				this.contexts.put(context, false);
			} else {
				throw new ContextAlreadyExistsException();
			}
		} catch (ContextAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void addOneTimeAction(IntAction oneTimeAction, String context) {
		boolean isNotNew = true;
		Random ran = new Random();
		String identifier = String.valueOf(ran.nextInt(255));
		
		do {
			if (this.oneTimeActions.containsKey(identifier)) {
				identifier = String.valueOf(ran.nextInt(10000));
			} else {
				this.oneTimeActions.put(identifier, oneTimeAction);
				this.assIdentifierContext.put(identifier, context);
				isNotNew = false;
			}
		} while (isNotNew);
	}
	
	public void addOneTimeAction(String identifier, IntAction oneTimeAction, String context) {
		try {
			if (!this.oneTimeActions.containsKey(identifier)) {
				this.oneTimeActions.put(identifier, oneTimeAction);
				this.assIdentifierContext.put(identifier, context);
			} else {
				throw new IdentifierAlreadyExistsException();
			}
		} catch (IdentifierAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void removeOneTimeAction(String identifier) {
		try {
			if (this.oneTimeActions.containsKey(identifier)) {
				this.oneTimeActions.remove(identifier);
				this.assIdentifierContext.remove(identifier);
			} else {
				throw new IdentifierNotFoundException();
			}
		} catch (IdentifierNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void executeOneTimeActions() {
		Set<String> idenAct = this.oneTimeActions.keySet();
		for (String tmp : idenAct) {
			this.oneTimeActions.get(tmp).execute(this.values.get(this.assIdentifierContext.get(tmp)));
		}
		
		this.oneTimeActions.clear();
		this.assIdentifierContext.clear();
	}
//--------------------------------------------------------------------------------------------------------------------
	
}
