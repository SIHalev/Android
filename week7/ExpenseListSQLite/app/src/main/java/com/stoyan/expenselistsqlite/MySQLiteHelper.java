package com.stoyan.expenselistsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXPENSES = "expenses";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXPENSE_NAME = "expense_name";
    public static final String COLUMN_EXPENSE_PRICE = "expense_price";

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EXPENSES +
            "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_EXPENSE_NAME + " text, "
                + COLUMN_EXPENSE_PRICE + " text);"; // text not null

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

}