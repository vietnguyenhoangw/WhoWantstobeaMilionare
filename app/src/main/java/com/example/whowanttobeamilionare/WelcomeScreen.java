package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

public class WelcomeScreen extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        mediaPlayer = MediaPlayer.create(WelcomeScreen.this, R.raw.themesong);
        playRawAudio();

        countdown();
    }

    public void countdown() {
        new CountDownTimer(7000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                stopRawAudio();

                Intent intent = new Intent(WelcomeScreen.this, MenuScreen.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    /*
      play and stop background music
    */
    private void playRawAudio() {
        MediaPlayer.create(this, R.raw.themesong);

        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void stopRawAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
