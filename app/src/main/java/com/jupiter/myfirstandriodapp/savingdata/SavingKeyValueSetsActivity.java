package com.jupiter.myfirstandriodapp.savingdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;

public class SavingKeyValueSetsActivity extends AppCompatActivity {
    private static final String preference_file_key = "com.jupiter.myfirstandriodapp.savingdata.preference.file.key";
    private static final String InputValue = "com.jupiter.myfirstandriodapp.savingdata.InputValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_key_value_sets);
    }

    public void onSaveAndDisplay(View view) {
        EditText editText = (EditText)findViewById(R.id.edit_keyvaluesets);
        String value = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(preference_file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(InputValue, value).commit();

        TextView textView = (TextView)findViewById(R.id.text_keyvaluesets);
        String restore = sharedPreferences.getString(InputValue, "defaultValue");
        textView.setText(restore);
    }
}
