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

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

public class Multiplication extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;
    TextView question;
    EditText answer;
    Button ok;
    Button next;

    int userscore=0;
    int userlife=3;

    Random random=new Random();
    int number1;
    int number2;
    int useranswer;
    int realanswer=0;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS=60000;
    long time_left_in_milis=START_TIMER_IN_MILIS;
    boolean timer_running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);

        score=findViewById(R.id.textViewScore3);
        life=findViewById(R.id.textViewLife3);
        time=findViewById(R.id.textViewTime3);
        question=findViewById(R.id.textViewQuestion3);
        answer=findViewById(R.id.editTextAnswer3);
        ok=findViewById(R.id.buttonOk3);
        next=findViewById(R.id.buttonNext3);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                useranswer=Integer.valueOf(answer.getText().toString());

                if(useranswer==realanswer){
                    userscore+=10;
                    score.setText(""+userscore);
                    question.setText("well done,that's true");
                }
                else{
                    userlife-=1;
                    life.setText(""+userlife);
                    question.setText("Your answer is not correct");
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                resetTimer();

                if(userlife<=0){
                    Toast.makeText(Multiplication.this, "Game Over", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Multiplication.this,Result.class);
                    intent.putExtra("score",userscore);
                    startActivity(intent);
                    finish();
                }
                else {
                    gameContinue();
                }

            }
        });

    }
    public void gameContinue(){
        startTimer();
        number1=random.nextInt(100);
        number2=random.nextInt(100);

        realanswer=number1*number2;
        question.setText(number1+" x "+number2);
    }

    public  void startTimer(){
        timer=new CountDownTimer(time_left_in_milis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_milis=millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {
                timer_running=false;
                pauseTimer();
                resetTimer();
                updateTimer();
                userlife-=1;
                life.setText(""+userlife);
                question.setText("TIME IS UP!!");
            }
        }.start();

        timer_running=true;
    }

    public void updateTimer(){
        int second=(int)(time_left_in_milis/1000)%60;
        String time_left=String.format(Locale.getDefault(),"%02d",second);
        time.setText(time_left);
    }
    public void pauseTimer(){
        timer.cancel();
        timer_running=false;
    }
    public void resetTimer(){
        time_left_in_milis=START_TIMER_IN_MILIS;
        updateTimer();
    }

}