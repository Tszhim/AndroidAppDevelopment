package com.example.homework1pt3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table setup.
    private static final String database_name = "Shopping_List.db";
    private static final String table_name = "Shopping_List";
    private static final String col_id = "ID";
    private static final String col_name = "Name";
    private static final String col_category = "Category";
    private static final String col_description = "Description";
    private static final String col_estimated_price = "Estimated_Price";
    private static final String col_purchase_status = "Purchase_Status";
    private static final String table_creator = "CREATE TABLE "
            + table_name + " (" + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + col_name + " TEXT, "
            + col_category + " TEXT, "
            + col_description + " TEXT, "
            + col_estimated_price + " REAL, "
            + col_purchase_status + " INTEGER); ";

    public DatabaseHelper(Context c) {
        super(c, database_name, null, 1);
    }

    //Creates table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_creator);
    }

    //Upgrades table.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }
}
