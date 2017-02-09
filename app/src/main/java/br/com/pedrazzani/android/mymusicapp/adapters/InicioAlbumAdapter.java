package br.com.pedrazzani.android.mymusicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.pedrazzani.android.mymusicapp.BibliotecaLocalActivity;
import br.com.pedrazzani.android.mymusicapp.DetalheActivity;
import br.com.pedrazzani.android.mymusicapp.PlayActivity;
import br.com.pedrazzani.android.mymusicapp.R;
import br.com.pedrazzani.android.mymusicapp.entidades.Album;
import br.com.pedrazzani.android.mymusicapp.services.LastFmService;

/**
 * Created by pedrazzani on 04/01/2017.
 */

public class InicioAlbumAdapter extends SimpleCursorAdapter {

    private final String TAG = InicioAlbumAdapter.class.getName();

    private Context appContext;

    public InicioAlbumAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c,
                new String[]{
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ALBUM,
                },
                new int[]{
                        R.id.artista_item_inicio_text_view,
                        R.id.album_item_inicio_text_view,
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

        ImageView capaImageView = (ImageView) view.findViewById(R.id.capa_item_inicio_image_view);
        TextView artistaTextView = (TextView) view.findViewById(R.id.artista_item_inicio_text_view);
        TextView albumTextView = (TextView) view.findViewById(R.id.album_item_inicio_text_view);

        artistaTextView.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
        albumTextView.setText(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));

        final Album album = new LastFmService().searchAlbum(
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)),
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));

        Picasso.with(appContext).load(album.getUrlImageLarge()).into(capaImageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(appContext, BibliotecaLocalActivity.class);
                intent.putExtra("ALBUM",album);
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
        View v = inflater.inflate(R.layout.item_inicio_grid_view, null);

        bindView(v, context, cursor);

        return v;
    }
}
