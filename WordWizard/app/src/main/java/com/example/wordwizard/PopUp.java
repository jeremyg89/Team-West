package com.example.wordwizard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class PopUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        //create the display menu
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int) (height*.7));

        //create buttons
        Button btnReset = (Button) findViewById(R.id.btnReset);
        Button btnNewGame = (Button) findViewById(R.id.btnNewG);
        Button btnMenuClose = (Button) findViewById(R.id.btnMenuClose);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMenuRest) {
                new NewGame().closeGame();
                new Timer().createClock();
                finish();
            }
        });
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMenuNew) {
                Intent i = new Intent(PopUp.this, NewGame.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                new Timer().stopClock();
                //finish();
                startActivity(i);
            }
        });
        btnMenuClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMenuClose) {
                new Timer().createClock();
                finish();
            }
        });


    }
}
