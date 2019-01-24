package com.example.kris.po.PresentationLayer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kris.po.BusinessLogicLayer.BLLTimer;
import com.example.kris.po.R;

public class TimerActivity extends AppCompatActivity {
    public static final String SCRAMBLE_MESSAGE = "scramble";

    private String scramble;

    private TextView textViewTime, textViewScramble, textViewNote;
    private Button buttonAddSolution;

    private BLLTimer timer;
    private TimerActivitySingleton singleton;

    public static void start(Context context, String scramble) {
        Intent starter = new Intent(context, TimerActivity.class);
        starter.putExtra(SCRAMBLE_MESSAGE, scramble);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        init();
        initTimer();
        setListeners();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP : {
                if(timer.isRunning()){
                    timer.stopTimer();

                    setVisibilities();
                } else if(timer.getLastTimeMeasurement() == BLLTimer.NO_MEASUREMENT){
                    timer.startTimer();
                }
            }
        }
        return true;
    }


    private void init(){
        textViewTime = findViewById(R.id.timer_text_view_time);
        textViewScramble = findViewById(R.id.timer_text_view_scramble);
        textViewNote = findViewById(R.id.timer_text_view_note);
        buttonAddSolution = findViewById(R.id.timer_button_add_solution);

        singleton = TimerActivitySingleton.getInstance();
        timer = singleton.getTimer();

        Intent intent = getIntent();
        scramble = intent.getStringExtra(SCRAMBLE_MESSAGE);

        singleton.changeScramble(scramble);

        setVisibilities();
        if(scramble != null) textViewScramble.setText(scramble);
    }

    private void initTimer(){
        final int SLEEP_TIME = 10;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(timer.isRunning()) {
                    textViewTime.setText(convertMillisecToDisplay(timer.currentRunningTime()));
                } else{
                    textViewTime.setText(convertMillisecToDisplay(timer.getLastTimeMeasurement()));
                }
                handler.postDelayed(this, SLEEP_TIME);
            }
        });
    }

    private void setVisibilities(){
        singleton.setDefault();
        textViewNote.setVisibility(singleton.getTextViewNoteVisibility());
        textViewScramble.setVisibility(singleton.getTextViewScrambleVisibility());
        buttonAddSolution.setVisibility(singleton.getButtonAddSolutionVisibility());
    }

    private void setListeners(){
        buttonAddSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSolutionActivity.start(v.getContext(), scramble, timer.getLastTimeMeasurement());
            }
        });
    }

    /**
     *
     * @param millisec time in milliseconds
     * @return time format to display on string
     */
    public static String convertMillisecToDisplay(long millisec){
        if(millisec < 0){
            return "00:00.00";
        }
        long centisecondsDisplay = millisec / 10 % 100;
        long secondsDisplay = millisec / 1000 % 60;
        long minutesDisplay = millisec / 60000;

        StringBuilder stringBuilder = new StringBuilder();
        if(minutesDisplay < 10) stringBuilder.append('0');
        stringBuilder.append(minutesDisplay);
        stringBuilder.append(':');
        if(secondsDisplay < 10) stringBuilder.append('0');
        stringBuilder.append(secondsDisplay);
        stringBuilder.append('.');
        if(centisecondsDisplay < 10) stringBuilder.append('0');
        stringBuilder.append(centisecondsDisplay);

        return stringBuilder.toString();
    }
}
