package com.example.lrh.sofare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LRH on 2017/7/11.
 */

public class NoteDB extends SQLiteOpenHelper {
    private SQLiteDatabase myDB;
    private final static int VERSION = 1;
    private static final String DBNAME = "notesDB.db";


    public static final String NOTE_ID = "_id";
    public final static String TABLE_NAME = "notes";
    public final static String NOTES_TITLE = "title";
    public final static String NOTES_CONTENT = "content";
    public final static String NOTES_DATE = "date";

    public NoteDB(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String notesTable = "CREATE TABLE " + TABLE_NAME +
                " (" +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTES_TITLE + " TEXT NOT NULL, " +
                NOTES_CONTENT + " TEXT NOT NULL, " +
                NOTES_DATE + " TEXT NOT NULL " +
                ")";
        sqLiteDatabase.execSQL(notesTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insertOneNote(String title, String content,String date) {
        ContentValues cv = new ContentValues();
        cv.put(NOTES_TITLE, title);
        cv.put(NOTES_CONTENT, content);
        cv.put(NOTES_DATE, date);
        return myDB.insert(TABLE_NAME, null, cv);
    }

    public long upateOneNote(int id, String title, String content, String date) {
        ContentValues cv = new ContentValues();
        cv.put(NOTES_TITLE, title);
        cv.put(NOTES_CONTENT, content);
        cv.put(NOTES_DATE, date);
        String where = NOTE_ID + "=" + id;
        return myDB.update(TABLE_NAME, cv, where, null);
    }
    public Cursor queryAllNotes(){
        return myDB.query(TABLE_NAME, null, null, null, null, null, null);
    }
    public int deleteOneNote(int id){
        String where = NOTE_ID + "=" + id;
        return myDB.delete(TABLE_NAME, where, null);
    }
}
