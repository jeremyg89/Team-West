package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    //if (PopSignIn.id != 0) {
    if(SharedPrefManager.getInstance(this).isLoggedIn()){
        startActivity(new Intent(MainActivity.this, GameMenu.class));
    }else{
        Toast.makeText(getApplicationContext(), "Please sign in or register an account.", Toast.LENGTH_LONG).show();
    }
}
public void signIn(View v){
            Intent i = new Intent(getApplicationContext(), PopSignIn.class);
            startActivity(i);
            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
    //startActivity(new Intent(MainActivity.this, RegisterUser.class));
}
public void closeApp(View v){
    finish();
    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
    homeIntent.addCategory( Intent.CATEGORY_HOME );
    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(homeIntent);
    System.exit(0);
}
}
