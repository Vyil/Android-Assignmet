package com.example.djim.marsrover.datalayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.djim.marsrover.domain.RoverCollection;

import java.util.ArrayList;

/**
 * Created by Djim on 14-3-2018.
 */

public class RoverData extends SQLiteOpenHelper{

    private static final String TAG = "RoverData";

    private static final String DB_NAME ="rover.db";
    private static final String TABLE_NAME="rover";

    public RoverData(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    Log.i(TAG,"onCreate() called");

    sqLiteDatabase.execSQL("CREATE TABLE `rover` (\n" +
            "\t`id`\tTEXT,\n" +
            "\t`cameraName`\tTEXT NOT NULL,\n" +
            "\t`imageUrl`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(`id`)\n" +
            ");");

    Log.i(TAG,"Database " + DB_NAME + " met tabel: " + TABLE_NAME+ " aangemaakt");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG," onUprade() called");

        String drop = "DROP TABLE" + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(drop,null);

        onCreate(sqLiteDatabase);
    }

    public void addImage(RoverCollection rover){
        Log.i(TAG,"addImage() called");

        ContentValues add = new ContentValues();
        add.put("id",rover.getId());
        add.put("cameraName", rover.getCameraName());
        add.put("imageURL", rover.getImageURL());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,add);
        Log.i(TAG,"image added");
        db.close();
    }

    public ArrayList<RoverCollection> getAllImages(){
        Log.i(TAG,"getAllImages called");
        ArrayList<RoverCollection> images = new ArrayList<>();

        String query = "SELECT * FROM "+TABLE_NAME+ ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String cameraName = cursor.getString(cursor.getColumnIndex("cameraName"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));

            //Geef gegevens mee aan rover instantie
            RoverCollection addImage = new RoverCollection(id,cameraName,imageUrl);

            Log.i(TAG,"Image: " + addImage.getId()+ " found and passed to main");
            images.add(addImage);

            cursor.moveToNext();
        }
        Log.i(TAG,"Image size: "+ images.size());

        return images;
    }

    public ArrayList<RoverCollection> getSpecificImages(String camera) {
        Log.i(TAG, "getSpecificImages called for: " + camera);
        ArrayList<RoverCollection> images = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE cameraName ='" + camera + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            // Lezen van gegevens van elke foto
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String cameraName = cursor.getString(cursor.getColumnIndex("cameraName"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));

            // Omzetten van gelezen gegevens naar MarsRoverPhoto instantie
            RoverCollection add = new RoverCollection(id, cameraName, imageUrl);

            // Toevoegen aan lijst van terug te geven fotos
            Log.i(TAG, "added: " + camera + "with id: " + id + " to the list");
            images.add(add);

            // Naar volgende foto
            cursor.moveToNext();
        }
        return images;
    }
}
