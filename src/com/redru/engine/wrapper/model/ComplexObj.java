package com.redru.engine.wrapper.model;

import java.util.Hashtable;
import java.util.Map;

import android.util.Log;

public class ComplexObj {
	private static final String TAG = "ComplexObj";
	
	private Map<String, Obj> complexObjParts = new Hashtable<String, Obj>();
	private float[] unifiedComplexData;
	
// CONSTRUCTOR -------------------------------------------------------------------------------------
	protected ComplexObj(Map<String, Obj> complexObjParts) {
		this.complexObjParts = complexObjParts;
		setUnifiedComplexData();
		
		Log.i(TAG, "Creation complete");
	}

// GETTERS AND SETTERS -----------------------------------------------------------------------------
	public Map<String, Obj> getComplexObjParts() {
		return complexObjParts;
	}

	public void setComplexObjParts(Map<String, Obj> complexObjParts) {
		this.complexObjParts = complexObjParts;
	}
	
	private void setUnifiedComplexData() {
		String[] tmp = (String[]) this.complexObjParts.keySet().toArray();
		
		int count = 0;
		for (int i = 0; i < tmp.length; i++) {
			count += tmp[i].length();
		}
		
		this.unifiedComplexData = new float[count];
		
		int total = 0;
		for (String str : tmp) {
			for (int i = 0; i < this.complexObjParts.get(str).getUnifiedData().length; i++, total++) {
				this.unifiedComplexData[total] = this.complexObjParts.get(str).getUnifiedData()[i];
			}
		}
	}

	public float[] getUnifiedComplexData() {
		return this.unifiedComplexData;
	}
//-------------------------------------------------------------------------------------------------
}
