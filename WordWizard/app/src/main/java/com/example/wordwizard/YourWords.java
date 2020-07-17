package com.example.wordwizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class YourWords extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_words);
        populateList();
    }
    public void populateList(){
        //Declaration part
        ArrayAdapter<String> adapter;
        ListView lv  = findViewById(R.id.listWords);

        //arraylist Append
        adapter=new ArrayAdapter<String>(this,
                R.layout.row,
                NewGame.usedwords);
        lv.setAdapter(adapter);
    }
    public void closeYourWords(View v){
        Intent i = new Intent(YourWords.this, GameMenu.class);
        startActivity(i);
        finish();
    }
    public void wordsResults(View v){
        Intent i = new Intent(YourWords.this, GameResults.class);
        startActivity(i);
        finish();
    }
}
