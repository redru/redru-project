package com.redru.engine.actions;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class ActionsManager {
	private static final String TAG = "ActionsManager";

	private static ActionsManager instance;
	private LinkedList<IntAction> actions = new LinkedList<IntAction>();

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

	/**
	 * 
	 * @param action
	 */
	public void addAction(IntAction newAction) {
		try {
			for (IntAction action : this.actions) {

				// Verify if exist two actions with the same name
				if (action.getClass().getSimpleName().equals(newAction.getClass().getSimpleName())) {
					throw new ActionAlreadyExistsException();
				}

			}

			this.actions.add(newAction);
			Log.i(TAG, "Action '" + newAction.getClass().getSimpleName() + "' was correctly loaded.");
		} catch (ActionAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public IntAction getAction(String name) {
		IntAction action = null;

		for (IntAction tmp : this.actions) {
			if (tmp.getClass().getSimpleName().equals(name)) {
				action = tmp;
				break;
			}
		}

		return action;
	}

	/**
	 * 
	 * @param name
	 */
	public void executeAction(String name) {
		IntAction action = this.getAction(name);

		if (action != null) {
			action.execute(null);
		}
	}

	/**
	 * 
	 * @param name
	 * @param args
	 */
	public void executeAction(String name, ArrayList<?> args) {
		IntAction action = this.getAction(name);

		if (action != null) {
			action.execute(args);
		}
	}

	/**
	 * 
	 * @param name
	 */
	public void removeAction(String name) {
		try {
			boolean found = true;

			for (IntAction action : this.actions) {
				if (action.getClass().getSimpleName().equals(name)) {
					this.actions.remove(action);
					Log.i(TAG, "Action '" + name + "' has been destroyed.");
					break;
				}
			}

			if (!found) {
				throw new NotFoundException(name);
			}

		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public LinkedList<IntAction> getActions() {
		return this.actions;
	}
}
