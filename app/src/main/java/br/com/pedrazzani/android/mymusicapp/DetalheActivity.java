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

        ImageView capa = (ImageView) findViewById(R.id.detalhe_album_image_view);
        TextView musica = (TextView) findViewById(R.id.detalhe_musica_text_view);
        TextView album = (TextView) findViewById(R.id.detalhe_album_text_view);
        TextView artista = (TextView) findViewById(R.id.detalhe_artista_text_view);
        TextView ano = (TextView) findViewById(R.id.detalhe_ano_text_view);
        TextView duracao = (TextView) findViewById(R.id.detalhe_duracao_text_view);
        TextView genero = (TextView) findViewById(R.id.detalhe_genero_text_view);

        musica.setText(bundle.getString(getResources().getString(R.string.MUSICA)));
        album.setText(bundle.getString(getResources().getString(R.string.ALBUM)));
        artista.setText(bundle.getString(getResources().getString(R.string.ARTISTA)));
        ano.setText(bundle.getString(getResources().getString(R.string.ANO)));
        genero.setText(bundle.getString(getResources().getString(R.string.GENERO)));

        Long d = Long.parseLong(bundle.getString(getResources().getString(R.string.DURACAO))) / 1000 / 60;

        duracao.setText(d + " min.");

        Album album1 = (Album) bundle.get(getResources().getString(R.string.CAPA));

        Picasso.with(getApplicationContext())
                .load(album1.getUrlImageLarge())
                .into(capa);

    }
}
