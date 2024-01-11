package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProfileActivity extends AppCompatActivity {
    private static int COINS;
    private static int LVL;
    public static int PROGRESS;



    public TextView coins_profile;
    public TextView level;
    public ProgressBar progress_level;

    public Button button_up;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        coins_profile = findViewById(R.id.coins_profile);

        level = findViewById(R.id.level);
        progress_level = findViewById(R.id.progressBar_level);
        getLvlData();
        getProgressData();

        progress_level.setProgress(PROGRESS);

        button_up = findViewById(R.id.button_up);

        Bundle arguments = getIntent().getExtras();

        String text_coins = arguments.get("coins").toString();
        coins_profile.setText(text_coins);

        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusCoins();
                plusLevel();
                saveProgressData();
            }
        });
    }

    public void startMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        saveProgressData();
    }

    private void minusCoins(){
        progress_level.setMax(Integer.parseInt(level.getText().toString()));
        COINS = Integer.parseInt(coins_profile.getText().toString());

        if (COINS > 0)
        {
            COINS = COINS - 1;
            String coinsFormatted = Integer.toString(COINS);
            coins_profile.setText(coinsFormatted);


            progress_level.setProgress(PROGRESS);
            PROGRESS = PROGRESS + 1;
        }

        saveData();
    }

    private void plusLevel(){
        if (progress_level.getProgress() == Integer.parseInt(level.getText().toString())){
            LVL = Integer.parseInt(level.getText().toString());
            LVL = LVL + 1;
            String levelFormatted = Integer.toString(LVL);
            level.setText(levelFormatted);
            saveLvlData();

            PROGRESS = 1;
        }
    }

    public void saveProgressData(){
        String progressFormatted = Integer.toString(PROGRESS);

        try {
            FileOutputStream fileOutputStream = openFileOutput("progress_data.txt", MODE_PRIVATE);
            fileOutputStream.write(progressFormatted.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не можем обработать файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(){
        String coins_file = coins_profile.getText().toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("data.txt", MODE_PRIVATE);
            fileOutputStream.write(coins_file.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не можем обработать файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLvlData(){
        String coins_file = level.getText().toString();

        try {
            FileOutputStream fileOutputStream = openFileOutput("lvl_data.txt", MODE_PRIVATE);
            fileOutputStream.write(coins_file.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Не можем обработать файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProgressData(){
        try {
            FileInputStream fileInputStream = openFileInput("progress_data.txt");

            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuffer = new StringBuilder();
            String lines = "";

            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }

            int set_progress = Integer.parseInt(stringBuffer.toString());
            PROGRESS = set_progress;

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLvlData(){
        try {
            FileInputStream fileInputStream = openFileInput("lvl_data.txt");

            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuffer = new StringBuilder();
            String lines = "";

            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }

            level.setText(stringBuffer);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}