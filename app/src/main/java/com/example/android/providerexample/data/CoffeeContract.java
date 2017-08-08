package com.example.android.providerexample.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2017/8/5.
 */

public final class CoffeeContract {

    final static String TABLE_COFFEE = "coffee";

    final static String CONTENT_AUTHORITY = "com.example.android.coffee";

    public final static Uri BASE_COLUMN_URI = Uri.parse("cotent://" + CONTENT_AUTHORITY);

    public final static String PATH_COFFEE = "coffee";

    public final static String PATH_COFFEE_ID = "/#";

    public static class CoffeeEntry implements BaseColumns {

        final static String COLUMN_ID = BaseColumns._ID;

        final static String COLUMN_NAME = "name";

        final static String COLUMN_BREED = "breed";

        final static String COLUMN_PRICE = "price";

        final static String COLUMN_AMOUNT = "amount";

        final static String CREATE_TABLE = "CREATE TABLE " + TABLE_COFFEE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_BREED + " TEXT NOT NULL, " +
                COLUMN_PRICE + " REAL DEFAULT 0, " +
                COLUMN_AMOUNT + " INTEGER DEFAULT 0)";

    }
}
