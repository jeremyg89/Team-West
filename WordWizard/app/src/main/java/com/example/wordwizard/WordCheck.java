package com.example.wordwizard;

public class WordCheck {
    //Connects to the api with the generated word and verifies if it exists.
    public void wordCheck(String word){
        new DictionaryAPI().execute(apiURL(word));
    }
    public String apiURL(String word)
    {
        //information to create the connection parameters for the API
        final String language = "en-us";
        final String word_id = word.toLowerCase();
        //return "https://od-api.oxforddictionaries.com:443/api/v2/lemmas/" + language + "/" + word_id;
        return "https://www.dictionaryapi.com/api/v3/references/collegiate/json/" + word_id + "?key=e3d1bad4-26de-40ba-beec-5cd0cad991db";
    }
}
