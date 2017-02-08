package com.jupiter.androidstudyapp.gettingstarted.savingdata.savingfiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.gettingstarted.savingdata.savingfiles.externalstorage.SavingOnExternalStoargeActivity;
import com.jupiter.androidstudyapp.gettingstarted.savingdata.savingfiles.internalstorage.SavingOnInternalStorageActivity;
import com.jupiter.androidstudyapp.R;

public class SavingFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_file);
    }

    public void onSavingOnInternalStorage(View view) {
        Intent intent = new Intent(this, SavingOnInternalStorageActivity.class);
        startActivity(intent);
    }

    public void onSavingOnExternalStorage(View view) {
        Intent intent = new Intent(this, SavingOnExternalStoargeActivity.class);
        startActivity(intent);
    }
}
