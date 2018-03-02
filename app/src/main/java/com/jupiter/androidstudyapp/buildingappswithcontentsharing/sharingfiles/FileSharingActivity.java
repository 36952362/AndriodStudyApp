package com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.R;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles.requestafile.RequestAFileActivity;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles.sharingafile.SharingAFileActivity;

public class FileSharingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sharing);
    }

    public void onRequestAFile(View view) {
        Intent intent = new Intent(this, RequestAFileActivity.class);
        startActivity(intent);
    }
}
