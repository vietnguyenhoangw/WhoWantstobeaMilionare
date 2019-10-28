package com.example.whowanttobeamilionare.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.whowanttobeamilionare.object.Question;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper {
    String DATABASE_NAME = "whowanttobeamillionare";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    public DBHelper(Context context) {
        this.context = context;

        processSQLite();
    }

    private void processSQLite() {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(context, "Copy successful !!!", Toast.LENGTH_SHORT).show();

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream databaseInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDatabaseSystem();

            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream databaseOutputStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = databaseInputStream.read(buffer)) > 0){
                databaseOutputStream.write(buffer,0,length);
            }


            databaseOutputStream.flush();
            databaseOutputStream.close();
            databaseInputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDatabaseSystem() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }


    public ArrayList<Question> getQuestion(){
        db = context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        Cursor cursor = db.rawQuery("Select * From question",null);

        ArrayList<Question> arrayList = new ArrayList<>();

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            int level = cursor.getInt(1);
            String content = cursor.getString(2);
            String a1 = cursor.getString(3);
            String a2 = cursor.getString(4);
            String a3 = cursor.getString(5);
            String ca = cursor.getString(6);

            Question question = new Question();
            question.setId(id);
            question.setContent(content);
            question.setLevel(level);
            question.setA1(a1);
            question.setA2(a2);
            question.setA3(a3);
            question.setCa(ca);

            arrayList.add(question);
        }

        return arrayList;
    }

    public long insertQuestion(Question question){
        db = context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE,
                null);

        //"INSERT INTO Student(name,address,gender) VALUES(?,?,?)"

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",question.getId());
        contentValues.put("level",question.getLevel());
        contentValues.put("content", question.getContent());
        contentValues.put("a1", question.getA1());
        contentValues.put("a2", question.getA2());
        contentValues.put("a3", question.getA3());
        contentValues.put("ca", question.getCa());

        long result = db.insert("question",
                null,
                contentValues);

        return result;

    }
}
