/*
 * WiFiAnalyzer Copyright (C) 2017 VREM Software Development <VREMSoftwareDevelopment@gmail.com>"
 *
 * License:WiFi Analyzer is licensed under the GNU General Public License v3.0 (GNU GPLv3)
 * License Details at "http://www.gnu.org/licenses/gpl-3.0.html"
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 *
 * Get Full Source Code"https://github.com/galaxydevelopers/WiFi-Analyzer"
 */
package com.galaxy.wifianalyzar.vendor.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper implements BaseColumns {

    protected static final String TABLE_NAME = "macvendorname";
    protected static final String COLUMN_MAC = "mac";
    protected static final String COLUMN_NAME = "name";
    protected static final String[] ALL_COLUMNS = new String[]{_ID, COLUMN_NAME, COLUMN_MAC};
    protected static final String SORT_ORDER = COLUMN_NAME + "," + COLUMN_MAC + "," + _ID;
    protected static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY NOT NULL,"
            + COLUMN_MAC + " TEXT UNIQUE NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL)";
    protected static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WiFiAnalyzerDB.db";


    public Database(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    public long insert(String mac, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValues();
        values.put(COLUMN_MAC, MacAddress.clean(mac));
        values.put(COLUMN_NAME, name);
        return db.insert(TABLE_NAME, null, values);
    }

    public ContentValues getContentValues() {
        return new ContentValues();
    }

    public String find(String mac) {
        String result = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_NAME},
                COLUMN_MAC + "=?",
                new String[]{MacAddress.clean(mac)},
                null, null, null);
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        }
        cursor.close();
        return result;
    }

    public List<VendorData> findAll() {
        List<VendorData> results = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ALL_COLUMNS, null, null, null, null, SORT_ORDER);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                VendorData vendorData = new VendorData(
                        cursor.getLong(cursor.getColumnIndexOrThrow(_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAC)));
                results.add(vendorData);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return results;
    }
}
