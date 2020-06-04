package com.example.wordwizard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //created to test connection to API
    public static TextView dataView;
    Button click;

@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //created to test connection to API
    click = findViewById(R.id.btnTest);
    dataView = findViewById(R.id.tvData);

    click.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            letterGenerator.letterGen();
            //new Oxford().execute(dictionaryEntries());
        }
    });
}
private String dictionaryEntries(){
    //information to create the connection parameters for the API
    final String language = "en-us";
    final String word = "zzs";
    final String word_id = word.toLowerCase();
    return "https://od-api.oxforddictionaries.com:443/api/v2/lemmas/" + language + "/" + word_id;
}
}
