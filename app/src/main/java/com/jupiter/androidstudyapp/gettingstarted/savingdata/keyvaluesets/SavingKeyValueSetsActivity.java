package com.jupiter.androidstudyapp.gettingstarted.savingdata.keyvaluesets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jupiter.androidstudyapp.R;

public class SavingKeyValueSetsActivity extends AppCompatActivity {
    public static final String preference_file_key = "com.jupiter.androidstudyapp.savingdata.preference.file.key";
    public static final String InputValue = "com.jupiter.androidstudyapp.savingdata.InputValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_key_value_sets);
    }

    public void onDisplayKeyvaluesets(View view) {
        EditText editText = (EditText)findViewById(R.id.edit_keyvaluesets);
        String value = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(InputValue, value).commit();

        Intent intent = new Intent(this, SavingKeyValueSetsDisplayActivity.class);
        startActivity(intent);
    }
}
