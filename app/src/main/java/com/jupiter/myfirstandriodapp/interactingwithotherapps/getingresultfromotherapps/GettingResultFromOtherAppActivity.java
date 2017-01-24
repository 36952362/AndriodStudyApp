package com.jupiter.myfirstandriodapp.interactingwithotherapps.getingresultfromotherapps;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;

import org.w3c.dom.Text;

public class GettingResultFromOtherAppActivity extends AppCompatActivity {
    private static final int PICK_CONTACT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_result_from_other_app);
    }

    public void onGetOneContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent,PICK_CONTACT_REQUEST_CODE);
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
}
