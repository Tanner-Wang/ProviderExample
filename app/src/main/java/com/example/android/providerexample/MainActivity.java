package com.example.android.providerexample;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.providerexample.data.CoffeeContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int mId = 0;

    private static Uri coffeeUri = Uri.parse("content://com.example.android.coffee/coffee");

    private static ViewHolder holder = new ViewHolder();

    private LruCache<String, Bitmap> bitmapLruCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holder.textQuery = (TextView) findViewById(R.id.edit_query);
        holder.textInsert = (TextView) findViewById(R.id.edit_insert);
        holder.textUpdate = (TextView) findViewById(R.id.edit_update);
        holder.textDelete = (TextView) findViewById(R.id.edit_delete);

        holder.textQuery.setOnClickListener(this);
        holder.textInsert.setOnClickListener(this);
        holder.textUpdate.setOnClickListener(this);
        holder.textDelete.setOnClickListener(this);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/8;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount()/1024;
            }
        };



    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch(id){

            case R.id.edit_query:
                Cursor cursor = getContentResolver().query(coffeeUri, null, null, null, null);
                if (cursor.moveToFirst()){
                    Toast.makeText(this, "query succeed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edit_insert:
                ContentValues values = new ContentValues();
                values.put("name", "a");
                values.put("breed", "apple");
                values.put("price", 9.9);
                values.put("amount", 22);
                Uri uri = getContentResolver().insert(coffeeUri, values);
                if (uri != null){
                    mId = (int) ContentUris.parseId(uri);
                    Toast.makeText(this, "insert succeed."+mId,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.edit_update:
                    ContentValues values1 = new ContentValues();
                    String projection = "name"+"=?";
                    String[] projectionArgs = new String[]{"a"};
                    values1.put("name", "banana");
                    int banana = getContentResolver().update(coffeeUri, values1, projection, projectionArgs);
                    if (banana > 0){
                        Toast.makeText(this, "update succeed.", Toast.LENGTH_SHORT).show();
                    }
                break;
            case R.id.edit_delete:
                if (mId>=0) {
                    String selection = CoffeeContract.CoffeeEntry._ID+"=?";
                    String[] selectionArgs = new String[]{String.valueOf(mId)};
                    int deleteId = getContentResolver().delete(coffeeUri, selection, selectionArgs);
                    if (deleteId > 0) {
                        Toast.makeText(this, "delete succeed."+mId, Toast.LENGTH_SHORT).show();
                        mId -= 1;
                    }
                }
                break;

        }

    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null) {
            bitmapLruCache.put(key, bitmap);
        }
    }
    public Bitmap getBitmapFromMemoryCache(String key){
        return bitmapLruCache.get(key);
    }
    public void loadBitmap(int resId, ImageView imageView){
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemoryCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.empty);
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer, Void, Bitmap>{
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            return null;
        }
    }



    private static class ViewHolder{
        public ViewHolder(){}
        TextView textQuery;
        TextView textInsert;
        TextView textUpdate;
        TextView textDelete;
    }
}
