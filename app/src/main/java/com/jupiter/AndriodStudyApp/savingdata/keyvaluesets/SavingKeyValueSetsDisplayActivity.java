package com.jupiter.AndriodStudyApp.savingdata.keyvaluesets;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;

public class SavingKeyValueSetsDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_key_value_sets_display);

        TextView textView = (TextView)findViewById(R.id.text_keyvaluesets);
        SharedPreferences sharedPreferences = getSharedPreferences(SavingKeyValueSetsActivity.preference_file_key, Context.MODE_PRIVATE);
        String restore = sharedPreferences.getString(SavingKeyValueSetsActivity.InputValue, "defaultValue");
        textView.setText(restore);
    }
}
