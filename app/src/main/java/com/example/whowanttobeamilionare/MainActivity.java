package com.example.whowanttobeamilionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whowanttobeamilionare.DBhelper.DBHelper;
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

    DBHelper dbHelper;

    Button btnConfirmAnswer, btn5050, btnAudience, btnChange;
    TextView tvQuestionNums, tvContent;
    RadioGroup radioGroup;
    RadioButton a1, a2, a3, a4;

    ArrayList<Question> questionsByLevel;
    ArrayList<Question> allQuestions;

    String questionID = "";
    String playerAnswer = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper= new DBHelper(MainActivity.this);

        createWidget();

        allQuestions = dbHelper.getQuestion();
        questionsByLevel = new ArrayList<>();

        addQuestion(MenuScreen.level);
        pickQuestion();

        btnConfirmAnswer = findViewById(R.id.btnCfAnswer);
        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClick();
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

    /* Dialog configure */
    private void craeteDialog() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("Are you sure?");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Question question = dbHelper.getQuestionByID(questionID);

                if (question.getCa().equals(playerAnswer)) {
                    MenuScreen.level ++;

                    Intent intent = new Intent(MainActivity.this, NextLevelScreen.class);
                    intent.putExtra("level", MenuScreen.level);
                    startActivity(intent);

                    finish();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, DoneGameScreen.class);
                    startActivity(intent);

                    finish();
                }
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        adb.show();
    }

    // 1
    private void addQuestion(int level) {
        questionsByLevel.clear();
        for (Question item : allQuestions) {
            if (item.getLevel() == level) {
                questionsByLevel.add(item);
            }
        }
    }

    // 2
    private void pickQuestion() {
        Question question = questionsByLevel.get(new Random().nextInt(questionsByLevel.size()));

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
        tvContent.setText(question.getContent());
        questionID = question.getId();
    }

    private void confirmClick() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rdtA1:
                playerAnswer = a1.getText().toString();
                craeteDialog();
                break;
            case R.id.rdtA2:
                playerAnswer = a2.getText().toString();
                craeteDialog();
                break;
            case R.id.rdtA3:
                playerAnswer = a3.getText().toString();
                craeteDialog();
                break;
            case R.id.rdtA4:
                playerAnswer = a4.getText().toString();
                craeteDialog();
                break;
                default:
                    Toast.makeText(this, "Pickup your answer.", Toast.LENGTH_SHORT).show();
                    break;
        }
    }

}
