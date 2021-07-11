package com.example.homework1pt3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ItemDataSource {

    // Setting up relevant database values.
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private static final String table_name = "Shopping_List";
    private static final String col_id = "ID";
    private static final String col_name = "Name";
    private static final String col_category = "Category";
    private static final String col_description = "Description";
    private static final String col_estimated_price = "Estimated_Price";
    private static final String col_purchase_status = "Purchase_Status";

    public ItemDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Opening database to pass queries.
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closing database when not in usage.
    public void close() {
        dbHelper.close();
    }

    // Adding new entry to database. If added successfully, return true, otherwise, false.
    public boolean appendEntry(Item item) {
        try {
            ContentValues c = new ContentValues();
            c.put(col_name, item.name);
            c.put(col_category, item.category);
            c.put(col_description, item.description);
            c.put(col_estimated_price, item.estimated_price);
            c.put(col_purchase_status, item.purchase_status);
            database.insert(table_name, null, c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Editing existing entry in database. If edited successfully, return true, otherwise, false.
    public boolean editEntry(Item item) {
        try {
            ContentValues c = new ContentValues();
            c.put(col_name, item.name);
            c.put(col_category, item.category);
            c.put(col_description, item.description);
            c.put(col_estimated_price, item.estimated_price);
            c.put(col_purchase_status, item.purchase_status);
            database.update(table_name, c, col_id + "=" + item.id, null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Retrieving all items in database, then returning as ArrayList<Item>. If unsuccessfully, return an empty ArrayList<Item>.
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM " + table_name;
            Cursor cursor = database.rawQuery(query, null);

            Item new_item;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                new_item = new Item(0, null, null, null, 0, 1);
                new_item.id = cursor.getInt(0);
                new_item.name = cursor.getString(1);
                new_item.category = cursor.getString(2);
                new_item.description = cursor.getString(3);
                new_item.estimated_price = cursor.getDouble(4);
                new_item.purchase_status = cursor.getInt(5);
                new_item.setImage();
                items.add(new_item);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            items = new ArrayList<Item>();
        }
        return items;
    }

}
