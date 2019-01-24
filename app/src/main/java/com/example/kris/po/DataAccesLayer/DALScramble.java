package com.example.kris.po.DataAccesLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kris.po.BusinessLogicLayer.BLLScramble;

/**
 * class to do operation on "Algorytm_mieszajacy" table in database
 */


public class DALScramble extends DALGeneralDataBase {
    public static String TABLE_NAME = "Algorytm_mieszajacy";
    public static String ID = "_id";
    public static String SCRAMBLE = "algorytm";

    public DALScramble(Context context){
        super(context);
    }

    /**
     *
     * @param scramble
     * @return the row id of the newly inserted row, or -1 if an error occurred
     */
    public int insert(String scramble){
        int id = nextId(TABLE_NAME);

        SQLiteDatabase db = dateBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, id);
        contentValues.put(SCRAMBLE, scramble);

        id = (int)db.insert(TABLE_NAME, null, contentValues);
        db.close();

        return id;
    }

    /**
     *
     * @param scramble
     * @return BLLScramble object of found row, or null if error occurred
     */
    public BLLScramble find(String scramble){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ID+" FROM "+TABLE_NAME+" WHERE "+ SCRAMBLE+
                " = ?", new String[]{scramble});

        BLLScramble bllScramble = null;

        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            bllScramble = new BLLScramble(id, scramble);
        }
        cursor.close();
        db.close();

        return bllScramble;
    }

    /**
     *
     * @param id of scramble in database
     * @return BLLScramble object of found row, or null if error occurred
     */

    public BLLScramble find(int id){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SCRAMBLE+" FROM "+TABLE_NAME+" WHERE "+ ID+
                " = ?", new String[]{Integer.toString(id)});

        BLLScramble bllScramble = null;

        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            String scramble = cursor.getString(0);
            bllScramble = new BLLScramble(id, scramble);
        }
        cursor.close();
        db.close();

        return bllScramble;
    }

    /**
     *
     * @param id of scramble in database
     * @return true if found at least one solution with this scramble
     */
    public boolean hasMoreThanZero(int id){
        if(id == -1) return false;

        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+SCRAMBLE+" FROM "+TABLE_NAME+" WHERE "+ ID+
                " = ?", new String[]{Integer.toString(id)});

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }

    /**
     *
     * @param id of scramble to remove, if doesn't exist nothing happened
     */
    public void remove(int id){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        db.delete(TABLE_NAME, ID + "=" + Integer.toString(id), null);
        db.close();
    }

}
