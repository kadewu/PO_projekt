package com.example.kris.po.DataAccesLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.kris.po.R;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class DALGeneralDataBase {
    protected Context context;
    private String DATA_BASE_NAME = "PO";
    private int VERSION = 1;

    public DALGeneralDataBase(Context context){
        this.context = context;
    }

    protected DateBaseHelper dateBaseHelper = null;

    public void open(){
        dateBaseHelper = new DateBaseHelper(context, DATA_BASE_NAME, null, VERSION);
    }

    public void close(){
        dateBaseHelper.close();
    }

    protected int nextId(String tableName){
        SQLiteDatabase db = dateBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM "+tableName+";", null);
        cursor.moveToFirst();
        int nextId = cursor.getInt(0) + 1;
        cursor.close();
        db.close();

        return nextId;
    }
//-----------------------------------------------------------
    protected class DateBaseHelper extends SQLiteOpenHelper {
        Context context;

        public DateBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.mydb);

            String queries = "";
            try {
                queries = IOUtils.toString(inputStream);
            } catch (IOException e) {}

            for (String query : queries.split(";")) {
                db.execSQL(query);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }
}
