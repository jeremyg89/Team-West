package com.example.wordwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class NewGame extends AppCompatActivity implements View.OnClickListener{
    //string array to hold the random letters
    String[] randomLetters = new String[16];
    private static final int NUM_ROWS = 4;
    private static final int NUM_COL = 4;
    //used to store the generate word
    public static String word = "";

    //used to check current count of words
    int wordCount = 0;
    //used to track users clicks
    int click = 0;

    public static TextView currentWord;

    boolean doubleTap = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        //assign display TextView
        currentWord = findViewById(R.id.currentWord);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.newGameLayout);
        relativeLayout.setOnClickListener(this);
        populateGrid();
    }
    @Override
    public void onClick(final View v) {
        click++;
        //new handler used to check if the button has been clicked twice
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                //if the button has been clicked once it will add the letter to the chain
                if (click == 1) {
                    if (wordCount <= 16 ) {
                        //add the letter to the word
                        if (v.getId() <= 16) {
                            word = word + randomLetters[v.getId()];
                            v.setEnabled(false);
                            wordCount++;
                            //display the current letter selection
                            currentWord.setText(word);
                        }
                    }
                }
                //if a double click is detected the word will be checked using the API
                else if (click == 2)
                {
                    if (wordCount < 3)
                    {
                    }
                    else {
                        wordCount = 0;
                        new WordCheck().wordCheck(word);
                        for (int i = 0; i < 16; i++){
                            Button btn = findViewById(i);
                            btn.setEnabled(true);
                        }
                    }
                }
                click = 0;
            }
        }, 200);

    }
    private void populateGrid(){
        letterGenerator.letterGen(randomLetters);
        TableLayout table = (TableLayout) findViewById(R.id.tableGrid);
        table.setOnClickListener(this);
        int count = 0;
        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableROW = new TableRow(this);
            tableROW.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            table.addView(tableROW);
            for (int col = 0; col < NUM_COL; col++){

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
                button.setId(count);
                button.setText(randomLetters[count].toString());
                button.setBackgroundResource(R.drawable.ic_ww_btn_grid_a);
                button.setPadding(0,0,0,0);
                button.setOnClickListener(this);
                count++;
                tableROW.addView(button);
            }
        }
    }
}