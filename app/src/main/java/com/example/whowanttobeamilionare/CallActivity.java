package com.example.whowanttobeamilionare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

public class CallActivity extends AppCompatActivity {

    RadioGroup rdGR;
    RadioButton rd1, rd2, rd3, rd4, rd5;
    static String name = "";

    TextView usname1, usname2, usname3;
    TextView tvNamehelper, tvQusetion, tvAnswerList, tvCorrectAnswer;

    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        createWidget();
        craeteDialog();

        Intent intent = getIntent();

        String a = intent.getStringExtra("a1");
        String b = intent.getStringExtra("a2");
        String c = intent.getStringExtra("a3");
        String d = intent.getStringExtra("ca");

        tvQusetion.setText(intent.getStringExtra("questioncontent"));
        String answerslist =
                  "- " + a + "\n"
                + "- " + b + "\n"
                + "- " + c + "\n"
                + "- " + d;
        tvAnswerList.setText(answerslist);

        tvCorrectAnswer.setText("- " + d);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void createWidget() {
        usname1 = findViewById(R.id.edtUserName);
        usname2 = findViewById(R.id.edtUserName2);
        usname3 = findViewById(R.id.edtUserName3);

        tvQusetion = findViewById(R.id.edtQuestionContent);
        tvAnswerList = findViewById(R.id.edtAnswerContent);
        tvCorrectAnswer = findViewById(R.id.correct_answer);
        btnOk = findViewById(R.id.btnOK);

        tvNamehelper = findViewById(R.id.edtName);
    }

    /* Dialog configure */
    private void craeteDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        final View dialogView = li.inflate(R.layout.call_list_dialog, null);


        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create();
        alertDialogBuilder.setView(dialogView);

        rdGR = dialogView.findViewById(R.id.rdGR);
        rd1 = dialogView.findViewById(R.id.radioButton1);
        rd2 = dialogView.findViewById(R.id.radioButton2);
        rd3 = dialogView.findViewById(R.id.radioButton3);
        rd4 = dialogView.findViewById(R.id.radioButton4);
        rd5 = dialogView.findViewById(R.id.radioButton5);

        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button =  alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (rdGR.getCheckedRadioButtonId()) {
                            case R.id.radioButton1:
                                name = rd1.getText().toString();

                                usname1.setText(name);
                                usname2.setText(name);
                                usname3.setText(name);
                                tvNamehelper.setText("Hello " + name);

                                alertDialogBuilder.dismiss();
                                break;
                            case R.id.radioButton2:
                                name = rd2.getText().toString();

                                usname1.setText(name);
                                usname2.setText(name);
                                usname3.setText(name);
                                tvNamehelper.setText("Hello " + name);

                                alertDialogBuilder.dismiss();
                                break;
                            case R.id.radioButton3:
                                name = rd3.getText().toString();

                                usname1.setText(name);
                                usname2.setText(name);
                                usname3.setText(name);
                                tvNamehelper.setText("Hello " + name);

                                alertDialogBuilder.dismiss();
                                break;
                            case R.id.radioButton4:
                                name = rd4.getText().toString();

                                usname1.setText(name);
                                usname2.setText(name);
                                usname3.setText(name);
                                tvNamehelper.setText("Hello " + name);

                                alertDialogBuilder.dismiss();
                                break;
                            case R.id.radioButton5:
                                name = rd5.getText().toString();

                                usname1.setText(name);
                                usname2.setText(name);
                                usname3.setText(name);
                                tvNamehelper.setText("Hello " + name);

                                alertDialogBuilder.dismiss();
                                break;


                        }


                    }
                });
            }
        });

        alertDialogBuilder.show();

        WindowManager.LayoutParams lp = alertDialogBuilder.getWindow().getAttributes();
        lp.dimAmount=1f;
        alertDialogBuilder.getWindow().setAttributes(lp);
        alertDialogBuilder.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}
