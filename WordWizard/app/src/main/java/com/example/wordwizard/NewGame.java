package com.example.wordwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewGame extends AppCompatActivity implements View.OnClickListener {
    private static Activity activity;
    private ProgressDialog progressDialog;
    //string array to hold the random letters
    public static String[] randomLetters = new String[16];
    private static final int NUM_ROWS = 4;
    private static final int NUM_COL = 4;

    //used to store the generate word
    public static String word = "";
    public static ArrayList<String> usedwords = new ArrayList<String>();
    public static ArrayList<Integer> wordScores = new ArrayList<Integer>();


    //Score
    public static int TotalScore = 0;

    //current word points
    static int currentPoints;

    //used to check current count of words
    int wordCount = 0;

    //used to track users clicks
    int click = 0;

    public static TextView currentWord;
    public static TextView currentTimer;
    public static TextView currentScore;

    //string variables to store the grids
    public static String grid1 = "";
    public static String grid2 = "";
    public static String grid3 = "";

    //arraylist to store the game information
    public static ArrayList<JSONObject> gameData = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //calls a blank word to initiate the API to speed up the gameplay
        new WordCheck().wordCheck("");
        setContentView(R.layout.activity_new_game);
        //assign display TextView
        currentWord = findViewById(R.id.currentWord);
        currentScore = findViewById(R.id.currentScore);
        currentTimer = findViewById(R.id.currentTime);

        GameResults.gameStored = false;
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.newGameLayout);
        relativeLayout.setOnClickListener(this);

        //reset the storage variables
        TotalScore = 0;
        wordCount = 0;
        currentPoints = 0;
        word = "";
        usedwords.clear();
        wordScores.clear();
        grid1 = "";
        grid2 = "";
        grid3 = "";

        //popup Menu button
        ImageButton btnMenu = (ImageButton) findViewById(R.id.img_View);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMenu) {
                Intent i = new Intent(getApplicationContext(), PopUp.class);
                new Timer(NewGame.this).pauseClock();
                startActivity(i);
            }
        });

        //this creates an onclick listener for the xout image to return to the start menu
        /*ImageButton imgReset = (ImageButton) findViewById(R.id.imgGameXout);
        imgReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vReset){
                resetGame();
            }
        });*/
        populateGrid();

        //store the first grid

        for (int i = 0; i < randomLetters.length; i++)
        {
            grid1 = grid1 + randomLetters[i];
        }
        grid1.trim();
        //start the timer
        progressDialog = new ProgressDialog(this);
        //GameResults.gameprogressDialog = new ProgressDialog(this);
        new Timer(NewGame.this).createClock();
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
                    if (wordCount <= 16) {
                        //add the letter to the word
                        if (v.getId() <= 16) { // only allows a letter to be added, if the id isn't outside of the button id's it will not do anything
                            word = word + randomLetters[v.getId()];
                            //disables the button selected
                            v.setEnabled(false);
                            v.setBackgroundResource(R.drawable.ic_ww_field_tile_b);
                            wordCount++;
                            //display the current letter selection
                            currentWord.setText(word);
                        }
                    }
                }
                //if a double click is detected the word will be checked using the API
                else if (click == 2) {
                    if (wordCount < 3) {
                        NewGame.currentWord.setText("Too short. Must be at least 3 letters!");
                    } else {
                        wordCount = 0;
                        //check if the word is real or not
                        new WordCheck().wordCheck(word);
                        //loop to re-enable all the disabled buttons
                        for (int i = 0; i < 16; i++) {
                            Button btn = findViewById(i);
                            btn.setEnabled(true);
                            btn.setBackgroundResource(R.drawable.ic_ww_field_tile_a);
                        }
                    }
                }else{
                    click = 0;
                }
                    click = 0;

            }
        }, 171);

    }

    private void populateGrid() {
        //grabs the random letters
        letterGenerator.letterGen(randomLetters);

        //fine the table layout on new game activity
        TableLayout table = (TableLayout) findViewById(R.id.tableGrid);
        //on click listener used to get the users word submission
        table.setOnClickListener(this);
        int count = 0;
        //loop to add four rows to the table
        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableROW = new TableRow(this);
            tableROW.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.01f));
            table.addView(tableROW);
            //loop to add four columns to the table
            for (int col = 0; col < NUM_COL; col++) {

                //adds a button in each x,y of the table
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.01f));
                button.setId(count);
                button.setText(randomLetters[count].toString());
                button.setBackgroundResource(R.drawable.ic_ww_field_tile_a);
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(this);
                count++;
                tableROW.addView(button);
            }
        }
    }

    public static void scoreCheck(String s) {
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
        Score = Score + word.length();
        wordScores.add(Score);
        //add the new score onto the total score
        TotalScore = TotalScore + Score;
        currentPoints = Score;
    }

    public void resetGame() {
        //reset the values
        finish();
        TotalScore = 0;
        word = "";
        currentWord.setText(word);
        currentScore.setText(Integer.toString(TotalScore));
        //loop to re-enable all the disabled buttons
        for (int i = 0; i < 16; i++) {
            Button btn = findViewById(i);
            btn.setEnabled(true);
            btn.setBackgroundResource(R.drawable.ic_ww_btn_grid_a);
        }

        new Timer(NewGame.this).stopClock();
        //close the current activity
        //finish();
    }

    public void closeGame(View v) {
        //end current activity
        finish();
        //stop timer
        new Timer(NewGame.this).stopClock();
        //create transition intent from this activty back to the game menu
        Intent i = new Intent(NewGame.this, GameMenu.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void changeRow(Activity m) {
        //clear the content prior to resetting
        word = "";
        wordCount = 0;
        currentWord.setText(word);
        for (int i = 0; i < 16; i++) {
            Button btn = m.findViewById(i);
            btn.setEnabled(true);
            btn.setBackgroundResource(R.drawable.ic_ww_btn_grid_a);
        }
        //change the row
        letterScramble x = new letterScramble();
        int line = x.getLine();
        int i = 0;
        Button btn;

        switch (line) {
            case 1:
                i = 0;
                for (int z = 0; z < 4; z++) {
                    btn = m.findViewById(i);
                    randomLetters[i] = Character.toString(x.getChar());
                    btn.setText(randomLetters[i].toString());
                    i++;
                }
                break;
            case 2:
                i = 3;
                for (int z = 0; z < 4; z++) {
                    btn = m.findViewById(i);
                    randomLetters[i] = Character.toString(x.getChar());
                    btn.setText(randomLetters[i].toString());
                    i++;
                }
                break;
            case 3:
                i = 8;
                for (int z = 0; z < 4; z++) {
                    btn = m.findViewById(i);
                    randomLetters[i] = Character.toString(x.getChar());
                    btn.setText(randomLetters[i].toString());
                    i++;
                }
                break;
            case 4:
                i = 12;
                for (int z = 0; z < 4; z++) {
                    btn = m.findViewById(i);
                    randomLetters[i] = Character.toString(x.getChar());
                    btn.setText(randomLetters[i].toString());
                    i++;
                }
                break;
        }
        if (grid2 == ""){
            for (int z = 0; z < randomLetters.length; z++)
            {
                grid2 = grid2 + randomLetters[z];
            }
        }
        else if (grid3 == "")
        {
            for (int z = 0; z < randomLetters.length; z++)
            {
                grid3 = grid3 + randomLetters[z];
            }
        }
    }
    public void storeGrids(){
        if(grid1 != "" && grid2 != "" && grid3 != "")
        {
            gameData.clear();
            //GameResults.gameprogressDialog.setMessage("Adding game data in...");
            //GameResults.gameprogressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GRID_CHECK,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("message").contains("Game successfully created"))
                                {
                                    //GameResults.gameprogressDialog.setMessage("Getting game data in...");
                                    //GameResults.gameprogressDialog.show();
                                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Constants.GRID_CHECK,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        if (!jsonObject.getBoolean("error")) {
                                                            SharedPrefManager.getInstance(NewGame.this).gameNumber(
                                                                    jsonObject.getInt("Game_Number")
                                                            );
                                                            //GameResults.gameprogressDialog.dismiss();
                                                        }
                                                    }
                                                    catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //GameResults.gameprogressDialog.dismiss();
                                                }
                                            }) {
                                        @NotNull
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("First_Grid_Letters", grid1);
                                            params.put("Second_Grid_Letters", grid2);
                                            params.put("Third_Grid_Letters", grid3);
                                            return params;
                                        }
                                    };
                                    RequestHandler.getInstance(NewGame.this).clearRequestQueue();
                                    RequestHandler.getInstance(NewGame.this).addToRequestQueue(stringRequest1);
                                }else {
                                    if (!jsonObject.getBoolean("error")) {
                                        SharedPrefManager.getInstance(NewGame.this).gameNumber(
                                                jsonObject.getInt("Game_Number")
                                        );
                                    }
                                }
                                }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //GameResults.gameprogressDialog.dismiss();
                            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @NotNull
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("First_Grid_Letters", grid1);
                    params.put("Second_Grid_Letters", grid2);
                    params.put("Third_Grid_Letters", grid3);
                    return params;
                }
            };
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);*/
            RequestHandler.getInstance(this).clearRequestQueue();
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}

/*    //checks if random letters exists in grid table
 //   public void checkGrid(Activity m){
 //       String Grid1 = randomLetters.toString();
 //       String Grid2 = grid2;
   //     StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.GRID_CHECK,
   //             new Response.Listener<String>() {
   //                 @Override
   //                 public void onResponse(String response) {
   //                     try {
  //                          JSONObject jsonObject = new JSONObject(response);
  //                          Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
 //                       } catch (JSONException e) {
  //                          e.printStackTrace();
 //                       }
 //                   }
 //               },
 //               new Response.ErrorListener() {
  //                  @Override
  //                  public void onErrorResponse(VolleyError error) {
 //                         Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
 //                   }
 //               }) {
 //           @NotNull
 //           @Override
  //          protected Map<String, String> getParams() throws AuthFailureError {
   //             Map<String, String> params = new HashMap<>();
  //              params.put("First_Grid_Letters", Grid1);
  //              params.put("Second_Grid_Letters", Grid2);
  //              params.put("Third_Grid_Letters", Grid3);
  //              return params;
//            }
 //       };
  //      RequestQueue requestQueue = Volley.newRequestQueue(this);
  //      requestQueue.add(stringRequest);
//   }
}
 */