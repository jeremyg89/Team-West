package com.example.wordwizard;

import java.util.Arrays;

public class letterGenerator {

    //public static void main (String[] args){
    public static void letterGen(){
        int[] num = new int[16];
        int min = 65;
        int max = 90;
        int vowels = 0;
        //added to test the code Hailey added
        String test = "";
        for (int i = 0; i < num.length; i++){
            int j = 0;
            j = (int) ((Math.random()*((max-min)+1))+min);
            num[i] = j;

            if (j == 65||j==69 || j==73 || j==79 ||j==85) {
                vowels ++;
            }

            char c = (char)j;
            //added to test the code Hailey added
            test = test + c;
            //System.out.println(c);
        }
        if (vowels<2) {
            //System.out.println("Not enough vowels");
        }
        //System.out.println("There are " + vowels + " vowels.");
        //System.out.println(Arrays.toString(num));
        //System.out.println(num.length);
        MainActivity.dataView.setText(test);
    }
}
