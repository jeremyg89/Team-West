package com.example.wordwizard;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.util.Arrays;

public class letterGenerator{

    //public static void main (String[] args){
    public static String[] letterGen(String[] z ){

        //set number of items
        int[] num = new int[16];

        //set min item equal to lowest char number in ascii
        int min = 65;
        int max = 90;
        int vowels = 0;

        for (int i = 0; i < num.length; i++){
            int j = 0;
            j = (int) ((Math.random()*((max-min)+1))+min);
            num[i] = j;

            if (j == 65||j==69 || j==73 || j==79 ||j==85) {
                vowels ++;
            }

            char c = (char)j;
            //added to test the code Hailey added
            z[i] = Character.toString(c);

            //System.out.println(c);
        }
        if (vowels<2) {
            letterGen(z);
        }
        //System.out.println("There are " + vowels + " vowels.");
        //System.out.println(Arrays.toString(num));
        //System.out.println(num.length);
        return z;
    }
}
