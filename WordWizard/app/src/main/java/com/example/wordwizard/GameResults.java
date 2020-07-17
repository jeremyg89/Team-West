package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class GameResults extends AppCompatActivity {
    String longestWord = "";
    private static Integer bestScore;
    private static String bestScoringWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        //find the longest word for this game
        longestWord = findLongestWord();
        TextView txtLongestWord = findViewById(R.id.txtLongestWord);
        txtLongestWord.setText(longestWord);

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
        TextView txtAveragePoints = findViewById(R.id.txtWordAverage);
        txtAveragePoints.setText(Double.toString(findPointAverage()));
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
}
