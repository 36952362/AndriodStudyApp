package com.jupiter.AndriodStudyApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.AndriodStudyApp.fragmentdemo.FragmentDemoActivity;
import com.jupiter.AndriodStudyApp.interactingwithotherapps.InteractingWithOtherAppsActivity;
import com.jupiter.AndriodStudyApp.launchanotheractivity.LaunchAnotherActivity;
import com.jupiter.AndriodStudyApp.savingdata.SavingDataDemoActivity;
import com.jupiter.myfirstandriodapp.R;

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
}
