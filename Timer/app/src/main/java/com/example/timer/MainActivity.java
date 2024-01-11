package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static long START_MILLIS;
    private static int COINS;

    public TextView coins;
    public TextView text_timer, text_reward, text_work;
    public ProgressBar progress_timer;

    public EditText edit_work, edit_minutes;

    private CountDownTimer count_timer;

    public Button button_timer;
    public Button button_reset;
    public Button button_install;

    private boolean TimerRunning;
    private long left_count_time = START_MILLIS;

    public DBMeneger dbMeneger;

    private ImageButton button_setting, button_profile, button_date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        coins = findViewById(R.id.coins);
        text_timer = findViewById(R.id.text_timer);

        text_reward = findViewById(R.id.text_reward);
        text_reward.setVisibility(View.INVISIBLE);

        text_work = findViewById(R.id.text_work);
        text_work.setVisibility(View.INVISIBLE);


        progress_timer = findViewById(R.id.progress_timer);

        edit_minutes = findViewById(R.id.edit_minutes);
        edit_work = findViewById(R.id.edit_work);

        button_timer = findViewById(R.id.timer_button);
        button_timer.setVisibility(View.INVISIBLE);

        button_reset = findViewById(R.id.reset_button);
        button_install = findViewById(R.id.button_install);

        button_install.setEnabled(false);

        button_setting = findViewById(R.id.button_setting);
        button_profile = findViewById(R.id.button_profile);
        button_date = findViewById(R.id.button_date);

        dbMeneger = new DBMeneger(this);

        getData();

        edit_minutes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                {
                    button_install.setEnabled(false);
                }
                else
                {
                    button_install.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_work.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(""))
                {
                    button_install.setEnabled(false);
                }
                else
                {
                    button_install.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimerRunning) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateText();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbMeneger.openDP();
    }

    public void startProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("coins", coins.getText().toString());
        startActivity(intent);
    }

    public void startSetting(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void startDate(View view){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }

    public void startInstall(View view){

        getTextValue();
        first_updateText();
        button_timer.setVisibility(View.VISIBLE);
    }

    public void saveData(){
        String coins_file = coins.getText().toString();

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

    public void getData(){
        try {
            FileInputStream fileInputStream = openFileInput("data.txt");

            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder stringBuffer = new StringBuilder();
            String lines = "";

            while ((lines = bufferedReader.readLine()) != null){
                stringBuffer.append(lines);
            }

            coins.setText(stringBuffer);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

/*    private void setNewSettingFragment() {
        SettingFragment settingFragment = new SettingFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_setting, settingFragment);
        ft.addToBackStack(null);
        ft.commit();
    }*/

    private void startTimer(){
        count_timer = new CountDownTimer(left_count_time, 1000) {
            @Override
            public void onTick(long until_finished) {
                left_count_time = until_finished;
                updateText();
            }

            @Override
            public void onFinish() {
                COINS = Integer.parseInt(coins.getText().toString());
                COINS = COINS + 1;
                String coinsFormatted = Integer.toString(COINS);
                coins.setText(coinsFormatted);
                saveData();
                TimerRunning = false;

                text_reward.setVisibility(View.VISIBLE);
                text_work.setVisibility(View.INVISIBLE);

                dbMeneger.insertDB(edit_work.getText().toString(), edit_minutes.getText().toString(), Kalender());

                button_timer.setText("Start");
                button_timer.setVisibility(View.INVISIBLE);
                button_reset.setVisibility(View.VISIBLE);
            }


        }.start();

        TimerRunning = true;
        text_reward.setVisibility(View.INVISIBLE);
        button_timer.setText("Pause");
        button_install.setVisibility(View.INVISIBLE);
        button_reset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        count_timer.cancel();

        TimerRunning = false;

        text_reward.setVisibility(View.INVISIBLE);
        button_timer.setText("Start");
        button_reset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        left_count_time = START_MILLIS;
        updateText();

        text_reward.setVisibility(View.INVISIBLE);
        text_work.setVisibility(View.INVISIBLE);
        button_install.setVisibility(View.VISIBLE);
        button_reset.setVisibility(View.INVISIBLE);
        button_timer.setVisibility(View.VISIBLE);
    }

    private void updateText(){
        int minutes = (int) (left_count_time / 1000) / 60;
        int seconds = (int) (left_count_time / 1000) % 60;

        progress_timer.setProgress((int)left_count_time / 1000);
        String timerFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        text_timer.setText(timerFormatted);
    }

    private void first_updateText(){
        int minutes = (int) (START_MILLIS / 1000) / 60;
        int seconds = (int) (START_MILLIS / 1000) % 60;

        String timerFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        text_timer.setText(timerFormatted);
        left_count_time = START_MILLIS;
    }

    private void getTextValue(){
        String work = edit_work.getText().toString();
        String minutes = edit_minutes.getText().toString();

        START_MILLIS = Integer.parseInt(minutes) * 60000;

        text_work.setText(work);
        text_work.setVisibility(View.VISIBLE);
    }
    // Календарное число
    private String Kalender(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        return dateTime;
    }
    // закрытие
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbMeneger.closeDB();
    }




}