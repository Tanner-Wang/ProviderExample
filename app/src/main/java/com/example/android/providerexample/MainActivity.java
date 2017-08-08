package com.example.android.providerexample;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.providerexample.data.CoffeeContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int mId = 0;

    private static Uri coffeeUri = Uri.parse("content://com.example.android.coffee/coffee");

    private static ViewHolder holder = new ViewHolder();

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

    private static class ViewHolder{
        public ViewHolder(){}
        TextView textQuery;
        TextView textInsert;
        TextView textUpdate;
        TextView textDelete;
    }
}
