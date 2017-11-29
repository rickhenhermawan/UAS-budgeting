package com.example.joseph.mobileproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joseph on 11/17/17.
 */


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "moneybook.db";
    public static final String TABLE_NAME = "money_data";
    public static final String COL1 = "M_ID";
    public static final String COL2 = "M_Name";
    public static final String COL3 = "M_Amount";
    public static final String COL4 = "M_Category";
    public static final String COL5 = "M_Date";


    public static final String TABLE_NAME2 = "category_data";
    public static final String COLC2 = "C_Name";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (M_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "M_Name TEXT NOT NULL, "
                + "M_Amount INTEGER NOT NULL, " + "M_Category TEXT NOT NULL, " + "M_Date DATE NOT NULL);";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLE_NAME2 + " (C_Name TEXT NOT NULL);";
        db.execSQL(createTable);

        String addTable = "INSERT INTO " + TABLE_NAME2 +" (C_Name) "+"VALUES ('Food & Beverage');";
        db.execSQL(addTable);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop = "DROP IF TABLE EXISTS " + TABLE_NAME;
        db.execSQL(drop);

        drop = "DROP IF TABLE EXISTS " + TABLE_NAME2;
        db.execSQL(drop);

        onCreate(db);

    }

    public boolean editExpense(String name, String category, String amount, String id) {

        String update = "UPDATE "+ TABLE_NAME +" SET M_Name='"+name+"', M_Category='"+category+"', M_Amount='"+amount+"' WHERE M_ID=" + id +";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }

    public boolean deleteExpense(String id) {

        String update = "DELETE FROM "+ TABLE_NAME +" WHERE M_ID=" + id +";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }

    public boolean deleteCategory(String name) {

        String update = "DELETE FROM "+ TABLE_NAME2 +" WHERE C_Name='" + name +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }

    public boolean editCategory(String category, String newName) {

        String update = "UPDATE "+ TABLE_NAME2 +" SET C_Name='"+newName+"' WHERE C_Name='" + category +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }


    public String getExpenseId(String name, String amount, String category) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT M_ID FROM "+TABLE_NAME+" WHERE M_Name = '"+name+"' AND M_Category = '"+category+"' AND M_Amount = '"+amount+"'", null);
        if(data.moveToFirst()) {
            return data.getString(0);
        }
        else return "1";
    }

    public boolean addData (String name, int amount, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, name);
        cv.put(COL3, amount);
        cv.put(COL4, category);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sCertDate = dateFormat.format(new Date());
        cv.put(COL5, sCertDate);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result==-1){
            return false;
        }
        else return true;
    }

    public boolean addCategory (String category){

        SQLiteDatabase db = this.getWritableDatabase();
        String exe="SELECT * FROM "+TABLE_NAME2+" WHERE C_Name = '"+category+"'";
        Cursor data = db.rawQuery(exe, null);

        if (data.getCount() == 0) {
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLC2, category);
            long result = db.insert(TABLE_NAME2, null, cv);

            if (result==-1){
                return false;
            }
            else return true;

        }
        else return false;

    }

    public Integer sumAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(M_Amount) FROM money_data", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }

    public Integer sumAll(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(M_Amount) FROM money_data WHERE M_Date='"+date+"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }

    public Integer sumAllToday()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(M_Amount) FROM money_data WHERE M_Date='"+sCertDate+"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String sCertDate = dateFormat.format(new Date());

    public Cursor getTodayListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE M_Date='"+sCertDate+"'", null);
        return data;
    }

    public Cursor getListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" ORDER BY M_Date DESC", null);
        return data;
    }

    public Cursor getListContents(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE M_Date='"+date+"'", null);
        return data;
    }

    public Cursor getListCategory()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " ORDER BY C_Name ASC", null);
        return data;
    }


}
