package com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles.sharingafile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jupiter.androidstudyapp.R;

import java.io.File;

//TODO: Other Activity how to intent this activity



public class SharingAFileActivity extends AppCompatActivity {
    //文件根目录
    private File privateRootDir;

    //images子目录
    private File imagesDir;

    //images子目录下的所有文件
    private File[] imagesFiles;

    //images子目录下的所有文件名
    private String[] imagesFilenames;

    private ListView listView;

    private Intent resultIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_afile);

        resultIntent = new Intent("com.jupiter.androidstudyapp.ACTION_RETURN_FILE");

        privateRootDir = getFilesDir();
        imagesDir = new File(privateRootDir, "images");
        imagesFiles = imagesDir.listFiles();
        //初始化返回值

        File requestFile = new File("/storge/emulated/0/DCIM/Camera/IMG_20170224_155349.jpg");
        Uri fileUri = null;
        try{
            fileUri = FileProvider.getUriForFile(SharingAFileActivity.this, "com.jupiter.androidstudyapp.fileprovider", requestFile);
        }
        catch (Exception e){
            Log.e("Cannot find the log", "detail information:" + e.getMessage());
        }

        if(null != fileUri){
            resultIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
            resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            setResult(Activity.RESULT_OK, resultIntent);
        }
        else{
            setResult(Activity.RESULT_CANCELED, null);
        }

        finish();


        /*
        if(null != listView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> adapterView, View view , int position, long rowId){
                    File requestFile = new File(imagesFilenames[position]);
                    Uri fileUri = null;
                    try{
                        fileUri = FileProvider.getUriForFile(SharingAFileActivity.this, "com.jupiter.androidstudyapp.fileprovidr", requestFile);
                    }
                    catch(IllegalArgumentException e){
                        Log.e("File Selector", "The selected file can't be shared: " + imagesFilenames[position]);
                    }

                    if(null != fileUri){
                        resultIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        resultIntent.setDataAndType(fileUri, getContentResolver().getType(fileUri));
                        setResult(Activity.RESULT_OK, resultIntent);
                    }
                    else{
                        resultIntent.setDataAndType(null, "");
                        setResult(Activity.RESULT_CANCELED, resultIntent);
                    }
                }

                public void onDoneClick(View view){
                    finish();
                }
            });
        }
        */
    }
}
