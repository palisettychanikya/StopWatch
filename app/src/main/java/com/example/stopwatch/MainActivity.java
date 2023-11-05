package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private int seconds = 0;
    private  boolean running, wasRunning;
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    @Override
    public void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }
    @Override
    public void onResume(){
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    public void onClickStart(View view){
            running = true;
            Button reset = findViewById(R.id.reset);
            Button stop = findViewById(R.id.stop);
            Button start = findViewById(R.id.start);
            stop.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
    }



    public void onClickStop(View view){
        Button stop = findViewById(R.id.stop);
        if(!wasRunning) {
            wasRunning = running;
            running = false;
            stop.setText("Resume");
        }
        else
        {
            wasRunning = running;
            running = true;
            stop.setText("Pause");
        }



    }

    public  void onClickReset(View view){
        Button reset = findViewById(R.id.reset);
        Button stop = findViewById(R.id.stop);
        Button start = findViewById(R.id.start);
        if(running)
        {
            stop.setText("Pause");
        }
            running = false;
            seconds = 0;
        stop.setVisibility(View.INVISIBLE);
        reset.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
    }
    private  void runTimer(){
        textView = (TextView) findViewById(R.id.timerview);
        final Handler handler = new Handler();
        handler.post(r = new Runnable() {
            @Override
            public void run() {
                int hour = seconds/3600;
                int min = (seconds%3600)/60;
                int sec = seconds%60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hour,min,sec);
                textView.setText(time);
                if(running)
                {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }
}