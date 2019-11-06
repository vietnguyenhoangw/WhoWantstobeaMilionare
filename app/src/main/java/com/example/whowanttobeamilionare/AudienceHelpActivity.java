package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Random;

public class AudienceHelpActivity extends AppCompatActivity {

    ProgressBar a1, a2, a3, a4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_help);

        a1 = findViewById(R.id.progressBar2);
        a2 = findViewById(R.id.progressBar3);
        a3 = findViewById(R.id.progressBar4);
        a4 = findViewById(R.id.progressBar5);

        Random rd = new Random();

        a1.setProgress(rd.nextInt(100));
        a2.setProgress(rd.nextInt(100 - a1.getProgress()));
        a3.setProgress(rd.nextInt(100 - (a1.getProgress() + a2.getProgress())));
        a4.setProgress(rd.nextInt(100 - (a1.getProgress() + a2.getProgress() + a3.getProgress())));
    }
}
