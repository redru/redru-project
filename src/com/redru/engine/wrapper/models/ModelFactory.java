package com.redru.engine.wrapper.models;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

import com.redru.Redru;
import com.redru.engine.exceptions.ModelAlreadyInStockException;
import com.redru.engine.utils.ResourceUtils;

/**
 * Created by Luca on 22/01/2015.
 */
public class ModelFactory {
    private static final String TAG = "ModelFactory";

    private static ModelFactory instance = new ModelFactory();
    private Map<String, Model> modelStock = new Hashtable<String, Model>();
    private ArrayList<String> modelFiles;

    /**
     * 
     */
    private ModelFactory() {
        this.modelFiles = ResourceUtils.getFilesList("obj_");
        this.loadModelStock();

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
    private void loadModelStock() {
        Log.i(TAG, "Starting loading obj stock.");
        
        for (int fIndex = 0; fIndex < modelFiles.size(); fIndex++) {
        	
        	// Objects are only loaded if they are not in the exception list in the properties file
        	if (!ResourceUtils.getApplicationProperty("loader_obj_exception").contains(modelFiles.get(fIndex))) {
        		
        		// Load the object and save into the objects stock
	            int id = Redru.getContext().getResources().getIdentifier(modelFiles.get(fIndex), "raw", Redru.getContext().getPackageName());
	            String file = ResourceUtils.readTextFileFromResource(Redru.getContext(), id);
	            modelStock.put(modelFiles.get(fIndex), ModelWrapper.getInstance().createObjFromFile(file, modelFiles.get(fIndex)));
        	} else {
        		Log.i(TAG, "The model '" + modelFiles.get(fIndex) + "' was in the exceptions list. It won't be loaded.");
        		modelFiles.remove(fIndex);
        		fIndex--;
        	}
        }

        if (modelFiles.size() == 0) {
            Log.i(TAG, "There was not files obj_");
        } else {
            Log.i(TAG, "Model stock loading complete. Correctly loaded '" + modelFiles.size() + "' files.");
        }
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getModelFiles() {
        return modelFiles;
    }

    /**
     * 
     * @param objectName
     * @return
     */
    public Model getStockedModel(String modelStockKey) {
        try {
            if (modelStock.containsKey(modelStockKey)) {
            	Log.i(TAG, "Requested model '" + modelStockKey + "' was correctly loaded from the factory.");
            	return modelStock.get(modelStockKey).clone();
                
            } else {
                Log.i(TAG, "Requested model '" + modelStockKey + "' was not in the objects stock.");
                return null;
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
		return null;
    }
    
    /**
     * 
     * @param identifier
     * @param obj
     */
    public void addModelToStock(String identifier, Model model) {
    	try {
	    	if (!this.modelStock.containsKey(identifier)) {
	    		this.modelStock.put(identifier, model);
	    	} else {
	    		throw new ModelAlreadyInStockException();
	    	}
    	} catch(ModelAlreadyInStockException e) {
    		e.printStackTrace();
    	}
    }

}
