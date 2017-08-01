package com.example.lrh.sofare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LRH on 2017/7/18.
 */

public class MediaDB extends SQLiteOpenHelper {

    private SQLiteDatabase myDB;
    private final static int VERSION = 1;
    private static final String DB_NAME = "MediaDB.db";

    public static final String TABLE_NAME = "media";
    public static final String MEDIA_PICTURE = "picture";
    public static final String MEDIA_ID = "_id";
    public static final String MEDIA_NOTES_ID = "notesID";


    public MediaDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String notesTable = "CREATE TABLE " + TABLE_NAME +
//                " (" +
//                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                NOTES_TITLE + " TEXT NOT NULL, " +
//                NOTES_CONTENT + " TEXT NOT NULL, " +
//                NOTES_DATE + " TEXT NOT NULL " +
//                ")";
//        sqLiteDatabase.execSQL(notesTable);
        String mediaTable = "CREATE TABLE " + TABLE_NAME
                + "("
                + MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MEDIA_NOTES_ID + " TEXT NOT NULL,"
                + MEDIA_PICTURE + " TEXT NOT NULL"
                + ")";
        sqLiteDatabase.execSQL(mediaTable);
    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insertOneMedia(int mediaNotesID, String picturePath) {
        ContentValues c = new ContentValues();
        c.put(MEDIA_NOTES_ID, mediaNotesID);
        c.put(MEDIA_PICTURE, picturePath);
        return myDB.insert(TABLE_NAME, null, c);
    }

    public long updateOneMedia(int mediaID, int mediaNotesID, String picturePath) {
        ContentValues c = new ContentValues();
        c.put(MEDIA_NOTES_ID, mediaNotesID);
        c.put(MEDIA_PICTURE, picturePath);
        String where = MEDIA_ID + "=" + mediaID;
        return myDB.update(TABLE_NAME, c, where, null);
    }

    public Cursor queryAllMedia() {
        return myDB.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public int deleteOneMedia(int mediaID) {
        String where = MEDIA_ID + "=" + mediaID;
        return myDB.delete(TABLE_NAME, where, null);
    }

    public String getPicturePathOfNoteID(int noteID) {
        String where = MEDIA_NOTES_ID + "=" + noteID;
        Cursor cursor = myDB.query(TABLE_NAME, null, where, null, null, null, null);
        return cursor.getString(cursor.getColumnIndex(MEDIA_PICTURE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
