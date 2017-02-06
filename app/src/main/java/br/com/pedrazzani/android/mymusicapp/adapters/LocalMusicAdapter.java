package br.com.pedrazzani.android.mymusicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import br.com.pedrazzani.android.mymusicapp.DetalheActivity;
import br.com.pedrazzani.android.mymusicapp.PlayActivity;
import br.com.pedrazzani.android.mymusicapp.R;
import br.com.pedrazzani.android.mymusicapp.services.LastFmService;

/**
 * Created by pedrazzani on 04/01/2017.
 */

public class LocalMusicAdapter extends SimpleCursorAdapter {

    private Context appContext;

    public LocalMusicAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c,
                new String[]{
                        MediaStore.MediaColumns.TITLE,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ALBUM,
                },
                new int[]{
                        R.id.title_text_view,
                        R.id.artista_text_view,
                        R.id.album_text_view,
                },
                flags);
        appContext = context;
    }

    /**
     * Associa os valores do Cursor as suas respectivas Views
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView titulo = (TextView) view.findViewById(R.id.title_text_view);
        TextView artista = (TextView) view.findViewById(R.id.artista_text_view);
        TextView album = (TextView) view.findViewById(R.id.album_text_view);

        titulo.setText(cursor.getString(
                cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
        artista.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
        album.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));

        //Alimenta o Botão Play
        ImageButton play = (ImageButton) view.findViewById(R.id.play_image_button);
        //Armazena o local em disco do arquivo de musica
        play.setTag(R.id.CURSOR, cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
        //Armazena a posição na ListView deste ImageButton
        play.setTag(R.id.PROSITION, cursor.getPosition());

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition((Integer) view.getTag(R.id.PROSITION));

                Bundle bundle = new Bundle();
                bundle.putString(context.getResources().getString(R.string.URI), view.getTag(R.id.CURSOR).toString());
                bundle.putString(context.getResources().getString(R.string.MUSICA), cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
                bundle.putString(context.getResources().getString(R.string.ARTISTA), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
                bundle.putString(context.getResources().getString(R.string.DURACAO), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                bundle.putParcelable(context.getResources().getString(R.string.ALBUM),
                        new LastFmService().searchAlbum(
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))));

                Intent intent = new Intent(appContext, PlayActivity.class);
                intent.putExtras(bundle);
                appContext.startActivity(intent);
            }
        });

        //Alimenta o Botão detalhes
        ImageButton detalhe = (ImageButton) view.findViewById(R.id.detalhe_image_button);
        detalhe.setTag(R.id.CURSOR, cursor);
        detalhe.setTag(R.id.PROSITION, cursor.getPosition());

        detalhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cursor.moveToPosition((Integer) view.getTag(R.id.PROSITION));

                Bundle bundle = new Bundle();

                bundle.putString(context.getResources().getString(R.string.ALBUM), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
                bundle.putString(context.getResources().getString(R.string.MUSICA), cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
                bundle.putString(context.getResources().getString(R.string.ARTISTA), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
                bundle.putString(context.getResources().getString(R.string.ANO), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR)));
                bundle.putString(context.getResources().getString(R.string.DURACAO), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
                bundle.putString(context.getResources().getString(R.string.GENERO), cursor.getString(cursor.getColumnIndex(context.getResources().getString(R.string.detalhe_genero_column))));

                bundle.putParcelable(context.getResources().getString(R.string.CAPA),
                        new LastFmService().searchAlbum(
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)),
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST))));

                Intent intent = new Intent(appContext, DetalheActivity.class);
                intent.putExtras(bundle);
                appContext.startActivity(intent);
            }
        });
    }

    /**
     * Cria uma nova View, que esta no Layout content_music.xml
     * E chama o método responsável por associar os valores do cursos às Views
     *
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.content_music, null);

        bindView(v, context, cursor);

        return v;
    }
}
