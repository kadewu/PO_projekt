package com.example.kris.po.BusinessLogicLayer;

import android.content.Context;

import com.example.kris.po.DataAccesLayer.DALSolution;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * class to represent solution in logic layer
 */

public class BLLSolution {
    public static int ADD_SOLUTION_CORRECT = 1, ADD_SOLUTION_WRONG = 0;
    public static String[] parametersKeys = new String[]{"f2l", "xcross", "ollskip", "pllskip"};

    private Map<String, Integer> parameters;
    private long time;
    private Date date = null;
    private Integer id = null;

    private BLLScramble scramble = null;

    public BLLSolution(){
        parameters = new HashMap<>();
    }

    /**
     *
     * @param id in database
     * @param time of the solution in milliseconds
     * @param date of the solution
     * @param scramble of the solution
     * @param parameters specified by the user (1- true, 0 - false)
     */
    public BLLSolution(int id, long time, Date date, BLLScramble scramble, Map<String, Integer>parameters) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.scramble = scramble;

        this.parameters = new HashMap<>(parameters);
    }

    /**
     *
     * @param time of the solution in milliseconds
     * @param scramble of the solution
     * @param parameters specified by the user
     */
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

    /**
     *
     * @return scramble of the solution, if you don't specified scramble return null
     */

    public String getScramble() {
        return scramble.getScramble();
    }

    /**
     *
     * @return date of the solution, if don't specified return null
     */

    public Date getDate() {
        return date;
    }

    /**
     *
     * @param key possible keys: {@link #parametersKeys}
     * @return value of specified parameter (1 - true, 0 - false)
     */
    public int getParameterValue(String key){
        return parameters.get(key);
    }

    /**
     *
     * @return time of the solution
     */
    public long getTime() {
        return time;
    }

    /**
     *
     * @param context important for android sqlite, {@link Context}
     * @param bllSolution solution what you like to add
     * @return {@link #ADD_SOLUTION_CORRECT} if added successful othwerwise {@link #ADD_SOLUTION_WRONG}
     */
    public static int addSolutionDB(Context context, BLLSolution bllSolution){
        BLLSpeedcuber speedcuber = BLLSpeedcuber.getInstance();
        DALSolution dalSolution = new DALSolution(context);
        dalSolution.open();
        int result = dalSolution.insert(bllSolution, speedcuber.getId());
        dalSolution.close();
        return result > 0 ? ADD_SOLUTION_CORRECT : ADD_SOLUTION_WRONG;
    }

    /**
     *
     * @return id of solution in database, if isn't in database return null
     */
    public Integer getID(){
        return id;
    }

    /**
     *
     * @param context context important for android sqlite, {@link Context}
     * @param dateFirst beginning date
     * @param dateSecond ending date
     * @param params set true/false to parameters, otherwise set null
     * @return @return {@link ArrayList} of solutions id's to find in database
     */
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

    /**
     *
     * @param context context important for android sqlite, {@link Context}
     * @param id of solution in database
     */
    public static void deleteSolutionDB(Context context, int id){
        DALSolution dalSolution = new DALSolution(context);
        dalSolution.open();
        dalSolution.remove(id);
        dalSolution.close();
    }
}
