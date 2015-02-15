package com.redru.engine.wrapper.models;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

import com.redru.Redru;
import com.redru.engine.exceptions.ObjectAlreadyInStockException;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class ModelFactory {
    private static final String TAG = "ObjFactory";

    private static ModelFactory instance = new ModelFactory();
    private Map<String, Model> objStock = new Hashtable<String, Model>();
    private ArrayList<String> objFiles;

    /**
     * 
     */
    private ModelFactory() {
        this.objFiles = ResourceUtils.getFilesList("obj_");
        this.loadObjStock();

        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     * @return
     */
    public static ModelFactory getInstance() {
        return instance;
    }

    /**
     * 
     */
    private void loadObjStock() {
        Log.i(TAG, "Starting loading obj stock.");
        
        for (int fIndex = 0; fIndex < objFiles.size(); fIndex++) {
        	
        	// Objects are only loaded if they are not in the exception list in the properties file
        	if (!ResourceUtils.getApplicationProperty("loader_obj_exception").contains(objFiles.get(fIndex))) {
        		
        		// Load the object and save into the objects stock
	            int id = Redru.getContext().getResources().getIdentifier(objFiles.get(fIndex), "raw", Redru.getContext().getPackageName());
	            String file = ResourceUtils.readTextFileFromResource(Redru.getContext(), id);
	            objStock.put(objFiles.get(fIndex), ModelWrapper.getInstance().createObjFromFile(file, objFiles.get(fIndex)));
        	} else {
        		Log.i(TAG, "The object '" + objFiles.get(fIndex) + "' was in the exceptions list. It won't be loaded.");
        		objFiles.remove(fIndex);
        		fIndex--;
        	}
        }

        if (objFiles.size() == 0) {
            Log.i(TAG, "There was not files obj_");
        } else {
            Log.i(TAG, "Obj stock loading complete. Correctly loaded '" + objFiles.size() + "' files.");
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getObjFiles() {
        return objFiles;
    }

    /**
     * 
     * @param objectName
     * @return
     */
    public Model getStockedObject(String objStockKey) {
        Model obj = null;

        try {
            if (objStock.containsKey(objStockKey)) {
                obj = (Model) objStock.get(objStockKey).clone();
                Log.i(TAG, "Requested object '" + objStockKey + "' was correctly created from the factory.");
            } else {
                Log.i(TAG, "Requested object '" + objStockKey + "' was not in the objects stock.");
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return obj;
    }
    
    /**
     * 
     * @param identifier
     * @param obj
     */
    public void addObjectToStock(String identifier, Model obj) {
    	try {
	    	if (!this.objStock.containsKey(identifier)) {
	    		this.objStock.put(identifier, obj);
	    	} else {
	    		throw new ObjectAlreadyInStockException();
	    	}
    	} catch(ObjectAlreadyInStockException e) {
    		e.printStackTrace();
    	}
    }

}