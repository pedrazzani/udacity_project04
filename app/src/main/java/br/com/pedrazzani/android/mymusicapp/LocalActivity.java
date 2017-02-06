package br.com.pedrazzani.android.mymusicapp;

import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.Arrays;

import br.com.pedrazzani.android.mymusicapp.adapters.LocalMusicAdapter;
import br.com.pedrazzani.android.mymusicapp.services.LastFmService;
import br.com.pedrazzani.android.mymusicapp.services.LocalMusicService;

public class LocalActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private LocalMusicAdapter localMusicAdapter;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        final Cursor cursor = new LocalMusicService(this).getCursor();

        if (cursor != null) {


            cursor.moveToFirst();

            for(int i = 0; i < cursor.getColumnCount(); i++){
                System.out.println(cursor.getColumnName(i) + " : " + cursor.getString(1));
            }

            localMusicAdapter = new LocalMusicAdapter(this, R.layout.content_music, cursor, null, null, 0);

            ListView lista = (ListView) findViewById(R.id.lista_local_musica);
            lista.setAdapter(localMusicAdapter);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void play(Uri uri) {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        mediaPlayer.release();
        mediaPlayer = null;
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
}


