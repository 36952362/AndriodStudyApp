package com.jupiter.myfirstandriodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.myfirstandriodapp.fragmentdemo.FragmentDemoActivity;
import com.jupiter.myfirstandriodapp.launchanotheractivity.LaunchAnotherActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchAnotherActivity(View view) {
        Intent intent = new Intent(this, LaunchAnotherActivity.class);
        startActivity(intent);
    }

    public void fragmentDemon(View view) {
        Intent intent = new Intent(this, FragmentDemoActivity.class);
        startActivity(intent);
    }
}
