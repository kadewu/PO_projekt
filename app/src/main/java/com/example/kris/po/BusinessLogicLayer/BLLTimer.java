package com.example.kris.po.BusinessLogicLayer;

public class BLLTimer {
    public BLLTimer(){}

    public static int NO_MEASUREMENT = -1;

    private Long startTimer = null;
    private long lastTimeMeasurement = NO_MEASUREMENT;

    public void startTimer(){
        if(startTimer == null){
            startTimer = System.currentTimeMillis();
        }
    }

    public long stopTimer(){
        if(startTimer != null){
            lastTimeMeasurement = System.currentTimeMillis() - startTimer;
            startTimer = null;
            return lastTimeMeasurement;
        }
        return 0;
    }

    public long currentRunningTime(){
        if(startTimer != null){
            return System.currentTimeMillis() - startTimer;
        }
        return 0;
    }

    public long getLastTimeMeasurement(){
        return lastTimeMeasurement;
    }

    public boolean isRunning(){
        return startTimer != null;
    }

    public void resetLastTimeMeasurement(){
        lastTimeMeasurement = NO_MEASUREMENT;
    }
}
