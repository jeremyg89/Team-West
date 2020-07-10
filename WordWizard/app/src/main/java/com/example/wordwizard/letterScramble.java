package com.example.wordwizard;

import java.util.Random;

public class letterScramble<line> {

    //generates random number between 1-4 to choose row
    int min = 1;
    int max = 4;

    int line = (int) ((Math.random() * ((max - min) + 1)) + min);

    public int getLine() {
        return line;
    }

    for (int i=0; i< 4; i++){
        getLine();
        NewGame.row = line;
        NewGame.col = 0;
        int min =65;
        int max =90;
        int letter = (int) ((Math.random() * ((max - min) + 1)) + min);
        char l = (char)letter;
        NewGame.col = col++;
    }

    }


}
