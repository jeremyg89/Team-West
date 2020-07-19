package com.example.wordwizard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  GameResults extends AppCompatActivity {
    String longestWord = "";
    private static Integer bestScore;
    private static String bestScoringWord;
    public static ProgressDialog gameprogressDialog;
    TextView txtGameNumber;
    public static boolean gameStored = false;

    public static ArrayList<JSONObject> gridData = new ArrayList<JSONObject>();
    public static int gameNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        //find the longest word for this game
        longestWord = findLongestWord();
        TextView txtLongestWord = findViewById(R.id.txtLongestWord);
        txtLongestWord.setText(longestWord);

        //number of words used
        txtGameNumber = findViewById(R.id.txtGameNumber);
        //txtGameNumber.setText(SharedPrefManager.getInstance(GameResults.this).getGameNumber());

        //number of words used
        TextView txtUsedWords = findViewById(R.id.txtWordsFound);
        txtUsedWords.setText(Integer.toString(NewGame.usedwords.size()));

        //end score of the current game
        TextView txtScore = findViewById(R.id.txtWizardScore);
        txtScore.setText(Integer.toString(NewGame.TotalScore));

        //Get best scoring word
        findBestScoringWord();
        TextView txtBestScore = findViewById(R.id.txtBestScoringWord);
        txtBestScore.setText(bestScoringWord);

        //Get and display the average point values
        DecimalFormat df2 = new DecimalFormat("0.00");
        TextView txtAveragePoints = findViewById(R.id.txtWordAverage);
        Double tempDouble = findPointAverage();
        txtAveragePoints.setText(df2.format(tempDouble));

        gameprogressDialog = new ProgressDialog(this);

        NewGame x = new NewGame();
        x.storeGrids();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                    txtGameNumber.setText(Integer.toString(SharedPrefManager.getInstance(GameResults.this).getGameNumber()));
                    registerResults();
            }
        },500);
    }
    public void closeResults(View v){
        finish();
        startActivity(new Intent(GameResults.this, GameMenu.class));
    }
    private String findLongestWord(){
        int maxLength = 0;
        String longestString = null;
        for (String s : NewGame.usedwords) {
            if (s.length() > maxLength) {
                maxLength = s.length();
                longestString = s;
            }
        }
        return longestString;
    }
    private void findBestScoringWord(){
        int pointValue = 0;
        String bestScoringString = null;
        for (int i = 0; i < NewGame.wordScores.size(); i++){
            if (NewGame.wordScores.get(i) > pointValue)
            {
                pointValue = NewGame.wordScores.get(i);
                bestScore = NewGame.wordScores.get(i);
                bestScoringWord = NewGame.usedwords.get(i);
            }
        }
    }
    private double findPointAverage(){
        double totalPoints = 0;
        double totalWords = 0;
        DecimalFormat df2 = new DecimalFormat("#.##");

        for(int i = 0; i < NewGame.wordScores.size();i++){
            totalPoints = totalPoints + NewGame.wordScores.get(i);
            totalWords = totalWords + 1;
        }
        return (totalPoints / totalWords);
    }
    public void yourWords(View v){
        Intent i = new Intent(GameResults.this, YourWords.class);
        startActivity(i);
    }
    public void gameTopResults(View v){
        Intent i = new Intent(GameResults.this, GameTopResults.class);
        startActivity(i);
    }
    public void registerResults() {
        if (gameStored == false) {
            final String userID = Integer.toString(SharedPrefManager.getInstance(getApplicationContext()).getID());
            final String gameID = Integer.toString(SharedPrefManager.getInstance(getApplicationContext()).getGameNumber());
            final String highest_word_score = Integer.toString(bestScore);
            final String highest_game_score = Integer.toString(NewGame.TotalScore);
            final String longest_word = findLongestWord();
            final String longest_word_count = Integer.toString(NewGame.usedwords.size());
            final String word_average = Double.toString(findPointAverage());

            //final String IsActive = editTextIsactive.getText().toString().trim();

            gameprogressDialog.setMessage("Registering Results...");
            gameprogressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERT_SCORE_DATA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            gameprogressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                gameStored = true;
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            gameprogressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @NotNull
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userID", userID);
                    params.put("gameID", gameID);
                    params.put("highest_word_score", highest_word_score);
                    params.put("highest_game_score", highest_game_score);
                    params.put("longest_word", longest_word);
                    params.put("longest_word_count", longest_word_count);
                    params.put("word_average", word_average);
                    return params;
                }
            };
            RequestHandler.getInstance(this).clearRequestQueue();
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
