package com.jupiter.andriodstudyapp.interactingwithotherapps.sendingusertoanotherapps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jupiter.androidstudyapp.R;

import java.util.Calendar;
import java.util.List;


public class SendingUserToAnotherAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_user_to_another_app);
    }

    public void onDialPhone(View view) {
        Uri uri = Uri.parse("tel:5551234");
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // Start an activity if it's safe
        if(activities.size() > 0 ) {
            startActivity(intent);
        }
    }

    public void onViewMap(View view) {
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Uri uri = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0) {
            startActivity(intent);
        }

    }

    public void onViewWeb(View view) {
        Uri uri = Uri.parse("http://www.baidu.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0) {
            startActivity(intent);
        }
    }

    public void onSendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"foo@example.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://www.baidu.com"));
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0) {
            startActivity(intent);
        }
    }

    public void onCreateCalendarEvent(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 1, 23, 19, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 1, 23, 20, 30);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.TITLE, "Ninja Class");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "secret dojo");
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0) {
            startActivity(intent);
        }
    }

    public void onChooseApps(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent chooser = Intent.createChooser(intent, "Select one App");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(chooser);
        }
    }
}
