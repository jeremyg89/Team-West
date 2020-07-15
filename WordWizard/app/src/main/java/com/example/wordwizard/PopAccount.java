package com.example.wordwizard;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PopAccount extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_account);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9), (int)(height*.7));

        TextView nickname = findViewById(R.id.txtAccountNickname);
        nickname.setText(PopSignIn.nickname.toString());

        TextView email = findViewById(R.id.txtAccountEmail);
        email.setText(PopSignIn.email.toString());

        TextView id = findViewById(R.id.txtUserId);
        id.setText(Integer.toString(PopSignIn.id));
    }
}
