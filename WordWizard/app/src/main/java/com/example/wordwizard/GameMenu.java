package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
    }
    public void newGame(View v){
        //calls the NewGame activity
            startActivity(new Intent(GameMenu.this, GameAffirmation.class));
    }
    public void closeMenu(View v){
        finish();
        startActivity(new Intent(GameMenu.this, MainActivity.class));
    }
    public void menuAccount(View v){
        startActivity(new Intent(GameMenu.this, PopAccount.class));
    }
    public void menuTutorial(View v){
        startActivity(new Intent(GameMenu.this, PopTutorial.class));
    }
}
