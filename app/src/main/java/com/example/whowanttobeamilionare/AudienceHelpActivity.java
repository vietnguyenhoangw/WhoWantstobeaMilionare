package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class AudienceHelpActivity extends AppCompatActivity {

    ProgressBar a1, a2, a3, a4;
    TextView tvResult;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_help);

        creatWidget();

        Random rd = new Random();

        int a1Rslt = rd.nextInt(100);
        a1.setProgress(a1Rslt);
        int a2Rslt = rd.nextInt(100 - a1Rslt);
        a2.setProgress(a2Rslt);
        int a3Rslt = rd.nextInt(100 - (a1Rslt + a2Rslt));
        a3.setProgress(a3Rslt);
        int a4Rslt = 100 - (a1Rslt + a2Rslt + a3Rslt);
        a4.setProgress(a4Rslt);

        String Result = "A " + a1Rslt + "% | "
                + "B " + a2Rslt + "% | "
                + "C " + a3Rslt + "% | "
                + "D " + a4Rslt + "%";

        tvResult.setText(Result);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void creatWidget() {
        a1 = findViewById(R.id.progressBara);
        a2 = findViewById(R.id.progressBarb);
        a3 = findViewById(R.id.progressBarc);
        a4 = findViewById(R.id.progressBard);
        tvResult = findViewById(R.id.tvResult);
        btnOk = findViewById(R.id.btnConfirm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
