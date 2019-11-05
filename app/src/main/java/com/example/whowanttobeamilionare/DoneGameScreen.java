package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoneGameScreen extends AppCompatActivity {

    Button buttonReturn;
    TextView tvScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_game_screen);
        tvScope = findViewById(R.id.tv_scope);

        if (MenuScreen.level == 1) {
            tvScope.setText("Your scope: 0");
        }
        else {
            int scope = MenuScreen.level * 1000;
            tvScope.setText("Your scope: " + scope);
        }

        buttonReturn = findViewById(R.id.btnReturn);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoneGameScreen.this, MenuScreen.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
