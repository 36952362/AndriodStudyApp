package com.jupiter.AndriodStudyApp.savingdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.myfirstandriodapp.R;
import com.jupiter.AndriodStudyApp.savingdata.keyvaluesets.SavingKeyValueSetsActivity;
import com.jupiter.AndriodStudyApp.savingdata.savingdatabases.SavingDatabaseActivity;
import com.jupiter.AndriodStudyApp.savingdata.savingfiles.SavingFileActivity;

public class SavingDataDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_data_demo);
    }

    public void onSavingKeyValueSets(View view) {
        Intent intent = new Intent(this, SavingKeyValueSetsActivity.class);
        startActivity(intent);
    }

    public void onSavingOnFile(View view) {
        Intent intent = new Intent(this, SavingFileActivity.class);
        startActivity(intent);
    }

    public void onSavingInDatabases(View view) {
        Intent intent = new Intent(this, SavingDatabaseActivity.class);
        startActivity(intent);
    }
}
