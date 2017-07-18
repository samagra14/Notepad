package com.example.samagra.notepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samagra.notepad.data.NoteListContract;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesListFragment extends Fragment implements NotesListAdapter.ListItemClickListener {
    private RecyclerView mRecyclerView;
    private NotesListAdapter mAdapter;
    SessionManager session;
    private TextView textView;

    private String user;

    public void setUser(String user) {
        this.user = user;
    }

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
        user = session.getUserDetails();
        Cursor cursor = getNotes();
        textView = view.findViewById(R.id.tv_empty_view);

        mAdapter = new NotesListAdapter(cursor,this);
        if (mAdapter.getItemCount()==0) {
            textView.setVisibility(View.VISIBLE);
        }
        else
            textView.setVisibility(View.GONE);

        GridLayoutManager layoutManager = new GridLayoutManager(container.getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeNote(id);
                mAdapter.swapCursor(getNotes());
                if (mAdapter.getItemCount()==0)
                    textView.setVisibility(View.VISIBLE);
                else
                    textView.setVisibility(View.GONE);


            }
        }).attachToRecyclerView(mRecyclerView);



        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(getActivity());
        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();
        user = session.getUserDetails();

    }

    private Cursor getNotes(){
        return mDb.query(NoteListContract.NoteListEntry.TABLE_NAME,
                null,
                NoteListContract.NoteListEntry.COLUMN_USER_NAME + " = '"+user+"'",
                null,
                null,
                null,
                NoteListContract.NoteListEntry.COLUMN_TIMESTAMP);
    }

    private boolean removeNote(long id){
        return mDb.delete(NoteListContract.NoteListEntry.TABLE_NAME, NoteListContract.NoteListEntry._ID + " = "+
        id,null)>0;
    }


    @Override
    public void onListItemClick(int clickedItemIndex, long id) {


        Cursor tempCursor = mDb.query(NoteListContract.NoteListEntry.TABLE_NAME,null,
                NoteListContract.NoteListEntry._ID+" = "+id,
                null,
                null,
                null,
                null);
        tempCursor.moveToFirst();
        NotesContentFragment fragment = new NotesContentFragment();
        String title = tempCursor.getString(tempCursor.getColumnIndex(NoteListContract.NoteListEntry.COLUMN_NOTES_TITLE));
        String text = tempCursor.getString(tempCursor.getColumnIndex(NoteListContract.NoteListEntry.COLUMN_NOTES_TEXT));
        fragment.setId(id);
        fragment.setUser(user);
        fragment.setFragment(title,text);
        FragmentManager tempManager = getActivity().getSupportFragmentManager();
        tempManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();


    }
}
