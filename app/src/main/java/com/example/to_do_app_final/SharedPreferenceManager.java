package com.example.to_do_app_final;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPreferenceManager {

    private static final String PREFS_NAME = "SignUpPrefs";
    private static final String USERS_SET = "users_set";
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

    public void saveUser(String name, String email, String password) {
        Set<String> users = sharedPreferences.getStringSet(USERS_SET, new HashSet<>());
        users.add(name);
        editor.putStringSet(USERS_SET, users);
        editor.putString(name + "_email", email);
        editor.putString(name + "_password", password);
        editor.apply();
    }

    public boolean validateUser(String name, String password) {
        Set<String> users = sharedPreferences.getStringSet(USERS_SET, new HashSet<>());
        if (users.contains(name)) {
            String savedPassword = sharedPreferences.getString(name + "_password", null);
            return savedPassword != null && savedPassword.equals(password);
        }
        return false;
    }

    public String getUserEmail(String name) {
        return sharedPreferences.getString(name + "_email", null);
    }

    public String getUserPassword(String name) {
        return sharedPreferences.getString(name + "_password", null);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}
