package com.redru.engine.wrapper;

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
    private String[] objFiles;

    private ObjFactory() {
        objFiles = ResourceUtils.getFilesList("obj_");
        loadObjStock();

        Log.i(TAG, "Creation complete.");
    }

    public static ObjFactory getInstance() {
        return instance;
    }

    private void loadObjStock() {
        Log.i(TAG, "Starting loading obj stock.");

        for (int fIndex = 0; fIndex < objFiles.length; fIndex++) {
            int id = Redru.getContext().getResources().getIdentifier(objFiles[fIndex], "raw", Redru.getContext().getPackageName());
            String file = ResourceUtils.readTextFileFromResource(Redru.getContext(), id);
            objStock.put(objFiles[fIndex], ObjWrapper.getInstance().createObjFromFile(file, objFiles[fIndex]));
        }

        if (objFiles.length == 0) {
            Log.i(TAG, "There was not files obj_");
        } else {
            Log.i(TAG, "Obj stock loading complete. Correctly loaded '" + objFiles.length + "' files.");
        }
    }

    public String[] getObjFiles() {
        return objFiles;
    }

    public EvoObj getStockedObject(String object) {
        EvoObj obj = null;

        try {
            if (objStock.containsKey(object)) {
                obj = (EvoObj) objStock.get(object).clone();
                Log.i(TAG, "Requested object '" + object + "' was correctly created from the factory.");
            } else {
                Log.i(TAG, "Requested object '" + object + "' was not in the objects stock.");
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
