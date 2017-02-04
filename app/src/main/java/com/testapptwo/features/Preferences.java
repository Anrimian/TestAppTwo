package com.testapptwo.features;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created on 02.05.2016.
 */
public class Preferences {

    private static SharedPreferences sharedPreferences;

    public static void init(Context ctx) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences get() {
        if (sharedPreferences == null) {
            throw new RuntimeException("preferences must be init first");
        }
        return sharedPreferences;
    }
}
