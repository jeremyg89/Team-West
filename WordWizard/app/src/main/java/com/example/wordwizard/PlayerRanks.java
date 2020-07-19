package com.example.wordwizard;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class PlayerRanks extends AppCompatActivity implements View.OnClickListener {
    private Button btnPlayer_Ranks, btnPlayer_Leaderboard, btnPlayer_LastMonth, btnPlayer_ThisMonth, btnPlayer_AllTime, btnPlayer_TotalScore, btnPlayer_TopScore, btnPlayer_WordLenght, btnPlayer_WordValue, btnPlayer_WordCount, btnPlayer_WordAverage;
    private ProgressDialog progressDialog;
    private ListView listResults;
    private TextView txtTimeLeft;
    private static ArrayList<JSONObject> users = new ArrayList<JSONObject>();
    private JSONArray jsonArray;
    private String  sqlStartDate, sqlEndDate;
    private int thisMonth, thisYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_ranks);

        //get buttons
        btnPlayer_Ranks = findViewById(R.id.btnPlayerRanks);
        btnPlayer_Ranks.setOnClickListener(this);
        btnPlayer_Leaderboard = findViewById(R.id.btnLeaderboard);
        btnPlayer_Leaderboard.setOnClickListener(this);
        btnPlayer_LastMonth = findViewById(R.id.btnLastMonth);
        btnPlayer_LastMonth.setOnClickListener(this);
        btnPlayer_ThisMonth = findViewById(R.id.btnThisMonth);
        btnPlayer_ThisMonth.setOnClickListener(this);
        btnPlayer_AllTime = findViewById(R.id.btnAllTime);
        btnPlayer_AllTime.setOnClickListener(this);
        btnPlayer_TotalScore = findViewById(R.id.btnTotalScore);
        btnPlayer_TotalScore.setOnClickListener(this);
        btnPlayer_TopScore = findViewById(R.id.btnTopScore);
        btnPlayer_TopScore.setOnClickListener(this);
        btnPlayer_WordLenght = findViewById(R.id.btnWordLength);
        btnPlayer_WordLenght.setOnClickListener(this);
        btnPlayer_WordValue = findViewById(R.id.btnWordValue);
        btnPlayer_WordValue.setOnClickListener(this);
        btnPlayer_WordCount = findViewById(R.id.btnWordCount);
        btnPlayer_WordCount.setOnClickListener(this);
        btnPlayer_WordAverage = findViewById(R.id.btnWordAverage);
        btnPlayer_WordAverage.setOnClickListener(this);


        //get time left text
        txtTimeLeft = findViewById(R.id.txtTimeLeft);
        //create the dates to check and generate the CountDowntimer
        Calendar start_calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        thisMonth = start_calendar.get(Calendar.MONTH) + 1;
        thisYear = start_calendar.get(Calendar.YEAR);

        Calendar end_calendar = Calendar.getInstance();
        end_calendar.set(2020, 06, getDays(thisMonth)); // 10 = November, month start at 0 = January

        long start_millis = start_calendar.getTimeInMillis(); //get the start time in milliseconds
        long end_millis = end_calendar.getTimeInMillis(); //get the end time in milliseconds
        long total_millis = (end_millis - start_millis); //total time in milliseconds

        //1000 = 1 second interval
        CountDownTimer cdt = new CountDownTimer(total_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                txtTimeLeft.setText(days + ":" + hours + ":" + minutes + ":" + seconds); //You can compute the millisUntilFinished on hours/minutes/seconds
            }

            @Override
            public void onFinish() {
                txtTimeLeft.setText("Finish!");
            }
        };
        cdt.start();




        //get display list
        listResults = findViewById(R.id.listPlayerResults);

        progressDialog = new ProgressDialog(this);

        //getPlayerRanks("highest_word_score");
    }
    public void getPlayerRanks(final String orderby) {

        progressDialog.setMessage("Loading scores...");
        progressDialog.show();

        if(sqlStartDate != null && sqlEndDate != null)
        {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SCORE_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                    JSONObject x = jsonArray.getJSONObject(i);
                                    x.getInt("id");
                                    x.getInt("userID");
                                    x.getInt("gameID");
                                    x.getInt("highest_word_score");
                                    x.getInt("highest_game_score");
                                    x.getString("longest_word");
                                    x.getInt("longest_word_count");
                                    x.getDouble("word_average");
                                    x.getString("time");
                                    x.getString("nickname");
                                    users.add(x);
                                    if(i == jsonArray.length() - 1)
                                    {
                                        loadList(orderby);
                                    }
                                }
                            if(!jsonArray.getJSONObject(0).getBoolean("error")){
                            }else{
                                Toast.makeText(getApplicationContext(), jsonArray.getJSONObject(0).getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("order", orderby);
                params.put("startdate", sqlStartDate);
                params.put("enddate", sqlEndDate);
                return params;
            }
        };
        users.clear();
        RequestHandler.getInstance(this).clearRequestQueue();
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }else{
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Please select Last Month, This Month, or All Time", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnPlayer_Ranks){
            btnPlayer_Ranks.setBackgroundColor(Color.parseColor("#860f12"));
            btnPlayer_Leaderboard.setBackgroundColor(Color.parseColor("#c7972e"));
        }else if (view == btnPlayer_Leaderboard){
            btnPlayer_Leaderboard.setBackgroundColor(Color.parseColor("#860f12"));
            btnPlayer_Ranks.setBackgroundColor(Color.parseColor("#c7972e"));
        }else if (view == btnPlayer_LastMonth){
            //math to get the date search variables
            sqlStartDate =  thisYear + "-" + (thisMonth - 1) + "-01";
            sqlEndDate = thisYear + "-" + (thisMonth - 1) + "-" + getDays(thisMonth - 1);
            btnPlayer_LastMonth.setBackgroundColor(Color.parseColor("#860f12"));
            btnPlayer_AllTime.setBackgroundColor(Color.parseColor("#c7972e"));
            btnPlayer_ThisMonth.setBackgroundColor(Color.parseColor("#c7972e"));
        } else if (view == btnPlayer_ThisMonth){
            //math to get the date search variables
            sqlStartDate = thisYear + "-" + thisMonth + "-01";
            sqlEndDate = thisYear + "-" + thisMonth + "-" + getDays(thisMonth);
            btnPlayer_ThisMonth.setBackgroundColor(Color.parseColor("#860f12"));
            btnPlayer_AllTime.setBackgroundColor(Color.parseColor("#c7972e"));
            btnPlayer_LastMonth.setBackgroundColor(Color.parseColor("#c7972e"));

        }else if (view == btnPlayer_AllTime) {
            //date search variables
            sqlStartDate = "2000-01-01";
            sqlEndDate = "9999-12-31";
            btnPlayer_AllTime.setBackgroundColor(Color.parseColor("#860f12"));
            btnPlayer_ThisMonth.setBackgroundColor(Color.parseColor("#c7972e"));
            btnPlayer_LastMonth.setBackgroundColor(Color.parseColor("#c7972e"));
        }
        else if (view == btnPlayer_TopScore){
            changebuttoncolor();
            btnPlayer_TopScore.setBackgroundColor(Color.parseColor("#860f12"));
        }else if (view == btnPlayer_TotalScore){
            changebuttoncolor();
            btnPlayer_TotalScore.setBackgroundColor(Color.parseColor("#860f12"));
            getPlayerRanks("highest_game_score");
        }else if (view == btnPlayer_WordLenght){
            changebuttoncolor();
            btnPlayer_WordLenght.setBackgroundColor(Color.parseColor("#860f12"));
            getPlayerRanks("longest_word");
        }else if (view == btnPlayer_WordValue){
            changebuttoncolor();
            btnPlayer_WordValue.setBackgroundColor(Color.parseColor("#860f12"));
            getPlayerRanks("highest_word_score");
        }else if (view == btnPlayer_WordCount){
            changebuttoncolor();
            btnPlayer_WordCount.setBackgroundColor(Color.parseColor("#860f12"));
            getPlayerRanks("longest_word_count");
        }else if (view == btnPlayer_WordAverage){
            changebuttoncolor();
            btnPlayer_WordAverage.setBackgroundColor(Color.parseColor("#860f12"));
            getPlayerRanks("word_average");
        }
        else
        {

        }
    }
    public void changebuttoncolor(){
        btnPlayer_TopScore.setBackgroundColor(Color.parseColor("#3c4d87"));
        btnPlayer_TotalScore.setBackgroundColor(Color.parseColor("#3c4d87"));
        btnPlayer_WordLenght.setBackgroundColor(Color.parseColor("#3c4d87"));
        btnPlayer_WordValue.setBackgroundColor(Color.parseColor("#3c4d87"));
        btnPlayer_WordCount.setBackgroundColor(Color.parseColor("#3c4d87"));
        btnPlayer_WordAverage.setBackgroundColor(Color.parseColor("#3c4d87"));
    }
    public void loadList(String s){
            ArrayList<FillScoreList> rank = new ArrayList<FillScoreList>();
            try {
                DecimalFormat df2 = new DecimalFormat("#.##");
                Double tempDouble;
                rank.add(new FillScoreList("Rank", "Player", "Score"));
                for (int i = 0; i < users.size(); i++) {
                    if(s == "longest_word")
                    {
                        rank.add(new FillScoreList(Integer.toString(i + 1), users.get(i).getString("nickname"), users.get(i).getString(s)));
                    }else if(s == "word_average"){
                        tempDouble = users.get(i).getDouble(s);
                        rank.add(new FillScoreList(Integer.toString(i + 1), users.get(i).getString("nickname"), df2.format(tempDouble)));
                    }
                    else {
                        rank.add(new FillScoreList(Integer.toString(i + 1), users.get(i).getString("nickname"), Integer.toString(users.get(i).getInt(s))));
                    }
                }
                ScoreListAdapter adapter = new ScoreListAdapter(this, R.layout.row_layout, rank);
                listResults.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
    public int getDays(int i){
        switch (i){
            case 0:
                i = 31;
                break;
            case 1:
                i = 28;
                break;
            case 2:
                i = 31;
                break;
            case 3:
                i = 30;
                break;
            case 4:
                i = 31;
                break;
            case 5:
                i = 30;
                break;
            case 6:
                i = 31;
                break;
            case 7:
                i = 31;
                break;
            case 8:
                i = 30;
                break;
            case 9:
                i = 31;
                break;
            case 10:
                i = 30;
                break;
            case 11:
                i = 31;
                break;
        }
        return i;
    }
}
