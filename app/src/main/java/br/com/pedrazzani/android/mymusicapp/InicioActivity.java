package br.com.pedrazzani.android.mymusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import br.com.pedrazzani.android.mymusicapp.adapters.LocalMusicAdapter;

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
                Intent intent = new Intent(inicioActivity,LocalActivity.class);
                inicioActivity.startActivity(intent);
            }
        });

    }
}
