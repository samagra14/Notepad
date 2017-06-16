package com.example.samagra.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NotesListAdapter mAdapter;

    private SQLiteDatabase mDb;
    public NotesListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_notes);
        /**
         *
         * Setting up the database
         */
        NotesDbHelper dbHelper = new NotesDbHelper(getActivity());
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getNotes();

        mAdapter = new NotesListAdapter(cursor);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);



        return view;
    }
    private Cursor getNotes(){
        return mDb.query(NoteListContract.NoteListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NoteListContract.NoteListEntry.COLUMN_TIMESTAMP);
    }
}
