package com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata.addinganeasyshareaction.AddingAnEasyShareActionActivity;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata.receivingsimpledatafromotherapps.ReceivingSimpleDataFromOtherAppsActivity;
import com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata.sendingsimpledatatootherapps.SendingSimpleDataToOtherAppsActivity;
import com.jupiter.androidstudyapp.R;

public class SharingSimpleDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_simple_data);
    }

    public void onSendingSimpleDataToOtherApps(View view) {
        Intent intent = new Intent(this, SendingSimpleDataToOtherAppsActivity.class);
        startActivity(intent);
    }

    public void onReceivingSimpleDataFromOtherApps(View view) {
        Intent intent = new Intent(this, ReceivingSimpleDataFromOtherAppsActivity.class);
        startActivity(intent);
    }

    public void onAddingAnEasyShareAction(View view) {
        Intent intent = new Intent(this, AddingAnEasyShareActionActivity.class);
        startActivity(intent);
    }
}
