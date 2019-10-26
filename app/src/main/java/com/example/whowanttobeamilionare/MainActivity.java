package com.example.whowanttobeamilionare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnConfirmAnswer;

    ArrayList<Question> arrayList;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        readFromFirebase();

        btnConfirmAnswer = findViewById(R.id.btnCfAnswer);
        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, arrayList.get(0).getContent() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* read Database from realtimefirebase
     * get item and add to arraylist's listview.
     * */
    public void readFromFirebase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Question question = item.getValue(Question.class);
                    arrayList.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
