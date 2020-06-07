package com.example.wordwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewGame extends AppCompatActivity implements View.OnClickListener{

    //string array to hold the random letters
    String[] randomLetters = new String[16];
    //used to store the generate word
    public static String word = "";

    //used to check current count of words
    int wordCount = 0;
    //used to track users clicks
    int click = 0;

    public static TextView currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        //assign display TextView
        currentWord = findViewById(R.id.currentWord);
        //generating the random letters
        letterGenerator.letterGen(randomLetters);
        int gridID;
        //loop to create and populate the buttons with random letters
        for (int i = 0; i < randomLetters.length; i++){
            //section of the grid to create the button
            gridID = getResources().getIdentifier("grid"+ (i + 1), "id", getPackageName());
            LinearLayout layout = (LinearLayout) findViewById(gridID);

            //creates the button it the selected grid location
            Button btnTag = new Button(this);
            btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            btnTag.setId(i);
            btnTag.setText(randomLetters[i]);
            layout.addView(btnTag);
            //creates a on click for each button created
            btnTag.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(final View v) {
        click++;
        //new handler used to check if the button has been clicked twice
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //if the button has been clicked once it will add the letter to the chain
                if (click == 1) {
                    if (wordCount <= 16) {
                        //add the letter to the word
                        word = word + randomLetters[v.getId()];
                        wordCount++;
                        //display the current letter selection
                        currentWord.setText(word);
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
                    }
                }
                click = 0;
            }
        }, 500);

    }
}