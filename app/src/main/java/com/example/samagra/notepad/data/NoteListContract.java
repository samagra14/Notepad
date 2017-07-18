package com.example.samagra.notepad.data;

import android.provider.BaseColumns;

/**
 * Created by samagra on 15/6/17.
 */

public class NoteListContract {
    public static final class NoteListEntry implements BaseColumns{
        public static final String TABLE_NAME = "noteslist";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_NOTES_TEXT = "notetext";
        public static final String COLUMN_NOTES_TITLE = "notetitle";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

    public static final class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_USER_PASSWORD = "user_password";
    }
}
