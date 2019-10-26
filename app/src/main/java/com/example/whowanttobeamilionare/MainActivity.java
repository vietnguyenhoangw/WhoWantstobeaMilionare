package com.example.whowanttobeamilionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    Button btnConfirmAnswer, btn5050, btnAudience, btnChange;
    TextView tvQuestionNums, tvContent;
    RadioGroup radioGroup;
    RadioButton a1, a2, a3, a4;

    ArrayList<Question> questionbyLevel;
    ArrayList<Question> questionPlay;

    FirebaseDatabase database;
    DatabaseReference myRef;

    int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWidget();

        questionbyLevel = new ArrayList<>();
        questionPlay = new ArrayList<>();

        getQuestion();

        btnConfirmAnswer = findViewById(R.id.btnCfAnswer);
        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();

                clickComfirm();
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

    // 1
    public void getQuestion() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionbyLevel.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Question question = item.getValue(Question.class);
                    questionbyLevel.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 2
    public void getQuestionbyLevel() {
        questionPlay.clear();
        for (int i = 0; i < questionbyLevel.size(); i++) {
            if (questionbyLevel.get(i).getLevel() == level) {
                Question q = questionbyLevel.get(i);
                questionPlay.add(q);
            }
        }
    }

    // 3
    public void addQuestion() {
        getQuestionbyLevel();

        Question question = questionPlay.get(new Random().nextInt(questionPlay.size()));

        ArrayList<String> addAnswers = new ArrayList<>();
        addAnswers.clear();
        addAnswers.add(question.getA1());
        addAnswers.add(question.getA2());
        addAnswers.add(question.getA3());
        addAnswers.add(question.getCa());

        Collections.shuffle(addAnswers);
        a1.setText(addAnswers.get(0));
        a2.setText(addAnswers.get(1));
        a3.setText(addAnswers.get(2));
        a4.setText(addAnswers.get(3));

        tvQuestionNums.setText(question.getLevel() + "");
        tvContent.setText(question.getContent() + "");
    }

    // 4
    public void clickComfirm() {
        String cr = "";

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rdtA1:
                cr = a1.getText().toString();
                break;
            case R.id.rdtA2:
                cr = a2.getText().toString();
                break;
            case R.id.rdtA3:
                cr = a3.getText().toString();
                break;
            case R.id.rdtA4:
                cr = a4.getText().toString();
                break;
        }

        Question question = questionPlay.get(level);

        if (question.getCa().equals(cr)) {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }
    }

}
