package com.jupiter.myfirstandriodapp.savingdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.myfirstandriodapp.R;

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
}
