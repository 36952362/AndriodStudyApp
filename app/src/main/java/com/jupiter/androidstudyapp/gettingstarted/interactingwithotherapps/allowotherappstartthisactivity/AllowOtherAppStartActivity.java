package com.jupiter.androidstudyapp.gettingstarted.interactingwithotherapps.allowotherappstartthisactivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jupiter.androidstudyapp.R;

public class AllowOtherAppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_other_app_start);

        Intent intent = getIntent();
        Uri data = intent.getData();
        if(intent.getType().indexOf("image/") != -1){
            System.out.print("got");
        }else if(intent.getType().equals("text/plain")){

        }

        // Create intent to deliver some kind of result data
        Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
