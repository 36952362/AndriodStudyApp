package com.jupiter.andriodstudyapp.gettingstarted.interactingwithotherapps.getingresultfromotherapps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jupiter.androidstudyapp.R;

public class GettingResultFromOtherAppActivity extends AppCompatActivity {
    private static final int PICK_CONTACT_REQUEST_CODE = 1;
    private static final int PERMISSION_READ_CONTACTS_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_result_from_other_app);
    }

    public void onGetOneContact(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(PackageManager.PERMISSION_GRANTED != permissionCheck) {
            // Should we show an explanation?
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_READ_CONTACTS_REQUEST_CODE);
            }
            return;
        }
        getOneContact();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(PICK_CONTACT_REQUEST_CODE == requestCode){
            if(RESULT_OK == resultCode){
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                TextView textView = (TextView)findViewById(R.id.textview_phone_number);
                textView.setText(number);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(PERMISSION_READ_CONTACTS_REQUEST_CODE == requestCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getOneContact();
            }
        }
    }

    private void getOneContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent,PICK_CONTACT_REQUEST_CODE);
    }
}
