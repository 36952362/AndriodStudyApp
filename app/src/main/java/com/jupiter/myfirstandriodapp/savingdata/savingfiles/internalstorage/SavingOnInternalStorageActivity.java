package com.jupiter.myfirstandriodapp.savingdata.savingfiles.internalstorage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jupiter.myfirstandriodapp.R;

import java.io.File;
import java.io.FileOutputStream;

public class SavingOnInternalStorageActivity extends AppCompatActivity {
    public final static String fileInternalName = "internalName";
    public final static String fileCacheName = "cacheName";
    public static File cacheFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_on_internal_storage);
    }

    public void onSavingOnInternalFileAndDisplay(View view) {
        EditText editText = (EditText)findViewById(R.id.edit_saving_on_internal_storage);
        String content = editText.getText().toString();
        try{
            FileOutputStream outputStream = openFileOutput(fileInternalName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, SavingOnInternalFileActivity.class);
        startActivity(intent);
    }

    public void onSavingOnCacheFileAndDispaly(View view) {
        EditText editText = (EditText)findViewById(R.id.edit_saving_on_internal_storage);
        String content = editText.getText().toString();
        try{
            cacheFile = File.createTempFile(fileCacheName, null, getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(cacheFile);
            outputStream.write(content.getBytes());
            outputStream.close();
            cacheFile.deleteOnExit();
        }catch (Exception e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, SavingOnCacheFileActivity.class);
        startActivity(intent);
    }
}
