package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}
public void newGame(View v){
    //calls the NewGame activity
    startActivity(new Intent(MainActivity.this, NewGame.class));
}
public void registerUser(View v){
    startActivity(new Intent(MainActivity.this, RegisterUser.class));
}}
