package com.example.dictinary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Leve1 extends AppCompatActivity {
    MediaPlayer mediaPlayerTrue, mediaPlayerFalse, mpbook;
    Dialog dialog;
    Button btn_wrong, btn_true, btn_back;
    TextView text_level, text_cards;
    DBMeneger dbMeneger;
    String randomElement;
    public int count = 0;
    final int[] progress = {
            R.id.point1, R.id.point2, R.id.point3, R.id.point4,
            R.id.point5, R.id.point6, R.id.point7
    };


    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leve1);

        text_level = findViewById(R.id.level);
        text_cards = findViewById(R.id.cards_text);

        btn_back = findViewById(R.id.bb);
        btn_wrong = findViewById(R.id.btn_wrong);
        btn_true = findViewById(R.id.btn_true);

        dbMeneger = new DBMeneger(this);

        mpbook = MediaPlayer.create(this, R.raw.book);
        mediaPlayerTrue = MediaPlayer.create(this, R.raw.true_click);
        mediaPlayerFalse =  MediaPlayer.create(this, R.raw.false_click);

        WaitingCard();



        text_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_cards.setText("");
                mpbook.start();
                StartCard();
            }
        });
    }

    public void WrongProgressArr(View view){
        SoundFalse();
        count = count - 1;

        if (count > 0){
            for (int i = 0; i < 6; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < count; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points_change);
            }
            WaitingCard();
        }
        else{
            for (int i = 0; i < 6; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < count; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points_change);
            }
            DialogFalseSchow();
        }
    }

    public void TrueProgressArr(View view){
        SoundTrue();
        count = count + 1;
        if (count < 7){
            for (int i = 0; i < 7; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < count; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points_change);
            }
            WaitingCard();
        }
        else{
            for (int i = 0; i < 7; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < count; i++){
                TextView tv = findViewById(progress[i]);
                tv.setBackgroundResource(R.drawable.style_points_change);
            }
            DialogTrueSchow();
        }
    }

    public void StartCard(){
        btn_wrong.setVisibility(View.VISIBLE);
        btn_true.setVisibility(View.VISIBLE);

        dbMeneger.openDP();

        String randomrussianElement = dbMeneger.getvalueDB2(randomElement).get(0);
        text_cards.setText(randomrussianElement);

        dbMeneger.closeDB();

        text_cards.setEnabled(false);
    }

    private void WaitingCard(){
        text_cards.setText("");

        btn_wrong.setVisibility(View.INVISIBLE);
        btn_true.setVisibility(View.INVISIBLE);

        dbMeneger.openDP();

        Random random = new Random();
        int index = random.nextInt(dbMeneger.getvalueDB().size());
        randomElement = dbMeneger.getvalueDB().get(index);
        text_cards.setText(randomElement);

        dbMeneger.closeDB();

        text_cards.setEnabled(true);
    }

    private void SoundTrue(){
        if (mediaPlayerTrue.isPlaying())
            mediaPlayerTrue.stop();

        //mediaPlayerTrue.seekTo(500);
        mediaPlayerTrue.start();


    }
    private void SoundFalse(){mediaPlayerFalse.start();}

    private void DialogTrueSchow(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView btn_close_true = dialog.findViewById(R.id.btn_close);

        dialog.show();
    }

    private void DialogFalseSchow(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void NewActivityLevel_1(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void NewActivityLevel_1_dialog(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
        dialog.dismiss();
    }

    public void NewActivityLevel_2_dialog(View view){
        Intent intent = new Intent(this, Dictinary.class);
        startActivity(intent);
        finish();
        dialog.dismiss();
    }
}