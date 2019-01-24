package com.example.kris.po.BusinessLogicLayer;

/**
 * class to do time measurement operation
 */

public class BLLTimer {
    public BLLTimer(){}

    /**
     * value when timer didn't measured time
     */
    public static int NO_MEASUREMENT = -1;

    private Long startTimer = null;
    private long lastTimeMeasurement = NO_MEASUREMENT;

    /**
     * start time, if already started, then nothing happened
     */
    public void startTimer(){
        if(startTimer == null){
            startTimer = System.currentTimeMillis();
        }
    }

    /**
     * stop timer
     * @return time in milliseconds, if already stopped return 0
     */
    public long stopTimer(){
        if(startTimer != null){
            lastTimeMeasurement = System.currentTimeMillis() - startTimer;
            startTimer = null;
            return lastTimeMeasurement;
        }
        return 0;
    }

    /**
     *
     * @return current running time in milliseconds, if don't running return 0
     */
    public long currentRunningTime(){
        if(startTimer != null){
            return System.currentTimeMillis() - startTimer;
        }
        return 0;
    }

    /**
     *
     * @return last time measurement, remember of {@link #resetLastTimeMeasurement()}
     */
    public long getLastTimeMeasurement(){
        return lastTimeMeasurement;
    }

    /**
     *
     * @return true if timer is running, otherwise false
     */
    public boolean isRunning(){
        return startTimer != null;
    }

    /**
     * {@link #NO_MEASUREMENT}
     */
    public void resetLastTimeMeasurement(){
        lastTimeMeasurement = NO_MEASUREMENT;
    }
}
