package com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingfiles.requestafile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jupiter.androidstudyapp.R;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class RequestAFileActivity extends AppCompatActivity {
    private static final int PICK_REQUEST_CODE = 0;

    private ParcelFileDescriptor parcelFileDescriptor = null;
    private FileDescriptor fileDescriptor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_afile);


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        startActivityForResult(Intent.createChooser(intent, "Please Choose"), PICK_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnIntent){
        if(requestCode != PICK_REQUEST_CODE){
            return;
        }

        if(RESULT_OK != resultCode){
            Log.e("failed:", "resultCode:" + resultCode);
            return;
        }

        Uri uri = returnIntent.getData();


        //Get FileDescription
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            Log.e("RequestAFileActivity", "File Not Found");
        }

        if(null != parcelFileDescriptor){
            fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        }

        //Get File MIME Type
        String mimeType = getContentResolver().getType(uri);

        //Get File Name and Size
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
        cursor.moveToFirst();
        String name = cursor.getString(nameIndex);
        long size = cursor.getLong(sizeIndex);

        String displayString = getString(R.string.text_file_description) + ":" + fileDescriptor.toString() + "\r\n";
        displayString += "Name:" + name + "\r\n";
        displayString += "Size:" + Long.toString(size);

        TextView textView = (TextView)findViewById(R.id.text_file_description);
        textView.setText(displayString);
    }
}
