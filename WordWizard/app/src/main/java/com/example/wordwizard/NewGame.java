package com.example.wordwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class CreateGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }
    public void gridGen(){
        String id = "btn1";
        //the layout on which you are working
            LinearLayout layout = (LinearLayout) findViewById(R.id.row1);
        //set the properties for button
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Button");
        btnTag.setId(Integer.parseInt(id));

        //add button to the layout
        layout.addView(btnTag);

    }
}