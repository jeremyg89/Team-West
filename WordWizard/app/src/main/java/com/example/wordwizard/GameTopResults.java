package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameTopResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_topresults);

        TextView txGameNumber = (TextView) findViewById(R.id.txtTopResultsGameNumber);
        txGameNumber.setText(Integer.toString(SharedPrefManager.getInstance(getApplicationContext()).getGameNumber()));
    }
    public void newGame(View v){
        Intent i = new Intent(GameTopResults.this, NewGame.class);
        startActivity(i);
    }
    public void play(View v){
        Intent i = new Intent(GameTopResults.this, NewGame.class);
        startActivity(i);
    }
    public void closeTopResults(View v){
        Intent i = new Intent(GameTopResults.this, GameMenu.class);
        startActivity(i);
        finish();
    }
}
