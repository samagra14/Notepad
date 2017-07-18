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

import com.example.samagra.notepad.data.NoteListContract;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesContentFragment extends Fragment {

    private EditText titleEditText,contentEditText;
    private Button saveBtn;
    private SQLiteDatabase mDb;
    private long mId=-1;
    String user;

    public void setUser(String user) {
        this.user = user;
    }

    String title ="",text="";

    public NotesContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_content,container,false);
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        titleEditText =(EditText) view.findViewById(R.id.et_notes_title);
        contentEditText = (EditText) view.findViewById(R.id.et_notes_content);
        titleEditText.setText(title);
        contentEditText.setText(text);
        saveBtn = view.findViewById(R.id.save_btn);
        NotesDbHelper dbHelper = new NotesDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final NotesListFragment notesListFragment = new NotesListFragment();
        notesListFragment.setUser(user);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mId==-1)
                addToNoteList();
                else
                    updateNote();
                fragmentManager.beginTransaction().replace(container.getId(),notesListFragment).commit();
                getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });


        return view;

    }


    private void addToNoteList(){
        if(titleEditText.getText().toString().length()==0||contentEditText.getText().toString().length()==0)
            return;
        addNote(titleEditText.getText().toString(),contentEditText.getText().toString());
        titleEditText.getText().clear();
        contentEditText.getText().clear();

    }



    private long addNote(String title, String text){
        ContentValues values = new ContentValues();
        values.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT,text);
        values.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE,title);
        values.put(NoteListContract.NoteListEntry.COLUMN_USER_NAME,user);
        return mDb.insert(NoteListContract.NoteListEntry.TABLE_NAME,null, values);
    }
    public void setId(long id){
        this.mId = id;
    }

    public void setFragment(String title, String text){
        this.title=title;
        this.text = text;
    }

    private void updateNote(){
        if(titleEditText.getText().toString().length()==0|contentEditText.getText().toString().length()==0)
            return;
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE,titleEditText.getText().toString());
        contentValues.put(NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT,contentEditText.getText().toString());
        mDb.update(NoteListContract.NoteListEntry.TABLE_NAME, contentValues, NoteListContract.NoteListEntry._ID+" = "+mId,null);
        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }
}
