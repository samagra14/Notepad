package com.example.samagra.notepad.data;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by samagra on 19/7/17.
 * A POJO representing a note
 */


public class Note {

    private final String mId;
    @Nullable
    private final String mTitle;
    @Nullable
    private final String mDescription;
    @Nullable
    private final String mImageUrl;


    public Note(@Nullable String title, @Nullable String description, @Nullable String imageUrl) {
        mId = UUID.randomUUID().toString();
        this.mTitle = title;
        this.mDescription = description;
        this.mImageUrl = imageUrl;
    }
}
