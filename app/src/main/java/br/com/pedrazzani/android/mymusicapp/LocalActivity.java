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
import android.widget.ListView;

import java.io.IOException;

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

        final Cursor c = new LocalMusicService(this).getCursor();

        if (c != null) {
            c.moveToFirst();
            localMusicAdapter = new LocalMusicAdapter(getApplicationContext(), R.layout.content_music, c, null, null, 0);

            ListView lista = (ListView) findViewById(R.id.lista_local_musica);
            lista.setAdapter(localMusicAdapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    c.moveToPosition(position);

                    Log.i(TAG, c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)));

                    Bundle bundle = new Bundle();
                    bundle.putString("URI",view.getTag().toString());
                    bundle.putString("MUSICA",c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE)));
                    bundle.putString("ARTISTA",c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
                    bundle.putString("DURACAO",c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                    bundle.putParcelable("ALBUM",
                            new LastFmService().searchAlbum(
                                    c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)),
                                    c.getString(c.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))));

                    Intent intent = new Intent(getBaseContext(),PlayActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            });

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


