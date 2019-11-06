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

    Button btnConfirmAnswer, btn5050, btnAudience, btnChange, btnCall;
    TextView tvQuestionNums, tvContent;
    RadioGroup radioGroup;
    RadioButton a1, a2, a3, a4;

    ArrayList<Question> questionsByLevel;
    ArrayList<Question> allQuestions;
    ArrayList<String> addAnswers = new ArrayList<>();

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

        helpBtnStatus();
        addQuestion(MenuScreen.level);
        pickQuestion();

        btnConfirmAnswer = findViewById(R.id.btnCfAnswer);
        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmClick();
            }
        });

        btn5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                craeteConfirmHelpDialog(view);
            }
        });

        btnAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                craeteConfirmHelpDialog(view);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                craeteConfirmHelpDialog(view);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                craeteConfirmHelpDialog(view);
            }
        });

    }

    private void createWidget() {
        btn5050 = findViewById(R.id.btn5050);
        btnAudience = findViewById(R.id.btnAudience);
        btnChange = findViewById(R.id.btnChange);
        btnCall = findViewById(R.id.btnCall);
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

                    if (MenuScreen.level == 16) {
                        Intent intent = new Intent(MainActivity.this, FinishGameScreen.class);
                        startActivity(intent);

                        finish();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, NextLevelScreen.class);
                        startActivity(intent);

                        finish();
                    }

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

    private void craeteConfirmHelpDialog(final View v) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("Do you want to use this help?");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clickConfirm(v);
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onResume();
            }
        });
        adb.show();
    }

    private void addQuestion(int level) {
        questionsByLevel.clear();
        for (Question item : allQuestions) {
            if (item.getLevel() == level) {
                questionsByLevel.add(item);
            }
        }
    }

    private void pickQuestion() {
        Question question = questionsByLevel.get(new Random().nextInt(questionsByLevel.size()));

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

    private void clickConfirm(View v) {
        switch (v.getId()) {
            case R.id.btn5050:
                Question question = dbHelper.getQuestionByID(questionID);

                MenuScreen.btn5050 = false;

                if (a1.getText().equals(question.getCa())) {
                    a3.setText("");
                    a2.setText("");
                }

                if (a2.getText().equals(question.getCa())) {
                    a1.setText("");
                    a3.setText("");
                }

                if (a3.getText().equals(question.getCa())) {
                    a1.setText("");
                    a2.setText("");
                }

                if (a4.getText().equals(question.getCa())) {
                    a1.setText("");
                    a2.setText("");
                }

                helpBtnStatus();
                break;
            case R.id.btnCall:

                MenuScreen.btnCall = false;

                helpBtnStatus();
                break;
            case R.id.btnAudience:

                MenuScreen.btnAudience = false;

                Intent intent = new Intent(MainActivity.this, AudienceHelpActivity.class);
                startActivity(intent);

                helpBtnStatus();
                break;
            case R.id.btnChange:

                MenuScreen.btnChange = false;

                helpBtnStatus();
                break;
        }
    }

    public void helpBtnStatus() {
        if (MenuScreen.btn5050 == false) {
            btn5050.setEnabled(false);
            btn5050.setAlpha((float) 0.2);
        }
        if (MenuScreen.btnAudience == false) {
            btnAudience.setEnabled(false);
            btnAudience.setAlpha((float) 0.2);
        }
        if (MenuScreen.btnCall == false) {
            btnCall.setEnabled(false);
            btnCall.setAlpha((float) 0.2);
        }
        if (MenuScreen.btnChange == false) {
            btnChange.setEnabled(false);
            btnChange.setAlpha((float) 0.2);
        }
    }
}
