package com.example.wordwizard;

public class gameCounter {

    int nextGame =1;
    public int currentGame() {
        int i = nextGame++;
        i++;
        nextGame = i;
        return nextGame;

    }

}
