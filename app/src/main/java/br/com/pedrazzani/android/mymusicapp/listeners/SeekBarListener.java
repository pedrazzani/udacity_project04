package br.com.pedrazzani.android.mymusicapp.listeners;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

/**
 * Created by pedrazzani on 26/01/2017.
 */

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private MediaPlayer mediaPlayer;
    private Handler mHandler;
    private Runnable atualizaBarra;

    public SeekBarListener(MediaPlayer mediaPlayer, Handler mHandler, Runnable atualizaBarra) {
        this.mediaPlayer = mediaPlayer;
        this.mHandler = mHandler;
        this.atualizaBarra = atualizaBarra;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(atualizaBarra);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(atualizaBarra);
        mediaPlayer.seekTo(seekBar.getProgress());
        mHandler.postDelayed(atualizaBarra, 100);

    }
}
