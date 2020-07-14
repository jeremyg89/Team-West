package com.example.wordwizard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class PopSignIn extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_signin);

        //call to change the attributes of the window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams wlp = getWindow().getAttributes();

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //align the window to the top of the screen
        wlp.gravity = Gravity.TOP;
        //set the window dimensions
        getWindow().setLayout((int)(width), (int)(height*.45));
    }
    public void registerUser(View v){
        Intent i = new Intent(getApplicationContext(), RegisterUser.class);
        startActivity(i);
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
        //startActivity(new Intent(MainActivity.this, RegisterUser.class));
    }
}
