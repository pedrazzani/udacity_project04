package br.com.pedrazzani.android.mymusicapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.pedrazzani.android.mymusicapp.entidades.Album;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ImageView capaImageView = (ImageView) findViewById(R.id.detalhe_album_image_view);
        TextView musicaTextView = (TextView) findViewById(R.id.detalhe_musica_text_view);
        TextView albumTextView = (TextView) findViewById(R.id.detalhe_album_text_view);
        TextView artistaTextView = (TextView) findViewById(R.id.detalhe_artista_text_view);
        TextView anoTextView = (TextView) findViewById(R.id.detalhe_ano_text_view);
        TextView duracaoTextView = (TextView) findViewById(R.id.detalhe_duracao_text_view);
        TextView generoTextView = (TextView) findViewById(R.id.detalhe_genero_text_view);

        musicaTextView.setText(bundle.getString(getResources().getString(R.string.MUSICA)));
        albumTextView.setText(bundle.getString(getResources().getString(R.string.ALBUM)));
        artistaTextView.setText(bundle.getString(getResources().getString(R.string.ARTISTA)));
        anoTextView.setText(bundle.getString(getResources().getString(R.string.ANO)));
        generoTextView.setText(bundle.getString(getResources().getString(R.string.GENERO)));

        Long d = Long.parseLong(bundle.getString(getResources().getString(R.string.DURACAO))) / 1000 / 60;

        duracaoTextView.setText(d + " min.");

        Album album = (Album) bundle.get(getResources().getString(R.string.CAPA));

        Picasso.with(getApplicationContext())
                .load(album.getUrlImageLarge())
                .into(capaImageView);

    }
}
