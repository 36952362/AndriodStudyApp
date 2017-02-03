package com.jupiter.AndriodStudyApp.fragmentdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jupiter.myfirstandriodapp.R;

public class FragmentDemoActivity extends FragmentActivity implements HeadlinesFragment.OnHeadlinesSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null) {
                return;
            }
            HeadlinesFragment firstFragment = new HeadlinesFragment();
            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,firstFragment).commit();
        }
    }

    @Override
    public void onArticleSelected(int position) {
        ArticleFragment articleFragment = (ArticleFragment)getSupportFragmentManager().findFragmentById(R.id.article_fragment);
        if(articleFragment != null){
            // If article frag is available, we're in two-pane layout...
            // Call a method in the ArticleFragment to update its content
            articleFragment.updateArticleView(position);
        } else{
            // Otherwise, we're in the one-pane layout and must swap frags...
            articleFragment = new ArticleFragment();
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            articleFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, articleFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
