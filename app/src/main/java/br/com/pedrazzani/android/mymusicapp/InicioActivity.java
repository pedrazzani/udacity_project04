package br.com.pedrazzani.android.mymusicapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import br.com.pedrazzani.android.mymusicapp.adapters.InicioAlbumAdapter;
import br.com.pedrazzani.android.mymusicapp.adapters.LocalMusicAdapter;
import br.com.pedrazzani.android.mymusicapp.services.LocalMusicService;

public class InicioActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private LocalMusicAdapter localMusicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        final InicioActivity inicioActivity = this;

        ImageView folder = (ImageView) findViewById(R.id.folder_image_view);
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inicioActivity, BibliotecaLocalActivity.class);
                inicioActivity.startActivity(intent);
            }
        });


        Cursor cursor = new LocalMusicService(this).getCursor(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                InicioAlbumAdapter inicioAlbumAdapter = new InicioAlbumAdapter(this, R.layout.item_inicio_grid_view, cursor, null, null, 0);

                GridView gridView = (GridView) findViewById(R.id.inicio_grid_view);
                gridView.setAdapter(inicioAlbumAdapter);
            }
        }


    }


}
