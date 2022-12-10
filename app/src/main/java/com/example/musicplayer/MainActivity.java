package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private final MediaPlayer.OnCompletionListener listener = completion -> Toast.makeText(this, "I'm done", Toast.LENGTH_SHORT).show();

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

    private void addPlayerButtonsListeners() {
        Button start = findViewById(R.id.start_button);
        Button pause = findViewById(R.id.pause_button);
        Button addThreeSec = findViewById(R.id.add_three_sec_button);
        Button subThreeSec = findViewById(R.id.subtract_three_sec_button);

        start.setOnClickListener(caughtEvent -> player.start());
        pause.setOnClickListener(caughtEvent -> player.pause());

        if(player != null) {
            addThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() + 1000 * 3));
            subThreeSec.setOnClickListener(caughtEvent -> player.seekTo(player.getCurrentPosition() - 1000 * 3));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(player != null) {
            player.release();
            player = null;
        }
    }
}