package com.example.wordwizard;

import android.os.AsyncTask;
import android.os.Bundle;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class DictionaryAPI extends AsyncTask<String, Integer, String> {

    //code to connect to the Webster API
    @Override
    protected String doInBackground(String... params) {

        try{
            //create a URL using the passed parameters
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //create the bufferReader and StringBuilder to gather the information from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;

            //Loop to write the information pulled from the API connection formatted in JSON
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    //code to connect to the Oxford API
    /*
    @Override
    protected String doInBackground(String... params) {
        final String appID = "01480e9c";
        final String appKey = "1da9051e74233120c76d09e3ae2e7c30";

        try{
            //create a URL using the passed parameters
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //authorize your connection request to the URL
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("app_id", appID);
            conn.setRequestProperty("app_key", appKey);

            //create the bufferReader and StringBuilder to gather the information from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;

            //Loop to write the information pulled from the API connection formatted in JSON
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
*/
    //code for the results of the word check using the Webster API
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        //write the results to the textview will later just show that the word is not real
        if (result.contains("[{\"meta\":"))
        {
            NewGame.currentWord.setText(NewGame.word + " is a real word. Points Points Points.");
            NewGame.word = "";
        }
        else
        {
            NewGame.currentWord.setText("Not a real word.");
            NewGame.word = "";
        }
    }
    //code for the results of the word check using the Oxford API
    /*
   @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        //write the results to the textview will later just show that the word is not real
        if (result.contains("java.io.FileNotFoundException: https://od-api.oxforddictionaries.com/api/v2/lemmas/en/"))
        {
            NewGame.currentWord.setText("Not a real word.");
            NewGame.word = "";
        }
        else
        {
            NewGame.currentWord.setText(NewGame.word + " is a real word. Points Points Points.");
            NewGame.word = "";
        }
    }

     */
}

