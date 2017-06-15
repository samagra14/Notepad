package com.example.samagra.notepad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by samagra on 15/6/17.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {

    private int mNumberItems;

    public NotesListAdapter(int mNumberItems) {
        this.mNumberItems = mNumberItems;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_list_item,parent,false);
        NotesViewHolder viewHolder = new NotesViewHolder(view);
        viewHolder.titleTextView.setText("Title");
        viewHolder.noteTextView.setText("Note");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView noteTextView;

        public NotesViewHolder(View itemView) {
            super(itemView);
            titleTextView =(TextView) itemView.findViewById(R.id.tv_notes_title);
            noteTextView = (TextView) itemView.findViewById(R.id.tv_notes_content);


        }
    }
}
