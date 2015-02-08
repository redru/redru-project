package com.redru.engine.wrapper.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

import com.redru.Redru;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class ObjFactory {
    private static final String TAG = "ObjFactory";

    private static ObjFactory instance = new ObjFactory();
    private Map<String, Obj> objStock = new Hashtable<String, Obj>();
    private Map<String, Obj> complexObjectStock = new Hashtable<String, Obj>();
    private ArrayList<String> objFiles = new ArrayList<String>();
    private ArrayList<String> complexObjFiles = new ArrayList<String>();

    /**
     * 
     */
    private ObjFactory() {
        this.objFiles = ResourceUtils.getFilesList("obj_");
        this.complexObjFiles = ResourceUtils.getFilesList("c_obj_");
        this.loadObjStock();

        Log.i(TAG, "Creation complete.");
    }

    /**
     * 
     * @return
     */
    public static ObjFactory getInstance() {
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
	            objStock.put(objFiles.get(fIndex), ObjWrapper.getInstance().createObjFromFile(file, objFiles.get(fIndex)));
        	} else {
        		Log.i(TAG, "The object '" + objFiles.get(fIndex) + "' was in the exceptions list. It won't be loaded.");
        		objFiles.remove(fIndex);
        		fIndex--;
        	}
        }
        
        for (int fIndex = 0; fIndex < complexObjFiles.size(); fIndex++) {
        	
        	// Objects are only loaded if they are not in the exception list in the properties file
        	if (!ResourceUtils.getApplicationProperty("loader_obj_exception").contains(complexObjFiles.get(fIndex))) {
        		
        		// Load the object and save into the objects stock
	            int id = Redru.getContext().getResources().getIdentifier(complexObjFiles.get(fIndex), "raw", Redru.getContext().getPackageName());
	            String file = ResourceUtils.readTextFileFromResource(Redru.getContext(), id);
	            complexObjectStock.put(complexObjFiles.get(fIndex), ObjWrapper.getInstance().createObjFromFile(file, complexObjFiles.get(fIndex)));
        	} else {
        		Log.i(TAG, "The object '" + complexObjFiles.get(fIndex) + "' was in the exceptions list. It won't be loaded.");
        		complexObjFiles.remove(fIndex);
        		fIndex--;
        	}
        }

        if (objFiles.size() == 0) {
            Log.i(TAG, "There was not files obj_");
        } else {
            Log.i(TAG, "Obj stock loading complete. Correctly loaded '" + objFiles.size() + "' files.");
        }
        
        if (complexObjFiles.size() == 0) {
            Log.i(TAG, "There was not files c_obj_");
        } else {
            Log.i(TAG, "Complex Obj stock loading complete. Correctly loaded '" + complexObjFiles.size() + "' files.");
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
     * @param objStockKey
     * @return
     */
	public Obj getStockedObject(String objStockKey) {
		Obj obj = null;

		if (objStock.containsKey(objStockKey)) {
			obj = (Obj) objStock.get(objStockKey);
			Log.i(TAG, "Requested object '" + objStockKey + "' was correctly created from the factory.");
		} else {
			Log.i(TAG, "Requested object '" + objStockKey + "' was not in the objects stock.");
		}

		return obj;
	}
    
	/**
	 * 
	 * @param objStockKey
	 * @return
	 */
    public Obj getStockedObjectClone(String objStockKey) {
        Obj obj = null;

        try {
            if (objStock.containsKey(objStockKey)) {
                obj = (Obj) objStock.get(objStockKey).clone();
                Log.i(TAG, "Requested object '" + objStockKey + "' was correctly created from the factory.");
            } else {
                Log.i(TAG, "Requested object '" + objStockKey + "' was not in the objects stock.");
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
