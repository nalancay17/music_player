package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private final MediaPlayer.OnCompletionListener listener = completion -> Toast.makeText(this, "I'm done", Toast.LENGTH_SHORT).show();
    private final Handler hn = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        player = MediaPlayer.create(this, R.raw.fun_audio);
        addPlayerButtonsListeners();
        player.setOnCompletionListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void addPlayerButtonsListeners() {
        Button start = findViewById(R.id.start_button);
        Button pause = findViewById(R.id.pause_button);
        Button addThreeSec = findViewById(R.id.add_three_sec_button);
        Button subThreeSec = findViewById(R.id.subtract_three_sec_button);

        start.setOnClickListener(caughtEvent -> {
            player.start();
            startSeekBar();
        });
        pause.setOnClickListener(caughtEvent -> player.pause());

        if (player != null) {
            addThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() + 1000 * 3));
            subThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() - 1000 * 3));
        }
    }

    private void startSeekBar() {
        SeekBar sb = findViewById(R.id.music_seekbar);
        sb.setMax(player.getDuration());
        sb.setOnSeekBarChangeListener(createSeekBarChangeListener());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sb.setProgress(player.getCurrentPosition());
                hn.postDelayed(this, 1);
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null  && fromUser) {
                    this.progress = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(progress);
            }
        };
    }
}