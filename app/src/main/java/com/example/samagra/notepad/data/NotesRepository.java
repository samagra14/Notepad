package com.example.samagra.notepad.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by samagra on 19/7/17.
 * Main entry point for accessing notes data.
 */

public interface NotesRepository {

    interface LoadNotesCallback{
        void onNotesLoaded(Cursor cursor);
    }

    interface GetNoteCallback{
        void onNoteLoaded(Note note);
    }

    void getNotes(@NonNull LoadNotesCallback callback);

    void getNote(@NonNull String noteId, @NonNull GetNoteCallback callback);

    void saveNote(@NonNull Note note);

    void refreshData();
}
