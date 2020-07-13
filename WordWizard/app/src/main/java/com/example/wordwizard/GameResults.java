package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GameResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);
    }
    public void closeResults(View v){
        finish();
        startActivity(new Intent(GameResults.this, GameMenu.class));
    }
}
