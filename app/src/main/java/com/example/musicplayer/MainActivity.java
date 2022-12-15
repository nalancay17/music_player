package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private final MediaPlayer.OnCompletionListener listener = completion -> Toast.makeText(this, "I'm done", Toast.LENGTH_SHORT).show();
    private final Handler handler = new Handler();
    private SeekBar musicSeekBar;
    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (player != null && fromUser) {
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

    private AudioManager audioManager;
    private final AudioAttributes attributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build();
    private int requestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
        initializeSeekBar();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void initializePlayer() {
        player = MediaPlayer.create(this, R.raw.fun_audio);
        addPlayerButtonsListeners();
        player.setOnCompletionListener(listener);
    }

    private void initializeSeekBar() {
        musicSeekBar = findViewById(R.id.music_seekbar);
        musicSeekBar.setMax(player.getDuration());
        musicSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void addPlayerButtonsListeners() {
        Button start = findViewById(R.id.start_button);
        Button pause = findViewById(R.id.pause_button);
        Button addThreeSec = findViewById(R.id.add_three_sec_button);
        Button subThreeSec = findViewById(R.id.subtract_three_sec_button);

        start.setOnClickListener(caughtEvent -> {
            makeFocusRequest();
            if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                player.start();
                startSeekBar();
            }
        });
        pause.setOnClickListener(caughtEvent -> player.pause());

        if (player != null) {
            addThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() + 1000 * 3));
            subThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() - 1000 * 3));
        }
    }

    private void makeFocusRequest() {
        // request for api level >= 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(attributes)
                    .build();
            requestResult = audioManager.requestAudioFocus(focusRequest);
        }
    }

    private void startSeekBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (player.isPlaying()) {
                    musicSeekBar.setProgress(player.getCurrentPosition());
                    handler.post(this);
                }
            }
        });
    }
}