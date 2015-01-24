package com.redru.engine.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.redru.R;

/**
 * Created by Luca on 16/01/2015.
 */
public class ResourceUtils {

    private static final String TAG = "ResourceUtils";

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }

        return body.toString();
    }

    public static String[] getFilesList(String suffix) {
        Field[] fields = R.raw.class.getFields();
        ArrayList<String> tmp = new ArrayList<String>();

        for(int i = 0; i < fields.length; i++){
            if (fields[i].getName().startsWith(suffix)) {
                tmp.add(fields[i].getName());
                Log.i(TAG, suffix + " file: " + fields[i].getName());
            }
        }

        String[] list = new String[tmp.size()];
        list = tmp.toArray(list);

        return list;
    }

}