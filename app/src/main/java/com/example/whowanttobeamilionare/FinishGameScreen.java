package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishGameScreen extends AppCompatActivity {

    Button btnBack;
    TextView tvScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game_screen);

        tvScope = findViewById(R.id.tv_scope);
        int scope = MenuScreen.level * 10000;
        tvScope.setText("Your scope: " + scope);

        btnBack = findViewById(R.id.btnReturn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishGameScreen.this, MenuScreen.class);

                startActivity(intent);
                finish();
            }
        });
    }
}
