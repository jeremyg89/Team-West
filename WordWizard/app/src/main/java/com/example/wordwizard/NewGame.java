package com.example.wordwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

    //Score
    static int TotalScore = 0;
    TextView scoreView;

    //current word points
    static int currentPoints;

    //used to check current count of words
    int wordCount = 0;

    //used to track users clicks
    int click = 0;

    public static TextView currentWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //calls a blank word to initiate the API to speed up the gameplay
        new WordCheck().wordCheck("");
        setContentView(R.layout.activity_new_game);
        //assign display TextView
        currentWord = findViewById(R.id.currentWord);
        scoreView = findViewById(R.id.currentScore);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.newGameLayout);
        relativeLayout.setOnClickListener(this);

        //this creates an onclick listener for the xout image to return to the start menu
        ImageButton btnReset = (ImageButton) findViewById(R.id.img_xout);
        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //changes the active activity view to MainActivity
                startActivity(new Intent(NewGame.this, MainActivity.class));
                //reset the values
                TotalScore = 0;
                word = "";
            }
        });
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
                        if (v.getId() <= 16) { // only allows a letter to be added, if the id isn't outside of the button id's it will not do anything
                            word = word + randomLetters[v.getId()];
                            //disables the button selected
                            v.setEnabled(false);
                            v.setBackgroundResource(R.drawable.ic_ww_btn_grid_b);
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
                        NewGame.currentWord.setText("Too short. Must be at least 3 letters!" );
                    }
                    else {
                        wordCount = 0;
                        //check if the word is real or not
                        new WordCheck().wordCheck(word);
                        //loop to re-enable all the disabled buttons
                        for (int i = 0; i < 16; i++){
                            Button btn = findViewById(i);
                            btn.setEnabled(true);
                            btn.setBackgroundResource(R.drawable.ic_ww_btn_grid_a);
                        }
                    }

                }
                click = 0;
            }
        }, 200);

    }
    private void populateGrid(){
        //grabs the random letters
        letterGenerator.letterGen(randomLetters);
        //fine the table layout on new game activity
        TableLayout table = (TableLayout) findViewById(R.id.tableGrid);
        //on click listener used to get the users word submission
        table.setOnClickListener(this);
        int count = 0;
        //loop to add four rows to the table
        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableROW = new TableRow(this);
            tableROW.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.01f));
            table.addView(tableROW);
            //loop to add four columns to the table
            for (int col = 0; col < NUM_COL; col++){

                //adds a button in each x,y of the table
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.01f));
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
    public static void scoreCheck(String s){
        //create an array that is filled with the characters of the correct word
        char[] charArray = NewGame.word.toCharArray();
        int Score = 0;

        //the letter broken up by point value
        String pointValue1 = "ADEHILNORSTU";
        String pointValue2 = "BCFGMPWY";
        String pointValue3 = "KY";
        String pointValue4 = "JQZX";

        //loop to determine the value of the letter
        for (int i = 0; i < charArray.length; i++) {
            if (pointValue1.contains(Character.toString(charArray[i]))) {
                Score = Score + 1;
            } else if (pointValue2.contains(Character.toString(charArray[i]))) {
                Score = Score + 2;
            } else if (pointValue3.contains(Character.toString(charArray[i]))) {
                Score = Score + 3;
            } else if (pointValue4.contains(Character.toString(charArray[i]))) {
                Score = Score + 4;
            }
        }
        //add the new score onto the total score
        TotalScore = TotalScore + Score;
        currentPoints = Score;
    }
}