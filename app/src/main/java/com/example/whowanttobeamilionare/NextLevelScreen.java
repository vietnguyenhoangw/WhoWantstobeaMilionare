package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NextLevelScreen extends AppCompatActivity {

    Button btnNextLevel;
    TextView tvScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_level_screen);

        tvScope = findViewById(R.id.tv_scope);
        int scope = MenuScreen.level * 1000;
        tvScope.setText("Your scope: " + scope);

        btnNextLevel = findViewById(R.id.btnNextLevel);
        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextLevelScreen.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }
}
