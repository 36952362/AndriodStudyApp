package com.jupiter.androidstudyapp.gettingstarted.savingdata.savingdatabases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haijutan on 22/01/2017.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedReaderEntry.TABLE_NAME + "(" +
            FeedReaderContract.FeedReaderEntry._ID + " INTEGER PRIMARY KEY," +
            FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " TEXT,"  +
            FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DELETE TABLE IF EXISTS" + FeedReaderContract.FeedReaderEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
