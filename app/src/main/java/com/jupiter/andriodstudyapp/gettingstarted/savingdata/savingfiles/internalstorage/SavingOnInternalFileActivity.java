package com.jupiter.andriodstudyapp.gettingstarted.savingdata.savingfiles.internalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jupiter.androidstudyapp.R;

import java.io.FileInputStream;

public class SavingOnInternalFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_on_internal_file);
        String content = "";
        try{
            FileInputStream inputStream = openFileInput(SavingOnInternalStorageActivity.fileInternalName);
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

        ViewGroup layout = (ViewGroup)findViewById(R.id.activity_saving_on_internal_file);
        layout.addView(textView);
    }
}
