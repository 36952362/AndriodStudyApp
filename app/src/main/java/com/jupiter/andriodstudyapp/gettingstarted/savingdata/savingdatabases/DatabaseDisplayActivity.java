package com.jupiter.andriodstudyapp.gettingstarted.savingdata.savingdatabases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jupiter.androidstudyapp.R;

public class DatabaseDisplayActivity extends AppCompatActivity {
    private  FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_display);

        /*
        Intent intent = getIntent();
        String recordName = intent.getStringExtra(SavingDatabaseActivity.NAME);
        */
        SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();

        String[] projection = {
                FeedReaderContract.FeedReaderEntry._ID,
                FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME,
                FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE
        };

        /*
        String selection = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = {"*"};
        */
        String sortOrder = FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(FeedReaderContract.FeedReaderEntry.TABLE_NAME, projection,null, null, null, null, sortOrder);

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while(cursor.moveToNext()){
            i++;
            stringBuilder.append(i + ":\n");
            stringBuilder.append("Name:" + cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_NAME)));
            stringBuilder.append(", Age:" + cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedReaderEntry.COLUMN_NAME_AGE)));
            stringBuilder.append("\n");
        }
        if(i > 0){
            TextView textView = new TextView(this);
            textView.setText(stringBuilder.toString());

            ViewGroup layout = (ViewGroup)findViewById(R.id.activity_database_display);
            layout.addView(textView);

        }
    }

    @Override
    protected void onDestroy(){
        feedReaderDbHelper.close();
        super.onDestroy();
    }
}
