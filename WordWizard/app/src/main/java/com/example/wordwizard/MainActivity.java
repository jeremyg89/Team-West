package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}
public void enterGame(View v){
    //calls the NewGame activity
    startActivity(new Intent(MainActivity.this, GameMenu.class));
}
public void registerUser(View v){
            Intent i = new Intent(getApplicationContext(), RegisterUser.class);
            startActivity(i);
    //startActivity(new Intent(MainActivity.this, RegisterUser.class));
}
public void closeGame(View v){
    finish();
    /*Intent homeIntent = new Intent(Intent.ACTION_MAIN);
    homeIntent.addCategory( Intent.CATEGORY_HOME );
    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(homeIntent);*/
    //finish();
    System.exit(0);
    ActivityCompat.finishAffinity(this);
}
}
