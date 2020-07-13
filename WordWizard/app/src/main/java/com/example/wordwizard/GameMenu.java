package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GameMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }
    public void newGame(View v){
        //calls the NewGame activity
        startActivity(new Intent(GameMenu.this, NewGame.class));
    }
    public void closeMenu(View v){
        finish();
        startActivity(new Intent(GameMenu.this, MainActivity.class));
    }
}
