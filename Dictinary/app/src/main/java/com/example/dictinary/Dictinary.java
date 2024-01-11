package com.example.dictinary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.content.Intent;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dictinary extends AppCompatActivity {

    Button backMenu, add, delete;
    EditText editdeutsch, editrussian;
    SharedPreferences sharedPreferences;
    DBMeneger dbMeneger;
    TextView dictinary_view, text1_see, text2_sort, text3_delall;
    // SHARED_PREF
    private static final String SHARED_PREF_NAME = "myperf";
    private static final String KEY_DEUTSCH = "deutsch";
    private static final String KEY_RUSSIAN = "russian";
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictinary);

        backMenu = findViewById(R.id.backMenu);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        text1_see = findViewById(R.id.text1);
        text2_sort = findViewById(R.id.text2);
        text3_delall = findViewById(R.id.text3);

        editdeutsch = findViewById(R.id.editdeutsch);
        editrussian = findViewById(R.id.editrussian);
        dictinary_view = findViewById(R.id.dictinary_view);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        dbMeneger = new DBMeneger(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbMeneger.openDP();
                dictinary_view.setText(""); // отчистка
                dbMeneger.insertDB(editdeutsch.getText().toString(), editrussian.getText().toString());
                dbMeneger.closeDB();

                startResult();
            }
        });

        text1_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dictinary_view.setText("");
                startResult();
            }
        });

        text2_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortResult();
            }
        });

        text3_delall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteallResult();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteResult();
            }
        });

    }
    // Удаление по ключу
    public void deleteResult(){
        dictinary_view.setText("");

        dbMeneger.openDP();
        dbMeneger.deletevalueDB(editdeutsch.getText().toString());
        dbMeneger.closeDB();

        startResult();
    }
    // Elfktybt
    public void deleteallResult(){
        dictinary_view.setText("");

        dbMeneger.openDP();
        dbMeneger.deleteDB();
        dbMeneger.closeDB();

        startResult();
    }
    // Сортировка
    public void sortResult(){
        dictinary_view.setText("");

        dbMeneger.openDP();
        for (String list : dbMeneger.sortDB()){
            dictinary_view.append(list);
            dictinary_view.append("\n");
        }
        dbMeneger.closeDB();

    }

    // Вывод результата из SQL
    private void startResult(){
        dbMeneger.openDP();
        for (String list : dbMeneger.getDB()){
            dictinary_view.append(list);
            dictinary_view.append("\n");
        }
        dbMeneger.closeDB();
    }
    // Intent
    public void NewActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}