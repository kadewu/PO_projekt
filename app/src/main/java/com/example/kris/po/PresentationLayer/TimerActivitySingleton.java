package com.example.kris.po.PresentationLayer;


import android.view.View;

import com.example.kris.po.BusinessLogicLayer.BLLTimer;

public class TimerActivitySingleton {
    private static TimerActivitySingleton instance = null;

    protected TimerActivitySingleton(){}
    public static TimerActivitySingleton getInstance(){
        if(instance == null){
            instance = new TimerActivitySingleton();
        }
        return instance;
    }

    private int buttonAddSolutionVisibility = View.INVISIBLE;
    private int textViewNoteVisibility = View.VISIBLE;
    private int textViewScrambleVisibility = View.INVISIBLE;

    private BLLTimer timer = new BLLTimer();
    private String scramble = null;

    /**
     * change scramble in timer activity, if scramble is the same nothing happened, otherwise activity setting visibility will be changed
     */
    public void changeScramble(String scramble) {
        if(scramble == null || this.scramble == null || this.scramble.compareTo(scramble) != 0){
            timer.resetLastTimeMeasurement();
        }
        this.scramble = scramble;
    }

    public int getButtonAddSolutionVisibility() {
        return buttonAddSolutionVisibility;
    }

    public int getTextViewNoteVisibility() {
        return textViewNoteVisibility;
    }

    public int getTextViewScrambleVisibility() {
        return textViewScrambleVisibility;
    }

    /**
     *
     * @return timer of this activity, that one are already setting, you can't set your own timer
     */
    public BLLTimer getTimer(){
        return timer;
    }

    /**
     * set visibility settings in activity, you should called it after {@link #changeScramble(String)}
     */
    public void setDefault(){
        if(timer.getLastTimeMeasurement() != BLLTimer.NO_MEASUREMENT) {
            buttonAddSolutionVisibility = View.VISIBLE;
            textViewNoteVisibility = View.INVISIBLE;
            textViewScrambleVisibility = View.VISIBLE;
        } else {
            buttonAddSolutionVisibility = View.INVISIBLE;
            textViewNoteVisibility = View.VISIBLE;
            textViewScrambleVisibility = View.INVISIBLE;
        }
    }
}
