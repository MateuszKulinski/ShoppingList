package com.example.komputer.shoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private float totalPrice = 0;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseNames.DATABASE_NAME, null, DatabaseNames.DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    public float getTotalPrice() {
        saveTotalPrice();
        return totalPrice;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create list table
        String sql = "CREATE TABLE " + DatabaseNames.LIST_SHOP + " (" + DatabaseNames.ID_LIST + " INTEGER PRIMARY KEY AUTOINCREMENT," + DatabaseNames.NAME_SHOP + " TEXT NOT NULL," + DatabaseNames.PRICE_LIST + " REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + DatabaseNames.LIST_SHOP;
        db.execSQL(sql);
        onCreate(db);
    }

    private void saveTotalPrice() {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseNames.PRICE_LIST, totalPrice);
        database.update(DatabaseNames.LIST_SHOP, values, null, null);
        database.close();
    }

    public void setBought(int productID, int bought) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseNames.BOUGHT, bought);
        String whereClause = DatabaseNames.ID_PROD + " = '" + productID + "'";
        database.update(DatabaseNames.LIST_PROD, values, whereClause, null);
        database.close();
    }

    //Add new list
    public long insertListData(String listName) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseNames.NAME_SHOP, listName);
        values.put(DatabaseNames.PRICE_LIST, totalPrice);
        long insert = database.insert(DatabaseNames.LIST_SHOP, null, values);
        if (insert == -1) {
            database.close();
            return 0;
        } else {
            //Create product table
            String sql = "CREATE TABLE IF NOT EXISTS " + DatabaseNames.LIST_PROD + " (" + DatabaseNames.ID_PROD + " INTEGER PRIMARY KEY AUTOINCREMENT," + DatabaseNames.ID_LIST + " INTEGER," + DatabaseNames.NAME_PROD + " TEXT NOT NULL," + DatabaseNames.QUANTITY_PROD + " INTEGER," + DatabaseNames.PRICE_PROD + " REAL," + DatabaseNames.BOUGHT + " INTEGER)";
            database.execSQL(sql);
            database.close();
            return insert;
        }
    }

    //Add new product
    public boolean insertProdData(Long shopID, String prodName, int quantity, float prodPrice) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseNames.ID_LIST, shopID);
        values.put(DatabaseNames.NAME_PROD, prodName);
        values.put(DatabaseNames.QUANTITY_PROD, quantity);
        values.put(DatabaseNames.PRICE_PROD, prodPrice);
        values.put(DatabaseNames.BOUGHT, 0);

        if (database.insert(DatabaseNames.LIST_PROD, null, values) == -1) {
            database.close();
            return false;
        } else {
            totalPrice += prodPrice;
            database.close();
            return true;
        }
    }

    public boolean deleteProduct(int productID) {
        String product = DatabaseNames.LIST_PROD + " WHERE " + DatabaseNames.ID_PROD + " = '" + productID + "'";
        String sql = "SELECT * FROM " + product;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            float price = cursor.getFloat(4);
            totalPrice -= price;
        }
        cursor.close();
        database = this.getWritableDatabase();
        sql = "DELETE FROM " + product;
        database.execSQL(sql);
        database.close();
        return true;
    }

    public void deleteList(long listID) {
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "DELETE FROM " + DatabaseNames.LIST_PROD + " WHERE " + DatabaseNames.ID_LIST + " = '" + listID + "'";
        database.execSQL(sql);
        sql = "DELETE FROM " + DatabaseNames.LIST_SHOP + " WHERE " + DatabaseNames.ID_LIST + " = '" + listID + "'";
        database.execSQL(sql);
        database.close();
    }

    public List<ProductItem> getListProducts(Long shopID) {
        List<ProductItem> list = new ArrayList<>();
        String shopIDStr = String.valueOf(shopID);
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + DatabaseNames.LIST_PROD + " WHERE " + DatabaseNames.ID_LIST + "=" + shopIDStr;
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ProductItem item = new ProductItem();
            item.setProductID(cursor.getInt(0));
            item.setListID(cursor.getInt(1));
            item.setProductName(cursor.getString(2));
            item.setProductQuantity(cursor.getInt(3));
            item.setProductPrice(cursor.getFloat(4));
            item.setBought(cursor.getInt(5));
            list.add(item);
        }
        cursor.close();
        database.close();
        return list;
    }

    public List<ListItem> getListShops() {
        List<ListItem> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String sql = "SELECT * FROM " + DatabaseNames.LIST_SHOP;
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ListItem item = new ListItem();
            item.setListID(cursor.getInt(0));
            item.setListName(cursor.getString(1));
            item.setListPrice(cursor.getFloat(2));
            list.add(item);
        }
        cursor.close();
        database.close();
        return list;
    }
}
