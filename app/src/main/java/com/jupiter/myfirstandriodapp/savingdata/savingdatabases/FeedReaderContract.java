package com.jupiter.myfirstandriodapp.savingdata.savingdatabases;

import android.provider.BaseColumns;

/**
 * Created by haijutan on 22/01/2017.
 */

public final class FeedReaderContract {

    private void FeedReaderContract(){}

    public static class  FeedReaderEntry implements BaseColumns{
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AGE = "age";
    }
}
