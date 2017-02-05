package br.com.pedrazzani.android.mymusicapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.math.BigDecimal;

import br.com.pedrazzani.android.mymusicapp.R;

/**
 * Created by pedrazzani on 04/01/2017.
 */

public class LocalMusicAdapter extends SimpleCursorAdapter {

    public LocalMusicAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c,
                new String[]{
                        MediaStore.Audio.AudioColumns.TRACK,
                        MediaStore.MediaColumns.TITLE,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.DURATION
                },
                new int[]{
                        R.id.track_text_view,
                        R.id.title_text_view,
                        R.id.artista_text_view,
                        R.id.album_text_view,
                        R.id.duracao_text_view
                },
                flags);
    }

    /**
     * Associa os valores do Cursor as suas respectivas Views
     *
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView trilha = (TextView) view.findViewById(R.id.track_text_view);
        TextView titulo = (TextView) view.findViewById(R.id.title_text_view);
        TextView artista = (TextView) view.findViewById(R.id.artista_text_view);
        TextView album = (TextView) view.findViewById(R.id.album_text_view);
        TextView duracao = (TextView) view.findViewById(R.id.duracao_text_view);

        trilha.setText(cursor.getString(
                cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TRACK)));

        titulo.setText(cursor.getString(
                cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));

        artista.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));

        album.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));

        long duracaoMs = Long.parseLong(cursor.getString(
                cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));

        double duracaoMin = duracaoMs / 1000.0 / 60;
        duracaoMin = BigDecimal.valueOf(duracaoMin).setScale(2, BigDecimal.ROUND_UP).doubleValue();

        duracao.setText(String.valueOf(duracaoMin));

        view.setTag(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));

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
