package com.example.to_do_app_final;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String PREFS_NAME = "SignUpPrefs";
    private static SharedPreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    // Add other methods to save/get different data types if needed
}
