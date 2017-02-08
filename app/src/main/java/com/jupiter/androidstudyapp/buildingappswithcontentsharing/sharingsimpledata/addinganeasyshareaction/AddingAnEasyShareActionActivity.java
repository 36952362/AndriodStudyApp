package com.jupiter.androidstudyapp.buildingappswithcontentsharing.sharingsimpledata.addinganeasyshareaction;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.jupiter.androidstudyapp.R;

public class AddingAnEasyShareActionActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_an_easy_share_action);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.sharemenu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share_text);
        shareActionProvider = new ShareActionProvider(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.menu_item_share_text){
            MenuItemCompat.setActionProvider(menuItem, shareActionProvider);
            doShareText();
        }
        else if(id == R.id.menu_item_share_picture){
            MenuItemCompat.setActionProvider(menuItem, shareActionProvider);
            doSharePicture();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void doShareText(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        setShareIntent(intent);
    }

    private  void doSharePicture(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://img0.imgtn.bdimg.com/it/u=1021712778,722195730&fm=23&gp=0.jpg"));
        setShareIntent(intent);
    }

    // Call to update the share intent
    private  void setShareIntent(Intent shareIntent){
        if(shareActionProvider != null){
            shareActionProvider.setShareIntent(shareIntent);
        }
    }
}
