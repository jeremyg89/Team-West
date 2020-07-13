package com.example.wordwizard;

import android.widget.Button;

import androidx.annotation.ContentView;

import java.util.Random;

public class letterScramble<line> {
    int line = 0;
    char l;

    //generates random number between 1-4 to choose row


    public int getLine() {
        int min = 1;
        int max = 4;
        int line = (int) ((Math.random() * ((max - min) + 1)) + min);
        return line;
    }

    public char getChar() {
        int min = 65;
        int max = 90;
        int letter = (int) ((Math.random() * ((max - min) + 1)) + min);
        char l = (char) letter;
        return l;
    }

   /* public void chooseButton() {

        switch (line) {
            case 1:
                NewGame.btn(1);
                break;
            case 2:
                NewGame.btn(4);
                break;
            case 3:
                NewGame.btn(9);
                break;
            case 4:
                NewGame.btn(13);
                break;


            for (int i = 0; i < 4; i++) {
                int x = l;
                NewGame.btn(x);
                x++;
            }
        }
    }*/
}
