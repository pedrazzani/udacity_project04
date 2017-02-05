package br.com.pedrazzani.android.mymusicapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.pedrazzani.android.mymusicapp.entidades.Album;
import br.com.pedrazzani.android.mymusicapp.listeners.SeekBarListener;

public class PlayActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        //Carrega informações da música nos campo de texto
        TextView musica = (TextView) findViewById(R.id.musica_player_text_view);
        TextView artista = (TextView) findViewById(R.id.artista_player_text_view);
        TextView duracao = (TextView) findViewById(R.id.duracao_player_text_view);
        musica.setText(bundle.get("MUSICA").toString());
        artista.setText(bundle.get("ARTISTA").toString());

        //Carrega a foto do Álbum da Música - Se existir
        Album album = bundle.getParcelable("ALBUM");
        new DownloadImageTask().execute(album.getUrlImageLarge());

        //Ajusta a duração da trilha
        Long duracaoMS = Long.parseLong(bundle.get("DURACAO").toString());
        String saida = geraTempo(duracaoMS);
        duracao.setText(saida);

        //Cria a Uri do local do arquivo
        final Uri uri = Uri.parse(bundle.get("URI").toString());

        //Inicia a Classe MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageButton play = (ImageButton) findViewById(R.id.play_image_button);
        play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                play();
                ((ImageButton) findViewById(R.id.play_image_button)).setElevation(0f);
                ((ImageButton) findViewById(R.id.pause_image_button)).setElevation(4f);
            }
        });

        ImageButton pause = (ImageButton) findViewById(R.id.pause_image_button);
        pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                pause();
                ((ImageButton) findViewById(R.id.play_image_button)).setElevation(4f);
                ((ImageButton) findViewById(R.id.pause_image_button)).setElevation(0f);
            }
        });

        final SeekBar progressBar = (SeekBar) findViewById(R.id.progress_bar_player);
        progressBar.setProgress(0);
        progressBar.setMax(mediaPlayer.getDuration());

        final Runnable atualizaBarra = new Runnable() {
            public void run() {

                int currentDuration = 0;
                try {
                    currentDuration = mediaPlayer.getCurrentPosition();
                } catch (NullPointerException e) {
                }

                TextView t = (TextView) findViewById(R.id.tempo_corrente_player_text_view);
                t.setText(geraTempo((long) currentDuration));
                progressBar.setProgress(currentDuration);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);
            }

        };

        progressBar.setOnSeekBarChangeListener(new SeekBarListener(mediaPlayer, mHandler, atualizaBarra));

        mHandler.postDelayed(atualizaBarra, 100);

    }

    private String geraTempo(Long duracaoMS) {
        double duracaoM = duracaoMS / 1000 / 60;
        int minuto = (int) duracaoM;
        int segundos = (int) ((duracaoMS / 1000) - (minuto * 60));
        return minuto + ":" + segundos;
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

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void play() {
        if (!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL urlImage = new URL(params[0]);
                Bitmap bmImage = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
                return bmImage;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView capaAlbum = (ImageView) findViewById(R.id.capa_album_image_view);
            capaAlbum.setImageBitmap(bitmap);
        }
    }

}
