package com.jupiter.AndriodStudyApp.fragmentdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jupiter.myfirstandriodapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    public final static String ARG_POSITION = "com.jupiter.myfirstandriodapp.fragmentdemo.position";
    int mCurrentPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        Bundle args = getArguments();
        if(args != null){
            updateArticleView(args.getInt(ARG_POSITION));
        }else if(mCurrentPosition != -1){
            updateArticleView(mCurrentPosition);
        }
    }

    public void updateArticleView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.article);
        article.setText(Ipsum.Articles[position]);
        mCurrentPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
