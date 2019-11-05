package com.example.whowanttobeamilionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MenuScreen extends AppCompatActivity {

    DBHelper dbHelper;
    Button btnPlaynewgame;
    TextView tvUsername;

    /* Dialog */
    EditText edtUserNameInput;
    static String UserName = "";
    static Boolean btn5050 = true;
    static Boolean btnCall = true;
    static Boolean btnAudience = true;
    static Boolean btnChange = true;

    ArrayList<Question> allQuestion;

    FirebaseDatabase database;
    DatabaseReference myRef;

    static int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        dbHelper= new DBHelper(MenuScreen.this);

        tvUsername = findViewById(R.id.tv_username);
        tvUsername.setText("Hello " + UserName);

        allQuestion = new ArrayList<>();

        if (UserName.equals("")) {
            craeteDialog();
            level = 1;
        }

        btnPlaynewgame = findViewById(R.id.btnPlaynewgame);
        btnPlaynewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuScreen.this, MainActivity.class);
                level = 1;
                btn5050 = true;
                btnCall = true;
                btnAudience = true;
                btnChange = true;
                startActivity(intent);
            }
        });
    }

    public void getQuestion() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allQuestion.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Question question = item.getValue(Question.class);
                    allQuestion.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MenuScreen.this, databaseError.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* Dialog configure */
    private void craeteDialog() {
        getQuestion();

        LayoutInflater li = LayoutInflater.from(this);
        final View dialogView = li.inflate(R.layout.name_input_dialog, null);


        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create();
        alertDialogBuilder.setView(dialogView);

        edtUserNameInput = dialogView.findViewById(R.id.edtUserNameInputt);

        alertDialogBuilder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button =  alertDialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtUserNameInput.getText().length() == 0) {
                            edtUserNameInput.setError("Empty name!");
                        }
                        else {
                            UserName = edtUserNameInput.getText().toString();
                            tvUsername.setText("Hello " + UserName);

                            addDatatoOfflineDB();
                            // when everything is ok, using dismiss.
                            alertDialogBuilder.dismiss();
                        }
                    }
                });
            }
        });

        alertDialogBuilder.show();
    }

    /* add all data from string(Firebase) to offline database */
    private void addDatatoOfflineDB() {
        for (int i = 0; i < allQuestion.size(); i++) {
            Question question = allQuestion.get(i);

            if(dbHelper.insertQuestion(question) > 0){

            }
        }
    }

}
