package br.com.pedrazzani.android.mymusicapp.services;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pedrazzani on 10/01/2017.
 */

public class LocalMusicService {

    private AppCompatActivity activity;

    public LocalMusicService(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Cursor getCursor() {

        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        return cursor;
    }

    public Cursor getCursor(String[] projection, String selectClause, String[] selectionArgs, String sortOrder) {

        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selectClause, selectionArgs, sortOrder);

        return cursor;
    }

}
