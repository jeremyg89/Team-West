package com.example.wordwizard;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    //keys for user information
    private static final String SHARED_PREF_NAME = "mysharedpref1";
    private static final String KEY_ID = "userid";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_FNAME = "firstname";
    private static final String KEY_LNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ISACTIVE = "active";

    //keys for score information
    private static final String SHARED_PREF_SCORE_NAME = "mysharedprefscores";
    private static final String KEY_SCORE_ID  = "scoreid";
    private static final String KEY_SCORE_USERID  = "userid";
    private static final String KEY_SCORE_GAMEID  = "gameid";
    private static final String KEY_SCORE_HIGHEST_WORD_SCORE  = "highestwordscore";
    private static final String KEY_SCORE_HIGHEST_GAME_SCORE  = "highestgamescore";
    private static final String KEY_SCORE_LONGEST_WORD  = "longestword";
    private static final String KEY_SCORE_LONGEST_WORD_COUNT  = "highestwordscore";
    private static final String KEY_SCORE_TIME  = "SCORETIME";


    private SharedPrefManager(Context context){
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String nickname, String first_name, String last_name, String email, String is_active){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NICKNAME, nickname);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FNAME, first_name);
        editor.putString(KEY_LNAME, last_name);
        editor.putString(KEY_ISACTIVE, is_active);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EMAIL, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
        return true;
    }
    public int getID(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0);
    }
    public String getNickname(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NICKNAME, null);
    }
    public String getFname(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FNAME, null);
    }
    public String getLname(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LNAME, null);
    }
    public String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }
    public String getIsActive(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ISACTIVE, null);
    }
    public void updateEmail(String email){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    public void updateNickname(String nickname){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_NICKNAME, nickname);
        editor.apply();
    }
}
