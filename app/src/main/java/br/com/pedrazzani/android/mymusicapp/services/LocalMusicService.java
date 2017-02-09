package br.com.pedrazzani.android.mymusicapp.services;

import android.database.Cursor;
import android.net.Uri;
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

    public Cursor getCursor(Uri uri, String[] projection, String selectionClause, String[] selectionArgs, String sortOrder) {

        Cursor cursor = activity.getContentResolver().query(
                uri, projection, selectionClause, selectionArgs, sortOrder);

        return cursor;
    }

}
