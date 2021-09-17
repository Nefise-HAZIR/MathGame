package com.develop.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class Subtraction extends AppCompatActivity {
    TextView score;
    TextView life;
    TextView time;
    TextView questions;
    EditText answer;
    Button ok;
    Button next;

    Random random=new Random();
    int number1;
    int number2;
    int realanswer=0;
    int useranswer;

    int userscore=0;
    int userlife=3;

    CountDownTimer timer;
    private static final long START_TIME_IN_MILIS=60000;
    long time_left_in_milis=START_TIME_IN_MILIS;
    Boolean timer_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);

        score=findViewById(R.id.textViewScore2);
        life=findViewById(R.id.textViewLife2);
        time=findViewById(R.id.textViewTime2);
        questions=findViewById(R.id.textViewQuestions2);
        answer=findViewById(R.id.editTextAnswer2);
        ok=findViewById(R.id.buttonOkey);
        next=findViewById(R.id.buttonNext);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTime();
                useranswer=Integer.valueOf(answer.getText().toString());
                if(useranswer==realanswer){
                    userscore+=10;
                    score.setText(""+userscore);

                    questions.setText("well done,that's true");
                }
                else{
                    userlife-=1;
                    life.setText(""+userlife);
                    questions.setText("Your answer is not correct");
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               answer.setText("");
               resetTime();
               if(userlife<=0){
                   Toast.makeText(Subtraction.this, "Game Over!", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(Subtraction.this,Result.class);
                   intent.putExtra("score",userscore);
                   startActivity(intent);
                   finish();
               }
               else{
                   gameContinue();
               }

            }
        });
    }

    public  void  gameContinue(){
        startTimer();
        number1=random.nextInt(100);
        number2=random.nextInt(100);
        realanswer=number1-number2;

        questions.setText(number1+" - "+number2);

    }
    public  void startTimer(){
        timer=new CountDownTimer(time_left_in_milis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_milis=millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running=false;

                pauseTime();
                resetTime();
                updateText();
                userlife-=1;
                life.setText(""+userlife);
                questions.setText("TIME IS UP!!");

            }
        }.start();
        timer_running=true;
    }
    public void updateText(){
        int second=(int)(time_left_in_milis/1000)%60;
        String time_left=String.format(Locale.getDefault(),"%02d",second);
        time.setText(time_left);
    }
    public  void pauseTime(){
        timer.cancel();
        timer_running=false;
    }
    public  void resetTime(){
        time_left_in_milis=START_TIME_IN_MILIS;
        updateText();
    }
}