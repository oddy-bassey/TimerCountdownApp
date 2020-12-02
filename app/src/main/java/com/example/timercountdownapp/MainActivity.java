package com.example.timercountdownapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Button button;
    CountDownTimer countDownTimer;

    MediaPlayer audioPlayer;
    boolean isTimerStarted = false;

    public void updateTimer(int progress){

        int minute = progress / 60;
        int secs = progress - (minute * 60);

        String secsText = String.valueOf(secs);

        if(secs < 10){
            secsText = "0".concat(secsText);
            if(minute < 1) {
                audioPlayer.start();
            }
        }

        textView.setText(String.valueOf(minute)+":"+secsText );
    }

    public void resetTimer(){
        isTimerStarted = false;
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        seekBar.setProgress(100);
        button.setText("Start");
    }

    public void run(View view){

        if(isTimerStarted){
            resetTimer();
        }else {
            isTimerStarted = true;
            seekBar.setEnabled(false);
            button.setText("Stop");

            countDownTimer = new CountDownTimer((seekBar.getProgress() * 1000) + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        button = (Button) findViewById(R.id.myButton);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(100);

        textView = (TextView) findViewById(R.id.textView);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
