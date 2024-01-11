package com.example.dictinary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button back;
    TextView btn_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        back = findViewById(R.id.button_back);

        btn_empty = findViewById(R.id.textView9);



    }

    public void NewActivity2_1(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void NewActivity2_level_empty(View v){
        Intent intent = new Intent(this, Leve1.class);
        startActivity(intent);
    }
}