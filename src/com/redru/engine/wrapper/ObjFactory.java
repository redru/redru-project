package com.redru.engine.wrapper;

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
    private Map<String, EvoObj> objStock = new Hashtable<String, EvoObj>();
    private ArrayList<String> objFiles;

    /**
     * 
     */
    private ObjFactory() {
        this.objFiles = ResourceUtils.getFilesList("obj_");
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
    public EvoObj getStockedObject(String objectName) {
        EvoObj obj = null;

        try {
            if (objStock.containsKey(objectName)) {
                obj = (EvoObj) objStock.get(objectName).clone();
                Log.i(TAG, "Requested object '" + objectName + "' was correctly created from the factory.");
            } else {
                Log.i(TAG, "Requested object '" + objectName + "' was not in the objects stock.");
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
