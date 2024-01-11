package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DateActivity extends AppCompatActivity {
    public TextView text_result;
    public DBMeneger dbMeneger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        text_result = findViewById(R.id.text_result);

        dbMeneger = new DBMeneger(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbMeneger.openDP();
        for (String list : dbMeneger.getDB()){
            text_result.append(list);
            text_result.append("\n");
        }
    }

    public void startResult(View view){
        for (String list : dbMeneger.getDB()){
            text_result.append(list);
            text_result.append("\n");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbMeneger.closeDB();
    }

    public void startMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}