package com.jupiter.AndriodStudyApp.savingdata.savingfiles.externalstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;

import java.io.File;

public class SavingOnExternalStoargeActivity extends AppCompatActivity {

    private static final  String albumName = "tmpAlbum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_on_external_stoarge);
        checkStorageStatus();
        checkStorageSpace();
    }

    private  void checkStorageStatus(){
        TextView textView = (TextView)findViewById(R.id.text_storage_status);

        String description = "External Storage Status:";

        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            description += " Read and Write";
        }else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            description += " Read Only";
        }else
            description += "Not Accessiable";
        textView.setText(description);
    }

    private void checkStorageSpace(){
        TextView textView = (TextView)findViewById(R.id.text_storage_space);
        File publicFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        String description = "public free space:" + publicFile.getFreeSpace();
        description += ",total space:" + publicFile.getTotalSpace() + "\n";
        publicFile.deleteOnExit();
        File privateFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName);
        description += "private free space:" + privateFile.getFreeSpace();
        description += ", total space:" + privateFile.getTotalSpace();
        textView.setText(description);
        privateFile.deleteOnExit();
    }
}
