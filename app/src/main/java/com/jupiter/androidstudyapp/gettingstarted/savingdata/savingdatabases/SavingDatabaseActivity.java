package com.jupiter.androidstudyapp.gettingstarted.savingdata.savingdatabases;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jupiter.androidstudyapp.R;

public class SavingDatabaseActivity extends AppCompatActivity {
    public final static String NAME = "com.jupiter.androidstudyapp.savingdata.savingdatabases.NAME";
    private FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_database);
    }

    public void onAddInDatabase(View view) {
        EditText recordNameEditText = (EditText)findViewById(R.id.edit_input_name);
        String recordName = recordNameEditText.getText().toString();
        EditText recordAgeEditText = (EditText)findViewById(R.id.edit_input_age);
        String recordAge = recordAgeEditText.getText().toString();

        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME, recordName);
        values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE, recordAge);

        long newRowId = db.insert(FeedReaderContract.FeedReaderEntry.TABLE_NAME, null, values);
    }

    protected void onDestory(){
        feedReaderDbHelper.close();
        super.onDestroy();
    }

    public void onUpdateInDatabase(View view) {
        EditText recordNameEditText = (EditText)findViewById(R.id.edit_input_name);
        String localRecordName = recordNameEditText.getText().toString();
        EditText recordAgeEditText = (EditText)findViewById(R.id.edit_input_age);
        String localRecordAge = recordAgeEditText.getText().toString();

        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME, localRecordName);
        values.put(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE, localRecordAge);

        String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {localRecordName};

        int count = db.update(FeedReaderContract.FeedReaderEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    public void onDeleteFromDatabase(View view) {
        EditText recordNameEditText = (EditText)findViewById(R.id.edit_input_name);
        String localRecordName = recordNameEditText.getText().toString();

        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();

        String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " LIKE ?";
        String[] selectionArgs = {localRecordName};

        int count = db.delete(FeedReaderContract.FeedReaderEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void onDisplay(View view) {
        Intent intent = new Intent(this, DatabaseDisplayActivity.class);
        startActivity(intent);
    }
}
