package com.example.ourproject;


import android.content.Context;
import android.content.SharedPreferences;

public class LocalUserDetails {


    private static final String SP_NAME = "UserDetails";
    private SharedPreferences sharedPreferences;


    public LocalUserDetails(Context context){

        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public void loginUser(User user){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("type", user.getType());
        editor.putBoolean("logged in", true);
        editor.commit();
    }

    public void logoutUser(){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        editor.putBoolean("logged in", false);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean("logged in", false);
    }

    public User getUser(){


        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        String type = sharedPreferences.getString("type", "");
        User user = new User(username, password);
        user.setType(type);

        return user;
    }

}
