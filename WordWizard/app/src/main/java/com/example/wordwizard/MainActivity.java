package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String[] randomLetters = new String[16];
    private static final int NUM_ROWS = 4;
    private static final int NUM_COL = 4;
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
}
public void newGame(View v){
    //calls the NewGame activity
    startActivity(new Intent(MainActivity.this, NewGame.class));
}
}
