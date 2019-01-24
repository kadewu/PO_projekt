package com.example.kris.po.DataAccesLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kris.po.BusinessLogicLayer.BLLScramble;
import com.example.kris.po.BusinessLogicLayer.BLLSolution;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * class to do operation on "Rozwiazanie" table in database
 */


public class DALSolution extends DALGeneralDataBase {
    public static String TABLE_NAME = "Rozwiazanie";
    public static String ID = "_id";
    public static String PUBLIC = "czy_publiczne";
    public static String TIME = "czas";
    public static String DATE = "data_dodania";
    public static String XCROSS = "czy_xcross";
    public static String OLLSKIP = "czy_ollskip";
    public static String PLLSKIP = "czy_pllskip";
    public static String F2L = "czy_f2l";
    public static String SPEEDCUBER = "ZawodnikID";
    public static String SCRAMBLE = "Algorytm_mieszajacyID";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DALSolution(Context context){
        super(context);
    }

    /**
     *
     * @param bllSolution object of BLLSolution which data you want insert
     * @param speedcuberId id of current user
     * @return the row id of the newly inserted row, or -1 if an error occurred
     */
    public int insert(BLLSolution bllSolution, int speedcuberId){
        if(bllSolution == null || bllSolution.getScramble() == null){
            return -1;
        }

        DALScramble dalScramble = new DALScramble(context);
        dalScramble.open();
        BLLScramble bllScramble = dalScramble.find(bllSolution.getScramble());

        int scrambleId;
        if(bllScramble == null){
            scrambleId = dalScramble.insert(bllSolution.getScramble());
        } else {
            scrambleId = bllScramble.getId();
        }
        dalScramble.close();


        int id = nextId(TABLE_NAME);

        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, id);
        contentValues.put(PUBLIC, 0);
        contentValues.put(TIME, bllSolution.getTime());
        contentValues.put(DATE, simpleDateFormat.format(bllSolution.getDate()));
        contentValues.put(XCROSS, bllSolution.getParameterValue("xcross"));
        contentValues.put(F2L, bllSolution.getParameterValue("f2l"));
        contentValues.put(OLLSKIP, bllSolution.getParameterValue("ollskip"));
        contentValues.put(PLLSKIP, bllSolution.getParameterValue("pllskip"));
        contentValues.put(SPEEDCUBER, speedcuberId);
        contentValues.put(SCRAMBLE, scrambleId);

        id = (int)db.insert(TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    /**
     *
     * @param dateFirst beginning date
     * @param dateSecond ending date
     * @param f2l 1 if true, 0 otherwise
     * @param xcross 1 if true, 0 otherwise
     * @param ollskip 1 if true, 0 otherwise
     * @param pllskip 1 if true, 0 otherwise
     * @return @return {@link ArrayList} of solutions id's to find in database
     */
    public ArrayList<BLLSolution> findBetween(Date dateFirst, Date dateSecond, Integer f2l,
                                              Integer xcross, Integer ollskip, Integer pllskip){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE ("+DATE+" BETWEEN ? AND ?)";
        String[] arguments = new String[]{simpleDateFormat.format(dateFirst),
                simpleDateFormat.format(dateSecond)};
        if(f2l != null) query += " AND "+ F2L + " = "+f2l.toString();
        if(xcross != null) query += " AND "+XCROSS + " = "+xcross.toString();
        if(ollskip != null) query += " AND "+OLLSKIP + " = "+ollskip.toString();
        if(pllskip != null) query += " AND "+PLLSKIP + " = "+pllskip.toString();
        query += " ORDER BY "+ DATE +" DESC;";
        Cursor cursor = db.rawQuery(query, arguments);

        ArrayList<BLLSolution> listSolution = new ArrayList<>();
        DALScramble dalScramble = new DALScramble(context);
        dalScramble.open();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Map<String, Integer> parametersMap = new HashMap<>();
            parametersMap.put("f2l", cursor.getInt(cursor.getColumnIndex(F2L)));
            parametersMap.put("xcross", cursor.getInt(cursor.getColumnIndex(XCROSS)));
            parametersMap.put("ollskip", cursor.getInt(cursor.getColumnIndex(OLLSKIP)));
            parametersMap.put("pllskip", cursor.getInt(cursor.getColumnIndex(PLLSKIP)));

            listSolution.add(new BLLSolution(cursor.getInt(cursor.getColumnIndex(ID)),
                    cursor.getInt(cursor.getColumnIndex(TIME)),
                    simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(DATE)), new ParsePosition(0)),
                    dalScramble.find(cursor.getInt(cursor.getColumnIndex(SCRAMBLE))),
                    parametersMap));
            cursor.moveToNext();
        }
        cursor.close();
        dalScramble.close();
        db.close();

        return listSolution;
    }

    /**
     *
     * @param id of solution in databse
     * @return if of scramble which are used in this solution, or -1 if an error occurred or didn't find
     */

    public int getScrambleId(int id){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT A."+DALScramble.ID+" FROM "+TABLE_NAME+" R JOIN "+DALScramble.TABLE_NAME
                + " A ON R." +SCRAMBLE+" = A."+ID +" WHERE R."+ ID+
                " = ?", new String[]{Integer.toString(id)});

        int scrambleId = -1;

        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            scrambleId = cursor.getInt(cursor.getColumnIndex(ID));
        }
        cursor.close();
        db.close();

        return scrambleId;
    }

    /**
     *
     * @param id of solution to remove from database, if doesn't exist nothing happened
     */
    public void remove(int id){
        int scrambleID = getScrambleId(id);

        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        db.delete(TABLE_NAME, ID + "=" + Integer.toString(id), null);
        db.close();

        DALScramble dalScramble = new DALScramble(context);
        dalScramble.open();
        if(!dalScramble.hasMoreThanZero(scrambleID)){
            dalScramble.remove(scrambleID);
        }
        dalScramble.close();
    }
}
