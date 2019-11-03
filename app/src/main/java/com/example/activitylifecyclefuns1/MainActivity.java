package com.example.activitylifecyclefuns1;

/*
 * This code is adapted from source code written by Griffiths and Griffiths
 * https://dogriffiths.github.io/HeadFirstAndroid/#/
 */

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // count the number of seconds that have passed
    private int seconds = 0;
    // accumulate seconds?
    private boolean running = false;

    static final String TAG = "ActivityLifecycleTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // restore saved instance from onSaveInstanceState()
        // using the bundle
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        // on a device orientation change (e.g. portrait -> landscape)
        // your app is destroyed!!
        // for landscape layouts, you could add layout-land folder
        // another example...
        // on locale change (e.g. USA -> France)
        // for french string.... you could add values-fr

        // BE AWARE OF THE ACTIVITY LIFECYCLE METHODS
        // go to the lessons folder on gdrive
        // and look at my table of descriptions for each one

        // onCreate() is a lifecycle method
        Log.d(TAG, "onCreate: ");

        // start the timer. we only update seconds if running is true though
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // called before onStop()
        // use it to save our views and our instance state
        // super class implementation saves *some* view state for you
        // won't save text of textviews
        // won't save view state for views that don't have IDs

        // like intents, bundles store key-value pairs
        // save seconds and running in the bundle
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);

        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    // task: repeat for the remaining 5 methods


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    // startButton click handler
    public void onClickStart(View view) {
        running = true;
    }

    // stopButton click handler
    public void onClickStop(View view) {
        running = false;
    }

    // resetButton click handler
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // startActivityButton click handler
    public void onClickStartActivity(View view) {
        // start second activity here
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    // method to start the timer using a Handler
    // read more about Handlers here: https://developer.android.com/reference/android/os/Handler.html
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.timeTextView);
        final Handler handler = new Handler();
        // the Runnable interface contains a single method, run()
        // post means run this code immediately
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // format the seconds in H:mm:ss format
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);

                // update the textview
                timeView.setText(time);
                // update seconds if we are in a running state
                if (running) {
                    seconds++;
                }
                // postDelayed means run this code after a delay of 1000 milliseconds
                // 1 second = 1000 milliseconds
                handler.postDelayed(this, 1000);
                // this method will keep getting called while this Activity is running
            }
        });

    }
}
