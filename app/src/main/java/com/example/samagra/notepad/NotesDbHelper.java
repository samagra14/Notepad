package com.example.samagra.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.samagra.notepad.data.NoteListContract;

import java.util.ArrayList;
import java.util.List;

import static com.example.samagra.notepad.data.NoteListContract.UserEntry.COLUMN_USER_PASSWORD;

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
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " +
                NoteListContract.NoteListEntry.TABLE_NAME + " ( " +
                NoteListContract.NoteListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteListContract.NoteListEntry.COLUMN_USER_NAME + " TEXT , " +
                NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE + " TEXT NOT NULL, " +
                NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT + " TEXT NOT NULL, " +
                NoteListContract.NoteListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                " ); ";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +
                NoteListContract.UserEntry.TABLE_NAME + " ( " +
                NoteListContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteListContract.UserEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL " +
                " ); ";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + NoteListContract.NoteListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);


    }


    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteListContract.UserEntry.COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(NoteListContract.UserEntry.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                NoteListContract.UserEntry._ID,
                NoteListContract.UserEntry.COLUMN_USER_NAME,
                NoteListContract.UserEntry.COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                NoteListContract.UserEntry.COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(NoteListContract.UserEntry.TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(NoteListContract.UserEntry._ID))));
                user.setUserName(cursor.getString(cursor.getColumnIndex(NoteListContract.UserEntry.COLUMN_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(NoteListContract.UserEntry.COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to check user exist or not
     *
     * @param userName
     * @return true/false
     */
    public boolean checkUser(String userName) {

        // array of columns to fetch
        String[] columns = {
                NoteListContract.UserEntry._ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = NoteListContract.UserEntry.COLUMN_USER_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {userName};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(NoteListContract.UserEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param userName
     * @param password
     * @return true/false
     */
    public boolean checkUser(String userName, String password) {

        // array of columns to fetch
        String[] columns = {
                NoteListContract.UserEntry.COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = NoteListContract.UserEntry.COLUMN_USER_NAME + " = ?" + " AND " + NoteListContract.UserEntry.COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {userName, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(NoteListContract.UserEntry.TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}


