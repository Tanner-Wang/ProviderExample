package com.example.android.providerexample.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/6.
 */

public class CoffeeProvider extends ContentProvider {

    final static String LOG_TAG = CoffeeProvider.class.getSimpleName();

    private CoffeeDbHelper mDbHelper;

    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    private final static int COFFEE = 1;

    private final static int COFFEE_ID = 2;

    static{
        matcher.addURI(CoffeeContract.CONTENT_AUTHORITY, CoffeeContract.PATH_COFFEE, COFFEE);
        matcher.addURI(CoffeeContract.CONTENT_AUTHORITY, CoffeeContract.PATH_COFFEE_ID, COFFEE_ID);
    }

    public CoffeeProvider(){}

    @Override
    public boolean onCreate() {
        mDbHelper = new CoffeeDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = matcher.match(uri);
        switch(match){

            case COFFEE: cursor = db.query(CoffeeContract.TABLE_COFFEE, projection, selection, selectionArgs,null, null, sortOrder);break;
            case COFFEE_ID:

                selection = CoffeeContract.CoffeeEntry.COLUMN_ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CoffeeContract.TABLE_COFFEE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default: throw new IllegalStateException("Can not query with unknown uri: "+uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = matcher.match(uri);
        switch (match) {

            case COFFEE:
                return "vnd.android.cursor.dir/"+CoffeeContract.TABLE_COFFEE;

            case COFFEE_ID:
                return "vnd.android.cursor.item/"+CoffeeContract.TABLE_COFFEE;

            default: throw new IllegalStateException("Illegal uri: " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = matcher.match(uri);
        switch(match){

            case COFFEE: return insetCoffee(uri, values);
            default: throw new IllegalStateException("Insertion is not supported for "+uri);

        }
    }

    private Uri insetCoffee(Uri uri, ContentValues values){

        String name = values.getAsString(CoffeeContract.CoffeeEntry.COLUMN_NAME);
        if (name.isEmpty()){
            throw new IllegalArgumentException("Coffee requires a name.");
        }

        String breed = values.getAsString(CoffeeContract.CoffeeEntry.COLUMN_BREED);
        if (breed.isEmpty()){
            throw new IllegalArgumentException("Coffee requires valid breed.");
        }

        Float price = values.getAsFloat(CoffeeContract.CoffeeEntry.COLUMN_PRICE);
        if (price == null || price <0){
            throw new IllegalArgumentException("Coffee requires valid price.");
        }

        Integer amount = values.getAsInteger(CoffeeContract.CoffeeEntry.COLUMN_AMOUNT);
        if (amount ==null || amount<0){
            throw new IllegalArgumentException("Coffee requires valid amount.");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(CoffeeContract.TABLE_COFFEE, null, values);
        if (id==-1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowDelete = db.delete(CoffeeContract.TABLE_COFFEE, selection, selectionArgs);
        if (rowDelete != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowUpdate = db.update(CoffeeContract.TABLE_COFFEE, values, selection, selectionArgs);
        if (rowUpdate >0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdate;
    }
}
