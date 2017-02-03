package com.jupiter.andriodstudyapp.interactingwithotherapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.R;
import com.jupiter.andriodstudyapp.interactingwithotherapps.getingresultfromotherapps.GettingResultFromOtherAppActivity;
import com.jupiter.andriodstudyapp.interactingwithotherapps.sendingusertoanotherapps.SendingUserToAnotherAppActivity;

public class InteractingWithOtherAppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interacting_with_other_apps);
    }

    public void onSendingUserToAnotherApp(View view) {
        Intent intent = new Intent(this, SendingUserToAnotherAppActivity.class);
        startActivity(intent);
    }

    public void onGetResultFromOtherApp(View view) {
        Intent intent = new Intent(this, GettingResultFromOtherAppActivity.class);
        startActivity(intent);
    }
}
