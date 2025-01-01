package com.technetia.sqliteadvance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        // Create database named "digital_wallet"
        super(context, "digital_wallet", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create "expense" and "income" tables
        db.execSQL("CREATE TABLE expense (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time DOUBLE)");
        db.execSQL("CREATE TABLE income (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT, time DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables if they exist
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS income");
        onCreate(db);
    }

    // Add Expense
    public void addExpense(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("reason", reason);
        values.put("time", System.currentTimeMillis());
        db.insert("expense", null, values);
    }

    // Add Income
    public void addIncome(double amount, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("reason", reason);
        values.put("time", System.currentTimeMillis());
        db.insert("income", null, values);
    }

    // Calculate Total Expense
    public double calculateTotalExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT amount FROM expense", null);
        double totalExpense = 0.0;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                totalExpense += cursor.getDouble(0);
            }
            cursor.close(); // Close cursor
        }
        return totalExpense;
    }

    // Calculate Total Income
    public double calculateTotalIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT amount FROM income", null);
        double totalIncome = 0.0;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                totalIncome += cursor.getDouble(0);
            }
            cursor.close(); // Close cursor
        }
        return totalIncome;
    }


    // show all expense data
    public Cursor showAllExpense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM expense", null);
        return cursor;
    }

    // show all income data
    public Cursor showAllIncome() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM income", null);
        return cursor;
    }

    // delete expense
    public void deleteExpense(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM expense WHERE id ="+id);

    }

    // delete income
    public void deleteIncome(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM income WHERE id ="+id);

    }




}
