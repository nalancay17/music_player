package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.fun_audio);
        addPlayerButtonsListeners();
    }

    private void addPlayerButtonsListeners() {
        Button start = findViewById(R.id.start_button);
        Button pause = findViewById(R.id.pause_button);

        start.setOnClickListener(caughtEvent -> player.start());
        pause.setOnClickListener(caughtEvent -> player.pause());
    }
}