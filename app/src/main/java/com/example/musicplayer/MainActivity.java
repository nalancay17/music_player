package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.fun_audio);
        addPlayerButtonsListeners();
        player.setOnCompletionListener(completion -> Toast.makeText(this, "I'm done", Toast.LENGTH_SHORT).show());
    }

    private void addPlayerButtonsListeners() {
        Button start = findViewById(R.id.start_button);
        Button pause = findViewById(R.id.pause_button);

        start.setOnClickListener(caughtEvent -> player.start());
        pause.setOnClickListener(caughtEvent -> player.pause());
    }
}