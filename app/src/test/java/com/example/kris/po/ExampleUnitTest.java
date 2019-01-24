package com.example.kris.po;

import com.example.kris.po.PresentationLayer.DeleteSolutionActivity;
import com.example.kris.po.PresentationLayer.TimerActivity;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void for_invalid_time_returned_zero_time(){
        String correcTime = "00:00.00";
        String calcTime = TimerActivity.convertMillisecToDisplay(-22321);
        assertEquals(correcTime, calcTime);
    }

    @Test
    public void for_valid_zero_time_returned_zero_time(){
        String correcTime = "00:00.00";
        String calcTime = TimerActivity.convertMillisecToDisplay(0);
        assertEquals(correcTime, calcTime);
    }

    @Test
    public void for_valid_time_return_correct_format_time(){
        String correcTime = "10:30.25";
        String calcTime = TimerActivity.convertMillisecToDisplay(630252);
        assertEquals(correcTime, calcTime);
    }

    @Test
    public void for_invalid_format_date_return_null(){
        Date date = DeleteSolutionActivity.validateAndSetDate("2000-01-01", "01-01-1970");
        assertEquals(null, date);
        date = DeleteSolutionActivity.validateAndSetDate("32-01-2018", "01-01-1970");
        assertEquals(null, date);
        date = DeleteSolutionActivity.validateAndSetDate("32-01", "01-01-1970");
        assertEquals(null, date);
    }

    @Test
    public void for_empty_string_return_default(){
        Calendar.Builder builder = new Calendar.Builder();
        Calendar temp = builder.setDate(1970, 0, 1).build();
        Date correct = temp.getTime();
        Date date = DeleteSolutionActivity.validateAndSetDate("", "01-01-1970");
        assertEquals(correct, date);
    }

    @Test
    public void for_valid_string_return_valid_date(){
        Calendar.Builder builder = new Calendar.Builder();
        Calendar temp = builder.setDate(2018, 1, 2).build();
        Date correct = temp.getTime();
        Date date = DeleteSolutionActivity.validateAndSetDate("02-02-2018", "01-01-1970");
        assertEquals(correct, date);
    }
}