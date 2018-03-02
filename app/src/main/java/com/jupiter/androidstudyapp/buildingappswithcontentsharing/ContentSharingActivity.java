package com.jupiter.androidstudyapp.buildingappswithcontentsharing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.R;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles.FileSharingActivity;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata.SharingSimpleDataActivity;

public class ContentSharingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_sharing);
    }

    public void onSharingSimpleData(View view) {
        Intent intent = new Intent(this, SharingSimpleDataActivity.class);
        startActivity(intent);
    }

    public void onSharingFiles(View view) {
        Intent intent = new Intent(this, FileSharingActivity.class);
        startActivity(intent);
    }
}
