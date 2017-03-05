package com.haibo.mobile.android.selfiefun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyan on 3/4/17.
 */

public class SelfieDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Selfie.db";

    public static final String SELFIE_TABLE_NAME = "selfies";

    public static final String  SELFIE_COLUMN_ID ="id";

    public static final String  SELFIE_COLUMN_TIMESTAMP ="timestamp";

    public static final String  SELFIE_COLUMN_BITMAP ="bitmap";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SELFIE_TABLE_NAME + " (" +
                    SELFIE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SELFIE_COLUMN_TIMESTAMP + " TEXT," +
                    SELFIE_COLUMN_BITMAP + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SELFIE_TABLE_NAME;

    public SelfieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insert(Selfie selfie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SELFIE_COLUMN_ID, selfie.getId());
        contentValues.put(SELFIE_COLUMN_TIMESTAMP, selfie.getImage_timestamp());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selfie.getBitmap().compress(Bitmap.CompressFormat.PNG, 0, stream);
        contentValues.put(SELFIE_COLUMN_BITMAP, stream.toByteArray());
        db.insert(SELFIE_TABLE_NAME, null, contentValues);
    }

    public List<Selfie> getAllSelfies() {
        List<Selfie> selfies = new ArrayList<>();

        String[] projection = {
                SELFIE_COLUMN_ID,
                SELFIE_COLUMN_TIMESTAMP,
                SELFIE_COLUMN_BITMAP
        };

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SELFIE_TABLE_NAME, null );
        System.out.println(res.getCount());
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Integer id = res.getInt(res.getColumnIndex(SELFIE_COLUMN_ID));
            String timestamp = res.getString(res.getColumnIndex(SELFIE_COLUMN_TIMESTAMP));
            byte[] imageBytes = res.getBlob(res.getColumnIndex(SELFIE_COLUMN_BITMAP));
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            Selfie selfie = new Selfie(id, timestamp, image);
            selfies.add(selfie);
            res.moveToNext();
        }
        return selfies;
    }

    public Integer deleteSelfie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SELFIE_TABLE_NAME,
                SELFIE_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
}
