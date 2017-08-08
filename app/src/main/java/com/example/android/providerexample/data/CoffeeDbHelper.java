package com.example.android.providerexample.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.providerexample.data.CoffeeContract.CoffeeEntry.CREATE_TABLE;

/**
 * Created by Administrator on 2017/8/5.
 */

public class CoffeeDbHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "coffee.db";

    private final static int DATABASE_VERSION = 1;

    public CoffeeDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        switch(oldVersion){

            case 1: db.execSQL(CREATE_TABLE);break;

        }

    }
}
