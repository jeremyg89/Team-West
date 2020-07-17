package com.example.wordwizard;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;
import java.util.Locale;

public class Timer extends AppCompatActivity {

    private static final long START_TIME_MILLIS = 20000;
    private static long TIME_LEFT_MILLIS = START_TIME_MILLIS;

    private static CountDownTimer countDownTimer;
    private static boolean timerRunning;
    private Activity mActivity;

    //variables for the changerow function
    private static int range1 = 120000;
    private static int range2 = 121000;

    public Timer(Activity activity){
        super();
        mActivity = activity;
    }

    public void pauseClock(){
        //cancels the timer, but the value is stored in the TIME_LEFT_MILLIS variable
        countDownTimer.cancel();
        //state that the timer is not running
        timerRunning = false;
    }
    public void createClock(){
        if (timerRunning){
            resetClock();
        }
        else
        {
            startClock();
        }
    }
    public void stopClock(){
        countDownTimer.cancel();
        TIME_LEFT_MILLIS = START_TIME_MILLIS;
        timerRunning = false;
    }
    public void startClock(){
        countDownTimer = new CountDownTimer(TIME_LEFT_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TIME_LEFT_MILLIS = millisUntilFinished;
                if (millisUntilFinished >= range1 && millisUntilFinished <= range2){
                    NewGame Test = new NewGame();
                    Test.changeRow(mActivity);
                    //code to create string from new letters correct place??

                    //set the variables for the next interval
                    range1 = 60000;
                    range2 = 61000;
                }
                updateTimerText();
            }

            @Override
            public void onFinish() {
                //need to add the endgame view or call here
                Intent startIntent = new Intent(mActivity,  GameResults.class);

                //code to insert scores in DB and display


                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mActivity.startActivity(startIntent);
                finish();
            }
        }.start();
        timerRunning = true;
    }
    public void updateTimerText(){
        //variables to hold the formated time
        int minutes = (int) ((TIME_LEFT_MILLIS / 1000) / 60);
        int seconds = (int) ((TIME_LEFT_MILLIS / 1000) % 60);
        //format the time variables into a string to display on the game activity
        String timeLeft = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        //set the textview to the time
        NewGame.currentTimer.setText(timeLeft);
    }
    public void resetClock(){
        //function to reset the clock
        countDownTimer.cancel();
        TIME_LEFT_MILLIS = START_TIME_MILLIS;
        updateTimerText();
        startClock();
    }
}
