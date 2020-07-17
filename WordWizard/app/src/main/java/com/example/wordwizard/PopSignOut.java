package com.example.wordwizard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class PopSignOut extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_signout);

        //call to change the attributes of the window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams wlp = getWindow().getAttributes();

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //align the window to the top of the screen
        wlp.gravity = Gravity.TOP;
        //set the window dimensions
        getWindow().setLayout((int) (width), (int) (height * .3));
    }
    public void signOut(View v){
        SharedPrefManager.getInstance(this).logout();
        Intent i = new Intent(PopSignOut.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
