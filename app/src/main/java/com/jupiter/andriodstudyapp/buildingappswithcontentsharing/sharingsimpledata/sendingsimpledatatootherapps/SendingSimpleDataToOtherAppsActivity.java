package com.jupiter.andriodstudyapp.buildingappswithcontentsharing.sharingsimpledata.sendingsimpledatatootherapps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.R;

import java.util.ArrayList;

public class SendingSimpleDataToOtherAppsActivity extends AppCompatActivity {
    private static final String EXTRA_TEXT = "This is my text to send.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_simple_data_to_other_apps);
    }

    public void onSendTextContent(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, EXTRA_TEXT);
        intent.setType("text/plain");
        //startActivity(intent);
        //if you call Intent.createChooser(), passing it your Intent object, it returns a version of your intent that will always display the chooser
        //This has some advantages:
        //1. Even if the user has previously selected a default action for this intent, the chooser will still be displayed.
        //2. If no applications match, Android displays a system message.
        //3. You can specify a title for the chooser dialog
        startActivity(Intent.createChooser(intent, "Please Choose"));
    }

    public void onSendBinaryContent(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=60c889ffb051f819f125044ce28f2dd0/ae51f3deb48f8c54cd34cafb3a292df5e1fe7f7a.jpg"));
        intent.setType("image/jpeg");
        startActivity(Intent.createChooser(intent, "Please Choose"));
    }

    public void onSendMultipleContent(View view) {
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.parse("http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=60c889ffb051f819f125044ce28f2dd0/ae51f3deb48f8c54cd34cafb3a292df5e1fe7f7a.jpg"));
        imageUris.add(Uri.parse("http://img0.imgtn.bdimg.com/it/u=1021712778,722195730&fm=23&gp=0.jpg"));

        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share Image to .."));
    }
}
