package com.example.samagra.notepad;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesContentFragment extends Fragment {

    private EditText titleEditText,contentEditText;
    private Button saveBtn;
    private SQLiteDatabase mDb;

    public NotesContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_content,container,false);
        titleEditText =(EditText) view.findViewById(R.id.et_notes_title);
        contentEditText = (EditText) view.findViewById(R.id.et_notes_content);
        saveBtn = view.findViewById(R.id.save_btn);
        NotesDbHelper dbHelper = new NotesDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final NotesListFragment notesListFragment = new NotesListFragment();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToNoteList();
                fragmentManager.beginTransaction().replace(container.getId(),notesListFragment).commit();
            }
        });


        return view;

    }


    private void addToNoteList(){
        if(titleEditText.getText().toString().length()==0||contentEditText.getText().toString().length()==0)
            return;
        addNote(titleEditText.getText().toString(),contentEditText.getText().toString());

    }



    private long addNote(String title, String text){
        ContentValues values = new ContentValues();
        values.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT,text);
        values.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE,title);
        return mDb.insert(NoteListContract.NoteListEntry.TABLE_NAME,null, values);
    }
}
