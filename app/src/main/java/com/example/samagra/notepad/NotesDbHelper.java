package com.example.samagra.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Notepad.db";
    static final int DATABASE_VERSION = 1;

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE "+
                NoteListContract.NoteListEntry.TABLE_NAME+" ( "+
                NoteListContract.NoteListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteListContract.NoteListEntry.COLUMN_USER_NAME +" TEXT, "+
                NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE + " TEXT NOT NULL, "+
                NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT + " TEXT NOT NULL, "+
                NoteListContract.NoteListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                " ); ";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + NoteListContract.NoteListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);


    }
}
