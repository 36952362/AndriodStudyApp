package com.jupiter.andriodstudyapp.gettingstarted.launchanotheractivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jupiter.androidstudyapp.R;

public class LaunchAnotherActivity extends AppCompatActivity {
    public final static String EXTRA_EDIT_MESSAGE = "com.jupiter.androidstudyapp.launchanotheractivity.EDIT_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_another);
    }

    public void displayEditMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_EDIT_MESSAGE, message);
        startActivity(intent);
    }
}
