package com.jupiter.myfirstandriodapp.savingdata.savingfiles.internalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class SavingOnCacheFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_on_cache_file);
        String content = "";
        if(null == SavingOnInternalStorageActivity.cacheFile){
            return;
        }
        try{
            FileInputStream inputStream = new FileInputStream(SavingOnInternalStorageActivity.cacheFile);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            inputStream.close();
            content = new String(b);
        }catch (Exception e){
            e.printStackTrace();
        }

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(content);

        ViewGroup layout = (ViewGroup)findViewById(R.id.activity_saving_on_cache_file);
        layout.addView(textView);
    }
}
