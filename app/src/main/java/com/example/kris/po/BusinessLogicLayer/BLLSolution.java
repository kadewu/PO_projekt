package com.example.kris.po.BusinessLogicLayer;

import android.content.Context;

import com.example.kris.po.DataAccesLayer.DALSolution;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BLLSolution {
    public static int ADD_SOLUTION_CORRECT = 1, ADD_SOLUTION_WRONG = 0;
    public static String[] parametersKeys = new String[]{"f2l", "xcross", "ollskip", "pllskip"};

    private Map<String, Integer> parameters;
    private long time;
    private Date date;
    private Integer id = null;

    private BLLScramble scramble = null;

    public BLLSolution(){
        parameters = new HashMap<>();
    }

    public BLLSolution(int id, long time, Date date, BLLScramble scramble, Map<String, Integer>parameters) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.scramble = scramble;

        this.parameters = new HashMap<>(parameters);
    }

    public BLLSolution(long time, String scramble, Map<String, Boolean>parameters) {
        this.time = time;
        this.date = Calendar.getInstance().getTime();
        this.scramble = new BLLScramble(scramble);

        this.parameters = new HashMap<>();
        for(Map.Entry<String, Boolean> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            this.parameters.put(key, value ? 1 : 0);
        }
    }

    public String getScramble() {
        return scramble.getScramble();
    }

    public Date getDate() {
        return date;
    }

    public int getParameterValue(String key){
        return parameters.get(key);
    }

    public long getTime() {
        return time;
    }

    public static int addSolutionDB(Context context, BLLSolution bllSolution){
        BLLSpeedcuber speedcuber = BLLSpeedcuber.getInstance();
        DALSolution dalSolution = new DALSolution(context);
        dalSolution.open();
        dalSolution.insert(bllSolution, speedcuber.getId());
        dalSolution.close();
        return 1;
    }

    public Integer getID(){
        return id;
    }

    public static ArrayList<BLLSolution> findSolutionsBetweenDateDB(Context context , Date dateFirst,
                                                                    Date dateSecond, Map<String, Boolean> params){

        DALSolution dalSolution = new DALSolution(context);
        dalSolution.open();
        ArrayList<BLLSolution> listSolutions = dalSolution.findBetween(dateFirst, dateSecond,
                params.get("f2l") == null ? null : params.get("f2l") ? 1: 0,
                params.get("xcross") == null ? null : params.get("xcross") ? 1: 0,
                params.get("ollskip") == null ? null : params.get("ollskip") ? 1: 0,
                params.get("pllskip") == null ? null : params.get("pllskip") ? 1: 0);
        dalSolution.close();

        return listSolutions;
    }

    public static void deleteSolutionDB(Context context, int id){
        DALSolution dalSolution = new DALSolution(context);
        dalSolution.open();
        dalSolution.remove(id);
        dalSolution.close();
    }
}
