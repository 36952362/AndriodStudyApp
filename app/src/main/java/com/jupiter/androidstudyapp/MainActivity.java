package com.jupiter.androidstudyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.buildingappswithcontentsharing.ContentSharingActivity;
import com.jupiter.androidstudyapp.gettingstarted.fragmentdemo.FragmentDemoActivity;
import com.jupiter.androidstudyapp.gettingstarted.interactingwithotherapps.InteractingWithOtherAppsActivity;
import com.jupiter.androidstudyapp.gettingstarted.launchanotheractivity.LaunchAnotherActivity;
import com.jupiter.androidstudyapp.gettingstarted.savingdata.SavingDataDemoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLaunchAnotherActivityDemo(View view) {
        Intent intent = new Intent(this, LaunchAnotherActivity.class);
        startActivity(intent);
    }

    public void onFragmentDemo(View view) {
        Intent intent = new Intent(this, FragmentDemoActivity.class);
        startActivity(intent);
    }

    public void onSavingDataDemo(View view) {
        Intent intent = new Intent(this, SavingDataDemoActivity.class);
        startActivity(intent);
    }

    public void onInteractingWithOtherApps(View view) {
        Intent intent = new Intent(this, InteractingWithOtherAppsActivity.class);
        startActivity(intent);
    }

    public void onContentSharing(View view) {
        Intent intent = new Intent(this, ContentSharingActivity.class);
        startActivity(intent);
    }
}
