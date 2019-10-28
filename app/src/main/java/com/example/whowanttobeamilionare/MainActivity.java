package com.example.whowanttobeamilionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whowanttobeamilionare.object.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnConfirmAnswer, btn5050, btnAudience, btnChange;
    TextView tvQuestionNums, tvContent;
    RadioGroup radioGroup;
    RadioButton a1, a2, a3, a4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWidget();

        btnConfirmAnswer = findViewById(R.id.btnCfAnswer);
        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void createWidget() {
        btn5050 = findViewById(R.id.btn5050);
        btnAudience = findViewById(R.id.btnAudience);
        btnChange = findViewById(R.id.btnChange);
        tvQuestionNums = findViewById(R.id.tvQuestionNumbs);
        tvContent = findViewById(R.id.tvContent);
        radioGroup = findViewById(R.id.gr);
        a1 = findViewById(R.id.rdtA1);
        a2 = findViewById(R.id.rdtA2);
        a3 = findViewById(R.id.rdtA3);
        a4 = findViewById(R.id.rdtA4);
    }

}
