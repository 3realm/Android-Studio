package com.example.dictinary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    // MediaPlayer fon;
    Button dictinary, start, rules, on_off, off1;
    SeekBar seekBar;
    AudioManager audioManager;
    boolean value = false;
    boolean flag = true;
    boolean sound_flag = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fon = MediaPlayer.create(this, R.raw.fon);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //SeekBar
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //
        dictinary = findViewById(R.id.dictinary);
        start = findViewById(R.id.start);
        rules = findViewById(R.id.option);
        on_off=findViewById(R.id.btn_on);
        off1=findViewById(R.id.btn_off);
        //
        seekBar.setVisibility(View.INVISIBLE);
        on_off.setVisibility(View.INVISIBLE);
        off1.setVisibility(View.INVISIBLE);

        //SoundStart();
    }

    public void NewActivity1_3(View v){
        Intent intent = new Intent(this, Dictinary.class);
        startActivity(intent);
    }

    public void NewActivity1_2(View v){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void SoundOption(View v){
        if (flag){
            seekBar.setVisibility(View.VISIBLE);
            on_off.setVisibility(View.VISIBLE);
            off1.setVisibility(View.VISIBLE);

            flag = false;
        }
        else{
            seekBar.setVisibility(View.INVISIBLE);
            on_off.setVisibility(View.INVISIBLE);
            off1.setVisibility(View.INVISIBLE);

            flag =true;
        }
    }

    /*public void SoundOn(View v){
        if (value){
            on_off.setText("music off");
            fon.pause();
            value = false;
        }
        else{
            on_off.setText("music on");
            fon.start();
            value = true;
        }
    }*/

    public void Click(View v) {
        Intent i = new Intent(this, MediaService.class);
        if (v.getId()==R.id.btn_on) {
            startService(i);
        }
        else if (v.getId()==R.id.btn_off) {
            stopService(i);
        }
        else {
            stopService(i);
        }
    }
}